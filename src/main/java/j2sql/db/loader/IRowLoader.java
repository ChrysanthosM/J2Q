package j2sql.db.loader;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IRowLoader<T> {
    T convertResultSet(ResultSet resultSet) throws SQLException;
    T convertDbRecord(DbRecord dbRecord);
}
