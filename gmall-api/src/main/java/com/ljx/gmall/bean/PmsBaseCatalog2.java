package com.ljx.gmall.bean;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 平台二级分类，实体类
 */
@Getter
@Setter
public class PmsBaseCatalog2 implements Serializable {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column
    private String name;
    @Column
    private String catalog1Id;

    @Transient
    private List<PmsBaseCatalog3> catalog3List;


}
