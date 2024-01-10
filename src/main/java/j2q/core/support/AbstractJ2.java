package j2q.core.support;

import j2q.AppConfig;
import j2q.ApplicationSQLRun;
import j2q.core.face.J2SQL;
import j2q.db.datasources.IDataSource;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.*;


public abstract class AbstractJ2<E extends Enum<E>> {
    private final int LOAD_TIMEOUT = 10;
    private long startLoadingTime;

    @Autowired private ApplicationContext context;
    @Getter private IDataSource defaultDataSource;

    @Getter private final Class<E> typeOfSQL;
    @Getter private final Map<E, J2SQL> bufferJ2SQLs = new ConcurrentHashMap<>();
    @Getter private final Map<E, String> bufferSQLs = new ConcurrentHashMap<>();

    protected AbstractJ2(Class<E> typeOfSQL) { this.typeOfSQL = typeOfSQL; }

    @PostConstruct
    public void startLoading() {
        startLoadingTime = System.currentTimeMillis();
        this.defaultDataSource = context.getBean(AppConfig.class).getDefaultDataSource();
    }

    public J2SQL getJ2SQL(E typeOfSQL) { return bufferJ2SQLs.getOrDefault(typeOfSQL, null); }
    public String getSQL(E typeOfSQL) { return bufferSQLs.getOrDefault(typeOfSQL, null); }

    protected <E extends Enum<E>> void loadBuffers(Map<Class<E>, CompletableFuture<J2SQL>> loadJ2SQLsAsync) {
        Arrays.stream(typeOfSQL.getEnumConstants()).parallel().forEach(v -> {
            try {
                bufferJ2SQLs.put(v, loadJ2SQLsAsync.get(v).get(LOAD_TIMEOUT, TimeUnit.SECONDS));
                bufferSQLs.put(v, bufferJ2SQLs.get(v).getSQL());
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                throw new RuntimeException(e);
            }
        });

        long loadingTime = System.currentTimeMillis() - startLoadingTime;
        System.out.println(this.getClass().getSimpleName() + " loaded in " + loadingTime);
        ApplicationSQLRun.addLoadingTime(loadingTime);
    }
}
