package org.iclass.dao;

import org.apache.ibatis.annotations.Mapper;
import org.iclass.dto.PageRequestDTO;
import org.iclass.dto.TodoDto;

import java.util.List;
@Mapper
public interface TodoMapper {
    String getTime();
    int insert(TodoDto dto);
    List<TodoDto> selectAll();
    TodoDto selectOne(long tno);
    void delete(long tno);
    int update(TodoDto dto);
    List<TodoDto> selectList(PageRequestDTO pageRequestDTO);
    long getCount();
}
