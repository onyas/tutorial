package com.onyas.thrift.mongodbused.server;

import java.util.List;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onyas.thrift.mongodbused.common.ThriftUtil;
import com.onyas.thrift.mongodbused.gen.ThriftMetadataService;

/**
 * @ClassName: MetadataServiceProcessor
 * @Description: MetadataServer的服务端处理器类
 */
public class MetadataServerProcessor  implements ThriftMetadataService.Iface {
	private static final Logger LOGGER = LoggerFactory.getLogger(MetadataServerProcessor.class);
	
	private MetadataServerService metadataServerService;
	
	public MetadataServerProcessor(){
		metadataServerService = new MetadataServerServiceMongoImpl();
	}
	

    @Override
    public String createFileMetadata(String userName, String pwd, String bucketName, String objectId, String fileMetadatas) throws TException {
        try {
            metadataServerService.add(userName, pwd, bucketName, objectId, fileMetadatas);
        } catch (MetadataServerException e) {
            LOGGER.error(e.getMessage(), e);
            return ThriftUtil.wrapFail(e.getMessage());
        }
        return ThriftUtil.wrapSuccess();
    }

    @Override
    public String updateFileMetadata(String userName, String pwd, String bucketName, String objectId, String fileMetadatas) throws TException {
        try {
            metadataServerService.update(userName, pwd, bucketName, objectId, fileMetadatas);
        } catch (MetadataServerException e) {
            LOGGER.error(e.getMessage(), e);
            return ThriftUtil.wrapFail(e.getMessage());
        }
        return ThriftUtil.wrapSuccess();
    }

    @Override
    public String deleteFileMetadata(String userName, String pwd, String bucketName, String objectId) throws TException {
        try {
            metadataServerService.delete(userName, pwd, bucketName, objectId);
        } catch (MetadataServerException e) {
            LOGGER.error(e.getMessage(), e);
            return ThriftUtil.wrapFail(e.getMessage());
        }
        return ThriftUtil.wrapSuccess();
    }

    @Override
    public String getFileMetadata(String userName, String pwd, String bucketName, String objectId) throws TException {
        String metadata = null;
        try {
            metadata = metadataServerService.getById(userName, pwd, bucketName, objectId);
        } catch (MetadataServerException e) {
            LOGGER.error(e.getMessage(), e);
            return ThriftUtil.wrapFail(e.getMessage());
        }
        return ThriftUtil.wrapSuccess(metadata);
    }

    @Override
    public String getFileMetadatas(String userName, String pwd, String bucketName, String fsCriteria) throws TException {
        List<String> metadatas = null;
        try {
            metadatas = metadataServerService.getByCriteria(userName, pwd, bucketName, fsCriteria);
        } catch (MetadataServerException e) {
            LOGGER.error(e.getMessage(), e);
            return ThriftUtil.wrapFail(e.getMessage());
        }
        return ThriftUtil.wrapSuccess(metadatas);
    }

    @Override
    public String addUser(String userName, String pwd, String buckets) throws TException {
        try {
            metadataServerService.addUser(userName, pwd, buckets);
        } catch (MetadataServerException e) {
            LOGGER.error(e.getMessage(), e);
            return ThriftUtil.wrapFail(e.getMessage());
        }
        return ThriftUtil.wrapSuccess();
    }

    @Override
    public String updateUser(String userName, String pwd, String buckets) throws TException {
        try {
            metadataServerService.updateUser(userName, pwd, buckets);
        } catch (MetadataServerException e) {
            LOGGER.error(e.getMessage(), e);
            return ThriftUtil.wrapFail(e.getMessage());
        }
        return ThriftUtil.wrapSuccess();
    }

    @Override
    public String getUser(String userName) throws TException {
        String user = null;
        try {
            user = metadataServerService.getUser(userName);
        } catch (MetadataServerException e) {
            LOGGER.error(e.getMessage(), e);
            return ThriftUtil.wrapFail(e.getMessage());
        }
        return ThriftUtil.wrapSuccess(user);
    }

    @Override
    public String deleteUser(String userName) throws TException {
        try {
            metadataServerService.deleteUser(userName);
        } catch (MetadataServerException e) {
            LOGGER.error(e.getMessage(), e);
            return ThriftUtil.wrapFail(e.getMessage());
        }
        return ThriftUtil.wrapSuccess();
    }

}
