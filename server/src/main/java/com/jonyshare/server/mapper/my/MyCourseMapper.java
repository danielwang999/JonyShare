package com.jonyshare.server.mapper.my;

import org.apache.ibatis.annotations.Param;

/**
 * @author WangQiang
 * @date 2020/10/30-14:23
 */
public interface MyCourseMapper {
    int  updateTime(@Param("courseId") String courseId);
}
