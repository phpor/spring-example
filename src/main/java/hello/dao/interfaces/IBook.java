package hello.dao.interfaces;

import hello.dao.entities.Book;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.repository.query.Param;

import java.util.List;

// 参考： http://blog.csdn.net/naruto_mr/article/details/48207437

public interface IBook<T> extends IBase<T> {
    @Insert("insert into book values(#{id}, #{name}, #{price}, #{isbn})")
    public int insert(@Param("book") Book book);

    @Update("update book set price=#{price} where name=#{name}")
    public int updateByName(@Param("book") Book book);

    @Delete("delete from book where name=#{name}")
    public int deleteByName(@Param("book") Book book);

    @Select("select * from book where name=#{name}")
    public List<Book> selectByName(@Param("name") String name);

    // 当定义返回值为单个对象时，自然只返回单个对象，如果结果集为多条记录则抛异常
    @Select("select * from book where id=#{id}")
    public Book selectById(@Param("id") String id);

}
