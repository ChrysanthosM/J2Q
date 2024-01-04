package j2q.definitions.design.repo.singles;

import j2q.j2sql.J2SQL;
import j2q.definitions.design.schema.tables.TOptions;
import j2q.definitions.design.repo.AbstractJ2;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.*;

@Component
public class OptionsJ2SQL extends AbstractJ2<OptionsRepo.TypeOfSQL> implements OptionsRepo {
    private @Autowired TOptions tOptions;

    protected OptionsJ2SQL() {
        super(TypeOfSQL.class);
    }

    @Override
    @PostConstruct
    public void loadBuffer() {
        Map<TypeOfSQL, CompletableFuture<J2SQL>> loadJ2SQLsAsync = new ConcurrentHashMap<>() ;
        loadJ2SQLsAsync.put(TypeOfSQL.ALL, getALL());
        loadJ2SQLsAsync.put(TypeOfSQL.INSERT_ROW, getINSERT_ROW());
        loadJ2SQLsAsync.put(TypeOfSQL.SPECIFIC_OPTION_TYPE, getSPECIFIC_OPTION_TYPE());

        super.loadBuffers((Map<Class<TypeOfSQL>, CompletableFuture<J2SQL>>) (Map<?, ?>) loadJ2SQLsAsync);
    }

    @Async private CompletableFuture<J2SQL> getALL() {
        return CompletableFuture.supplyAsync(() -> J2SQL.create(getDefaultDataSource()).from(tOptions));
    }
    @Async private CompletableFuture<J2SQL> getINSERT_ROW() {
        return CompletableFuture.supplyAsync(() -> J2SQL.create(getDefaultDataSource()).insertInto(tOptions).insertRow());
    }
    @Async private CompletableFuture<J2SQL> getSPECIFIC_OPTION_TYPE() {
        return CompletableFuture.supplyAsync(() -> J2SQL.create(getDefaultDataSource()).from(tOptions).where(tOptions.OPTION_TYPE.eq("?")));
    }


}
