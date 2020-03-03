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
public class PmsProductInfo implements Serializable {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column
    private String productName;

    @Column
    private String description;

    @Column
    private String catalog3Id;

    @Transient
    private List<PmsProductSaleAttr> spuSaleAttrList;
    @Transient
    private List<PmsProductImage> spuImageList;


}


