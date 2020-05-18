package dao;

import java.util.List;

public interface CrudInterface<T> {

    public void create(T obj);

    public void update(T obj);

    public void delete(T obj);

    public T findByName(String name);

    public T findById(int id);

    public List<T> findAll();
}