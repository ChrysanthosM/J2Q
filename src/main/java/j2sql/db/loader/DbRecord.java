package j2sql.db.loader;

import lombok.Getter;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

@ToString
public final class DbRecord {
    @Getter private final int recordSeq;
    private final Map<Integer, Object> fieldIndexValue = new ConcurrentHashMap<>();
    private final Map<String, Integer> fieldNameIndex = new ConcurrentHashMap<>();

    public DbRecord(int recordSeq, List<Pair<String, Object>> columnNamesValues) {
        this.recordSeq = recordSeq;
        if (CollectionUtils.isNotEmpty(columnNamesValues)) {
            IntStream.range(0, columnNamesValues.size()).parallel().forEach(i -> {
                fieldIndexValue.put(i, columnNamesValues.get(i).getValue());
                fieldNameIndex.put(columnNamesValues.get(i).getKey(), i);
            });
        }
    }

    public <T> T getValue(String forFieldName) {
        if (!fieldNameIndex.containsKey(forFieldName)) return null;
        return (T) fieldIndexValue.getOrDefault(fieldNameIndex.get(forFieldName), null);
    }
}
