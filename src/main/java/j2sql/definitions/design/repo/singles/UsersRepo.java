package j2sql.definitions.design.repo.singles;

import j2sql.definitions.design.repo.IRepo;

public interface UsersRepo extends IRepo {
    enum TypeOfSQL {
        ALL,
        INSERT_ROW,

    }
}
