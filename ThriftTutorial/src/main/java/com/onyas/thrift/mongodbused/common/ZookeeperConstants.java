package com.onyas.thrift.mongodbused.common;

public class ZookeeperConstants {
    //zookeeper连接串在配置文件中的属性名
    public static final String ZK_CONNECT_STRING = "ZK_CONNECT";
    //zookeeper用户名在配置文件中的属性名
    public static final String ZK_USER_NAME = "uds.fs.zookeeper.u";
    //zookeeper用户密码在配置文件中的属性名
    public static final String ZK_USER_PWD = "uds.fs.zookeeper.p";

    //系统集群节点在zookeeper上的路径
    public static final String ZK_CLUSTER_NODE = "uds.fs.zookeeper.fsCluster.nodePath";
}
