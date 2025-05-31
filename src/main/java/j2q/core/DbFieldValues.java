package j2q.core;

import j2q.setup.definition.design.schema.enums.DbF;
import j2q.setup.definition.design.schema.enums.DbFValues;
import jakarta.annotation.PostConstruct;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public final class DbFieldValues {
    private static final ConcurrentHashMap<DbF, List<String>> bufferValues = new ConcurrentHashMap<>();
    public static List<String> getValues(@Nonnull DbF forField) {
        return bufferValues.getOrDefault(forField, null);
    }

    @PostConstruct
    public void init() {
        List<Class<?>> dbfWithValues = Arrays.asList(DbFValues.class.getDeclaredClasses());
        if (CollectionUtils.isNotEmpty(dbfWithValues)) {
            dbfWithValues.parallelStream().filter(e -> e.isEnum() && IValueFor.class.isAssignableFrom(e)).forEach(e -> {
                List<?> dbfValuesList = Arrays.asList(e.getEnumConstants());
                if (CollectionUtils.isNotEmpty(dbfValuesList)) {
                    dbfValuesList.parallelStream().forEach(dbf -> bufferValues.put(
                            ((IValueFor) dbf).getForDbF(),
                            dbfValuesList.stream().map(v -> ((IValueFor) v).getValue()).collect(Collectors.toList())));
                }
            });
        }
    }
}
