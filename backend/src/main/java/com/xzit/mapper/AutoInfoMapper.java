package com.xzit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzit.entity.AutoInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Gule
 * @since 2025-06-01
 */
public interface AutoInfoMapper extends BaseMapper<AutoInfo> {


    Page<AutoInfo> searchByPage(@Param("page") Page<AutoInfo> page,
                                @Param("autoInfo") AutoInfo autoInfo);

    List<AutoInfo> toBeMaintain();
}
