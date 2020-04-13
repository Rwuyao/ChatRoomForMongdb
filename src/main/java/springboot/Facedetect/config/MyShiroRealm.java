package springboot.Facedetect.config;

import javax.annotation.Resource;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import springboot.Facedetect.entity.UserInfo;
import springboot.Facedetect.service.UserSerice;

public class MyShiroRealm extends AuthorizingRealm{
	@Resource
	private UserSerice userInfoService;
	
	@Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException{
        
    	System.out.println("MyShiroRealm.doGetAuthenicationInfo()");  	
    	String username = (String)token.getPrincipal();
    	System.out.println(token.getCredentials());   	
    	UserInfo userInfo = userInfoService.findByUsername(username);
    	System.out.println("------->userInfo"+userInfo);
    	if(userInfo == null){
    		return null;
    	}
    	SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
    			userInfo,userInfo.getPassword(),ByteSource.Util.bytes("salt"),
    			getName());
    	return authenticationInfo;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        
    	System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        //为当前用户设置角色和权限
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();      
        	authorizationInfo.addRole("admin");        	
        	authorizationInfo.addStringPermission("admin");	      
        return authorizationInfo;
    }
}
