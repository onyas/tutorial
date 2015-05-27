package com.onyas.thrift.mongodbused.server;

import java.util.List;

/**
 * @ClassName: ThriftMetadataService
 * @Description: 在MetadataServer端的元数据处理接口类（目前应有mongodb和oracle两个类型的实现类）
 */
public interface MetadataServerService {

    public void add(String userName, String pwd, String bucketName, String objectId, String fileMetadatas);

    public void update(String userName, String pwd, String bucketName, String objectId, String fileMetadatas);

    public void delete(String userName, String pwd, String bucketName, String objectId);

    public String getById(String userName, String pwd, String bucketName, String objectId);

    public List<String> getByCriteria(String userName, String pwd, String bucketName, String fsCriteria);

    public void addUser(String userName, String pwd, String buckets);

    public void updateUser(String userName, String pwd, String buckets);

    public String getUser(String userName);

    public void deleteUser(String userName);

}
