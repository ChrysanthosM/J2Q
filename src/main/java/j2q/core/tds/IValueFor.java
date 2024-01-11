package j2q.core.tds;

import j2q.setup.definitions.design.schema.enums.DbF;

public interface IValueFor {
    DbF getForDbF();
    String getValue();
}
