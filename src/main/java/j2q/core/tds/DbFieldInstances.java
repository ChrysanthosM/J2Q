package j2q.core.tds;

import j2q.setup.definitions.design.schema.enums.GlobalFieldsDefinition;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

@Component
public final class DbFieldInstances {
    private static final ConcurrentHashMap<GlobalFieldsDefinition.DbF, DbField> mapFieldInstances = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        Arrays.stream(GlobalFieldsDefinition.DbF.values()).parallel().forEach(f -> mapFieldInstances.put(f, new DbField(f)));
    }

    public static DbField getMapTableInstance(GlobalFieldsDefinition.DbF forDbF) {
        return mapFieldInstances.getOrDefault(forDbF, null);
    }
}
