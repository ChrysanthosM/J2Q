package j2sql.commons;

import com.google.common.base.Joiner;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CommonMethods {
    public static Object getKeyOr(@Nullable MutablePair fromMutablePair, Object defaultIfNull) {
        if (fromMutablePair == null) return defaultIfNull;
        return fromMutablePair.getKey();
    }
    public static Object getValueOr(@Nullable MutablePair fromMutablePair, Object defaultIfNull) {
        if (fromMutablePair == null) return defaultIfNull;
        return fromMutablePair.getValue();
    }

    public static String stringsConcat(boolean encloseInParenthesis, @Nonnull String... args) {
        StringBuilder concatString = new StringBuilder();
        if (encloseInParenthesis) concatString.append("(");
        concatString.append(Joiner.on(StringUtils.EMPTY).join(args));
        if (encloseInParenthesis) concatString.append(")");
        return concatString.toString();
    }

    public static String splitCamelCase(String input) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);

            // Insert space before uppercase letters that follow a lowercase letter
            if (i > 0 && Character.isUpperCase(currentChar) && Character.isLowerCase(input.charAt(i - 1))) {
                result.append(StringUtils.SPACE);
            }
            result.append(currentChar);
        }
        return result.toString();
    }
}
