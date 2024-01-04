package j2q.core.sqlRetriever;

public interface IDefaultsSQLRetrieverForDBs {
    String getDefaultSQLStatementForSelect();
    String getDefaultSQLStatementForDelete();
    String getDefaultSQLStatementForUpdate();
    String getDefaultSQLStatementForInsert();
    String getDefaultSQLStatementForInsertGetOnlyValues();

}
