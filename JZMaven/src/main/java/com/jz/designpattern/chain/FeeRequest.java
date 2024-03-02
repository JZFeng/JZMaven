package com.jz.designpattern.chain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor

public class FeeRequest {
    private String user;
    private int fee;

    public int getFee() {
        return fee;
    }

    public String getUser() {
        return user;
    }

}
