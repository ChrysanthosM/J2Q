package j2q.db;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import j2q.db.loader.DbRecord;
import j2q.db.loader.IRowLoader;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

@ThreadSafe
@Component
public class JdbcIO {
    public Map<Integer, DbRecord> selectRecords(@Nonnull DataSource dataSource,
                                                @Nonnull String query, @Nullable Object... params) throws SQLException {
        Preconditions.checkNotNull(dataSource);
        Preconditions.checkNotNull(query);
        Map<Integer, DbRecord> returnHashMap = new ConcurrentHashMap<>();

        List<CompletableFuture<DbRecord>> futureDbRecords = Lists.newArrayList();
        try (Connection conn = dataSource.getConnection();
             final PreparedStatement stmt = conn.prepareStatement(query)) {
            setParams(stmt, params);

            try (ResultSet resultSet = stmt.executeQuery()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                while (resultSet.next()) {
                    int recordSeq = resultSet.getRow();
                    final List<Pair<String, Object>> columnNamesValues = getColumnNamesValues(metaData, resultSet);
                    CompletableFuture<DbRecord> futureDbRecord = CompletableFuture.supplyAsync(() -> {
                        try {
                            return new DbRecord(recordSeq, columnNamesValues);
                        } catch (Exception e) {
                            throw new RuntimeException("Error creating futureDbRecord", e);
                        }
                    });
                    futureDbRecords.add(futureDbRecord);
                }
                futureDbRecords.parallelStream().forEach(futureRecord -> {
                    DbRecord completedDbRecord = futureRecord.join();
                    returnHashMap.put(completedDbRecord.getRecordSeq(), completedDbRecord);
                });
            }
        }
        return ImmutableMap.copyOf(returnHashMap);
    }
    private List<Pair<String, Object>> getColumnNamesValues(ResultSetMetaData metaData, ResultSet fromResultSet) throws SQLException {
        int columnsCount = metaData.getColumnCount();
        final List<Pair<String, Object>> columnNamesValues = new ArrayList<>(columnsCount);
        IntStream.range(1, columnsCount + 1).forEach(i -> {
            try {
                columnNamesValues.add(Pair.of(metaData.getColumnName(i), fromResultSet.getObject(i)));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        return ImmutableList.copyOf(columnNamesValues);
    }

    public <T> List<T> selectAsync(@Nonnull DataSource dataSource, @Nonnull IRowLoader<T> rowLoader,
                                   @Nonnull String query, @Nullable Object... params) throws SQLException {
        Preconditions.checkNotNull(dataSource);
        Preconditions.checkNotNull(rowLoader);
        Preconditions.checkNotNull(query);

        Map<Integer, DbRecord> dbRecordsHashMap = selectRecords(dataSource, query, params);
        if (dbRecordsHashMap.isEmpty()) return new ArrayList<>();

        List<T> returnList = new CopyOnWriteArrayList<>();
        dbRecordsHashMap.entrySet().parallelStream().forEach(entry -> {
            T pojo = rowLoader.convertDbRecord(entry.getValue());
            if (pojo != null) returnList.add(pojo);
        });

        return ImmutableList.copyOf(returnList);
    }

    public <T> List<T> select(@Nonnull DataSource dataSource, @Nonnull IRowLoader<T> rowLoader,
                              @Nonnull String query, @Nullable Object... params) throws SQLException {
        Preconditions.checkNotNull(dataSource);
        Preconditions.checkNotNull(rowLoader);
        Preconditions.checkNotNull(query);
        List<T> returnList = Lists.newArrayList();

        try (Connection conn = dataSource.getConnection();
             final PreparedStatement stmt = conn.prepareStatement(query)) {
            setParams(stmt, params);

            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    T pojo = rowLoader.convertResultSet(resultSet);
                    if (pojo != null) returnList.add(pojo);
                }
            }
        }
        return ImmutableList.copyOf(returnList);
    }

    public <T> Optional<T> selectOne(@Nonnull DataSource dataSource, @Nonnull IRowLoader<T> rowLoader,
                                     @Nonnull String query, @Nullable Object... params) throws SQLException {
        Preconditions.checkNotNull(dataSource);
        Preconditions.checkNotNull(rowLoader);
        Preconditions.checkNotNull(query);

        try (Connection conn = dataSource.getConnection();
             final PreparedStatement stmt = conn.prepareStatement(query)) {
            setParams(stmt, params);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.ofNullable(rowLoader.convertResultSet(resultSet));
                }
            }
        }
        return Optional.empty() ;
    }

    public boolean executeQuery(@Nonnull DataSource dataSource,
                                @Nonnull String query, @Nullable Object... params) throws SQLException {
        Preconditions.checkNotNull(dataSource);
        Preconditions.checkNotNull(query);

        try (Connection conn = dataSource.getConnection();
             final PreparedStatement stmt = conn.prepareStatement(query)) {
            setParams(stmt, params);
            return stmt.execute();
        }
    }
    public int executeUpdate(@Nonnull DataSource dataSource,
                             @Nonnull String query, @Nullable Object... params) throws SQLException {
        Preconditions.checkNotNull(dataSource);
        Preconditions.checkNotNull(query);

        try (Connection conn = dataSource.getConnection();
             final PreparedStatement stmt = conn.prepareStatement(query)) {
            setParams(stmt, params);
            return stmt.executeUpdate();
        }
    }
    public long executeLargeUpdate(@Nonnull DataSource dataSource,
                                   @Nonnull String query, @Nullable Object... params) throws SQLException {
        Preconditions.checkNotNull(dataSource);
        Preconditions.checkNotNull(query);

        try (Connection conn = dataSource.getConnection();
             final PreparedStatement stmt = conn.prepareStatement(query)) {
            setParams(stmt, params);
            return stmt.executeLargeUpdate();
        }
    }

    private void setParams(PreparedStatement toStmt, Object... params) throws SQLException {
        if (params != null) {
            if (Arrays.stream(params).anyMatch(Objects::isNull)) throw new SQLException("Null Param not allowed");
            int i = 1;
            for (Object param : params) {
                toStmt.setObject(i++, param);
            }
        }
    }
}

