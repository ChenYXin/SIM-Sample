package org.itzixi.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.itzixi.base.BaseInfoProperties;
import org.itzixi.grace.result.GraceJSONResult;
import org.itzixi.grace.result.ResponseStatusEnum;
import org.itzixi.pojo.Users;
import org.itzixi.pojo.bo.RegisterLoginBO;
import org.itzixi.pojo.vo.UsersVO;
import org.itzixi.service.IUsersService;
import org.itzixi.tasks.SMSTask;
import org.itzixi.utils.IPUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/passport")
@Slf4j
public class PassportController extends BaseInfoProperties {
    // 127.0.0.1:88/passport/getSMSCode
    @Resource
    private SMSTask smsTask;

    @Resource
    private IUsersService usersService;

    @PostMapping("/getSMSCode")
    public GraceJSONResult getSMSCode(String mobile, HttpServletRequest request) throws Exception {
        if (StringUtils.isBlank(mobile)) {
            return GraceJSONResult.error();
        }

        //获得用户的手机号/ip
        String userIp = IPUtil.getRequestIp(request);
        //限制该用户的手机号/ip在60秒内只能获得一次验证码
        redis.setnx60s(MOBILE_SMSCODE + ":" + userIp, mobile);

        String code = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        smsTask.sendSmsInTask(mobile, code);

        //把验证码存入到redis中，用于后续的注册/校验 ,30分钟
        redis.set(MOBILE_SMSCODE + ":" + mobile, code, 30 * 60);

        return GraceJSONResult.ok();
    }

    @PostMapping("/register")
    public GraceJSONResult register(@RequestBody @Valid RegisterLoginBO registerLoginBO,
                                    HttpServletRequest request) throws Exception {
        String mobile = registerLoginBO.getMobile();
        String code = registerLoginBO.getSmsCode();
        String nickName = registerLoginBO.getNickName();
        //1.从redis中获得验证码进行校验判断是否匹配
        String redisCode = redis.get(MOBILE_SMSCODE + ":" + mobile);
        if (StringUtils.isBlank(redisCode) || !redisCode.equalsIgnoreCase(code)) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.SMS_CODE_ERROR);
        }
        //2.根据mobile查询数据库，如果用户存在，则提示不能重复注册
        Users user = usersService.queryMobileIfExists(mobile);
        if (user != null) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.USER_ALREADY_EXIST_ERROR);
        }
        //2.1如果查询数据库中用户为空，则表示用户没有注册过，则需要进行用户信息数据的入库
        user = usersService.createUsers(mobile, nickName);

        //3.用户注册成功后，删除redis中的短信验证码使其失效
        redis.del(MOBILE_SMSCODE + ":" + mobile);
        //4.设置用户分布式会话，保存用户的token令牌，存储到redis中
        String uToken = TOKEN_USER_PREFIX + SYMBOL_DOT + UUID.randomUUID();
        redis.set(REDIS_USER_TOKEN + ":" + user.getId(), uToken);//设置分布式会话
        //5.返回用户数据给前端
        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(user, usersVO);
        usersVO.setUserToken(uToken);

        return GraceJSONResult.ok(usersVO);
    }

    @PostMapping("/login")
    public GraceJSONResult login(@RequestBody @Valid RegisterLoginBO registerLoginBO,
                                 HttpServletRequest request) throws Exception {
        String mobile = registerLoginBO.getMobile();
        String code = registerLoginBO.getSmsCode();
        //1.从redis中获得验证码进行校验判断是否匹配
        String redisCode = redis.get(MOBILE_SMSCODE + ":" + mobile);
        if (StringUtils.isBlank(redisCode) || !redisCode.equalsIgnoreCase(code)) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.SMS_CODE_ERROR);
        }
        //2.根据mobile查询数据库，如果用户不存在，则提示还没注册
        Users user = usersService.queryMobileIfExists(mobile);
        if (user == null) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.USER_NOT_EXIST_ERROR);
        }

        //3.用户登录成功后，删除redis中的短信验证码使其失效
        redis.del(MOBILE_SMSCODE + ":" + mobile);

        //4.设置用户分布式会话，保存用户的token令牌，存储到redis中
        String uToken = TOKEN_USER_PREFIX + SYMBOL_DOT + UUID.randomUUID();
        redis.set(REDIS_USER_TOKEN + ":" + user.getId(), uToken);//设置分布式会话

        //5.返回用户数据给前端
        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(user, usersVO);
        usersVO.setUserToken(uToken);

        return GraceJSONResult.ok(usersVO);
    }

    @PostMapping("/registerOrLogin")
    public GraceJSONResult registerOrLogin(@RequestBody @Valid RegisterLoginBO registerLoginBO,
                                    HttpServletRequest request) throws Exception {
        String mobile = registerLoginBO.getMobile();
        String code = registerLoginBO.getSmsCode();
        String nickName = registerLoginBO.getNickName();
        //1.从redis中获得验证码进行校验判断是否匹配
        String redisCode = redis.get(MOBILE_SMSCODE + ":" + mobile);
        if (StringUtils.isBlank(redisCode) || !redisCode.equalsIgnoreCase(code)) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.SMS_CODE_ERROR);
        }
        //2.根据mobile查询数据库，如果用户存在，则直接登录
        Users user = usersService.queryMobileIfExists(mobile);
        if (user == null) {
            //2.1如果查询数据库中用户为空，则表示用户没有注册过，则需要进行用户信息数据的入库
            user = usersService.createUsers(mobile, nickName);
        }

        //3.用户注册成功后，删除redis中的短信验证码使其失效
        redis.del(MOBILE_SMSCODE + ":" + mobile);

        //4.设置用户分布式会话，保存用户的token令牌，存储到redis中
        String uToken = TOKEN_USER_PREFIX + SYMBOL_DOT + UUID.randomUUID();
        redis.set(REDIS_USER_TOKEN + ":" + user.getId(), uToken);//设置分布式会话
        //5.返回用户数据给前端
        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(user, usersVO);
        usersVO.setUserToken(uToken);

        return GraceJSONResult.ok(usersVO);
    }
}
