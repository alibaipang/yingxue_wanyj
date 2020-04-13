package com.baizhi.wyj.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVo2 implements Serializable {
    private String releaseMonth;
    private Integer number;
}
