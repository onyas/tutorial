package com.onyas.thrift.mongodbused.server;

import com.mongodb.*;
import com.onyas.thrift.mongodbused.common.ConfigConstants;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: MongoManager
 * @Description: Mongodb控制类，设置mongodb的连接池，获取链接等。
 */
public class MongoManager{

	private static final Logger LOGGER = LoggerFactory.getLogger(MongoManager.class);
	
    private DB db = null;
    
    private ConfigService configService;

    private MongoManager() {

    }

    /**
     * 根据名称获取DB，相当于是连接
     *
     * @return
     */
    public DB getDB() {
        if (db == null) {
            init();
        }
        return db;
    }

    public DBCollection getMetaCollection() {
        String metaCollectionName = configService.getConfigValueByPath(ConfigConstants.METASERVER_META_COLLECTION_NAME);
        return getDB().getCollection(metaCollectionName);
    }

    public DBCollection getUserCollection() {
        String userCollectionName = configService.getConfigValueByPath(ConfigConstants.METASERVER_USER_COLLECTION_NAME);
        return getDB().getCollection(userCollectionName);
    }

    /**
     * 初始化连接池，设置参数。
     */
    private void init() {
        String servers = configService.getConfigValueByPath(ConfigConstants.METASERVER_SERVERS);
        String dbName = configService.getConfigValueByPath(ConfigConstants.METASERVER_DBNAME);
        String userName = configService.getConfigValueByPath(ConfigConstants.METASERVER_DBUSER);
        String password = configService.getConfigValueByPath(ConfigConstants.METASERVER_DBPASS);
        int poolSize = Integer.valueOf(configService.getConfigValueByPath(ConfigConstants.METASERVER_DBPOOL_SIZE));
        int blockSize = Integer.valueOf(configService.getConfigValueByPath(ConfigConstants.METASERVER_DBBLOCK_SIZE));
        try {
            List<ServerAddress> addresses = getAddresses(servers);
            MongoOptions opt = new MongoOptions();
            opt.connectionsPerHost = poolSize;
            opt.threadsAllowedToBlockForConnectionMultiplier = blockSize;
            Mongo mongo = new Mongo(addresses, opt);
            db = mongo.getDB(dbName);
            db.authenticate(userName, password.toCharArray());
        } catch (MongoException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (UnknownHostException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private List<ServerAddress> getAddresses(String servers) throws UnknownHostException {
        List<ServerAddress> addresses = new ArrayList<ServerAddress>();
        String[] split = servers.split(",");
        for (String serverString : split) {
            String[] server = serverString.split(":");
            ServerAddress address = new ServerAddress(server[0], Integer.valueOf(server[1]));
            addresses.add(address);
        }
        return addresses;
    }
}
