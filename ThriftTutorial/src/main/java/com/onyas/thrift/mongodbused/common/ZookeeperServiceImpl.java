package com.onyas.thrift.mongodbused.common;

import com.github.zkclient.IZkChildListener;
import com.github.zkclient.IZkDataListener;
import com.github.zkclient.ZkClient;
import com.github.zkclient.ZkConnection;

import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class ZookeeperServiceImpl implements ZookeeperService {

    public static final int CONNECTION_TIMEOUT = 30000;

    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperServiceImpl.class);

    private final String scheme = "digest";

    private String charsetName = "UTF-8";

    private ZkClient zkClient;

    private ZooKeeper zooKeeper;

    private String connectString;

    private String userName;

    private String password;

    private List<ACL> acls = new ArrayList<ACL>();

    public ZookeeperServiceImpl(){}

    public void init() {
        if(StringUtils.isBlank(this.connectString)){
            this.connectString = ConfigProperties.getPropertyValue(ZookeeperConstants.ZK_CONNECT_STRING);
        }
        if(StringUtils.isBlank(this.userName)){
            this.userName = ConfigProperties.getPropertyValue(ZookeeperConstants.ZK_USER_NAME);
        }
        if (StringUtils.isBlank(this.password)) {
            this.password = ConfigProperties.getPropertyValue(ZookeeperConstants.ZK_USER_PWD);
        }

        try {
            Id id = new Id(scheme, DigestAuthenticationProvider.generateDigest(userName+":"+password));
            ACL acl = new ACL(ZooDefs.Perms.ALL, id);
            acls.add(acl);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e.getMessage(), e);
        }

        this.zkClient = new ZkClient(new Connection(this.connectString, CONNECTION_TIMEOUT, this.acls), CONNECTION_TIMEOUT);
        this.zooKeeper = zkClient.getZooKeeper();
        this.zooKeeper.addAuthInfo(scheme, (userName+":"+password).getBytes());
    }

    public static void main(String[] args) throws IOException {
//        ZkClient client = new ZkClient("10.142.49.230:2181");
//        Resource resource = new ClassPathResource("config/configuration.json");
//        String json = IOUtils.toString(resource.getInputStream());
//        LOGGER.info(json);
//        client.writeData(ConfigContants.CONFIGURATION_NODE, json.getBytes(Charset.forName("UTF-8")));
    }

    @Override
    public String readData(String path) {
        byte[] bytes = zkClient.readData(path);
        return new String(bytes, Charset.forName(charsetName));
    }

    @Override
    public void saveData(String path, String value) {
        createPersistentNode(path);
        zkClient.writeData(path, value.getBytes(Charset.forName(charsetName)));
    }

    @Override
    public void deleteData(String path) {
        zkClient.delete(path);
    }

    @Override
    public void createPersistentNode(String path) {
        zkClient.createPersistent(path, true);
    }

    @Override
    public void createEmphemerSequenceNode(String path, String value) {
        String parent = path.substring(0, path.lastIndexOf("/"));
        if(!isExists(parent)){
            createPersistentNode(parent);
        }
        zkClient.createEphemeralSequential(path, value.getBytes(Charset.forName(charsetName)));
    }

    @Override
    public String createSequenceNode(String path) {
        String persistentSequential = zkClient.createPersistentSequential(path, "".getBytes(Charset.forName(charsetName)));
        return persistentSequential.substring(persistentSequential.lastIndexOf("/") + 1, persistentSequential.length());
    }

    @Override
    public boolean isExists(String path) {
        return zkClient.exists(path);
    }

    @Override
    public List<String> getChildren(String path) {
        return zkClient.getChildren(path);
    }

    @Override
    public void addDataChangeListener(String path, IZkDataListener listener) {
        zkClient.subscribeDataChanges(path, listener);
    }

    @Override
    public void addChildrenChangeListener(String path, IZkChildListener listener) {
        zkClient.subscribeChildChanges(path, listener);
    }

    @Override
    public ZooKeeper getZooKeeper() {
        return zooKeeper;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConnectString(String connectString) {
        this.connectString = connectString;
    }

    class Connection extends ZkConnection{

        private List<ACL> acls = new ArrayList<ACL>();

        public Connection(String zkServers, int sessionTimeOut, List<ACL> acls){
            super(zkServers, sessionTimeOut);
            this.acls = acls;
        }

        @Override
        public String create(String path, byte[] data, CreateMode mode) throws KeeperException, InterruptedException {
            return getZooKeeper().create(path, data, acls, mode);
        }
    }
}
