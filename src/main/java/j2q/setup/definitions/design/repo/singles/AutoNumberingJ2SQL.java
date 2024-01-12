package j2q.setup.definitions.design.repo.singles;

import j2q.core.face.J2SQL;
import j2q.core.support.AbstractJ2;
import j2q.setup.definitions.design.schema.tables.TAutoNumbering;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

import static j2q.core.face.J2SQLShared.MAX;

@Component
public class AutoNumberingJ2SQL extends AbstractJ2<AutoNumberingRepo.TypeOfSQL> implements AutoNumberingRepo {
    @Autowired private TAutoNumbering tAutoNumbering;

    protected AutoNumberingJ2SQL() {
        super(TypeOfSQL.class);
    }

    @Async public Pair<TypeOfSQL, CompletableFuture<J2SQL>> getALL() {
        return Pair.of(TypeOfSQL.ALL, CompletableFuture.supplyAsync(() -> J2SQL.create(getDefaultDataSource()).from(tAutoNumbering)));
    }
    @Async public Pair<TypeOfSQL, CompletableFuture<J2SQL>> getINSERT_ROW() {
        return Pair.of(TypeOfSQL.INSERT_ROW, CompletableFuture.supplyAsync(() -> J2SQL.create(getDefaultDataSource()).insertInto(tAutoNumbering).insertRow()));
    }
    @Async public Pair<TypeOfSQL, CompletableFuture<J2SQL>> getSPECIFIC_ENTITY() {
        return Pair.of(TypeOfSQL.SPECIFIC_ENTITY, CompletableFuture.supplyAsync(() -> J2SQL.create(getDefaultDataSource()).from(tAutoNumbering).where(tAutoNumbering.ENTITY_TYPE.eq("?"))));
    }
    @Async public Pair<TypeOfSQL, CompletableFuture<J2SQL>> getMAX_NUMBER_PER_ENTITY() {
        return Pair.of(TypeOfSQL.MAX_NUMBER_PER_ENTITY, CompletableFuture.supplyAsync(() -> J2SQL.create(getDefaultDataSource()).from(tAutoNumbering).
                select(tAutoNumbering.ENTITY_TYPE, MAX(tAutoNumbering.ENTITY_NUMBER))
                .groupBy(tAutoNumbering.ENTITY_TYPE)
                .orderBy(tAutoNumbering.ENTITY_TYPE)));
    }
}
