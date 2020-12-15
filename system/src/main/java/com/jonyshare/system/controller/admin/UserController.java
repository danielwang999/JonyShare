package com.jonyshare.system.controller.admin;

import com.jonyshare.server.dto.LoginUserDto;
import com.jonyshare.server.dto.PageDto;
import com.jonyshare.server.dto.ResponseDto;
import com.jonyshare.server.dto.UserDto;
import com.jonyshare.server.service.UserService;
import com.jonyshare.server.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/admin/user")
public class UserController {

    public static final String BUSINESS_NAME = "";

    @Resource
    private UserService userService;

    /**
     * 列表查询
     * @param pageDto
     * @return
     */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        userService.list(pageDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存，包括新增和修改
     * @param userDto
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody UserDto userDto) {
        // 保存校验
        ValidatorUtil.require(userDto.getLoginName(), "登陆名");
        ValidatorUtil.length(userDto.getLoginName(), "登陆名", 1, 50);
        ValidatorUtil.length(userDto.getName(), "昵称", 1, 50);
        ValidatorUtil.require(userDto.getPassword(), "密码");
        ResponseDto responseDto = new ResponseDto();
        userService.save(userDto);
        responseDto.setContent(userDto);
        return responseDto;
    }

    /**
     * 保存密码
     * @param userDto
     * @return
     */
    @PostMapping("/save-password")
    public ResponseDto savePassword(@RequestBody UserDto userDto) {
        // 保存校验
        ValidatorUtil.require(userDto.getPassword(), "密码");
        ResponseDto responseDto = new ResponseDto();
        userService.savePassword(userDto);
        responseDto.setContent(userDto);
        return responseDto;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        userService.delete(id);
        return responseDto;
    }

    /**
     * 登录
     * @param userDto
     */
    @PostMapping("/login")
    public ResponseDto login(@RequestBody UserDto userDto) {
        // 保存校验
        ValidatorUtil.require(userDto.getPassword(), "密码");
        ResponseDto responseDto = new ResponseDto();
        LoginUserDto loginUserDto = userService.login(userDto);
        responseDto.setContent(loginUserDto);
        return responseDto;
    }

}
