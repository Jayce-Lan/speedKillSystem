package org.seckill.service;

import org.seckill.model.AyUserKillProduct;

public interface AyUserKillProductService {
    /**
     * 保存用户秒杀记录
     *
     * @param killProduct
     * @return
     */
    AyUserKillProduct save(AyUserKillProduct killProduct);
}
