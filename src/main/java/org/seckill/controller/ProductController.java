package org.seckill.controller;

import org.seckill.model.AyProduct;
import org.seckill.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {
    @Autowired
    private ProductServiceImpl productServiceImpl;

//    @RequestMapping(value = "/all", method = RequestMethod.GET)
    private ModelAndView findAll() {
        List<AyProduct> products = productServiceImpl.findAll();

        ModelAndView mav = new ModelAndView();
        mav.addObject("products", products);
        mav.setViewName("product_list");

        return mav;
    }

    /**
     * 使用缓存替代原本的非缓存列表
     * @return
     */
    @RequestMapping("/all")
    private ModelAndView findAllCache() {
        Collection<AyProduct> allCache = productServiceImpl.findAllCache();

        ModelAndView mav = new ModelAndView();
        mav.addObject("products", allCache);
        mav.setViewName("product_list");

        return mav;
    }

    @RequestMapping("/{id}/kill")
    public ModelAndView killProduct(Model model, @PathVariable("id") Integer productId, @RequestParam("userId") Integer userId) {
        AyProduct ayProduct = productServiceImpl.killProduct(productId, userId);
        ModelAndView mav = new ModelAndView();

        if (null != ayProduct) {
            mav.setViewName("success");
        } else {
            mav.setViewName("fail");
        }

        return mav;
    }

    /////////////////////使用接口（前后端分离）

    @RequestMapping(value = "allmap", method = RequestMethod.GET)
    private Map<String, Object> findAllMap() {
        Map<String, Object> productMap = new HashMap<>();
        List<AyProduct> products = productServiceImpl.findAll();
        productMap.put("products", products);
        return productMap;
    }

    @RequestMapping(value = "/killmap", method = RequestMethod.POST)
    public String killProductMap(@RequestParam("productId") Integer productId, @RequestParam("userId") Integer userId) {
        AyProduct ayProduct = productServiceImpl.killProduct(productId, userId);
        if (null != ayProduct) {
            return "success";
        }
        return "fail";
    }
}
