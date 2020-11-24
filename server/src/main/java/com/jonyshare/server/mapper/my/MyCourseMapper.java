package com.jonyshare.server.mapper.my;

import com.jonyshare.server.dto.SortDto;
import org.apache.ibatis.annotations.Param;

/**
 * @author WangQiang
 * @date 2020/10/30-14:23
 */
public interface MyCourseMapper {
    int  updateTime(@Param("courseId") String courseId);

    void updateSort(SortDto sortDto);

    void moveSortsForward(SortDto sortDto);

    void moveSortsBackward(SortDto sortDto);
}
