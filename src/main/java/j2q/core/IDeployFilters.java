package j2q.core;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public interface IDeployFilters {
    default IWhere lessThan(@Nullable Object compareValue) { return lt(compareValue); }
    default IWhere lt(@Nullable Object compareValue) { return J2SQLShared.Filter.whereValue(this, LinSQL.TypeOfComparison.LT, compareValue); }
    default IWhere notLessThan(@Nullable Object compareValue) { return notLt(compareValue); }
    default IWhere notLt(@Nullable Object compareValue) { return J2SQLShared.not(J2SQLShared.Filter.whereValue(this, LinSQL.TypeOfComparison.LT, compareValue)); }

    default IWhere lessEqual(@Nullable Object compareValue) { return le(compareValue); }
    default IWhere le(@Nullable Object compareValue) { return J2SQLShared.Filter.whereValue(this, LinSQL.TypeOfComparison.LE, compareValue); }
    default IWhere notLessEqual(@Nullable Object compareValue) { return notLe(compareValue); }
    default IWhere notLe(@Nullable Object compareValue) { return J2SQLShared.not(J2SQLShared.Filter.whereValue(this, LinSQL.TypeOfComparison.LE, compareValue)); }

    default IWhere equal(@Nullable Object compareValue) { return eq(compareValue); }
    default IWhere eq(@Nullable Object compareValue) { return J2SQLShared.Filter.whereValue(this, LinSQL.TypeOfComparison.EQ, compareValue); }
    default IWhere notEqual(@Nullable Object compareValue) { return ne(compareValue); }
    default IWhere ne(@Nullable Object compareValue) { return J2SQLShared.not(J2SQLShared.Filter.whereValue(this, LinSQL.TypeOfComparison.EQ, compareValue)); }

    default IWhere greaterEqual(@Nullable Object compareValue) { return ge(compareValue); }
    default IWhere ge(@Nullable Object compareValue) { return J2SQLShared.Filter.whereValue(this, LinSQL.TypeOfComparison.GE, compareValue); }
    default IWhere notGreaterEqual(@Nullable Object compareValue) { return notGe(compareValue); }
    default IWhere notGe(@Nullable Object compareValue) { return J2SQLShared.not(J2SQLShared.Filter.whereValue(this, LinSQL.TypeOfComparison.GE, compareValue)); }

    default IWhere greaterThan(@Nullable Object compareValue) { return gt(compareValue); }
    default IWhere gt(@Nullable Object compareValue) { return J2SQLShared.Filter.whereValue(this, LinSQL.TypeOfComparison.GT, compareValue); }
    default IWhere notGreaterThan(@Nullable Object compareValue) { return notGt(compareValue); }
    default IWhere notGt(@Nullable Object compareValue) { return J2SQLShared.not(J2SQLShared.Filter.whereValue(this, LinSQL.TypeOfComparison.GT, compareValue)); }

    default IWhere in(@Nonnull Object... inValues) { return in(Arrays.asList(inValues)); }
    default IWhere in(@Nonnull List<Object> inValues) { return J2SQLShared.Filter.whereInValues(this, inValues); }
    default IWhere notIn(@Nonnull Object... inValues) { return notIn(Arrays.asList(inValues)); }
    default IWhere notIn(@Nonnull List<Object> inValues) { return J2SQLShared.not(J2SQLShared.Filter.whereInValues(this, inValues)); }

    default IWhere inSubSelect(@Nonnull String inSubSelect) { return J2SQLShared.Filter.whereInSubSelect(this, inSubSelect); }
    default IWhere notInSubSelect(@Nonnull String inSubSelect) { return J2SQLShared.not(J2SQLShared.Filter.whereInSubSelect(this, inSubSelect)); }

    default IWhere between(@Nonnull Object fromObject, @Nonnull Object toObject) { return J2SQLShared.Filter.whereBetweenValues(this, fromObject, toObject); }
    default IWhere notBetween(@Nonnull Object fromObject, @Nonnull Object toObject) { return J2SQLShared.not(J2SQLShared.Filter.whereBetweenValues(this, fromObject, toObject)); }

    default IWhere like(@Nullable String compareValue) { return like(compareValue, null, null); }
    default IWhere like(@Nullable String compareValue, @Nullable String escapeLeft, @Nullable String escapeRight) { return J2SQLShared.Filter.whereLikeValue(this, compareValue, escapeLeft, escapeRight); }
    default IWhere notLike(@Nullable String compareValue) { return notLike(compareValue, null, null); }
    default IWhere notLike(@Nullable String compareValue, @Nullable String escapeLeft, @Nullable String escapeRight) { return J2SQLShared.not(J2SQLShared.Filter.whereLikeValue(this, compareValue, escapeLeft, escapeRight)); }

}
