package j2sql.core.sqlRetriever;

public interface IDefaultsSQLRetrieverForDBs {
    String getDefaultSQLStatementForSelect();
    String getDefaultSQLStatementForDelete();
    String getDefaultSQLStatementForUpdate();
    String getDefaultSQLStatementForInsert();
    String getDefaultSQLStatementForInsertGetOnlyValues();

}
