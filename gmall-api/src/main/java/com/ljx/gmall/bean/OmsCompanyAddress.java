package com.ljx.gmall.bean;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import java.io.Serializable;
@Getter
@Setter
public class OmsCompanyAddress implements Serializable {

    @Id
    private String id;
    private String addressName;
    private int sendStatus;
    private int receiveStatus;
    private String name;
    private String phone;
    private String province;
    private String city;
    private String region;
    private String detailAddress;

}
