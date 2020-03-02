package com.kexin.admin.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.kexin.admin.entity.pojo.InitData;
import com.kexin.admin.service.InitService;
import com.kexin.admin.service.UserService;
import com.kexin.common.util.ResponseEntity;
import com.kexin.common.util.ResponseEty;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录controller
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private final static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);


    @Autowired
    private Environment server;


    /**
     * @Description:用户登陆
     * @Author: 巫恒强  @Date: 2019/10/23 12:40
     * @Param: [map, session]
     * @Return: com.kexin.common.util.ResponseEntity
     */
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @PostMapping("account")
    @ResponseBody
    public ResponseEty loginAccount(@RequestBody Map map,HttpSession session){

        String username= (String) map.get("username");
        String password= (String) map.get("password");
        ResponseEty responseEty=new ResponseEty();

        Map[] tokens=new HashMap[2];
        Map admin=new HashMap();
        admin.put("token","admin-token");
        Map editor=new HashMap();
        editor.put("token","editor-token'");
        tokens[0]=admin;
        tokens[1]=editor;
        try {
            responseEty.setAny("token",tokens[0]);
            responseEty.setSuccess(20000);
            return responseEty;
//            responseEntity = userService.userLogin(userName,password);
        } catch (Exception e) {
            e.printStackTrace();
            return responseEty.failure("数据库连接错误,请检查数据库");
        }

    }

    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
/*
    @PostMapping("login/initdata")
    @ResponseBody
    public InitData initData(@RequestBody Map map){
        System.out.println(server.getProperty("address"));
        InitData data=new InitData();
        String userId= (String) map.get("userId");
        if(StringUtils.isNotEmpty(userId)){
            return initService.systemInit(Integer.parseInt(userId));
        }
        return initService.systemInit(1);
    }
*/




    /**
     * @Description:用户锁屏解锁功能
     * @Author: 巫恒强  @Date: 2019/10/23 12:39
     * @Param: [map]
     * @Return: com.kexin.common.util.ResponseEntity
     */
    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
/*
    @PostMapping("login/lock")
    @ResponseBody
    public ResponseEntity lock(@RequestBody Map map){

        String userId= (String) map.get("userId");
        Map passwordMap= (Map) map.get("password");
        if (passwordMap.size()==0){
            return ResponseEntity.failure("请输入密码");
        }
        String password= (String) passwordMap.get("password");
        ResponseEntity responseEntity=userService.userLock(userId,password);
        return responseEntity;
    }
*/

    /*@GetMapping("/getCaptcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //设置页面不缓存
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        String verifyCode = captchaProducer.createText();
        //将验证码放到HttpSession里面
        request.getSession().setAttribute(Constants.VALIDATE_CODE, verifyCode);
        LOGGER.info("本次生成的验证码为[" + verifyCode + "],已存放到HttpSession中");
        //设置输出的内容的类型为JPEG图像
        response.setContentType("image/jpeg");
        BufferedImage bufferedImage = captchaProducer.createImage(verifyCode);
        //写给浏览器
        ImageIO.write(bufferedImage, "JPEG", response.getOutputStream());
    }*/



/*
    @GetMapping(value = "")
    public String welcome() {
        return "redirect:admin";
    }

    @GetMapping(value = {"admin","admin/index"})
    public String adminIndex(RedirectAttributes attributes, ModelMap map) {
      *//*  Subject s = SecurityUtils.getSubject();
        attributes.addFlashAttribute(LOGIN_TYPE, LoginTypeEnum.ADMIN);
        if(s.isAuthenticated()) {
            return "redirect:index";
        }*//*
        return "redirect:toLogin";
    }

    @GetMapping(value = "toLogin")
    public String adminToLogin(HttpSession session, @ModelAttribute(LOGIN_TYPE) String loginType) {
        if(StringUtils.isBlank(loginType)) {
            LoginTypeEnum attribute = (LoginTypeEnum) session.getAttribute(LOGIN_TYPE);
            loginType = attribute == null ? loginType : attribute.name();
        }

        if(LoginTypeEnum.ADMIN.name().equals(loginType)) {
            session.setAttribute(LOGIN_TYPE,LoginTypeEnum.ADMIN);
            return "admin/login";
        }else {
            session.setAttribute(LOGIN_TYPE,LoginTypeEnum.PAGE);
            return "login";
        }
    }

    @GetMapping(value = "index")
    public String index(HttpSession session, @ModelAttribute(LOGIN_TYPE) String loginType) {
        if(StringUtils.isBlank(loginType)) {
            LoginTypeEnum attribute = (LoginTypeEnum) session.getAttribute(LOGIN_TYPE);
            loginType = attribute == null ? loginType : attribute.name();
        }
        if(LoginTypeEnum.ADMIN.name().equals(loginType)) {
//            AuthRealm.ShiroUser principal = (AuthRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
//            session.setAttribute("icon",StringUtils.isBlank(principal.getIcon()) ? "/static/admin/img/face.jpg" : principal.getIcon());
            return "admin/index";
        }else {
            return "index";
        }

    }

    @GetMapping("/getCaptcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //设置页面不缓存
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        String verifyCode = captchaProducer.createText();
        //将验证码放到HttpSession里面
        request.getSession().setAttribute(Constants.VALIDATE_CODE, verifyCode);
        LOGGER.info("本次生成的验证码为[" + verifyCode + "],已存放到HttpSession中");
        //设置输出的内容的类型为JPEG图像
        response.setContentType("image/jpeg");
        BufferedImage bufferedImage = captchaProducer.createImage(verifyCode);
        //写给浏览器
        ImageIO.write(bufferedImage, "JPEG", response.getOutputStream());
    }

    @PostMapping("admin/login")
    @SysLog("用户登录")
    @ResponseBody
    public ResponseEntity adminLogin(HttpServletRequest request) {

        return ResponseEntity.success("dd");
    }



    *//***
     * 获得用户所拥有的菜单列表
     * @return
     */
    /*
    @GetMapping("/admin/user/getUserMenu")
    @ResponseBody
    public List<ShowMenuVo> getUserMenu(){
//        String userId = MySysUser.id();
        List<ShowMenuVo> list = menuService.getShowMenuByUser("1");
        return list;
    }

   */

}
