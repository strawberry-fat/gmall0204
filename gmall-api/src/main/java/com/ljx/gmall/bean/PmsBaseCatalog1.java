package com.ljx.gmall.bean;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 平台一级分类，实体类
 */
@Getter
@Setter
public class PmsBaseCatalog1 implements Serializable {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column
    private String name;

    @Transient
    private List<PmsBaseCatalog2> catalog2s;


}

