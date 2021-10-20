package com.neu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 功能描述:
 *
 * @author zzb
 * @email 1135462964@qq.com
 * @date 2021-10-17 22:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private Integer id;
    private String name;
    private String date;
    private String link;
    private Double score;
    private Integer type;
    private List<String> typeName;

}
