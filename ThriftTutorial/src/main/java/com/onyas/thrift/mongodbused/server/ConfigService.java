package com.onyas.thrift.mongodbused.server;

import java.util.Map;

/**
 * @ClassName: ConfigService
 * @Description:
 */

public interface ConfigService {

    /**
     * 通过配置项名称获得配置的值
     *
     * @param path 配置项json路径
     * @return 配置项的值
     */
    public String getConfigValueByPath(String path);

    Map<String, Object> getConfigMapByPath(String path);

    void saveConfig(String path, String value, String caption);

    void deleteConfig(String path);
}
