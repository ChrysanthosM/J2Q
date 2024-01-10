package j2q.core.linSQL;

import j2q.commons.CommonMethods;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;

public final class LinSQLCommons {
    public final static String QUOTE =  "'";
    public final static String ASTERISK =  "*";

    private final static String UNDERSCORE =  "_";

    // Functions
    public static String applyAsAlias(@Nonnull String toResult, String applyAlias, boolean inParenthesis, boolean inQuotes) {
        if (StringUtils.isBlank(toResult)) return toResult;
        if (inQuotes) toResult = CommonMethods.stringsConcat(false, QUOTE, toResult.replaceAll(QUOTE, StringUtils.EMPTY), QUOTE);
        if (StringUtils.isBlank(applyAlias)) return toResult;
        return CommonMethods.stringsConcat(inParenthesis, toResult, asAliasMain(applyAlias));
    }
    public static String asAliasMain(@Nonnull String applyAlias) {
        if (StringUtils.isBlank(applyAlias)) return StringUtils.EMPTY;
        applyAlias = fixAsAliasName(applyAlias);
        return CommonMethods.stringsConcat(false, " AS ", "\"", applyAlias.trim(), "\"");
    }
    public static String fixAsAliasName(@Nonnull String applyAlias) { return applyAlias.replaceAll(StringUtils.SPACE, UNDERSCORE); }


}
