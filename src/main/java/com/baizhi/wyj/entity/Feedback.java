package com.baizhi.wyj.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
@Table(name = "yx_feedback")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Feedback implements Serializable {
    @Id
    private String id;

    private String title;

    private String content;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "save_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date saveDate;
}