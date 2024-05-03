package j2q.setup.definitions.design.schema.enums;

import j2q.core.IValueFor;
import lombok.AllArgsConstructor;
import lombok.Getter;

public final class DbFValues {
    @Getter @AllArgsConstructor
    public enum ValuesForEntityType implements IValueFor {
        TEMP_STUCK("E01"), SURROGATE_NUM("E02");
        private final DbF forDbF = DbF.EntityType;
        private final String value;
    }

    @Getter @AllArgsConstructor
    public enum ValuesForOptionType implements IValueFor {
        SYS_PARAM("O01"), FORM_SETTING("O02");
        private final DbF forDbF = DbF.OptionType;
        private final String value;
    }
}
