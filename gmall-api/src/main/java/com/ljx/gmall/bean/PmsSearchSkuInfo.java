package com.ljx.gmall.bean;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
@Getter
@Setter
public class PmsSearchSkuInfo implements Serializable{

    @Id
    private String id;
    private String skuName;
    private String skuDesc;
    private String catalog3Id;
    private BigDecimal price;
    private String skuDefaultImg;
    private double hotScore;
    private String productId;
    private List<PmsSkuAttrValue> skuAttrValueList;


}
