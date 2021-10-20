package com.neu.mapper;

import com.neu.entity.Relation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 功能描述:
 *
 * @author zzb
 * @email 1135462964@qq.com
 * @date 2021-10-17 22:42
 */
@Mapper
public interface RelationMapper {
    List<Relation> selectAll();
    Relation selectById(Integer id);
    int insert(Relation relation);
    int delete(Integer id);
    int update(Relation relation);
    int insertBatch(List<Relation> relations);
    List<Relation> selectByUserId(Integer userId);
    List<Relation> selectByItemId(Integer itemId);
}
