package com.ljx.gmall.cart.BaseMapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface TkBaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
}