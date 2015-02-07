### Release 0.1.0

- Added support for select queries
- Added support for insert queries
- Added support for update queries
- Added support for delete queries
- Added support for join clause on select queries
- Added support for group by clause on select queries
- Added support for order by clause on select queries
- Added support for limit clause on select queries
- Added support for basic types (string, integer, long, float, double, boolean, date, time, datetime)
- Added support for basic conditions in where and on clauses (=, !=, >, >=, <, <=, in, not in, and, or)
- Added `count` function
- Added `year` function
- Added `sum` function


### Release 0.2.0

- Refactored implementation of queries; impl.* package is no longer available, nor is support for OO building of queries (e.g. `select.setWhere(new Where())`)
- Added `now` function
- Insert queries now return the generated keys of the type specified (or default to integers)
- Added experimental support for simplified paginated select queries
- Added support for transactions
