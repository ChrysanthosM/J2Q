package j2q.db.datasource;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WorkWithDataSource {
    @Value("${datasource.type}")
    private String datasourceType;

    @AllArgsConstructor
    @Getter
    public enum DataSourceType {
        SQLITE ("sqlite", null, "$.", true),
        MSSQL ("mssql", null, null, false),
        DB2_AS400 ("db2i", null, "$.", true),
        ;
        private final String propertyName;
        private final String attributeExtension;
        private final String tablePrefixToReplace;
        private final Boolean tableMustPrefixFields;

        public static DataSourceType getByPropertyName(String propertyName) {
            for (DataSourceType type : values()) {
                if (type.propertyName.equalsIgnoreCase(propertyName)) return type;
            }
            throw new IllegalArgumentException("No enum constant with property name " + propertyName);
        }
    }

    @Getter
    private DataSourceType defaultDataSourceType;
    @Getter
    private DataSourceProvider defaultDataSourceProvider;

    private @Autowired DataSourceResolver dataSourceResolver;

    @PostConstruct
    private void init() {
        defaultDataSourceType = DataSourceType.getByPropertyName(datasourceType);
        defaultDataSourceProvider = dataSourceResolver.getDefaultDataSource(defaultDataSourceType);
    }

}
