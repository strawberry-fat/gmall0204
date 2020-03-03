package com.ljx.gmall.bean;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
@Getter
@Setter
public class PmsSkuAttrValue implements Serializable {

    @Id
    @Column
    String id;

    @Column
    String attrId;

    @Column
    String valueId;

    @Column
    String skuId;

}
