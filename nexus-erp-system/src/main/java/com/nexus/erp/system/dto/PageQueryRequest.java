package com.nexus.erp.system.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public abstract class PageQueryRequest {

    @Min(1)
    @Max(100)
    private long pageSize = 10;   // 默认值保留，但可被请求覆盖
    @Min(1)
    private long pageNum = 1;

    public PageQueryRequest() {
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public void setPageNum(long pageNum) {
        this.pageNum = pageNum;
    }

    public long getPageSize() {
        return pageSize;
    }

    public long getPageNum() {
        return pageNum;
    }
}
