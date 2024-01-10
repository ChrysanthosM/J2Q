package j2q.core.sqlCreator.sqlBuilder;

sealed interface IBuildSQLCore permits BuildSQLCore {
    String getStringForSQL();
    void setStringForSQL(String setString);
}
