package j2q.setup.definitions.design.repo.singles;

import j2q.core.face.J2SQL;
import j2q.setup.definitions.design.schema.tables.TUsers;
import j2q.core.support.AbstractJ2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsersJ2SQL extends AbstractJ2<UsersRepo.TypeOfSQL> implements UsersRepo {
    @Autowired private TUsers tUsers;

    protected UsersJ2SQL() {
        super(UsersRepo.TypeOfSQL.class);
    }

    public void loadALL() {
        addLoader(TypeOfSQL.ALL, J2SQL.create(getDefaultDataSource()).from(tUsers));
    }
    public void loadINSERT_ROW() {
        addLoader(TypeOfSQL.INSERT_ROW, J2SQL.create(getDefaultDataSource()).insertInto(tUsers).insertRow());
    }
}
