package hello.dao.Mappers;


import hello.dao.entities.Book;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface BookMapper {

    @Select("SELECT * FROM book WHERE id = #{id}")
    Book getById(@Param("id") String id);
}
