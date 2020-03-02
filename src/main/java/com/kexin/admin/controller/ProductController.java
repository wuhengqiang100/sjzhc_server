package com.kexin.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.tables.Products;
import com.kexin.admin.service.ProductsService;
import com.kexin.common.annotation.SysLog;
import com.kexin.common.base.Data;
import com.kexin.common.base.PageDataBase;
import com.kexin.common.util.ResponseEty;
import org.apache.commons.lang3.StringUtils;
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
    

    //@CrossOrigin(origins = "http://192.168.0.100:4200", maxAge = 3600)
    @GetMapping("list")
    @ResponseBody
    @SysLog("产品列表获取")
    public PageDataBase<Products> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                       @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                       @RequestParam(value = "sort")String sort,
                                       @RequestParam(value = "useFlag",defaultValue = "")String useFlag,
                                       @RequestParam(value = "title",defaultValue = "") String title,
                                       ServletRequest request){
//        Map map = WebUtils.getParametersStartingWith(request, "s_");
        PageDataBase<Products> productPageData = new PageDataBase<>();
        Data data=new Data();
        QueryWrapper<Products> productWrapper = new QueryWrapper<>();
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
        data.setItems(productPage.getRecords());
        productPageData.setData(data);
        return productPageData;
    }


    @PostMapping("create")
    @ResponseBody
    @SysLog("新增产品数据")
    public ResponseEty create(@RequestBody  Products product){
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
        return ResponseEty.success("保存成功");
    }

    @PostMapping("update")
    @ResponseBody
    @SysLog("保存产品修改数据")
    public ResponseEty update(@RequestBody  Products product){
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
        return ResponseEty.success("操作成功");
    }

    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除产品数据(单个)")
    public ResponseEty delete(@RequestParam(value = "id",required = false)Integer id){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        Products product=productService.getById(id);
        if(product == null){
            return ResponseEty.failure("产品不存在");
        }
        productService.deleteProducts(product);
        return ResponseEty.success("删除成功");
    }
    //
//    @RequiresPermissions("sys:user:delete")
    @PostMapping("deleteSome")
    @ResponseBody
    @SysLog("删除产品数据(多个)")
    public ResponseEty deleteSome(@RequestBody List<Products> Products){
        if(Products == null || Products.size()==0){
            return ResponseEty.failure("请选择需要删除的信息");
        }
        Products.forEach(m -> productService.deleteProducts(m));
        return ResponseEty.success("批量删除成功");
    }


    @PostMapping("updateUseFlag")
    @ResponseBody
    @SysLog("禁用或者启用产品")
    public ResponseEty updateUseFlag(@RequestParam(value = "id",required = false)Integer id){
        if(id==null){
            return ResponseEty.failure("参数错误");
        }
        Products product=productService.getById(id);
        if(product == null){
            return ResponseEty.failure("产品不存在");
        }
        productService.lockProducts(product);
        return ResponseEty.success("操作成功");
    }
}
