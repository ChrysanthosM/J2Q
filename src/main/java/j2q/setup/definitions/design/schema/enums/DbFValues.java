package j2q.setup.definitions.design.schema.enums;

import com.google.common.collect.ImmutableMap;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DbFValues {
    private static final Map<DbF, List<String>> bufferValues = new ImmutableMap.Builder<DbF, List<String>>()
            .put(DbF.EntityType, Arrays.stream(ValuesForEntityType.values()).map(ValuesForEntityType::getValue).collect(Collectors.toList()))
            .put(DbF.OptionType, Arrays.stream(ValuesForOptionType.values()).map(ValuesForOptionType::getValue).collect(Collectors.toList()))
            .build();

    public static List<String> getValues(@Nonnull DbF forField) {
        return bufferValues.getOrDefault(forField, null);
    }

    public interface IValueFor {
        String getValue();
    }

    public enum ValuesForEntityType implements IValueFor {
        TEMP_STUCK("E01"), SURROGATE_NUM("E02");
        private final String value;
        ValuesForEntityType(String value) {
            this.value = value;
        }
        @Override public String getValue() {
            return this.value;
        }
    }

    public enum ValuesForOptionType implements IValueFor {
        SYS_PARAM("O01"), FORM_SETTING("O02");
        private final String value;
        ValuesForOptionType(String value) {
            this.value = value;
        }
        @Override public String getValue() {
            return this.value;
        }
    }
}
