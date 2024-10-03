package j2q.core;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import j2q.AppConfig;
import j2q.ApplicationSQLRun;
import j2q.db.datasources.IDataSource;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static j2q.setup.definitions.design.repo.LoadParams.LOAD_TIMEOUT;


public abstract class AbstractJ2<E extends Enum<E>> {
    private @Autowired ApplicationContext context;
    @Getter private IDataSource defaultDataSource;

    @Getter private final Class<E> typeOfSQL;
    @Getter private final Map<E, J2SQL> bufferJ2SQLs = Maps.newConcurrentMap();
    @Getter private final Map<E, String> bufferSQLs = Maps.newConcurrentMap();

    private final Deque<Pair<E, CompletableFuture<J2SQL>>> loadBuffers = new ConcurrentLinkedDeque<>();

    protected AbstractJ2(Class<E> typeOfSQL) { this.typeOfSQL = typeOfSQL; }

    public void addLoader(E typeOfSQL, J2SQL j2SQL) { loadBuffers.add(Pair.of(typeOfSQL, CompletableFuture.supplyAsync(() -> j2SQL))); }

    public J2SQL getJ2SQL(E typeOfSQL) { return bufferJ2SQLs.getOrDefault(typeOfSQL, null); }
    public String getSQL(E typeOfSQL) { return bufferSQLs.getOrDefault(typeOfSQL, null); }

    @PostConstruct
    public void load() {
        this.defaultDataSource = context.getBean(AppConfig.class).getDefaultDataSource();

        long startLoadingTime = System.currentTimeMillis();
        loadBuffers();
        long loadingTime = System.currentTimeMillis() - startLoadingTime;
        System.out.println(this.getClass().getSimpleName() + " loaded in " + loadingTime);
        ApplicationSQLRun.addLoadingTime(loadingTime);
    }

    private void loadBuffers() {
        List<Method> loaders = Arrays.stream(this.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(LoadJ2SQL.class))
                .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(loaders)) {
            loaders.parallelStream().forEach(this::invokeThis);
        }
        if (loadBuffers.isEmpty()) return;

        loadBuffers.parallelStream().forEach(m -> {
            try {
                bufferJ2SQLs.put(m.getKey(), m.getValue().get(LOAD_TIMEOUT, TimeUnit.SECONDS));
                bufferSQLs.put(m.getKey(), bufferJ2SQLs.get(m.getKey()).getSQL());
                loadBuffers.remove(m);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                throw new RuntimeException(e);
            }
        });
    }
    private void invokeThis(Method m) {
        try {
            m.invoke(this);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
