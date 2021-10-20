package com.neu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能描述:
 *
 * @author zzb
 * @email 1135462964@qq.com
 * @date 2021-10-18 16:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemType {
    private Integer id;
    private Integer code;
    private String name;
}
