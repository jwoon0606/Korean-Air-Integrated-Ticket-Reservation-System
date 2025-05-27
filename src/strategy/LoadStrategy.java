package strategy;

import java.util.List;

public interface LoadStrategy<T> {
    List<T> load();
}
