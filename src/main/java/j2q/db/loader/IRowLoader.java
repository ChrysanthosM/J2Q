package j2q.db.loader;

import org.apache.commons.lang3.tuple.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

public sealed interface IRowLoader<T> permits RowLoader {
    T convertResultSet(ResultSet resultSet) throws SQLException;
    T convertResultSet(List<Pair<String, Object>> columnNamesValues) throws NoSuchElementException;
}
