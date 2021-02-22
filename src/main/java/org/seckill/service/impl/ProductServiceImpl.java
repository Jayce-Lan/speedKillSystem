package org.seckill.service.impl;

import org.seckill.dao.ProductRepository;
import org.seckill.model.AyProduct;
import org.seckill.model.AyUserKillProduct;
import org.seckill.model.KillStatus;
import org.seckill.service.AyUserKillProductService;
import org.seckill.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AyUserKillProductService ayUserKillProductService;

    @Override
    public List<AyProduct> findAll() {
        try {
            List<AyProduct> ayProducts = productRepository.findAll();
            return ayProducts;
        } catch (Exception e) {
            System.err.println("查询错误：" + e);
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public AyProduct killProduct(Integer productId, Integer userId) {
        //查询商品
        AyProduct ayProduct = productRepository.findById(productId).get();
        //查询是否有库存
        if (ayProduct.getNumber() <= 0) {
            return null;
        }

        //更新商品信息
        ayProduct.setNumber(ayProduct.getNumber() - 1);
        ayProduct = productRepository.save(ayProduct);

        //建立新的秒杀记录
        AyUserKillProduct killProduct = new AyUserKillProduct();
        killProduct.setCreateTime(new Date());
        killProduct.setProductId(productId);
        killProduct.setUserId(userId);
        //设置秒杀状态
        killProduct.setState(KillStatus.SUCCESS.getCode());
        ayUserKillProductService.save(killProduct);
        return ayProduct;
    }
}
