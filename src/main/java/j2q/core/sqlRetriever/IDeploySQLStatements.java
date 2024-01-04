package j2q.core.sqlRetriever;

interface IDeploySQLStatements extends IDefaultsSQLRetrieverForDBs {
    default String getSQLStatementForSelect() { return getDefaultSQLStatementForSelect(); }
    default String getSQLStatementForDelete() { return getDefaultSQLStatementForDelete(); }
    default String getSQLStatementForUpdate() { return getDefaultSQLStatementForUpdate(); }
    default String getSQLStatementForInsert() { return getDefaultSQLStatementForInsert(); }
    default String getSQLStatementForInsertGetOnlyValues() { return getDefaultSQLStatementForInsertGetOnlyValues(); }
}
