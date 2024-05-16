package j2q.core;

import j2q.commons.CommonMethods;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Objects;

final class BuildSQLUnionWith extends BuildSQLCore {
    static BuildSQLUnionWith createFor(SQLRetrieverForDBs forSQLRetrieverForDB) { return new BuildSQLUnionWith(forSQLRetrieverForDB); }
    private BuildSQLUnionWith(SQLRetrieverForDBs forSQLRetrieverForDB) {
        List<LinSQL> unionWithLinSQLS = forSQLRetrieverForDB.getWorkLInSQLBuilderParams().getUnionWithQueries();
        if (CollectionUtils.isEmpty(unionWithLinSQLS)) return;

        List<String> unionWithQueries = Lists.newArrayList();
        unionWithLinSQLS.stream().filter(Objects::nonNull).forEach(u -> unionWithQueries.add(u.getSQL()));
        if (CollectionUtils.isEmpty(unionWithQueries)) return;

        super.setStringForSQL(CommonMethods.stringsConcat(false, " UNION ", Joiner.on(" UNION ").join(unionWithQueries)));
    }
}
