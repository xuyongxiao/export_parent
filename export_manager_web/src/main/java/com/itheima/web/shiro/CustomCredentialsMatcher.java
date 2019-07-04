package com.itheima.web.shiro;


import com.itheima.common.utils.Encrypt;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * 自定义凭证（密码）比较器
 */
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {

    /**
     * This implementation acquires the {@code token}'s credentials
     * (via {@link #getCredentials(AuthenticationToken) getCredentials(token)})
     * and then the {@code account}'s credentials
     * (via {@link #getCredentials(AuthenticationInfo) getCredentials(account)}) and then passes both of
     * them to the {@link #equals(Object, Object) equals(tokenCredentials, accountCredentials)} method for equality
     * comparison.
     *
     * @param token the {@code AuthenticationToken} submitted during the authentication attempt.
     * @param info  the {@code AuthenticationInfo} stored in the system matching the token principal.
     * @return {@code true} if the provided token credentials are equal to the stored account credentials,
     * {@code false} otherwise
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        //从token中获取用户名（email）和密码
        UsernamePasswordToken uToken = (UsernamePasswordToken) token;
        String email = uToken.getUsername();
        String uTokenPassword = new String (uToken.getPassword(),0,uToken.getPassword().length);
        //
        String md5Password = Encrypt.md5(uTokenPassword, email);
        //
        String dbPassword = info.getCredentials().toString();

        return super.equals(md5Password,dbPassword);
    }
}
