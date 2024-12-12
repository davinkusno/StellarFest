package model.join;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class JoinFields<T> {

    private final List<Long> ids;
    private final Supplier<List<T>> loader;

    private Function<T, List<T>> adder;
    private Function<T, List<T>> remover;
    private List<T> value = null;

    public JoinFields(List<Long> ids, Supplier<List<T>> loader) {
        this.ids = ids;
        this.loader = loader;
    }

    public JoinFields(List<Long> ids, Supplier<List<T>> loader, Function<T, List<T>> adder, Function<T, List<T>> remover) {
        this.ids = ids;
        this.loader = loader;
        this.adder = adder;
        this.remover = remover;
    }

    public List<Long> getIds() {
        return ids;
    }

    public List<T> getValue() {
        if (value == null) {
            value = loader.get();
        }

        return value;
    }

    public List<T> add(T item) {
        if (adder == null) {
            throw new IllegalStateException("Adder not set");
        }

        value = adder.apply(item);
        value.add(item);

        return value;
    }

    public List<T> remove(T item) {
        if (remover == null) {
            throw new IllegalStateException("Remover not set");
        }

        value = remover.apply(item);
        value.remove(item);

        return value;
    }

}
