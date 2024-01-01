package j2sql.definitions.design.repo.singles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public final class UsersSQL {
    @Autowired private UsersJ2SQL j2Users;
    public String getSQL(UsersRepo.TypeOfSQL type) { return j2Users.getSQL(type); }
}
