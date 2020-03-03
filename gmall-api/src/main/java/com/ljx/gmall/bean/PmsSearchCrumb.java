package com.ljx.gmall.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class PmsSearchCrumb implements Serializable {
    private String valueId;
    private String valueName;
    private String urlParam;


}
