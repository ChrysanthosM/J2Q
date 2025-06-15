package j2q.commons;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;

@UtilityClass
public class CommonMethods {

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
