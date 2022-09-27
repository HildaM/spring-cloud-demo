package com.quan.goods.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

// value: 指定需要调用的名称
@FeignClient(value = "service-store", configuration = FeignOkhttpConfig.class)
public interface StoreClient {

    @GetMapping("/store/{skuID}")
    Map<String, Object> getStoreNum(@PathVariable String skuID);
}
