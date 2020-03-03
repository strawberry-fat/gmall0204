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
public class PmsBaseSaleAttr implements Serializable {

    @Id
    @Column
    String id;

    @Column
    String name;


}