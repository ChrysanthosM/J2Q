package j2q.core;

import j2q.commons.CommonMethods;
import com.google.common.base.Joiner;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

final class BuildSQLUnionWith extends BuildSQLCore {
    static BuildSQLUnionWith createFor(SQLRetrieverForDBs forSQLRetrieverForDB) { return new BuildSQLUnionWith(forSQLRetrieverForDB); }
    private BuildSQLUnionWith(SQLRetrieverForDBs forSQLRetrieverForDB) {
        List<LinSQL> unionWithLinSQLS = forSQLRetrieverForDB.getWorkLInSQLBuilderParams().getUnionWithQueries();
        if (CollectionUtils.isEmpty(unionWithLinSQLS)) return;

        List<String> unionWithQueries = unionWithLinSQLS.stream()
                .filter(Objects::nonNull)
                .map(LinSQL::getSQL)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(unionWithQueries)) return;

        super.setStringForSQL(CommonMethods.stringsConcat(false, " UNION ", Joiner.on(" UNION ").join(unionWithQueries)));
    }
}
