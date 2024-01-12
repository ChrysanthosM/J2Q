package j2q.core.support;

import com.google.common.collect.Lists;
import j2q.AppConfig;
import j2q.ApplicationSQLRun;
import j2q.core.face.J2SQL;
import j2q.db.datasources.IDataSource;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;


public abstract class AbstractJ2<E extends Enum<E>> {
    private final int LOAD_TIMEOUT = 10;

    @Autowired private ApplicationContext context;
    @Getter private IDataSource defaultDataSource;

    @Getter private final Class<E> typeOfSQL;
    @Getter private final Map<E, J2SQL> bufferJ2SQLs = new ConcurrentHashMap<>();
    @Getter private final Map<E, String> bufferSQLs = new ConcurrentHashMap<>();

    protected AbstractJ2(Class<E> typeOfSQL) { this.typeOfSQL = typeOfSQL; }

    public J2SQL getJ2SQL(E typeOfSQL) { return bufferJ2SQLs.getOrDefault(typeOfSQL, null); }
    public String getSQL(E typeOfSQL) { return bufferSQLs.getOrDefault(typeOfSQL, null); }

    @PostConstruct
    public void startLoading() {
        this.defaultDataSource = context.getBean(AppConfig.class).getDefaultDataSource();
        loadBuffers();
    }

    private void loadBuffers() {
        long startLoadingTime = System.currentTimeMillis();

        List<Method> methods = getMethodsToLoad();
        if (CollectionUtils.isNotEmpty(methods)) {
            methods.parallelStream().forEach(m -> {
                try {
                    Pair<E, CompletableFuture<J2SQL>> result = (Pair<E, CompletableFuture<J2SQL>>) m.invoke(this);
                    bufferJ2SQLs.put(result.getKey(), result.getValue().get(LOAD_TIMEOUT, TimeUnit.SECONDS));
                    bufferSQLs.put(result.getKey(), bufferJ2SQLs.get(result.getKey()).getSQL());
                } catch (IllegalAccessException | InvocationTargetException | InterruptedException | ExecutionException | TimeoutException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        long loadingTime = System.currentTimeMillis() - startLoadingTime;
        System.out.println(this.getClass().getSimpleName() + " loaded in " + loadingTime);
        ApplicationSQLRun.addLoadingTime(loadingTime);
    }

    private List<Method> getMethodsToLoad() {
        List<Method> methodsWithCompletableFuture = Lists.newArrayList();

        Method[] methods = this.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getGenericReturnType() instanceof ParameterizedType type) {
                if (type.getRawType() == Pair.class) {
                    Type[] typeArguments = type.getActualTypeArguments();
                    if (typeArguments.length == 2 &&
                            typeArguments[0].getTypeName().endsWith(this.typeOfSQL.getSimpleName()) &&
                            typeArguments[1] instanceof ParameterizedType secondType) {
                        if (secondType.getRawType() == CompletableFuture.class &&
                                secondType.getActualTypeArguments().length == 1 &&
                                secondType.getActualTypeArguments()[0].getTypeName().endsWith(J2SQL.class.getSimpleName())) {
                            methodsWithCompletableFuture.add(method);
                        }
                    }
                }
            }
        }
        return methodsWithCompletableFuture;
    }
}
