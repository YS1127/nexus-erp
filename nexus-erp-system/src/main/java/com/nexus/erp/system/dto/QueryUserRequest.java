package com.nexus.erp.system.dto;

public class QueryUserRequest extends PageQueryRequest{

    private String username;

    public QueryUserRequest() {
    }

    public QueryUserRequest(String username) {
        this.username = username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
