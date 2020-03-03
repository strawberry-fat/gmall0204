package com.ljx.gmall.bean;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
@Getter
@Setter
public class UmsMember implements Serializable {

    @Id
    private String id;
    private String memberLevelId;
    private String username;
    private String password;
    private String nickname;
    private String phone;
    private int status;
    private Date createTime;
    private String icon;
    private int gender;
    private Date birthday;
    private String city;
    private String job;
    private String personalizedSignature;
    private int sourceType;
    private int integration;
    private int growth;
    private int luckeyCount;
    private int historyIntegration;


}
