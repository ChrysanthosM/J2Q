package j2q.setup.definitions.design.repo.singles;

import j2q.core.j2sql.J2SQL;
import j2q.core.bridge.LoadJ2SQL;
import j2q.setup.definitions.design.schema.tables.TUsers;
import j2q.core.bridge.AbstractJ2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class UsersJ2SQL extends AbstractJ2<UsersRepo.TypeOfSQL> implements UsersRepo {
    private @Autowired TUsers tUsers;

    private UsersJ2SQL() {
        super(UsersRepo.TypeOfSQL.class);
    }

    @LoadJ2SQL public void loadALL() {
        addLoader(TypeOfSQL.ALL, J2SQL.create(getDefaultDataSource()).from(tUsers));
    }
    @LoadJ2SQL public void loadINSERT_ROW() {
        addLoader(TypeOfSQL.INSERT_ROW, J2SQL.create(getDefaultDataSource()).insertInto(tUsers).insertRow());
    }
}
