package j2q.core;

import j2q.setup.definitions.design.schema.enums.DbT;
import com.google.common.collect.ImmutableList;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public final class DbTableInstances {
    private static final ConcurrentHashMap<DbT, DbTable> mapTableInstances = new ConcurrentHashMap<>();
    private final List<IDbTable> implementations;

    @Autowired
    private DbTableInstances(List<IDbTable> implementations) {
        this.implementations = ImmutableList.copyOf(implementations);
    }
    @PostConstruct
    public void init() {
        this.implementations.parallelStream().forEach(dbT -> mapTableInstances.put(((DbTable) dbT).getDbT(), (DbTable) dbT));
    }


    static DbTable getMapTableInstance(DbT forDbT) {
        return mapTableInstances.getOrDefault(forDbT, null);
    }
}
