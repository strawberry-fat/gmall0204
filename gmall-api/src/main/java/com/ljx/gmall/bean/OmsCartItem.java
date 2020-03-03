package com.ljx.gmall.bean;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Getter
@Setter
public class OmsCartItem implements Serializable {

    @Id
    private String id;
    private String productId;
    private String productSkuId;
    private String memberId;
    private BigDecimal quantity;
    private BigDecimal price;
    private String sp1;
    private String sp2;
    private String sp3;
    private String productPic;
    private String productName;
    private String productSubTitle;
    private String productSkuCode;
    private String memberNickname;
    private Date createDate;
    private Date modifyDate;
    private int deleteStatus;
    private String productCategoryId;
    private String productBrand;
    private String productSn;
    private String productAttr;
    private String isChecked;
    @Transient
    private BigDecimal totalPrice;
}
