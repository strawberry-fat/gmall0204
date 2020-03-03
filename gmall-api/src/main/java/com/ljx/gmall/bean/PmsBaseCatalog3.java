package com.ljx.gmall.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 平台三级分类，实体类
 */
@Getter
@Setter
public class PmsBaseCatalog3 implements Serializable {
    private String id;
    private String name;
    private String catalog2Id;


}
