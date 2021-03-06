package com.ct.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserVO {
    private String loginName;
    private String userName;
    private String password;
    private String gender;
    private String identityCode;
    private String email;
    private String mobile;

}
