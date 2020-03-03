package com.ljx.gmall.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
@Getter
@Setter
public class PmsSearchParam implements Serializable{

    private String catalog3Id;

    private String keyword;

    private List<PmsSkuAttrValue> skuAttrValueList;

    private String[] valueId;


}
