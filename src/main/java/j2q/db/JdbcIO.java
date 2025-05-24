package j2q.db;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import j2q.db.loader.IRowLoader;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@ThreadSafe
@Component
public class JdbcIO {
    private static List<Pair<String, Object>> getColumnNamesValues(ResultSetMetaData metaData, ResultSet fromResultSet) throws SQLException {
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

    @Beta
    public <T> List<T> selectAsync(@Nonnull DataSource dataSource, @Nonnull IRowLoader<T> rowLoader,
                                   @Nonnull String query, @Nullable Object... params) throws SQLException {
        Preconditions.checkNotNull(dataSource);
        Preconditions.checkNotNull(query);

        try (Connection conn = dataSource.getConnection();
             final PreparedStatement stmt = conn.prepareStatement(query)) {
            setParams(stmt, params);

            try (ResultSet resultSet = stmt.executeQuery()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                List<CompletableFuture<T>> futureTs = Lists.newArrayList();
                while (resultSet.next()) {
                    final List<Pair<String, Object>> columnNamesValues = getColumnNamesValues(metaData, resultSet);
                    CompletableFuture<T> futureT = CompletableFuture.supplyAsync(() -> getConvertedResult(rowLoader, columnNamesValues));
                    futureTs.add(futureT);
                }

                List<T> results = futureTs.parallelStream()
                        .map(CompletableFuture::join)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
                return ImmutableList.copyOf(results);
            }
        }
    }
    private static <T> T getConvertedResult(IRowLoader<T> rowLoader, List<Pair<String, Object>> columnNamesValues) {
        try {
            return rowLoader.convertResultSet(columnNamesValues);
        } catch (Exception e) {
            throw new RuntimeException("Error creating futureT", e);
        }
    }

    public <T> List<T> select(@Nonnull DataSource dataSource, @Nonnull IRowLoader<T> rowLoader,
                              @Nonnull String query, @Nullable Object... params) throws SQLException {
        Preconditions.checkNotNull(dataSource);
        Preconditions.checkNotNull(rowLoader);
        Preconditions.checkNotNull(query);

        try (Connection conn = dataSource.getConnection();
             final PreparedStatement stmt = conn.prepareStatement(query)) {
            setParams(stmt, params);

            try (ResultSet resultSet = stmt.executeQuery()) {
                List<T> returnList = new ArrayList<>(resultSet.getMetaData().getColumnCount());
                while (resultSet.next()) {
                    T pojo = rowLoader.convertResultSet(resultSet);
                    if (pojo != null) returnList.add(pojo);
                }
                return ImmutableList.copyOf(returnList);
            }
        }
    }
    public Optional<Long> selectNumeric(@Nonnull DataSource dataSource,
                                        @Nonnull String query, @Nullable Object... params) throws SQLException {
        Preconditions.checkNotNull(dataSource);
        Preconditions.checkNotNull(query);

        try (Connection conn = dataSource.getConnection();
             final PreparedStatement stmt = conn.prepareStatement(query)) {
            setParams(stmt, params);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(resultSet.getLong(1));
                }
            }
        }
        return Optional.empty();
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
        return Optional.empty();
    }

    @Transactional
    public int[] addBatch(@Nonnull DataSource dataSource,
                          @Nonnull String query, @Nullable List<List<Object>> params) throws SQLException {
        Preconditions.checkNotNull(dataSource);
        Preconditions.checkNotNull(query);

        try (Connection conn = dataSource.getConnection();
             final PreparedStatement stmt = conn.prepareStatement(query)) {
            if (CollectionUtils.isNotEmpty(params)) {
                params.forEach(p -> {
                    try {
                        setParams(stmt, p.toArray());
                        stmt.addBatch();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            return stmt.executeBatch();
        }
    }
    @Transactional
    public long[] addLargeBatch(@Nonnull DataSource dataSource,
                                @Nonnull String query, @Nullable List<List<Object>> params) throws SQLException {
        Preconditions.checkNotNull(dataSource);
        Preconditions.checkNotNull(query);

        try (Connection conn = dataSource.getConnection();
             final PreparedStatement stmt = conn.prepareStatement(query)) {
            if (CollectionUtils.isNotEmpty(params)) {
                params.forEach(p -> {
                    try {
                        setParams(stmt, p.toArray());
                        stmt.addBatch();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            return stmt.executeLargeBatch();
        }
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

