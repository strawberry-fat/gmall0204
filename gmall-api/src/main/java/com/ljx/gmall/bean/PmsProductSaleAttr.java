package com.ljx.gmall.bean;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;
@Getter
@Setter
public class PmsProductSaleAttr implements Serializable {

    @Id
    @Column
    String id;

    @Column
    String productId;

    @Column
    String saleAttrId;

    @Column
    String saleAttrName;


    @Transient
    List<PmsProductSaleAttrValue> spuSaleAttrValueList;


}
