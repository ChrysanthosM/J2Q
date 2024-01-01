package j2sql.definitions.design.repo.singles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public final class OptionsSQL {
    @Autowired private OptionsJ2SQL j2Options;
    public String getSQL(OptionsRepo.TypeOfSQL type) { return j2Options.getSQL(type); }
}
