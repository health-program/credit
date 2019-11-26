package com.paladin.credit.core;

import com.paladin.common.model.syst.SysUser;
import com.paladin.common.service.syst.SysUserService;
import com.paladin.framework.core.session.UserSession;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreditUserRealm extends AuthorizingRealm {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAN1VXxqq0uF2aTcgtaKMhba5Yq2l5lvaUzU3s1aIC/axtt05E+PWN+Nx1n2KiwFqgPflT8qV7C6IX7LeYAsNNgi2+aAoMPE0rgZhPYl5rUy3PuwgJU/CLdWE07RiQ2+k0fpkrkQYtBH0XXxWPTFzYP/P1DWUMM/UQsu80Zwd0PEjAgMBAAECgYBERUwTlrN7diHYLk+4du3bwe0tMOoQVRSraqX3v/kovAJy1GNNOMt+baPCj5R2+FoVnPbyvzHd3JTgQHWRkx6iGGffxcdmnLYU+F5l99xGMnup1TNqDxpdz/uBCL9020YXKbNnzcEDUl5yR87TIXLmoZlyx1Oy0NN0nDI7n5iPOQJBAPz4BBuo4FE1RovOFUJogG8VrQRVucy7+Vv7WT1TpBpa47gWOorZnuYKcFhmko1bOrnpJvkpDnHeYNAz5/HAKR8CQQDf/FBWCwSdqOBF2Qp+o6MNKXUynYjvC0vjbjUS0jehX/I3swFFGgvg7Wk+ugbG1/0uIWfTSjAvvvvuD8t5zoN9AkAHGi+DV0p0/Tnt0utl4ek5NTKWXJHK3bwyiOwIfMCuvL6H/JfILjP34XMmCDs1HMqZTaJ6fFybddLiBZMNhoehAkAGqF1K8XDlrstbyCPDt+F01rKhGoWjdVaAIBp7wLvelBeDCFMVVrzbprLmJmllJ65i3KNVaHMeODMM1HNb34ORAkEAgzhrZEZaDLjIideFTu691wsE+/rThwoUVK9GTZ0G235t2lT75Tc5RTjSdkOQ5Jy7+jyK6P7ZXKoz0wQVjIaIxA==df723820";

	//private static final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDdVV8aqtLhdmk3ILWijIW2uWKtpeZb2lM1N7NWiAv2sbbdORPj1jfjcdZ9iosBaoD35U/KlewuiF+y3mALDTYItvmgKDDxNK4GYT2Jea1Mtz7sICVPwi3VhNO0YkNvpNH6ZK5EGLQR9F18Vj0xc2D/z9Q1lDDP1ELLvNGcHdDxIwIDAQAB";

	private SysUserService sysUserService;
	private CreditUserSessionFactory userSessionFactory;

	//private LimitFailCredentialsMatcher hashedCredentialsMatcher;

	public CreditUserRealm(SysUserService sysUserService, CreditUserSessionFactory userSessionFactory) {
		this.sysUserService = sysUserService;
		this.userSessionFactory = userSessionFactory;

		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		hashedCredentialsMatcher.setHashAlgorithmName("md5");// 散列算法:这里使用MD5算法;
		hashedCredentialsMatcher.setHashIterations(1);// 散列的次数，当于 m比如散列两次，相d5("");

		setCredentialsMatcher(hashedCredentialsMatcher);
	}

//	public void unlock(){
//		hashedCredentialsMatcher.unlock();
//	}

	/**
	 * 认证信息.(身份验证) : Authentication 是用来验证用户身份
	 * 
	 * @param token
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auToken) throws AuthenticationException {

		UsernamePasswordToken token = (UsernamePasswordToken) auToken;

		try {
			String password = RSAEncrypt.decrypt(new String(token.getPassword()), privateKey);
			token.setPassword(password.toCharArray());
		} catch (Exception e) {
			throw new UnknownAccountException();
		}

		logger.debug("后台登录：SysUserRealm.doGetAuthenticationInfo()");

		String username = (String) token.getPrincipal();
		SysUser sysUser = sysUserService.getUserByAccount(username);

		if (sysUser == null) {
			throw new UnknownAccountException();
		}

		if (sysUser.getState() != SysUser.STATE_ENABLED) {
			throw new LockedAccountException(); // 帐号锁定
		}

		/*
		 * 获取权限信息:这里没有进行实现， 请自行根据UserInfo,Role,Permission进行实现； 获取之后可以在前端for循环显示所有链接;
		 */
		UserSession userSession = userSessionFactory.createSession(sysUser);

		// 加密方式;
		// 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现

		// usersession 对象会存入session属性中，与session保持同步，并在session中实现权限控制
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userSession, sysUser.getPassword(), ByteSource.Util.bytes(sysUser.getSalt()),
				username);

		logger.info("===>用户[" + username + ":" + userSession.getUserName() + "]登录系统<===");

		// 登录日志与更新最近登录时间
		sysUserService.updateLastTime(username);

		return authenticationInfo;
	}

	/**
	 * 此方法调用 hasRole,hasPermission的时候才会进行回调.
	 *
	 * 权限信息.(授权): 1、如果用户正常退出，缓存自动清空； 2、如果用户非正常退出，缓存自动清空；
	 * 3、如果我们修改了用户的权限，而用户不退出系统，修改的权限无法立即生效。 （需要手动编程进行实现；放在service进行调用）
	 * 在权限修改后调用realm中的方法，realm已经由spring管理，所以从spring中获取realm实例， 调用clearCached方法；
	 * :Authorization 是授权访问控制，用于对用户进行的操作授权，证明该用户是否允许进行当前操作，如访问某个链接，某个资源文件等。
	 * 
	 * @param principals
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) throws AuthenticationException {
		// 废弃shiro缓存验证信息策略
		return null;
	}

	protected AuthorizationInfo getAuthorizationInfo(PrincipalCollection principals) {
		if (principals == null) {
			return null;
		}
		return (AuthorizationInfo) principals.getPrimaryPrincipal();
	}

}
