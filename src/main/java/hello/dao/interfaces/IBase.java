package hello.dao.interfaces;


public interface IBase<T> {
    T findById(String id);
    void save(T t);
}
