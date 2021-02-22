package org.seckill.dao;

import org.seckill.model.AyProduct;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 负责商品CRUD
 */
public interface ProductRepository extends JpaRepository<AyProduct, Integer> {

}
