package j2q.db.jdbc;

import org.jetbrains.annotations.NotNull;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Nullable;
import java.util.List;

@Validated
public record AddBatchInfo(@NotNull String query, @Nullable List<List<Object>> params) {
}
