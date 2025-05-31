package j2q.core;

import j2q.db.jdbc.JdbcIO;
import j2q.setup.definition.dto.Idto;
import lombok.Getter;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract non-sealed class AbstractService<T> implements IBaseDAO<T> {
    @Autowired @Getter private JdbcIO jdbcIO;

    protected static ImmutablePair<String, List<Object>> getBulkInsertQueryAndValues(String query, List<Idto> insertRows, TTable tTable) {
        final String putValuesParams = query.substring(query.indexOf("VALUES ") + "VALUES ".length());
        final String repeatedValues = IntStream.range(0, insertRows.size()).mapToObj(i -> putValuesParams).collect(Collectors.joining(", "));
        final String insertQuery = query.replace(putValuesParams, repeatedValues);
        final List<Object> insertValues = insertRows.stream().flatMap(dto -> dto.getInsertValues(tTable, dto).stream()).toList();
        return ImmutablePair.of(insertQuery, insertValues);
    }
}
