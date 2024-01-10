package j2q.controllers;

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


//
//    private String checkResult(String stmt, String shouldBe) {
//        return Objects.equals(StringUtils.trimToEmpty(stmt), shouldBe) + " - " + stmt;
//    }
//
//    //localhost:8080/Test
//    @GetMapping("/Test")
//    public String getTest() {
//        List<String> stmts = Lists.newArrayList();
//
//        stmts.add(checkResult(
//                optionsSQL.getSQL(OptionsRepo.TypeOfSQL.INSERT_ROW),
//                "INSERT INTO $.Sys_Options (Sys_OptionType, Sys_OptionName, Sys_OptionValue, Sys_OptionDetails, Sys_UserStamp, Sys_DateStamp) " +
//                        "VALUES (?, ?, ?, ?, ChrysanthosM, 2023-12-16 12:18:53.611)"));
//        stmts.add(checkResult(
//                usersSQL.getSQL(UsersRepo.TypeOfSQL.INSERT_ROW),
//                "INSERT INTO $.Sys_Users (Sys_RecID, Sys_UserName, Sys_Password) VALUES (?, ?, ?)"));
//        stmts.add(checkResult(
//                autoNumberingSQL.getSQL(AutoNumberingRepo.TypeOfSQL.INSERT_ROW),
//                "INSERT INTO $.Sys_AutoNumbering (Sys_EntityType, Sys_EntityNumber) VALUES (?, ?)"));
//
//        stmts.add(checkResult(
//                autoNumberingSQL.getSQL(AutoNumberingRepo.TypeOfSQL.SPECIFIC_ENTITY),
//                "SELECT Sys_RecID, Sys_EntityType, Sys_EntityNumber FROM $.Sys_AutoNumbering  WHERE (Sys_EntityType = ?)"));
//
//        stmts.add(checkResult(
//                autoNumberingSQL.getSQL(AutoNumberingRepo.TypeOfSQL.MAX_NUMBER_PER_ENTITY),
//                "SELECT Sys_EntityType, MAX(Sys_EntityNumber) FROM $.Sys_AutoNumbering  GROUP BY Sys_EntityType ORDER BY Sys_EntityType ASC"));
//
//
//
//        stmts.forEach(System.out::println);
//        return "ΟΚ";
//    }

}
