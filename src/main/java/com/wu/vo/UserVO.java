package com.wu.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.wu.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

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
