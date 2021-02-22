package org.seckill.controller;

import org.seckill.model.AyProduct;
import org.seckill.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {
    @Autowired
    private ProductServiceImpl productServiceImpl;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    private String findAll(Model model) {
        List<AyProduct> products = productServiceImpl.findAll();
        model.addAttribute("products", products);
        return "product_list";
    }

    @RequestMapping("/{id}/kill")
    public String killProduct(Model model, @PathVariable("id") Integer productId, @RequestParam("userId") Integer userId) {
        AyProduct ayProduct = productServiceImpl.killProduct(productId, userId);
        if (null != ayProduct) {
            return "success";
        }
        return "fail";
    }

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
