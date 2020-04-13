package com.kexin.common.config;


import com.kexin.common.realm.AuthRealm;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@SpringBootConfiguration
public class ShiroConfig {

/*    private Logger logger = LoggerFactory.getLogger(ShiroConfig.class);

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("authRealm")AuthRealm authRealm){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager(authRealm));
//        bean.setSuccessUrl("/common/index");//认证成功跳转的页面
//        bean.setLoginUrl("/common/login");//没有认证,认证失败,回到登录界面
//        Map<String,Filter> map = new HashMap();
//        map.put("authc",new FormAuthenticationFilter());
//        bean.setFilters(map);
//        Map<String, Filter> filterMap = new LinkedHashMap<>();
//        filterMap.put("authc", new AjaxPermissionsAuthorizationFilter());
//        bean.setFilters(filterMap);

        //自定义过滤器
//        Map<String, Filter> filterMap = new LinkedHashMap<>();
//        filterMap.put("corsAuthenticationFilter", corsAuthenticationFilter());
//        bean.setFilters(filterMap);
        //配置访问权限
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap();
        filterChainDefinitionMap.put("/","anon");
        filterChainDefinitionMap.put("/common/**","anon");
        filterChainDefinitionMap.put("/static/**","anon");
        filterChainDefinitionMap.put("/machine/check/**","anon");
//        filterChainDefinitionMap.put("/machine/**","authc");
//        filterChainDefinitionMap.put("/machine/template/**","authc");
//        filterChainDefinitionMap.put("/menu/**","authc");
//        filterChainDefinitionMap.put("/operation/**","authc");
//        filterChainDefinitionMap.put("/operator/**","authc");
//        filterChainDefinitionMap.put("/product/**","authc");
//        filterChainDefinitionMap.put("/role/**","authc");
//        filterChainDefinitionMap.put("/loginUser/list","authc");
//        filterChainDefinitionMap.put("/log/**","authc");

//        filterChainDefinitionMap.put("/**","authc");
//        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        //authc:所有url必须通过认证才能访问，anon:所有url都可以匿名访问
        filterChainDefinitionMap.put("/**", "corsAuthenticationFilter");
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        //自定义过滤器
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("corsAuthenticationFilter", corsAuthenticationFilter());
        bean.setFilters(filterMap);
        return bean;
    }

    @Bean
    public SecurityManager securityManager(@Qualifier("authRealm")AuthRealm authRealm){
        logger.info("- - - - - - -shiro开始加载- - - - - - ");
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();

        defaultWebSecurityManager.setRememberMeManager(rememberMeManager());
        defaultWebSecurityManager.setSessionManager(sessionManager());
        defaultWebSecurityManager.setCacheManager(cacheManager());
        defaultWebSecurityManager.setRealm(authRealm);
        return defaultWebSecurityManager;
    }


    *//**
     * shiro缓存管理器;
     * 需要注入安全管理器：securityManager
     *//*
    @Bean
    public EhCacheManager cacheManager(){
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return cacheManager;
    }


    @Bean
    public SimpleCookie rememberMeCookie(){
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        cookie.setHttpOnly(true);
        //记住我有效期长达一周
        cookie.setMaxAge(604800);
        return cookie;
    }

    @Bean
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        rememberMeManager.setCookie(rememberMeCookie());
        rememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
        return rememberMeManager;
    }



*//*    @Bean
    public SessionManager webSessionManager(){
        DefaultWebSessionManager manager = new DefaultWebSessionManager();
        //设置session过期时间为1小时(单位：毫秒)，默认为30分钟
        manager.setGlobalSessionTimeout(60 * 60 * 1000);
        manager.setSessionValidationSchedulerEnabled(true);
        //manager.setSessionDAO(redisSessionDAO());
        return manager;
    }*//*

    //自定义sessionManager
    @Bean
    public SessionManager sessionManager() {
        return new CustomSessionManager();
    }

    public CORSAuthenticationFilter corsAuthenticationFilter(){
        return new CORSAuthenticationFilter();
    }

    *//**
     * AOP式方法级权限检查
     * @return
     *//*
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    *//**
     *  保证实现了Shiro内部lifecycle函数的bean执行
     * @return
     *//*
    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("authRealm")AuthRealm authRealm) {
        SecurityManager manager = securityManager(authRealm);
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(manager);
        return advisor;
    }*/

/*    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }*/


    @Bean
    public Realm realm() {
        return new AuthRealm();
    }

    @Bean
    public CacheManager cacheManager() {
        return new MemoryConstrainedCacheManager();
    }

    /**
     * cookie对象;
     * rememberMeCookie()方法是设置Cookie的生成模版，比如cookie的name，cookie的有效时间等等。
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie(){
        //System.out.println("ShiroConfiguration.rememberMeCookie()");
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }

    /**
     * cookie管理对象;
     * rememberMeManager()方法是生成rememberMe管理器，而且要将这个rememberMe管理器设置到securityManager中
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(){
        //System.out.println("ShiroConfiguration.rememberMeManager()");
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
        return cookieRememberMeManager;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager sm = new DefaultWebSecurityManager();
        sm.setRealm(realm());
        sm.setCacheManager(cacheManager());
        //注入记住我管理器
        sm.setRememberMeManager(rememberMeManager());
        //注入自定义sessionManager
        sm.setSessionManager(sessionManager());
        return sm;
    }

    //自定义sessionManager
    @Bean
    public SessionManager sessionManager() {
        return new CustomSessionManager();
    }

    public CORSAuthenticationFilter corsAuthenticationFilter(){
        return new CORSAuthenticationFilter();
    }

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        //SecurityUtils.setSecurityManager(securityManager);
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //配置不会被拦截的链接，顺序判断
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("authc", new AjaxPermissionsAuthorizationFilter());
        shiroFilter.setFilters(filterMap);
        filterChainDefinitionMap.put("/","anon");
        filterChainDefinitionMap.put("/common/**","anon");
        filterChainDefinitionMap.put("/static/**","anon");
//        filterChainDefinitionMap.put("/machine/check/**","anon");
//
//                filterChainDefinitionMap.put("/machine/**","authc");
//        filterChainDefinitionMap.put("/machine/template/**","authc");
//        filterChainDefinitionMap.put("/menu/**","authc");
//        filterChainDefinitionMap.put("/operation/**","authc");
//        filterChainDefinitionMap.put("/operator/**","authc");
//        filterChainDefinitionMap.put("/product/**","authc");
//        filterChainDefinitionMap.put("/role/**","authc");
//        filterChainDefinitionMap.put("/loginUser/list","authc");
//        filterChainDefinitionMap.put("/log/**","authc");
//        //authc:所有url必须通过认证才能访问，anon:所有url都可以匿名访问
//        filterChainDefinitionMap.put("/**", "corsAuthenticationFilter");
        filterChainDefinitionMap.put("/**", "anon");
        shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);
        //自定义过滤器
//        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("corsAuthenticationFilter", corsAuthenticationFilter());
        shiroFilter.setFilters(filterMap);

        return shiroFilter;
    }

    /**
     * Shiro生命周期处理器 * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证 * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能 * @return
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
