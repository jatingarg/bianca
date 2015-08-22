### Release 0.1.0

- Added support for select queries
- Added support for insert queries
- Added support for update queries (#2)
- Added support for delete queries (#1)
- Added support for `join` clause on select queries
- Added support for `group by` clause on select queries
- Added support for `order by` clause on select queries
- Added support for `limit` clause on select queries
- Added support for basic types (string, integer, long, float, double, boolean, date, time, datetime)
- Added support for basic conditions in `where` and `on` clauses (`=`, `!=`, `>`, `>=`, `<`, `<=`, `in`, `not in`, `and`, `or`)
- Added `count` function (#3)
- Added `year` function (#9)
- Added `sum` function (#11)


### Release 0.2.0

- Refactored implementation of queries; impl.* package is no longer available, nor is support for OO building of queries (e.g. `select.setWhere(new Where())`)
- Insert queries are now able to return the generated keys of the type specified, as well as simply inserted row count
- Added experimental support for simplified paginated select queries
- Added support for `between` and `not between` conditions (#7)
- Added support for `having` clause (#4)
- Added support for transactions (#6)
- Added support to make any class a partial by implementing the `Partiable` interface (#14)
- Added support for distinct select (#13)
- Added support for distinct count (#12)
- Added various date functions (`month`, `now`) (#9)
- Added various mathematical operations (`+`, `-`, `*`, `/`, `%`) (#8)
- Added various aggregate functions (`avg`, `min`, `max`) (#10)
- Added `abs` function (#22)
- Added `if` function (#15)
