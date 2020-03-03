package com.ljx.gmall.bean;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @param
 * @return
 */
@Getter
@Setter
public class PmsBaseAttrValue implements Serializable {
    @Id
    @Column
    private String id;
    @Column
    private String valueName;
    @Column
    private String attrId;
    @Column
    private String isEnabled;

    @Transient
    private String urlParam;

}
