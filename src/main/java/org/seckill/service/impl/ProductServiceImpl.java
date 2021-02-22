package org.seckill.service.impl;

import org.seckill.dao.ProductRepository;
import org.seckill.model.AyProduct;
import org.seckill.model.AyUserKillProduct;
import org.seckill.model.KillStatus;
import org.seckill.service.AyUserKillProductService;
import org.seckill.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AyUserKillProductService ayUserKillProductService;

    @Autowired
    private RedisTemplate redisTemplate;
    //定义缓存key
    private static final String KILL_PRODUCT_LIST = "kill_product_list";

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
    public Collection<AyProduct> findAllCache() {
        try {
            //从缓存中查询商品数据
            Map<Integer, AyProduct> productMap = redisTemplate.opsForHash().entries(KILL_PRODUCT_LIST);
            Collection<AyProduct> ayProducts = null;
            //若缓存中无法查询商品数据
            if (CollectionUtils.isEmpty(productMap)) {
                //从数据库中查询商品
                ayProducts = productRepository.findAll();
                //将List转换为Map
                productMap = convertToMap(ayProducts);
                //将商品数据移入缓存
                redisTemplate.opsForHash().putAll(KILL_PRODUCT_LIST, productMap);
                //设置缓存数据过期时间
                redisTemplate.expire(KILL_PRODUCT_LIST, 10000, TimeUnit.MICROSECONDS);
                return ayProducts;
            }
            ayProducts = productMap.values();
            return ayProducts;
        } catch (Exception e) {
            System.err.println("商品缓存错误：" + e);
            return Collections.EMPTY_LIST;
        }
    }

    private Map<Integer, AyProduct> convertToMap(Collection<AyProduct> ayProducts) {
        if (CollectionUtils.isEmpty(ayProducts)) {
            return Collections.EMPTY_MAP;
        }
        Map<Integer, AyProduct> productMap = new HashMap<>(ayProducts.size());
        for (AyProduct ayProduct : ayProducts) {
            productMap.put(ayProduct.getId(), ayProduct);
        }
        return productMap;
    }

    @Override
    @Transactional
    public AyProduct killProduct(Integer productId, Integer userId) {
        //查询商品
        AyProduct ayProduct = productRepository.findById(productId).get();

        long now = new Date().getTime();

        //查询是否有库存
        if (ayProduct.getNumber() <= 0) {
            return null;
        }

        if (ayProduct.getStartTime().getTime() > now || ayProduct.getEndTime().getTime() < now) {
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
