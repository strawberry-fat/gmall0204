package com.ljx.gmall.bean;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @param
 * @return
 */
@Getter
@Setter
public class PmsBaseAttrInfo implements Serializable {
    //主键返回策略
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    private String id;
    @Column
    private String attrName;
    @Column
    private String catalog3Id;
    @Column
    private String isEnabled;
    @Transient
    List<PmsBaseAttrValue> attrValueList;


}
