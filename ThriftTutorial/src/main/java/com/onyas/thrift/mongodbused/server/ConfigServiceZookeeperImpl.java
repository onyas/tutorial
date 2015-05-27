package com.onyas.thrift.mongodbused.server;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.zkclient.IZkDataListener;
import com.onyas.thrift.mongodbused.common.ConfigConstants;
import com.onyas.thrift.mongodbused.common.ZookeeperService;

/**
 * @ClassName: ConfigServiceZookeeperImpl
 * @Description: 通过Zookeeper实现分布式系统的配置集中管理
 */
public class ConfigServiceZookeeperImpl extends ConfigServiceBase implements ConfigService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigServiceZookeeperImpl.class);

    protected ZookeeperService zookeeperService;

    public ConfigServiceZookeeperImpl() {
    }

    public ConfigServiceZookeeperImpl(ZookeeperService zookeeperService) {
        setZookeeperService(zookeeperService);
        loadConfiguration();
    }

    public static void main(String[] args) {
//        Resource resource = new ClassPathResource("config/configuration.json");
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            Map<String, Object> configItemMap = mapper.readValue(resource.getInputStream(), new TypeReference<Map<String, Object>>() {
//            });
//            configItemMap.keySet();
//        } catch (IOException e) {
//            LOGGER.error(e.getMessage(), e);
//        }
    }

    /**
     * 初始化
     */
    public void init() {
        //给保存配置信息的zookeeper节点添加监听器
        zookeeperService.addDataChangeListener(ConfigConstants.CONFIGURATION_NODE, new ZkDataListenerImpl());
        loadConfiguration();
    }

    /**
     * 从zookeeper中读取配置到内存中，并添加zookeeper监听，节点内容改变后重新读取配置。
     */
    private void loadConfiguration() {
        LOGGER.info("从zookeeper读取配置信息开始...");
        String configurationJson = zookeeperService.readData(ConfigConstants.CONFIGURATION_NODE);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            configurations = objectMapper.readValue(configurationJson, Map.class);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        LOGGER.info("从zookeeper读取配置完成。");
    }

    public ZookeeperService getZookeeperService() {
        return zookeeperService;
    }

    public void setZookeeperService(ZookeeperService zookeeperService) {
        this.zookeeperService = zookeeperService;
    }

    protected void saveConfiguration() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String configurations = mapper.writeValueAsString(this.configurations);
            zookeeperService.saveData(ConfigConstants.CONFIGURATION_NODE, configurations);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    class ZkDataListenerImpl implements IZkDataListener {

        ZkDataListenerImpl() {
        }

        @Override
        public void handleDataChange(String dataPath, byte[] data) throws Exception {
            loadConfiguration();
        }

        @Override
        public void handleDataDeleted(String dataPath) throws Exception {
        }
    }
}
