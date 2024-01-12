package j2q.setup.definitions.design.repo.singles;

public interface AutoNumberingRepo {
    enum TypeOfSQL {
        ALL,
        INSERT_ROW,
        SPECIFIC_ENTITY,
        MAX_NUMBER_PER_ENTITY,
    }
}
