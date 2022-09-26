package com.quan.goods.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class GoodSkuDTO {
    private Long id;
    private Long store;     // 需要从库存服务中获取
    private String name;
    private String skuNo;
    private String size;
    private String color;
    private BigDecimal price;
}
