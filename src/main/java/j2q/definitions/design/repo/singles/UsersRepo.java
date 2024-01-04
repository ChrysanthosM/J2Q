package j2q.definitions.design.repo.singles;

import j2q.definitions.design.repo.IRepo;

public interface UsersRepo extends IRepo {
    enum TypeOfSQL {
        ALL,
        INSERT_ROW,

    }
}
