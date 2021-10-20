package com.neu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能描述:
 *
 * @author zzb
 * @email 1135462964@qq.com
 * @date 2021-10-17 22:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Relation {
    private Integer id;
    private Integer userId;
    private Integer itemId;
    private Double score;

//    @Override
//    public int compareTo(Relation o) {
//        return this.getItemId().compareTo(o.getItemId());
//    }
}
