package j2q.setup.definition.design.schema.db2i.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor @Getter
public enum DbT {
    USERS("Sys_Users", "AC", List.of(DbF.REC_ID), false, false),
    AUTO_NUMBERING("Sys_AutoNumbering", "AA", List.of(DbF.REC_ID), true, false),
    OPTIONS("Sys_Options", "AB", List.of(DbF.REC_ID), true, true),
    ;

    private final String systemName;
    private final String tablePrefixForFields;
    private final List<DbF> hasKeys;
    private final Boolean autoIncrease;
    private final Boolean putAutoStamp;
}
