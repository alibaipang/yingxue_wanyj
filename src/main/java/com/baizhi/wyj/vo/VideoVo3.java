package com.baizhi.wyj.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoVo3 implements Serializable {
    private String id;
    private String videoTitle;
    private String cover;
    private String path;
    private Date uploadTime;
    private String description;
    private Integer likeCount = 12;
    private Integer playCount = 12;
    private Boolean isAttention = true;
    private String categoryId;
    private String cateName;
    private String userId;
    private String userName;
    private String userPicImg;
    private List<VideoVo2>videoList;
}
