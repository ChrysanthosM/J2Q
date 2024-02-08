package j2q.core.creator.resolvers;

import j2q.core.j2sql.J2SQLShared;
import org.apache.commons.lang3.tuple.MutablePair;

import javax.swing.*;

public interface IDeployOrdering {
    default MutablePair<Object, SortOrder> asc() { return J2SQLShared.asc(this); }
    default MutablePair<Object, SortOrder> desc() { return J2SQLShared.desc(this); }
}
