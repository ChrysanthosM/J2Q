package j2q.setup.controllers;

import j2q.setup.definitions.design.repo.singles.AutoNumberingRepo;
import j2q.setup.definitions.design.repo.singles.OptionsSQL;
import j2q.setup.definitions.design.repo.singles.UsersSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import j2q.setup.definitions.design.repo.singles.AutoNumberingSQL;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class RestControllerSQL {
    @Autowired private UsersSQL usersSQL;
    @Autowired private AutoNumberingSQL autoNumberingSQL;
    @Autowired private OptionsSQL optionsSQL;

    //localhost:8080/AutoNumberingSQL?type=ALL
    @GetMapping("/AutoNumberingSQL")
    public String getAutoNumberingSQL(@RequestParam(name = "type") AutoNumberingRepo.TypeOfSQL type) {
        return autoNumberingSQL.getSQL(type);
    }

}
