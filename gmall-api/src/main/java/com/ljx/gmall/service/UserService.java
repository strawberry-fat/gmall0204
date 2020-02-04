package com.ljx.gmall.service;

import com.ljx.gmall.bean.UmsMember;
import com.ljx.gmall.bean.UmsMemberReceiveAddress;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<UmsMember> getAllUser();

    List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId);

    void updateUser(UmsMember umsMember);

    void addUser(UmsMember umsMember);

    void deleteUser(String memberId);

    void addUmsMemberReceiveAddress(UmsMemberReceiveAddress umsMemberReceiveAddress);

    void updateUmsMemberReceiveAddress(UmsMemberReceiveAddress umsMemberReceiveAddress);

    void deleteUmsMemberReceiveAddress(String id);

    UmsMember getUser(String id);

    UmsMemberReceiveAddress getReceiveAddressById(String id);
}
