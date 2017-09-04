package hello.dao.impl;

import hello.dao.entities.Book;
import hello.dao.interfaces.IBook;
import org.apache.ibatis.annotations.Insert;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


public abstract class BookImpl extends BaseImpl<Book> implements IBook<Book> {

    public int insert(Book book) {
        return super.insert(book);
    }

    @Override
    public Book findById(String id) {
        return getById(id);
    }

    @Override
    public void save(Book book) {
        insert(book);
    }
}
