package com.jonyshare.server.mapper.my;

import com.jonyshare.server.dto.CourseDto;
import com.jonyshare.server.dto.CoursePageDto;
import com.jonyshare.server.dto.SortDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author WangQiang
 * @date 2020/10/30-14:23
 */
public interface MyCourseMapper {
    int  updateTime(@Param("courseId") String courseId);

    void updateSort(SortDto sortDto);

    void moveSortsForward(SortDto sortDto);

    void moveSortsBackward(SortDto sortDto);

    List<CourseDto> list(@Param("pageDto") CoursePageDto pageDto);
}
