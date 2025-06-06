package j2q.commons;

import com.google.common.base.Joiner;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;

@UtilityClass
public class CommonMethods {
    public static String stringsConcat(boolean encloseInParenthesis, @Nonnull String... args) {
        StringBuilder concatString = new StringBuilder();
        if (encloseInParenthesis) concatString.append("(");
        concatString.append(Joiner.on(StringUtils.EMPTY).join(args));
        if (encloseInParenthesis) concatString.append(")");
        return concatString.toString();
    }

    public static String splitCamelCase(@Nonnull String input) {
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
