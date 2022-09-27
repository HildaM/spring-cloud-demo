package com.quan.goods.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quan.goods.dto.GoodSkuDTO;
import com.quan.goods.feign.StoreClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/goods")
@RestController
@RefreshScope
@Slf4j
public class GoodsController {

    @Autowired
    RestTemplate restTemplate;

    @Value("${env.name:}")
    String envName;

    @Value("${env.ip:}")
    String envIP;

    @Value("${spring.application.name}")
    String applicationName;

    // 通过Feign调用服务，相当于对restTemplate进行封装
    @Autowired
    StoreClient storeClient;

    @GetMapping("/config")
    public String getConfig() {
        return "当前读取到的环境为：" + envName + "\nIP地址为：" + envIP;
    }

    @GetMapping("/{skuID}")
    public GoodSkuDTO getGoodIndo(@PathVariable String skuID) throws JsonProcessingException {
        System.out.println("当前请求商品详情服务, 参数: " + skuID);

        GoodSkuDTO goodSkuDTO = new GoodSkuDTO();
        goodSkuDTO.setSkuNo("123456");
        goodSkuDTO.setColor("白色");
        goodSkuDTO.setName("潮流T恤男");
        goodSkuDTO.setSize("L");
        goodSkuDTO.setPrice(BigDecimal.valueOf(39.90));

        // 从库存服务中获取store的值
//        Optional<String> optional = Optional.ofNullable(     // 可以使用之前学过的Optional来实现非空判断
//                restTemplate.getForObject("http://service-store/store/" + skuID, String.class)
//        );
//        String res = optional.orElseGet(String::new);   // 如果为空，则返回空字符串
        

        // res是json格式，需要进行解析
//        ObjectMapper objectMapper = new ObjectMapper();
//        Map map = objectMapper.readValue(res, Map.class);


        // 采用Feign方式远程调用服务
        Map<String, Object> map = null;
        log.info("请求库存服务参数：" + skuID);
        long startTime = System.currentTimeMillis();

        try {
            map = storeClient.getStoreNum(skuID);
        } catch (Exception e) {
            log.error("{}请求{}，参数skuID={}出错", applicationName, "service-store", skuID);
        }

        log.info("请求返回数据：" + map);
        log.info("请求库存服务耗时：" + (System.currentTimeMillis() - startTime) + " ms");

        if (map != null) {
            Long num = Long.valueOf(map.get("num").toString());
            String port = (String) map.get("port");

            goodSkuDTO.setStore(num);

            System.out.println("获取当前库存服务端口：" + port);
        }

        return goodSkuDTO;
    }
}
