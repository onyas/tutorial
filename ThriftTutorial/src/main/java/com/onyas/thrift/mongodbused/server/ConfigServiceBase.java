package com.onyas.thrift.mongodbused.server;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onyas.thrift.mongodbused.common.ConfigException;

public abstract class ConfigServiceBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigServiceBase.class);

    private String valueKey = "value";

    protected Map<String, Object> configurations;

    public abstract void init();

    public String getConfigValueByPath(String path) {
        return (String) getConfigMapByPath(path).get(valueKey);
    }

    public Map<String, Object> getConfigMapByPath(String path) {
        String[] paths = path.split("/");
        Map<String, Object> current = configurations;
        for (int i = 1; i < paths.length; i++) {
            current = ((Map<String, Object>) current.get(paths[i]));
            if (current == null) {
                throw new ConfigException("获取配置时出错:" + path + "中 /" + paths[i] + "节点不存在。");
            }
        }
        return current;
    }

    public void saveConfig(String path, String value, String caption) {
        String[] paths = path.split("/");
        Map<String, Object> parent;
        Map<String, Object> current = configurations;
        for (int i = 1; i < paths.length; i++) {
            parent = current;
            current = ((Map<String, Object>) current.get(paths[i]));
            if (current == null) {
                current = new HashMap<String, Object>();
                parent.put(paths[i], current);
            }
        }
        current.put(valueKey, value);
        current.put("caption", caption);
        saveConfiguration();
    }

    public void deleteConfig(String path) {
        String[] paths = path.split("/");
        Map<String, Object> parent = null;
        Map<String, Object> current = configurations;
        for (int i = 1; i < paths.length; i++) {
            parent = current;
            current = ((Map<String, Object>) current.get(paths[i]));
            if (current == null) {
                throw new ConfigException("获取配置时出错：" + path + "中 /" + paths[i] + "节点不存在。");
            }
        }
        parent.remove(paths[paths.length - 1]);
        saveConfiguration();
    }

    protected abstract void saveConfiguration();

}
