package com.hotelsystem.commons;

import java.io.Serializable;
import java.util.List;

public interface GenericServiceAPI<T,ID extends Serializable> {
    public T save(T entity);

    public void delete(ID id);

    public T get(ID id);

    public List<T> getAll();
}
