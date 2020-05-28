package com.kexin.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.CartNumFirst;
import com.kexin.admin.entity.tables.Products;
import com.kexin.admin.service.CartNumFirstService;
import com.kexin.admin.service.ProductsService;
import com.kexin.admin.service.SystemLogService;
import com.kexin.common.annotation.SysLog;
import com.kexin.common.base.Data;
import com.kexin.common.base.PageDataBase;
import com.kexin.common.util.ResponseEty;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.List;

/**
 * 产品配置管理controller层
 */
@Controller
@RequestMapping("product")
public class ProductController {


    @Autowired
    ProductsService productService;

    @Autowired
    SystemLogService systemLogService;//系统日志记录service

    @Autowired
    CartNumFirstService cartNumFirstService;//首字母序号service


    @GetMapping("list")
    @ResponseBody
    @SysLog("产品列表获取")
    public PageDataBase<Products> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                       @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                       @RequestParam(value = "sort")String sort,
                                       @RequestParam(value = "useFlag",defaultValue = "")String useFlag,
                                       @RequestParam(value = "title",defaultValue = "") String title,
                                       @RequestHeader(value="token",required = false) Integer token,
                                       ServletRequest request){
//        Map map = WebUtils.getParametersStartingWith(request, "s_");
        PageDataBase<Products> productPageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<Products> productWrapper = new QueryWrapper<>();
        productWrapper.orderByDesc("START_DATE");

        if (sort.equals("+id")){
            productWrapper.orderByAsc("PRODUCT_ID");
        }else{
            productWrapper.orderByDesc("PRODUCT_ID");
        }
        if (StringUtils.isNotEmpty(useFlag)){
            productWrapper.eq("USE_FLAG",useFlag);
        }
        if (StringUtils.isNotEmpty(title)){
            productWrapper.like("PRODUCT_NAME",title);
        }
        IPage<Products> productPage = productService.page(new Page<>(page,limit),productWrapper);
        data.setTotal(productPage.getTotal());
        productPage.getRecords().forEach(r->r.setCartNumFirst(cartNumFirstService.getById(r.getCartnumFirstId())));//外键实体添加
        data.setItems(productPage.getRecords());
        productPageData.setData(data);
        systemLogService.saveMachineLog(token,"查询","查询了产品种类列表");

        return productPageData;
    }


    @PostMapping("create")
    @ResponseBody
    @SysLog("新增产品数据")
    public ResponseEty create(@RequestBody  Products product,@RequestHeader(value="token",required = false) Integer token){
        if(StringUtils.isBlank(product.getProductCode())){
            return ResponseEty.failure("产品编号不能为空");
        }
        if(StringUtils.isBlank(product.getProductName())){
            return ResponseEty.failure("产品名称不能为空");
        }
        if (productService.productCountByCode(product.getProductCode())>0){
            return ResponseEty.failure("产品编号已使用,请重新输入");
        }
        if (productService.productCountByName(product.getProductName())>0){
            return ResponseEty.failure("产品名称已使用,请重新输入");
        }
        productService.saveProducts(product);
        if(product.getProductId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        systemLogService.saveMachineLog(token,"新增","新增了产品种类:"+product.getProductName());
        return ResponseEty.success("保存成功");
    }

    @PostMapping("update")
    @ResponseBody
    @SysLog("保存产品修改数据")
    public ResponseEty update(@RequestBody  Products product,@RequestHeader(value="token",required = false) Integer token){
        if(product.getProductId()==null){
            return ResponseEty.failure("产品ID不能为空");
        }
        if(StringUtils.isBlank(product.getProductCode())){
            return ResponseEty.failure("产品编号不能为空");
        }
        if(StringUtils.isBlank(product.getProductName())){
            return ResponseEty.failure("产品名称不能为空");
        }
        Products oldProductss = productService.getById(product.getProductId());
        if(StringUtils.isNotBlank(product.getProductCode())){
            if(!product.getProductCode().equals(oldProductss.getProductCode())){
                if(productService.productCountByCode(product.getProductCode())>0){
                    return ResponseEty.failure("该产品编码已经使用");
                }
            }
        }
        if(StringUtils.isNotBlank(product.getProductName())){
            if(!product.getProductName().equals(oldProductss.getProductName())){
                if(productService.productCountByName(product.getProductName())>0){
                    return ResponseEty.failure("该产品名称已经使用");
                }
            }
        }
        productService.updateProducts(product);

        if(product.getProductId()==null){
            return ResponseEty.failure("保存信息出错");
        }
        systemLogService.saveMachineLog(token,"更新","更新了产品种类:"+product.getProductName());

        return ResponseEty.success("操作成功");
    }

    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除产品数据(单个)")
    public ResponseEty delete(@RequestParam(value = "id",required = false)Integer id,@RequestHeader(value="token",required = false) Integer token){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        Products product=productService.getById(id);
        if(product == null){
            return ResponseEty.failure("产品不存在");
        }
        productService.deleteProducts(product);
        systemLogService.saveMachineLog(token,"删除","删除了产品种类:"+product.getProductName());

        return ResponseEty.success("删除成功");
    }

    @PostMapping("updateUseFlag")
    @ResponseBody
    @SysLog("禁用或者启用产品")
    public ResponseEty updateUseFlag(@RequestParam(value = "id",required = false)Integer id,@RequestHeader(value="token",required = false) Integer token){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        Products product=productService.getById(id);
        if(product == null){
            return ResponseEty.failure("产品不存在");
        }
        productService.lockProducts(product);
        systemLogService.saveMachineLog(token,"禁用","禁用了产品种类:"+product.getProductName());

        return ResponseEty.success("操作成功");
    }
}
