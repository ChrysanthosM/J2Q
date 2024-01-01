package j2sql.definitions.design.repo.singles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public final class AutoNumberingSQL {
    @Autowired private AutoNumberingJ2SQL j2AutoNumbering;
    public String getSQL(AutoNumberingRepo.TypeOfSQL type) { return j2AutoNumbering.getSQL(type); }
}
