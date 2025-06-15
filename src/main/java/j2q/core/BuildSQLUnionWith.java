package j2q.core;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Objects;

final class BuildSQLUnionWith extends BuildSQLCore {
    static BuildSQLUnionWith createFor(SQLRetrieverForDBs forSQLRetrieverForDB) { return new BuildSQLUnionWith(forSQLRetrieverForDB); }
    private BuildSQLUnionWith(SQLRetrieverForDBs forSQLRetrieverForDB) {
        List<LinSQL> unionWithLinSQLS = forSQLRetrieverForDB.getWorkLInSQLBuilderParams().getUnionWithQueries();
        if (CollectionUtils.isEmpty(unionWithLinSQLS)) return;

        List<String> unionWithQueries = unionWithLinSQLS.stream()
                .filter(Objects::nonNull)
                .map(LinSQL::getSQL)
                .toList();
        if (CollectionUtils.isEmpty(unionWithQueries)) return;

        super.setStringForSQL(" UNION " + String.join(" UNION ", unionWithQueries));
    }
}
