package j2q.core;

import j2q.setup.definition.design.schema.sqlite.enums.DbF;

public interface IValueFor {
    DbF getForDbF();
    String getValue();
}
