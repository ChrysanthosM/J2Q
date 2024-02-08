package j2q.core.creator.sqlbuilder;

import j2q.commons.CommonMethods;
import j2q.core.linsql.LinSQL;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import j2q.core.retrievers.SQLRetrieverForDBs;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Objects;

public final class BuildSQLUnionWith extends BuildSQLCore {
    public static BuildSQLUnionWith createFor(SQLRetrieverForDBs forSQLRetrieverForDB) { return new BuildSQLUnionWith(forSQLRetrieverForDB); }
    private BuildSQLUnionWith(SQLRetrieverForDBs forSQLRetrieverForDB) {
        List<LinSQL> unionWithLinSQLS = forSQLRetrieverForDB.getWorkLInSQLBuilderParams().getUnionWithQueries();
        if (CollectionUtils.isEmpty(unionWithLinSQLS)) return;

        List<String> unionWithQueries = Lists.newArrayList();
        unionWithLinSQLS.stream().filter(Objects::nonNull).forEach(u -> unionWithQueries.add(u.getSQL()));
        if (CollectionUtils.isEmpty(unionWithQueries)) return;

        super.setStringForSQL(CommonMethods.stringsConcat(false, " UNION ", Joiner.on(" UNION ").join(unionWithQueries)));
    }
}
