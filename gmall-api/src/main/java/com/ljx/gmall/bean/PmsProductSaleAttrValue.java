package com.ljx.gmall.bean;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
@Getter
@Setter
public class PmsProductSaleAttrValue implements Serializable {
    @Id
    @Column
    String id;

    @Column
    String productId;

    @Column
    String saleAttrId;

    @Column
    String saleAttrValueName;

    @Transient
    String isChecked;


}
