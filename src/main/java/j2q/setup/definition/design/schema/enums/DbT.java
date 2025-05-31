package j2q.setup.definition.design.schema.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor @Getter
public enum DbT {
    Users("Sys_Users", "AC", List.of(DbF.RecID), false, false),
    AutoNumbering("Sys_AutoNumbering", "AA", List.of(DbF.RecID), true, false),
    Options("Sys_Options", "AB", List.of(DbF.RecID), true, true),
    ;

    private final String systemName;
    private final String tablePrefixForFields;
    private final List<DbF> hasKeys;
    private final Boolean autoIncrease;
    private final Boolean putAutoStamp;
}
