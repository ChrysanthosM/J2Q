package j2q.setup.definitions.design.repo.singles;

import j2q.core.support.IRepo;

public interface UsersRepo extends IRepo {
    enum TypeOfSQL {
        ALL,
        INSERT_ROW,

    }
}
