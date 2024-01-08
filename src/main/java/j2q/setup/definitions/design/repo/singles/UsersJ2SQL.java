package j2q.setup.definitions.design.repo.singles;

import j2q.j2sql.J2SQL;
import j2q.setup.definitions.design.schema.tables.TUsers;
import j2q.core.support.AbstractJ2;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UsersJ2SQL extends AbstractJ2<UsersRepo.TypeOfSQL> implements UsersRepo {
    private @Autowired TUsers tUsers;

    protected UsersJ2SQL() {
        super(UsersRepo.TypeOfSQL.class);
    }

    @Override
    @PostConstruct
    public void loadBuffer() {
        Map<TypeOfSQL, CompletableFuture<J2SQL>> loadJ2SQLsAsync = new ConcurrentHashMap<>() ;
        loadJ2SQLsAsync.put(TypeOfSQL.ALL, getALL());
        loadJ2SQLsAsync.put(TypeOfSQL.INSERT_ROW, getINSERT_ROW());

        super.loadBuffers((Map<Class<TypeOfSQL>, CompletableFuture<J2SQL>>) (Map<?, ?>) loadJ2SQLsAsync);
    }

    @Async private CompletableFuture<J2SQL> getALL() {
        return CompletableFuture.supplyAsync(() -> J2SQL.create(getDefaultDataSource()).from(tUsers));
    }
    @Async private CompletableFuture<J2SQL> getINSERT_ROW() {
        return CompletableFuture.supplyAsync(() -> J2SQL.create(getDefaultDataSource()).insertInto(tUsers).insertRow());
    }


}
