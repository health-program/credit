package com.paladin.credit.core;

import com.paladin.framework.utils.IPUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.web.subject.WebSubject;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author TontoZhou
 * @since 2019/11/21
 */
public class LimitFailCredentialsMatcher extends HashedCredentialsMatcher {

    public Map<String, LimitStatus> blacklist = new ConcurrentHashMap<>();

    private static int limitErrorNum = 5;
    private static long MILLIS_IN_DAY = 60 * 60 * 24 * 1000L;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        String ip;
        if (token instanceof HostAuthenticationToken) {
            ip = ((HostAuthenticationToken) token).getHost();
        } else {
            // 获取request ip
            WebSubject webSubject = (WebSubject) SecurityUtils.getSubject();
            HttpServletRequest request = (HttpServletRequest) webSubject.getServletRequest();
            ip = IPUtil.getIpAddress(request);
        }

        LimitStatus status = blacklist.get(ip);
        if (status != null && status.limit) {
            if (System.currentTimeMillis() - status.limitTime > MILLIS_IN_DAY) {
                synchronized (blacklist) {
                    if (System.currentTimeMillis() - status.limitTime > MILLIS_IN_DAY) {
                        status.errorNum = 0;
                        status.limit = false;
                    } else {
                        throw new LockedAccountException("多次登录失败，账号已被锁定！");
                    }
                }
            } else {
                throw new LockedAccountException("多次登录失败，账号已被锁定！");
            }
        }

        boolean match = super.doCredentialsMatch(token, info);
        if (!match) {
            synchronized (blacklist) {
                if (status == null) {
                    status = new LimitStatus(ip);
                    blacklist.put(ip, status);
                }

                status.errorNum++;
                if (status.errorNum >= limitErrorNum) {
                    status.limit = true;
                    status.limitTime = System.currentTimeMillis();
                }
            }
        }
        return match;
    }

    public void unlock() {
        blacklist.clear();
    }

    private static class LimitStatus {
        String ip;
        int errorNum;
        long limitTime;
        boolean limit;

        LimitStatus(String ip) {
            this.ip = ip;
            errorNum = 0;
            limitTime = 0;
            limit = false;
        }
    }


}
