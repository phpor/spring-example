package hello.cmds;


import hello.dao.entities.Book;
import hello.dao.interfaces.IBook;
import hello.interfaces.ICmd;
import hello.utils.StringUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class BookCmd implements ICmd{

    @Autowired
    private SqlSession sqlSession;


    @Override
    public void run(String[] args) {

        IBook dbBook = sqlSession.getMapper(IBook.class);
        Book book = new Book("N1234563", "西游记6", StringUtil.get32UUID(), 10);
        dbBook.insert(book);
        sqlSession.commit();

        book = new Book();
        book.setName("西游记");
        List<Book> books = dbBook.selectByName("西游记");
        int price = 0;
        for(Book book2:books) {
            System.out.printf("%s\n", book2.getName());
            price = book2.getPrice();
        }
        book = new Book();
        book.setName("西游记");
        book.setPrice(price + 1);
        dbBook.updateByName(book);
        sqlSession.commit();

        book = (Book)dbBook.selectByName(book.getName()).get(0);
        System.out.printf("price: %d\n", book.getPrice());

        book = new Book();
        book.setName("西游记6");
        dbBook.deleteByName(book);
        sqlSession.commit();
    }
}
