package j2q.core.sqlCreator.sqlResolvers.sqlFilters;

public sealed interface IWhere permits AbstractFilter {
    enum TypeOfWhere {
        WhereValue (null),
        WhereInValues("IN"),
        WhereBetween ("BETWEEN"),
        WhereLike ("LIKE"),

        WhereInSubSelect ("IN"),
        WhereExist ("EXISTS"),
        ;

        private final String putClause;
        TypeOfWhere(String putClause) {
            this.putClause = putClause;
        }
        String getPutClause() { return this.putClause; }
    }

    IWhere and(IWhere attachFilter);
    IWhere or(IWhere attachFilter);
    IWhere getAttachedFilters();
}
