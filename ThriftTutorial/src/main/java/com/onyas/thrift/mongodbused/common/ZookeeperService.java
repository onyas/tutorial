package com.onyas.thrift.mongodbused.common;

import com.github.zkclient.IZkChildListener;
import com.github.zkclient.IZkDataListener;
import org.apache.zookeeper.ZooKeeper;

import java.util.List;

/**
 * @ClassName: ZookeeperService
 * @Description:
 */
public interface ZookeeperService {

    String readData(String path);

    void saveData(String path, String value);

    void deleteData(String path);

    void createPersistentNode(String path);

    void createEmphemerSequenceNode(String path, String value);

    String createSequenceNode(String path);

    boolean isExists(String path);

    List<String> getChildren(String path);

    void addDataChangeListener(String path, IZkDataListener listener);

    void addChildrenChangeListener(String path, IZkChildListener listener);

    public ZooKeeper getZooKeeper();

    public void setConnectString(String connectString);

}
