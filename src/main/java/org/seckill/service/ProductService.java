package org.seckill.service;

import org.seckill.model.AyProduct;

import java.util.Collection;
import java.util.List;

/**
 * 商品接口
 */
public interface ProductService {
    List<AyProduct> findAll();

    /**
     * 商品查询列表缓存
     *
     * @return
     */
    Collection<AyProduct> findAllCache();

    /**
     * 秒杀商品
     * @param productId 商品id
     * @param userId 用户id
     * @return 返回被秒杀商品
     */
    AyProduct killProduct(Integer productId, Integer userId);
}
