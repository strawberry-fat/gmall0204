package com.ljx.gmall.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ljx.gmall.bean.UmsMember;
import com.ljx.gmall.bean.UmsMemberReceiveAddress;
import com.ljx.gmall.service.UserService;
import com.ljx.gmall.user.mapper.UmsMemberReceiveAddressMapper;
import com.ljx.gmall.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired(required = true)
    UserMapper userMapper;
    @Autowired(required = true)
    UmsMemberReceiveAddressMapper umsMemberReceiveAddressMapper;


    //查询所有用户
    @Override
    public List<UmsMember> getAllUser() {
        List<UmsMember> umsMembers = userMapper.selectAll();
        return umsMembers;
    }

    //根据Menmberid查询收货地址
    @Override
    public List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId) {
        UmsMemberReceiveAddress umsMemberReceiveAddress = new UmsMemberReceiveAddress();
        umsMemberReceiveAddress.setMemberId(memberId);
        List<UmsMemberReceiveAddress> receiveAddresses = umsMemberReceiveAddressMapper.select(umsMemberReceiveAddress);
        return receiveAddresses;
    }

    //更新用户
    @Override
    public void updateUser(UmsMember umsMember) {
        userMapper.updateByPrimaryKey(umsMember);
    }

    //增加用户
    @Override
    public void addUser(UmsMember umsMember) {
        userMapper.insert(umsMember);
    }

    //删除用户根据ID
    @Override
    public void deleteUser(String memberId) {
        userMapper.deleteByPrimaryKey(memberId);
    }

    //增加收货地址
    @Override
    public void addUmsMemberReceiveAddress(UmsMemberReceiveAddress umsMemberReceiveAddress) {
        umsMemberReceiveAddressMapper.insert(umsMemberReceiveAddress);
    }

    //更新收货地址
    @Override
    public void updateUmsMemberReceiveAddress(UmsMemberReceiveAddress umsMemberReceiveAddress) {
        umsMemberReceiveAddressMapper.updateByPrimaryKey(umsMemberReceiveAddress);
    }

    //根据收货地址id删除收货地址
    @Override
    public void deleteUmsMemberReceiveAddress(String id) {
        umsMemberReceiveAddressMapper.deleteByPrimaryKey(id);
    }

    //根据用户id获取用户
    @Override
    public UmsMember getUser(String id) {
        UmsMember umsMember = userMapper.selectByPrimaryKey(id);
        return umsMember;
    }

    @Override
    public UmsMemberReceiveAddress getReceiveAddressById(String id) {
        UmsMemberReceiveAddress umsMemberReceiveAddress = umsMemberReceiveAddressMapper.selectByPrimaryKey(id);
        return umsMemberReceiveAddress;
    }
}
