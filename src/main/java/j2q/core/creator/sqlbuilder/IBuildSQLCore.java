package j2q.core.creator.sqlbuilder;

sealed interface IBuildSQLCore permits BuildSQLCore {
    String getStringForSQL();
    void setStringForSQL(String setString);
}
