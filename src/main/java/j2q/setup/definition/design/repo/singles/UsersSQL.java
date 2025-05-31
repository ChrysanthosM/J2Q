package j2q.setup.definition.design.repo.singles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class UsersSQL {
    private @Autowired UsersJ2SQL j2Users;
    public String getSQL(UsersRepo.TypeOfSQL type) { return j2Users.getSQL(type); }
}
