package j2q.setup.definitions.design.repo.singles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class AutoNumberingSQL {
    @Autowired private AutoNumberingJ2SQL j2AutoNumbering;
    public String getSQL(AutoNumberingRepo.TypeOfSQL type) { return j2AutoNumbering.getSQL(type); }
}
