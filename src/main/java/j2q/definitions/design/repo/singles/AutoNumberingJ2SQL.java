package j2q.definitions.design.repo.singles;

import j2q.J2SQL;
import j2q.definitions.design.repo.AbstractJ2;
import j2q.definitions.design.schema.tables.TAutoNumbering;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.*;

import static j2q.J2SQLShared.MAX;

@Component
public class AutoNumberingJ2SQL extends AbstractJ2<AutoNumberingRepo.TypeOfSQL> implements AutoNumberingRepo {
    private @Autowired TAutoNumbering tAutoNumbering;

    protected AutoNumberingJ2SQL() {
        super(TypeOfSQL.class);
    }

    @Override
    @PostConstruct
    public void loadBuffer() {
        Map<TypeOfSQL, CompletableFuture<J2SQL>> loadJ2SQLsAsync = new ConcurrentHashMap<>();
        loadJ2SQLsAsync.put(TypeOfSQL.ALL, getALL());
        loadJ2SQLsAsync.put(TypeOfSQL.SPECIFIC_ENTITY, getSPECIFIC_ENTITY());
        loadJ2SQLsAsync.put(TypeOfSQL.INSERT_ROW, getINSERT_ROW());
        loadJ2SQLsAsync.put(TypeOfSQL.MAX_NUMBER_PER_ENTITY, getMAX_NUMBER_PER_ENTITY());

        super.loadBuffers((Map<Class<TypeOfSQL>, CompletableFuture<J2SQL>>) (Map<?, ?>) loadJ2SQLsAsync);
    }

    @Async private CompletableFuture<J2SQL> getALL() {
        return CompletableFuture.supplyAsync(() -> J2SQL.create(getDefaultDataSource()).from(tAutoNumbering));
    }
    @Async private CompletableFuture<J2SQL> getINSERT_ROW() {
        return CompletableFuture.supplyAsync(() -> J2SQL.create(getDefaultDataSource()).insertInto(tAutoNumbering).insertRow());
    }
    @Async private CompletableFuture<J2SQL> getSPECIFIC_ENTITY() {
        return CompletableFuture.supplyAsync(() -> J2SQL.create(getDefaultDataSource()).from(tAutoNumbering).where(tAutoNumbering.ENTITY_TYPE.eq("?")));
    }
    @Async private CompletableFuture<J2SQL> getMAX_NUMBER_PER_ENTITY() {
        return CompletableFuture.supplyAsync(() -> J2SQL.create(getDefaultDataSource()).from(tAutoNumbering).
                select(tAutoNumbering.ENTITY_TYPE, MAX(tAutoNumbering.ENTITY_NUMBER))
                .groupBy(tAutoNumbering.ENTITY_TYPE)
                .orderBy(tAutoNumbering.ENTITY_TYPE));
    }

}
