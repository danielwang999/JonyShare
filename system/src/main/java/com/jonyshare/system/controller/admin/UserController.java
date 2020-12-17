package com.jonyshare.system.controller.admin;

import com.jonyshare.server.dto.*;
import com.jonyshare.server.service.UserService;
import com.jonyshare.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/admin/user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    public static final String BUSINESS_NAME = "用户";

    @Resource
    private UserService userService;

    @Resource
    public RedisTemplate redisTemplate;

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
    public ResponseDto login(@RequestBody UserDto userDto, HttpServletRequest request) {
        // 保存校验
        ValidatorUtil.require(userDto.getPassword(), "密码");
        ResponseDto responseDto = new ResponseDto();

        // 根据验证码token去获取缓存中的验证码，和用户输入的验证码是否一致
        //String imageCode = (String) request.getSession().getAttribute(userDto.getImageCodeToken());
        String imageCode = (String) redisTemplate.opsForValue().get(userDto.getImageCodeToken());
        LOG.info("从redis中获取到的验证码：{}", imageCode);
        if (StringUtils.isEmpty(imageCode)) {
            responseDto.setSuccess(false);
            responseDto.setMessage("验证码已过期");
            LOG.info("用户登录失败，验证码已过期");
            return responseDto;
        }
        if (!imageCode.toLowerCase().equals(userDto.getImageCode().toLowerCase())) {
            responseDto.setSuccess(false);
            responseDto.setMessage("验证码不对");
            LOG.info("用户登录失败，验证码不对");
            return responseDto;
        } else {
            // 验证通过后，移除验证码
            //request.getSession().removeAttribute(userDto.getImageCodeToken());
            redisTemplate.delete(userDto.getImageCodeToken());
        }

        LoginUserDto loginUserDto = userService.login(userDto);
        request.getSession().setAttribute(Constants.LOGIN_USER, loginUserDto);
        responseDto.setContent(loginUserDto);
        return responseDto;
    }

    /**
     * 退出登录Get
     * @param
     */
    @GetMapping("/logout")
    public ResponseDto logout(HttpServletRequest request) {
        ResponseDto responseDto = new ResponseDto();
        request.getSession().removeAttribute(Constants.LOGIN_USER);
        return responseDto;
    }

}
