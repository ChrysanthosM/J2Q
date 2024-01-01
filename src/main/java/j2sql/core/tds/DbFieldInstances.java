package j2sql.core.tds;

import j2sql.definitions.design.schema.enums.GlobalFieldsDefinition;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Scope("singleton")
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
