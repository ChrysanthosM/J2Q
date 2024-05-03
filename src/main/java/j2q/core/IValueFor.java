package j2q.core;

import j2q.setup.definitions.design.schema.enums.DbF;

public interface IValueFor {
    DbF getForDbF();
    String getValue();
}
