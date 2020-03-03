package com.ljx.gmall.bean;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @param
 * @return
 */
@Getter
@Setter
public class PmsProductImage implements Serializable {

    @Column
    @Id
    private String id;
    @Column
    private String productId;
    @Column
    private String imgName;
    @Column
    private String imgUrl;


}