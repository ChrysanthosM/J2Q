package j2q.core;

sealed interface IDefaultsSQLRetrieverForDBs
        permits IDeploySQLFunctions, IDeploySQLStatements, SQLRetrieverForDBs {
    String getDefaultSQLStatementForSelect();
    String getDefaultSQLStatementForDelete();
    String getDefaultSQLStatementForUpdate();
    String getDefaultSQLStatementForInsert();
    String getDefaultSQLStatementForInsertGetOnlyValues();

}
