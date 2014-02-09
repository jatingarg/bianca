# The power of SQL in the syntax of Java

Bianca is a lovely little library that makes it really easy to write type-safe object oriented SQL queries in Java. Here is a simple example demonstrating her ease of use.

_**Note:** Bianca is designed for and requires Java 8!_

#### Step 1: Write a class that defines the schema

_Future updates will provide utility classes that can automate this process, and to configure the names of tables and columns in an external resource file._

```java
import com.github.yuthura.bianca.schema.*;

public class Schema {
	public final static class Products {
		public final static SchemaTable table = new SchemaTable("products");
		public final static SchemaColumn<Integer> id = table.addColumn("id", Type.INTEGER);
		public final static SchemaColumn<String> name = table.addColumn("name", Type.STRING);
		public final static SchemaColumn<Double> price = table.addColumn("price", Type.DOUBLE);
		public final static SchemaColumn<Boolean> onSale = table.addColumn("on_sale", Type.BOOLEAN);
		public final static SchemaColumn<Integer> categoryID = table.addColumn("category_id", Type.INTEGER);
	}

	public final static class Categories {
		public final static SchemaTable table = new SchemaTable("categories");
		public final static SchemaColumn<Integer> id = table.addColumn("id", Type.INTEGER);
		public final static SchemaColumn<String> name = table.addColumn("name", Type.STRING);
	}
}
```

#### Step 2: Use your newly defined schema

_The example below seems large due to comments and formatting, scroll down to view a more compacted version of the same code._

```java
import java.util.List;
import java.util.LinkedList;

// This import is not needed in this class if you define your own connection factory.
import com.github.yuthura.bianca.JDBCConnectionFactory;

// This import provides all condition and function helper methods and will often be all you need in BL classes.
import static com.github.yuthura.bianca.Helpers.*;

public class Application {
	public static void main(String[] args) {
		// Forces MySQL driver to be loaded
		JDBCConnectionFactory.mysql();

		// The default JDBCConnectionFactory class does no connection pooling,
		// and closes the connection after every query.
		// Future updates will include an implementation that uses https://github.com/swaldman/c3p0.
		ConnectionFactory cf = new JDBCConnectionFactory(
			"jdbc:mysql://localhost:3306/database",
			"username",
			"password"
		);

		List<Product> products =

		// Define the columns for your select clause.
		select(
			Products.id,
			Products.name,
			Products.price,
			Products.onSale,
			Categories.id,
			Categories.name

		// Define the table for your select clause.
		// If all selection columns originate from the same table *object*, you don't need this statement.
		).from(
			Products.table

		// Define a left join; rightJoin and innerJoin are also available, plus join (alias for innerJoin).
		// First argument is the table to join on.
		// Subsequent varargs are the conditions to join on.
		).leftJoin(
			Categories.table,
			eq(Products.categoryID, Categories.id)

		// Define your where conditions.
		).where(
			or(
				gte(Products.price, 10.0),
				eq(Products.onSale, true)
			)

		// Map the result to objects (forEach, mapFirst and forFirst are also available).
		).mapEach(
			cf,
			new LinkedList<Product>(),

			// Designed with Java 8 in mind (argument must be a java.util.function.Consumer<Result>).
			result -> {
				// Future updates will provide utility classes to do this binding automatically.
				return new Product(
					result.get(Products.id),
					result.get(Products.name),
					result.get(Products.price),
					result.get(Products.onSale),
					// You probably want some caching for categories instead.
					new Category(
						result.get(Categories.id),
						result.get(Categories.name)
					)
				); // Return value is automatically added to the collection by mapEach().
			}
		);
	}
}
```

_Same example in compacted form:_

```java
import java.util.List;
import java.util.LinkedList;
import com.github.yuthura.bianca.JDBCConnectionFactory;
import static com.github.yuthura.bianca.Helpers.*;

public class Application {
	public static ConnectionFactory connection() {
		JDBCConnectionFactory.mysql();
		return ConnectionFactory cf = new JDBCConnectionFactory(
			"jdbc:mysql://localhost:3306/database", "username", "password");
	}

	public static void main(String[] args) {
		List<Product> products = new LinkedList<>();

		select(Products.id, Products.name, Products.price, Products.onSale, Categories.id, Categories.name)
		.from(Products.table)
		.leftJoin(Categories.table, eq(Products.categoryID, Categories.id))
		.where(or(gte(Products.price, 10.0), eq(Products.onSale, true)))
		.forEach(connection(), r -> {
			products.add(
				new Product(r.get(Products.id), r.get(Products.name), r.get(Products.price),
					r.get(Products.onSale), new Category(r.get(Categories.id), r.get(Categories.name))));
		});
	}
}
```

# Bianca's philosophy

## Queries impact business logic

Some libraries insist on externalizing your raw SQL queries to resource files, so that they can be modified without recompiling the entire application. This is a bad idea; changing a query almost always impacts the business logic using it, so code modification and recompilation is virtually always a necessity. Keep your queries with the code that uses them, this makes them easier to maintain and debug.

## Queries are hard to write and maintain

When writing raw SQL strings embedded in your code, you often have to result to string concatenation or ugly loops, and above all, most editors don't provide inline syntax highlighting or auto completion for SQL inside your Java strings. We already have a solution for that: don't write SQL, write Java! Especially if you define your schema as fields (see examples above), it becomes trivial to find the right column or table using your IDE's auto completion.

## Type coercion is a pain the ass

Nobody likes type coercion, but it is an evil necessity when using JDBC. Simply setting a NULL value requires you to know the type of the column. Bianca simplifies and automates this wherever she can, and you are always free to write your own implementations of classes to do any coercion yourself.

## Programmers know what their programs do

Meaning libraries should not introduce gotcha's because of intricate hidden magic, so Bianca doesn't. You still have to write all of your queries yourself, but now you can do it in Java and you will never have to write raw SQL strings again (well, not from your Java applications at least).

# Current restrictions

Because Bianca is still in a stage of concept and development, some restrictions currently apply that may make it impractical or impossible to start using now for some developers. Hopefully most of these will be addressed in the near future.

1. MySQL syntax only (however, you can subclass almost everything you need for other drivers)
2. No automatic binding to business logic models
3. Recompilation needed when renaming tables or columns
4. Only select queries available now (insert, update and delete will be added in future releases)
