package com.jonyshare.server.dto;


public class CourseCategoryDto {

    /**
     * id
     */
    private String id;

    /**
     * 课程|course.id
     */
    private String courseId;

    /**
     * 分类|course.id
     */
    private String categoryId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", courseId=").append(courseId);
        sb.append(", categoryId=").append(categoryId);
        sb.append("]");
        return sb.toString();
    }

}