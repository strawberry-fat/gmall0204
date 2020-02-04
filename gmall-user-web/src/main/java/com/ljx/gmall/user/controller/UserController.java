package com.ljx.gmall.user.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.ljx.gmall.bean.UmsMember;
import com.ljx.gmall.bean.UmsMemberReceiveAddress;
import com.ljx.gmall.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Reference
    UserService userService;
//    @Autowired
//    TestService testService;
//    @ResponseBody
//    @GetMapping("/get")
//    public String testBean(){
//        return testService.Hello();
//    }


    //根据用户id查询收货地址
    @ResponseBody
    @GetMapping("/getAllReceiveAddressByMemberId")
    public List<UmsMemberReceiveAddress> getAllReceiveAddressByMemberId(String memberId) {
        List<UmsMemberReceiveAddress> umsMemberReceiveAddresses = userService.getReceiveAddressByMemberId(memberId);
        return umsMemberReceiveAddresses;
    }

    @ResponseBody
    @GetMapping("/getReceiveAddressById")
    public UmsMemberReceiveAddress getReceiveAddressByMemberId(String Id) {
        UmsMemberReceiveAddress umsMemberReceiveAddress = userService.getReceiveAddressById(Id);
        return umsMemberReceiveAddress;
    }


    //更新地址
    @PutMapping("/updateAddress")
    @ResponseBody
    public void updateAddress(@RequestBody UmsMemberReceiveAddress umsMemberReceiveAddress) {
        userService.updateUmsMemberReceiveAddress(umsMemberReceiveAddress);
    }

    //删除地址
    @DeleteMapping("/deleteAddress")
    @ResponseBody
    public void deleteAddress(String id) {
        userService.deleteUmsMemberReceiveAddress(id);
    }

    //增加地址
    @PostMapping("/addAddress")
    @ResponseBody
    public void addAddress(@RequestBody UmsMemberReceiveAddress umsMemberReceiveAddress) {
        userService.addUmsMemberReceiveAddress(umsMemberReceiveAddress);
    }

    //查询用户
    @GetMapping("/getUser")
    @ResponseBody
    public UmsMember getUser(String id) {
        UmsMember umsMembers = userService.getUser(id);
        return umsMembers;
    }

    //查询所有用户
    @GetMapping("/getAllUser")
    @ResponseBody
    public List<UmsMember> getAllUser() {
        List<UmsMember> umsMembers = userService.getAllUser();
        return umsMembers;
    }

    //更新用户
    @PutMapping("/updateUser")
    @ResponseBody
    public void updateAllUser(@RequestBody UmsMember umsMember) {
        userService.updateUser(umsMember);
    }

    //增加用户
    @PostMapping("/addUser")
    @ResponseBody
    public void addUser(@RequestBody UmsMember umsMember) {
        userService.addUser(umsMember);
    }

    //删除用户
    @DeleteMapping("/deleteUser")
    @ResponseBody
    public void deleteUser(String memberId) {
        userService.deleteUser(memberId);
    }


    @GetMapping("/ll")
    @ResponseBody
    public String get() {

        return "HE22";
    }

    @ResponseBody
    @GetMapping("/get")
    public String getAll() {
        userService.getAllUser();
        return "sdaa";
    }
}
