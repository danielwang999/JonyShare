package com.jonyshare.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jonyshare.server.domain.Course;
import com.jonyshare.server.domain.CourseContent;
import com.jonyshare.server.domain.CourseExample;
import com.jonyshare.server.dto.*;
import com.jonyshare.server.enums.CourseStatusEnum;
import com.jonyshare.server.mapper.CourseContentMapper;
import com.jonyshare.server.mapper.CourseMapper;
import com.jonyshare.server.mapper.my.MyCourseMapper;
import com.jonyshare.server.util.CopyUtil;
import com.jonyshare.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CourseService {
    private static final Logger LOG = LoggerFactory.getLogger(CourseService.class);

    @Resource
    private CourseMapper courseMapper;
    @Resource
    private MyCourseMapper myCourseMapper;
    @Resource
    private CourseContentMapper courseContentMapper;
    @Resource
    private CourseCategoryService courseCategoryService;

    @Resource
    private TeacherService teacherService;
    @Resource
    private ChapterService chapterService;
    @Resource
    private SectionService sectionService;



    /**
     * 列表查询
     */
    public void list(CoursePageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        List<CourseDto> courseDtoList = myCourseMapper.list(pageDto);
        PageInfo<CourseDto> pageInfo = new PageInfo<>(courseDtoList);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(courseDtoList);
    }

    /**
     * 新课列表查询，只查询已发布的，按创建日期倒序
     */
    public List<CourseDto> listNew(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        CourseExample courseExample = new CourseExample();
        courseExample.createCriteria().andStatusEqualTo(CourseStatusEnum.PUBLISH.getCode());
        courseExample.setOrderByClause("created_at desc");
        List<Course> courseList = courseMapper.selectByExample(courseExample);
        return CopyUtil.copyList(courseList, CourseDto.class);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @Transactional
    public void save(CourseDto courseDto) {
        Course course = CopyUtil.copy(courseDto, Course.class);
        if (StringUtils.isEmpty(courseDto.getId())) {
            this.insert(course);
        } else {
            this.update(course);
        }

        // 批量保存分类
        courseCategoryService.saveBatch(course.getId(), courseDto.getCategorys());
    }

    /**
     * 新增
     */
    private void insert(Course course) {
        Date now = new Date();
        course.setCreatedAt(now);
        course.setUpdatedAt(now);
        course.setId(UuidUtil.getShortUuid());
        courseMapper.insert(course);
    }

    /**
     * 更新
     */
    private void update(Course course) {
        course.setUpdatedAt(new Date());
        courseMapper.updateByPrimaryKey(course);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        courseMapper.deleteByPrimaryKey(id);
    }

    public void updateTime(String courseId) {
        LOG.info("更新课程时长：{}", courseId);
        myCourseMapper.updateTime(courseId);
    }

    /**
     * 查找课程介绍内容
     * @param courseId
     * @return
     */
    public CourseContentDto findContent(String courseId) {
        CourseContent courseContent = courseContentMapper.selectByPrimaryKey(courseId);
        if (courseContent == null) {
            return null;
        }
        return CopyUtil.copy(courseContent, CourseContentDto.class);
    }

    /**
     * 保存课程内容，包含新增和修改
     * @param courseContentDto
     * @return
     */
    public int saveCourseContent (CourseContentDto courseContentDto) {
        CourseContent courseContent = CopyUtil.copy(courseContentDto, CourseContent.class);
        int i = courseContentMapper.updateByPrimaryKeyWithBLOBs(courseContent);
        // 如果发现更新到的行数为0，在选择插入
        if (i == 0) {
            i = courseContentMapper.insert(courseContent);
        }
        return i;
    }

    /**
     * 排序
     * @param sortDto
     */
    @Transactional
    public void sort(SortDto sortDto) {
        // 修改当前记录的排序值
        myCourseMapper.updateSort(sortDto);

        // 如果排序值变大
        if (sortDto.getNewSort() > sortDto.getOldSort()) {
            myCourseMapper.moveSortsForward(sortDto);
        }

        // 如果排序值变小
        if (sortDto.getNewSort() < sortDto.getOldSort()) {
            myCourseMapper.moveSortsBackward(sortDto);
        }
    }

    /**
     * 查找某一课程，供web模块用，只能查已发布的
     * 包含大章，小节，讲师信息
     * @param id
     * @return
     */
    public CourseDto findCourse(String id) {
        Course course = courseMapper.selectByPrimaryKey(id);
        if (course == null || !CourseStatusEnum.PUBLISH.getCode().equals(course.getStatus())) {
            return null;
        }
        CourseDto courseDto = CopyUtil.copy(course, CourseDto.class);

        // 查询内容
        CourseContent content = courseContentMapper.selectByPrimaryKey(id);
        if (content != null) {
            courseDto.setContent(content.getContent());
        }

        // 查找讲师信息
        TeacherDto teacherDto = teacherService.findById(courseDto.getTeacherId());
        courseDto.setTeacher(teacherDto);

        // 查找章信息
        List<ChapterDto> chapterDtoList = chapterService.listByCourse(id);
        courseDto.setChapters(chapterDtoList);

        // 查找节信息
        List<SectionDto> sectionDtoList = sectionService.listByCourse(id);
        courseDto.setSections(sectionDtoList);

        return courseDto;
    }
}
