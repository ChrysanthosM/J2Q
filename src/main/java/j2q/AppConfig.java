package j2q;

import j2q.core.DbFieldInstances;
import j2q.core.DbTableInstances;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    private @Autowired DbFieldInstances dbFieldInstances;
    private @Autowired DbTableInstances dbTableInstances;
}
