package j2q.db.jdbc;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import j2q.db.datasource.WorkWithDataSource;
import j2q.db.loader.IRowLoader;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Nullable;
import java.sql.*;
import java.util.*;

@Component
@Validated
public class JdbcIO {
    private @Autowired WorkWithDataSource workDataSource;

    public <T> List<T> select(@NotNull IRowLoader<T> rowLoader,
                              @NotNull String query, @Nullable Object... params) throws SQLException {
        try (Connection conn = workDataSource.getDefaultDataSourceProvider().getDS().getConnection();
             final PreparedStatement stmt = conn.prepareStatement(query)) {
            setParamsMain(stmt, params);

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

    public <T> Optional<T> selectOne(@NotNull IRowLoader<T> rowLoader,
                                     @NotNull String query, @Nullable Object... params) throws SQLException {
        try (Connection conn = workDataSource.getDefaultDataSourceProvider().getDS().getConnection();
             final PreparedStatement stmt = conn.prepareStatement(query)) {
            setParamsMain(stmt, params);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.ofNullable(rowLoader.convertResultSet(resultSet));
                }
            }
        }
        return Optional.empty();
    }

    public Optional<Long> selectNumeric(@NotNull String query, @Nullable Object... params) throws SQLException {
        try (Connection conn = workDataSource.getDefaultDataSourceProvider().getDS().getConnection();
             final PreparedStatement stmt = conn.prepareStatement(query)) {
            setParamsMain(stmt, params);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(resultSet.getLong(1));
                }
            }
        }
        return Optional.empty();
    }

    @Transactional
    public List<int[]> addBatchResultList(@NotNull AddBatchInfo... addBatchInfos) throws SQLException {
        final List<int[]> resultBatch = Lists.newArrayList();
        try (Connection conn = workDataSource.getDefaultDataSourceProvider().getDS().getConnection()) {
            if (!workDataSource.getDefaultDataSourceProvider().isAutomaticTransactional()) conn.setAutoCommit(false);
            for (AddBatchInfo addBatchInfo : addBatchInfos) {
                try (final PreparedStatement stmt = conn.prepareStatement(addBatchInfo.query())) {
                    if (CollectionUtils.isNotEmpty(addBatchInfo.params())) {
                        setParams(addBatchInfo, stmt);
                    }
                    resultBatch.add(stmt.executeBatch());
                }
            }
            if (!workDataSource.getDefaultDataSourceProvider().isAutomaticTransactional()) conn.setAutoCommit(true);
        }
        return resultBatch;
    }
    @Transactional
    public List<long[]> addLargeBatchResultList(@NotNull AddBatchInfo... addBatchInfos) throws SQLException {
        final List<long[]> resultBatch = Lists.newArrayList();
        try (Connection conn = workDataSource.getDefaultDataSourceProvider().getDS().getConnection()) {
            if (!workDataSource.getDefaultDataSourceProvider().isAutomaticTransactional()) conn.setAutoCommit(false);
            for (AddBatchInfo addBatchInfo : addBatchInfos) {
                try (final PreparedStatement stmt = conn.prepareStatement(addBatchInfo.query())) {
                    if (CollectionUtils.isNotEmpty(addBatchInfo.params())) {
                        setParams(addBatchInfo, stmt);
                    }
                    resultBatch.add(stmt.executeLargeBatch());
                }
            }
            if (!workDataSource.getDefaultDataSourceProvider().isAutomaticTransactional()) conn.setAutoCommit(true);
        }
        return resultBatch;
    }

    @Transactional
    public int[] addBatchResultArray(@NotNull AddBatchInfo addBatchInfo) throws SQLException {
        try (Connection conn = workDataSource.getDefaultDataSourceProvider().getDS().getConnection();
             final PreparedStatement stmt = conn.prepareStatement(addBatchInfo.query())) {
            if (CollectionUtils.isNotEmpty(addBatchInfo.params())) {
                if (!workDataSource.getDefaultDataSourceProvider().isAutomaticTransactional()) conn.setAutoCommit(false);
                setParams(addBatchInfo, stmt);
            }
            int[] updateCounts = stmt.executeBatch();
            if (!workDataSource.getDefaultDataSourceProvider().isAutomaticTransactional()) conn.setAutoCommit(true);
            return updateCounts;
        }
    }

    @Transactional
    public long[] addLargeBatchResultArray(@NotNull AddBatchInfo addBatchInfo) throws SQLException {
        try (Connection conn = workDataSource.getDefaultDataSourceProvider().getDS().getConnection();
             final PreparedStatement stmt = conn.prepareStatement(addBatchInfo.query())) {
            if (CollectionUtils.isNotEmpty(addBatchInfo.params())) {
                if (!workDataSource.getDefaultDataSourceProvider().isAutomaticTransactional()) conn.setAutoCommit(false);
                setParams(addBatchInfo, stmt);
            }
            long[] updateCounts = stmt.executeLargeBatch();
            if (!workDataSource.getDefaultDataSourceProvider().isAutomaticTransactional()) conn.setAutoCommit(true);
            return updateCounts;
        }
    }

    @Transactional
    public boolean executeQuery(@NotNull String query, @Nullable Object... params) throws SQLException {
        try (Connection conn = workDataSource.getDefaultDataSourceProvider().getDS().getConnection();
             final PreparedStatement stmt = conn.prepareStatement(query)) {
            setParamsMain(stmt, params);
            return stmt.execute();
        }
    }

    @Transactional
    public int executeUpdate(@NotNull String query, @Nullable Object... params) throws SQLException {
        try (Connection conn = workDataSource.getDefaultDataSourceProvider().getDS().getConnection();
             final PreparedStatement stmt = conn.prepareStatement(query)) {
            setParamsMain(stmt, params);
            return stmt.executeUpdate();
        }
    }
    @Transactional
    public long executeLargeUpdate(@NotNull String query, @Nullable Object... params) throws SQLException {
        try (Connection conn = workDataSource.getDefaultDataSourceProvider().getDS().getConnection();
             final PreparedStatement stmt = conn.prepareStatement(query)) {
            setParamsMain(stmt, params);
            return stmt.executeLargeUpdate();
        }
    }

    private void setParams(AddBatchInfo addBatchInfo, PreparedStatement stmt) throws SQLException {
        for (List<Object> params : Objects.requireNonNull(addBatchInfo.params())) {
            setParamsMain(stmt, params.toArray());
            stmt.addBatch();
        }
    }
    private void setParamsMain(PreparedStatement toStmt, Object... params) throws SQLException {
        if (ArrayUtils.isNotEmpty(params)) {
            int i = 1;
            for (Object param : params) {
                if (param == null) throw new SQLException("Null Param not allowed");
                toStmt.setObject(i++, param);
            }
        }
    }
}

