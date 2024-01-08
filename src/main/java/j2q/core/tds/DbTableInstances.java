package j2q.core.tds;

import j2q.setup.definitions.design.schema.enums.GlobalTablesDefinition;
import com.google.common.collect.ImmutableList;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public final class DbTableInstances {
    private static final ConcurrentHashMap<GlobalTablesDefinition.DbT, DbTable> mapTableInstances = new ConcurrentHashMap<>();
    private final List<IDbTable> implementations;

    @Autowired
    public DbTableInstances(List<IDbTable> implementations) {
        this.implementations = ImmutableList.copyOf(implementations);
    }
    @PostConstruct
    public void init() {
        this.implementations.parallelStream().forEach(dbT -> mapTableInstances.put(dbT.getDbT(), (DbTable) dbT));
    }


    public static DbTable getMapTableInstance(GlobalTablesDefinition.DbT forDbT) {
        return mapTableInstances.getOrDefault(forDbT, null);
    }
}
