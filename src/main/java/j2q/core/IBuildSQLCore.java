package j2q.core;

sealed interface IBuildSQLCore permits BuildSQLCore {
    String getStringForSQL();
    void setStringForSQL(String setString);
}
