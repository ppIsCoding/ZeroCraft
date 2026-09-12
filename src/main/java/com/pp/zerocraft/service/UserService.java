package com.pp.zerocraft.service;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.pp.zerocraft.model.dto.user.UserQueryRequest;
import com.pp.zerocraft.model.entity.User;
import com.pp.zerocraft.model.vo.LoginUserVO;
import com.pp.zerocraft.model.vo.UserVO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * 用户 服务层。
 *
 * @author <a href="https://github.com/ppiscoding">pp</a>
 */
public interface UserService extends IService<User> {
    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 对密码进行加密
     * @param userPassword
     * @return
     */
     String getEncryptPassword(String userPassword);

    /**
     * 获取脱敏的已登录用户信息
     *
     * @return
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request  这个request对象，可以方便我们操作sessio、
     *
    .     * @return 脱敏后的用户信息
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);
    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    boolean userLogout(HttpServletRequest request);

    UserVO getUserVO(User user);

    List<UserVO> getUserVOList(List<User> userList);

    /**
     * 根据查询条件去构建查询对象
     * @param userQueryRequest
     * @return
     */
    QueryWrapper getQueryWrapper(UserQueryRequest userQueryRequest);
}
