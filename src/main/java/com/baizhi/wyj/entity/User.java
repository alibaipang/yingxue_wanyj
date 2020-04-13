package com.baizhi.wyj.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "yx_user")
public class User implements Serializable {
    @Id
    @ExcelIgnore
    @Excel(name = "ID")
    private String id;
    @Excel(name = "姓名")
    private String username;
    @Excel(name = "电话")
    private String phone;
    @Column(name = "head_img")
    @Excel(name = "头像",type = 2 ,width = 20 , height = 20)
    private String headImg;
    @Excel(name = "签名")
    private String sign;
    @Excel(name = "微信")
    private String wechat;
    @Excel(name = "状态")
    private String status;
    @Column(name = "create_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间",format = "yyyy年MM月dd日",width = 20)
    private Date createDate;
    @Excel(name = "性别")
    private String sex;
    @Excel(name = "地址")
    private String address;

}