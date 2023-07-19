package com.kkoch.user.api.controller.member.response;

import lombok.Data;

@Data
public class MemberResponse {

    private String email;
    private String name;
    private String tel;
    private String businessNumber;
    private int point;
}
