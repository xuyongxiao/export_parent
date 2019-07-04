package com.itheima.web.shiro;

import com.itheima.domain.system.Module;
import com.itheima.domain.system.User;
import com.itheima.service.system.IModuleService;
import com.itheima.service.system.IUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private IUserService userService;

    @Autowired
    private IModuleService moduleService;
    /**
     * -------------------------授权-------------------------------
     * Retrieves the AuthorizationInfo for the given principals from the underlying data store.  When returning
     * an instance from this method, you might want to consider using an instance of
     * {@link SimpleAuthorizationInfo SimpleAuthorizationInfo}, as it is suitable in most cases.
     *
     * @param principals the primary identifying principals of the AuthorizationInfo that should be retrieved.
     * @return the AuthorizationInfo associated with this principals.
     * @see SimpleAuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //授权过程是用用户token获取到对应的模块信息，并创建返回值
        User user = (User) principals.fromRealm(this.getName()).iterator().next();

        List<Module> moduleList = moduleService.findModleByUser(user);

        //去重
        Set<String> permissions = new HashSet<>();
        for (Module module : moduleList) {
            permissions.add(module.getName());
        }

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        authorizationInfo.setStringPermissions(permissions);

        return authorizationInfo;
    }



    /**
     * -----------------------认证---------------------------------
     * 从给定身份验证令牌的特定于实现的数据源（RDBMS，LDAP等）中检索身份验证数据。
     * <p/>
     * 对于大多数数据源而言，这意味着只需“pulling拉动”相关主题/用户的身份验证数据，仅此而已让Shiro完成其余工作。
     * 但是在某些系统中，除了检索数据之外，这种方法实际上可以执行EIS特定的登录逻辑 - 这取决于Realm的实现。
     * <p/>
     * A {@code null} 返回值表示没有帐户可以与指定的标记关联。
     *
     * @param token 包含用户主体和凭据的身份验证令牌。
     * @return an {@link AuthenticationInfo} 仅包含查询成功的帐户数据的对象（即帐户存在且有效等）
     * @throws AuthenticationException 如果获取数据或执行时出错
     *                                 指定<tt>token</ tt>的特定于域的认证逻辑
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken uToken = (UsernamePasswordToken) token;
        String email = uToken.getUsername();
        String password = new String(uToken.getPassword(),0,uToken.getPassword().length);
        User user = userService.findByEmail(email);
        if (user != null){
            //创建返回值对象
            AuthenticationInfo authenticationInfonfo = new SimpleAuthenticationInfo(user,user.getPassword(),this.getName());
            return authenticationInfonfo;
        }
        //如果返回null，则shiro会抛异常
        return null;
    }
}
