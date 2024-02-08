package j2q.core.creator.resolvers.filters;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import j2q.core.creator.LInSQLBuilderShared;
import j2q.core.retrievers.SQLRetrieverForDBs;
import org.apache.commons.collections4.CollectionUtils;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

import static j2q.commons.CommonMethods.stringsConcat;

public final class InValuesWhere extends AbstractWhere {
    @Override TypeOfWhere getTypeOfWhere() { return TypeOfWhere.WhereInValues; }

    private final List<Object> inValues;

    public InValuesWhere(@Nonnull Object whereObject, @Nonnull List<Object> inValues) {
        super(whereObject);
        this.inValues = inValues;
    }

    @Override
    public String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB) {
        StringBuilder returnValue = new StringBuilder(super.whereObjectForSQL(forSQLRetrieverForDB));
        if (CollectionUtils.isNotEmpty(this.inValues)) {
            List<String> newInValues = Lists.newArrayList();
            this.inValues.stream().filter(Objects::nonNull).forEach(o -> newInValues.add(LInSQLBuilderShared.getSqlUserSelection(o, super.getDataTypeForSQL()).getResolveObjectForSQL(forSQLRetrieverForDB)));
            String valuesJoined = stringsConcat(true, Joiner.on(", ").join(newInValues));
            returnValue.append(valuesJoined);
        }
        returnValue.append(super.resolveAttachedFilters(forSQLRetrieverForDB));
        return returnValue.append(super.resolveParenthesisRight()).toString();
    }
}
