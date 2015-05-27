package com.onyas.thrift.mongodbused.common;

/**
 * @ClassName: ConfigContants
 * @Description:
 */
public abstract class ConfigConstants {

    //存放配置的zookeeper节点路径
    public static final String CONFIGURATION_NODE = "/udsfs/configuration";

    //存放存储资源配置的Zookeeper根节点
    public static final String STORAGERESOURCE_NODE = "/udsfs/storageResource";

    //系统序列根节点
    public static final String SEQUENCES_NODE = "/udsfs/sequence";

    //-------------------------------通用系统参数配置-----------------------------//
    //系统临时文件存放路径
    public static final String SYS_TEMP_LINUX = "/uds/fs/sys/sys_temp_dir/linux";
    public static final String SYS_TEMP_WINDOWS = "/uds/fs/sys/sys_temp_dir/windows";
    //默认存储资源码
    public static final String SYS_DEFAULT_STORE_RESOURCE_CODE = "/uds/fs/sys/default_store_resource_code";


    //-------------------------------metaserver配置------------------------------//
    //在zookeeper上的集群节点名称
    public static final String METASERVER_CLUSTER_NAME = "/uds/fs/metaserver/cluster_name";
    //在zookeeper上的集群节点IP
    public static final String METASERVER_CLUSTER_IPS = "/uds/fs/metaserver/cluster_ips";
    //mongodb地址
    public static final String METASERVER_SERVERS = "/uds/fs/metaserver/mongodb_servers";
    //mongodb数据库名
    public static final String METASERVER_DBNAME = "/uds/fs/metaserver/dbname";
    //mongodb用户名
    public static final String METASERVER_DBUSER = "/uds/fs/metaserver/dbuser";
    //mongodb密码
    public static final String METASERVER_DBPASS = "/uds/fs/metaserver/dbpass";
    //mongodb的连接池大小
    public static final String METASERVER_DBPOOL_SIZE = "/uds/fs/metaserver/dbpool_size";
    //mongodb连接池等待连接的大小
    public static final String METASERVER_DBBLOCK_SIZE = "/uds/fs/metaserver/dbblock_size";
    //保存元数据的集合名
    public static final String METASERVER_META_COLLECTION_NAME = "/uds/fs/metaserver/meta_collection_name";
    //保存用户信息的集合名
    public static final String METASERVER_USER_COLLECTION_NAME = "/uds/fs/metaserver/user_collection_name";
    //metaserver的thrift服务地址
    public static final String METASERVER_THRIFT_SERVER_HOSTS = "/uds/fs/metaserver/thrift_server_hosts";
    //thrift服务监听端口
    public static final String METASERVER_THRIFT_SERVER_PORT = "/uds/fs/metaserver/thrift_server_port";

    //--------------------------------nameserver配置-------------------------------//
    //在zookeeper上的集群节点名称
    public static final String NAMESERVER_CLUSTER_NAME = "/uds/fs/nameserver/cluster_name";
    //在zookeeper上的集群节点IP
    public static final String NAMESERVER_CLUSTER_IPS = "/uds/fs/nameserver/cluster_ips";
    //riak地址
    public static final String NAMESERVER_RIAK_HOSTS = "/uds/fs/nameserver/riak_hosts";
    //riak提供的pb协议访问端口
    public static final String NAMESERVER_RIAK_PORT = "/uds/fs/nameserver/riak_port";
    //riak单节点连接池大小
    public static final String NAMESERVR_RIAK_POOLSIZE = "/uds/fs/nameserver/riak_poolSize";
    //riak单节点连接池初始化的连接数
    public static final String NAMESERVR_RIAK_INITPOOLSIZE = "/uds/fs/nameserver/riak_initPoolSize";
    //保存文件路径信息的bucket名称
    public static final String NAMESERVER_NAMEBUCKET_NAME = "/uds/fs/nameserver/nameBucket_name";
    //保存用户信息的bucket名称
    public static final String NAMESERVER_USERBUCKET_NAME = "/uds/fs/nameserver/userBucket_name";
    //nameserver的thrift服务地址
    public static final String NAMESERVER_THRIFT_SERVER_HOSTS = "/uds/fs/nameserver/thrift_server_hosts";
    //nameserver的thrift服务监听端口
    public static final String NAMESERVER_THRIFT_SERVER_PORT = "/uds/fs/nameserver/thrift_server_port";

    //--------------------------------contentserver配置-------------------------------//
    //在zookeeper上的集群节点名称
    public static final String CONTENTSERVER_CLUSTER_NAME = "/uds/fs/contentserver/cluster_name";
    //在zookeeper上的集群节点IP
    public static final String CONTENTSERVER_CLUSTER_IPS = "/uds/fs/contentserver/cluster_ips";

    public static final String CONTENTSERVER_SERVER_PORT = "/uds/fs/contentserver/server_port";

    public static final String CONTENTSERVER_MESSAGE_PORT = "/uds/fs/contentserver/message_port";

    //--------------------------------riakcs内容服务器配置-------------------------------//
    public static final String RIAKCS_SERVER_ACCESSKEY = "/uds/fs/riakcsContentserver/access_key";

    public static final String RIAKCS_SERVER_SECRETKEY = "/uds/fs/riakcsContentserver/secret_key";

    public static final String RIAKCS_SERVER_HTTPPORT = "/uds/fs/riakcsContentserver/http_prot";

}
