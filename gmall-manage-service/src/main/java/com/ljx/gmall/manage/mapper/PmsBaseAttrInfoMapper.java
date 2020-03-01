package com.ljx.gmall.manage.mapper;

import com.ljx.gmall.bean.PmsBaseAttrInfo;
import com.ljx.gmall.manage.BaseMapper.TkBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmsBaseAttrInfoMapper extends TkBaseMapper<PmsBaseAttrInfo> {
    //根据valueIds查询属性集合,   valueIds = (14,45,46) 这种格式
    List<PmsBaseAttrInfo> selectAttrValueListByValueId(@Param("valueIdsStr") String valueIdsStr);
}
