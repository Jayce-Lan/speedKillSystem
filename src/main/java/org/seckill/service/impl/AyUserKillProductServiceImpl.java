package org.seckill.service.impl;

import org.seckill.dao.AyUserKillProductRepository;
import org.seckill.model.AyUserKillProduct;
import org.seckill.service.AyUserKillProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AyUserKillProductServiceImpl implements AyUserKillProductService {
    @Autowired
    private AyUserKillProductRepository ayUserKillProductRepository;

    @Override
    public AyUserKillProduct save(AyUserKillProduct killProduct) {
        return ayUserKillProductRepository.save(killProduct);
    }
}
