package j2q.setup.definitions.design.repo.singles;

import j2q.setup.definitions.design.repo.IRepo;

public interface UsersRepo extends IRepo {
    enum TypeOfSQL {
        LIST,
        INSERT,
    }
}
