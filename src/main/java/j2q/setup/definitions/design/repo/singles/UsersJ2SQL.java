package j2q.setup.definitions.design.repo.singles;

import j2q.core.J2SQL;
import j2q.core.LoadJ2SQL;
import j2q.setup.definitions.design.schema.tables.TUsers;
import j2q.core.AbstractJ2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class UsersJ2SQL extends AbstractJ2<UsersRepo.TypeOfSQL> implements UsersRepo {
    private @Autowired TUsers tUsers;

    private UsersJ2SQL() {
        super(UsersRepo.TypeOfSQL.class);
    }

    @LoadJ2SQL public void loadList() {
        addLoader(TypeOfSQL.LIST, J2SQL.create(getWorkWithDataSource()).from(tUsers));
    }
    @LoadJ2SQL public void loadInsert() {
        addLoader(TypeOfSQL.INSERT, J2SQL.create(getWorkWithDataSource()).insertInto(tUsers).insertRow());
    }
}
