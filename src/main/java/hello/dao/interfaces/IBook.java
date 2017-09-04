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


    // 如何注解才能只返回单个对象呢？
    @Select("select * from book where name=#{name}")
    public List<Book> selectByName(@Param("name") String name);

}
