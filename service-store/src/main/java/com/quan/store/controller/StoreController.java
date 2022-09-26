package com.quan.store.controller;


import org.bouncycastle.crypto.agreement.jpake.JPAKEPrimeOrderGroup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/store")
@RestController
public class StoreController {

    @Value("${server.port}")
    String port;

    @GetMapping("/{skuID}")
    public Map<String, Object> getStoreNum(@PathVariable String skuID) {
        System.out.println("当前请求服务: " + port + " 参数: " + skuID);
        HashMap<String, Object> map = new HashMap<>();
        map.put("port", port);
        map.put("num", 100L);
        return map;
    }
}
