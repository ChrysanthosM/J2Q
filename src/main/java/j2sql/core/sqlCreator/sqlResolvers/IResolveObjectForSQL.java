package j2sql.core.sqlCreator.sqlResolvers;

import j2sql.core.sqlRetriever.SQLRetrieverForDBs;

public interface IResolveObjectForSQL {
    String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB);
}
