package j2q.db.jdbc;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public record AddBatchInfo(@Nonnull String query, @Nullable List<List<Object>> params) {
}
