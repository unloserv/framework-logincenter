package com.canghuang.logincenter.core;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author cs
 * @date 2019/2/13
 * @description
 */
@Data
@Accessors(chain = true)
public class IpAddressInfo {

    /**
     * 国家
     */
    String country;

    /**
     * 地区
     */
    String region;

    /**
     * 城市
     */
    String city;

    /**
     * isp供应商
     */
    String isp;
}
