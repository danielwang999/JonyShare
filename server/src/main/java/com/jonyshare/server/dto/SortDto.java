package com.jonyshare.server.dto;

/**
 * @author WangQiang
 * @date 2020/11/24-19:27
 */
public class SortDto {
    private String id;
    private int oldSort;
    private int newSort;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOldSort() {
        return oldSort;
    }

    public void setOldSort(int oldSort) {
        this.oldSort = oldSort;
    }

    public int getNewSort() {
        return newSort;
    }

    public void setNewSort(int newSort) {
        this.newSort = newSort;
    }

    @Override
    public String toString() {
        return "SortDto{" +
                "id='" + id + '\'' +
                ", oldSort=" + oldSort +
                ", newSort=" + newSort +
                '}';
    }
}
