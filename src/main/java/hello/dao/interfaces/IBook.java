package hello.dao.interfaces;

import hello.dao.entities.Book;
//import org.apache.ibatis.annotations.Delete;
//import org.apache.ibatis.annotations.Insert;
//import org.apache.ibatis.annotations.Select;
//import org.apache.ibatis.annotations.Update;
import org.springframework.data.repository.query.Param;

import java.util.List;

// 参考： http://blog.csdn.net/naruto_mr/article/details/48207437
// xml sql配置和注解sql配置是不能同时存在的，会冲突

public interface IBook<T> extends IBase<T> {

    //@Insert("insert into book values(#{id}, #{name}, #{price}, #{isbn})")
    public int insert(@Param("book") Book book);

    //@Update("update book set price=#{price} where name=#{name}")
    public int updateByName(@Param("book") Book book);

    //@Delete("delete from book where name=#{name}")
    public int deleteByName(@Param("book") Book book);

    //@Select("select * from book where name=#{name}")
    public List<Book> selectByName(String name); // xml配置sql时， @Param("name") 是可以没有的，哪怕参数名和sql中的名字不一致也没关系

    // 当定义返回值为单个对象时，自然只返回单个对象，如果结果集为多条记录则抛异常
    //@Select("select * from book where id=#{id}")
    public Book selectById(@Param("id") String id);

}
