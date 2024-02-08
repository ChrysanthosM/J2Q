package j2q.core.retrievers;

import j2q.core.linsql.LinSQL;
import org.apache.commons.lang3.tuple.Pair;

import javax.swing.*;
import java.math.BigInteger;
import java.util.Map;

sealed interface IDeployMethods permits SQLRetrieverCore {
    String getNullWord();
    Map<LinSQL.TypeOfComparison, String> getComparisonType();
    Map<SortOrder, String> getOrderByType();
    Map<LinSQL.TypeOfJoin, String> getJoinType();
    String createComments(String comments);

    String getLimitOffsetForSQL(Pair<BigInteger, BigInteger> setLimitOffset);
    String getOffsetFetchForSQL(Pair<BigInteger, BigInteger> setOffsetFetch);
}
