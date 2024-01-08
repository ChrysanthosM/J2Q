package j2q.setup.definitions.design.repo.singles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class UsersSQL {
    @Autowired private UsersJ2SQL j2Users;
    public String getSQL(UsersRepo.TypeOfSQL type) { return j2Users.getSQL(type); }
}
