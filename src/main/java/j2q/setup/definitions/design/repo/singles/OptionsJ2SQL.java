package j2q.setup.definitions.design.repo.singles;

import j2q.core.face.J2SQL;
import j2q.setup.definitions.design.schema.tables.TOptions;
import j2q.core.support.AbstractJ2;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Component
public class OptionsJ2SQL extends AbstractJ2<OptionsRepo.TypeOfSQL> implements OptionsRepo {
    private @Autowired TOptions tOptions;

    protected OptionsJ2SQL() {
        super(TypeOfSQL.class);
    }

    @Async public Pair<TypeOfSQL, CompletableFuture<J2SQL>> getALL() {
        return Pair.of(TypeOfSQL.ALL, CompletableFuture.supplyAsync(() -> J2SQL.create(getDefaultDataSource()).from(tOptions)));
    }
    @Async public Pair<TypeOfSQL, CompletableFuture<J2SQL>> getINSERT_ROW() {
        return Pair.of(TypeOfSQL.INSERT_ROW, CompletableFuture.supplyAsync(() -> J2SQL.create(getDefaultDataSource()).insertInto(tOptions).insertRow()));
    }
    @Async public Pair<TypeOfSQL, CompletableFuture<J2SQL>> getSPECIFIC_OPTION_TYPE() {
        return Pair.of(TypeOfSQL.SPECIFIC_OPTION_TYPE, CompletableFuture.supplyAsync(() -> J2SQL.create(getDefaultDataSource()).from(tOptions).where(tOptions.OPTION_TYPE.eq("?"))));
    }
}
