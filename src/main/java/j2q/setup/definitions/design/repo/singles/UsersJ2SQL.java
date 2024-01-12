package j2q.setup.definitions.design.repo.singles;

import j2q.core.face.J2SQL;
import j2q.setup.definitions.design.schema.tables.TUsers;
import j2q.core.support.AbstractJ2;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class UsersJ2SQL extends AbstractJ2<UsersRepo.TypeOfSQL> implements UsersRepo {
    @Autowired private TUsers tUsers;

    protected UsersJ2SQL() {
        super(UsersRepo.TypeOfSQL.class);
    }

    @Async public Pair<TypeOfSQL, CompletableFuture<J2SQL>> getALL() {
        return Pair.of(TypeOfSQL.ALL, CompletableFuture.supplyAsync(() -> J2SQL.create(getDefaultDataSource()).from(tUsers)));
    }
    @Async public Pair<TypeOfSQL, CompletableFuture<J2SQL>> getINSERT_ROW() {
        return Pair.of(TypeOfSQL.INSERT_ROW, CompletableFuture.supplyAsync(() -> J2SQL.create(getDefaultDataSource()).insertInto(tUsers).insertRow()));
    }
}
