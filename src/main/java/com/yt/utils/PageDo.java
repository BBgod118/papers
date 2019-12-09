package com.yt.utils;

import java.util.List;

/**
 * 分页工具类
 *
 * @author yt
 * @date 2019/4/30 - 8:52
 */
public class PageDo<T> {
    /**
     * 当前页，从请求那边传过来的
     */
    private Integer pageNum;
    /**
     * 每页显示的数据条
     */
    private Integer pageSize;
    /**
     * 总的记录条数，查询数据库得到的数据
     */
    private Integer totalRecord;
    /**
     * 总页数，通过totalRexord和pageSize计算可以得来
     */
    private Integer totalPage;
    /**
     * 将每页需要显示的数据放在list集合中
     */
    private List<T> datas;
    /**
     * 开始索引，也就是我们在数据集中要从第几行数据开始拿
     */
    private Integer startIndex;

    public PageDo(Integer pageNum, Integer pageSize, Integer totalRecord) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalRecord = totalRecord;

        //totalPage总页数
        if (totalRecord % pageSize == 0) {
            //如果能整除，没有多余一页要显示余下的数据
            this.totalPage = totalRecord / pageSize;
        } else {
            //不能整除时则多需要一行来显示余下的数据
            this.totalPage = totalRecord / pageSize + 1;
        }

        //开始索引，表示从哪一行开始取，当前页乘每页显示数量
        this.startIndex = (pageNum - 1) * pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(Integer totalRecord) {
        this.totalRecord = totalRecord;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }


    @Override
    public String toString() {
        return "PageDo{" + "pageNum=" + pageNum + ", pageSize=" + pageSize + ", totalRecord=" + totalRecord + ", totalPage=" + totalPage + ", datas=" + datas + ", startIndex=" + startIndex + '}';
    }
}
