package com.baizhi.wyj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "yx_category")
public class Category implements Serializable {
    @Id
    private String id;
    @Column(name = "cate_name")
    private String cateName;

    private String levels;
    @Column(name = "parent_id")
    private String parentId;

}