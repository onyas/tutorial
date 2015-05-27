package com.onyas.thrift.mongodbused.server;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.BasicBSONList;

import java.util.*;
import java.util.Map.Entry;

/**
 * @ClassName: MetadataServerServiceMongoImpl
 * @Description: 元数据管理基于mongodb的实现类
 * 文档元数据格式：{"objectId":"XXXXXXXXXXXX", "fileMetadata":{ "XXX":"XXX", ..., biz:{XXX:XXXX, ...}}}
 * 用户元数据格式：{userName: XXX, password: XXX, buckets: []}
 */

public class MetadataServerServiceMongoImpl implements MetadataServerService {

    private final String mongoId = "_id";
    private String idKey = "objectId";
    private String userNameKey = "userName";
    private String pwdKey = "password";
    private String bucketKey = "buckets";

    private MongoManager mongoManager;
    

    /**
     * 将包含查询条件的Map对象（客户端传输至服务端的json转换而来）转换成查询使用的DBObject
     *
     * @param map
     * @return
     */
    private static DBObject getMapped(Map<String, Object> map) {
        DBObject dbObject = new BasicDBObject();
        Iterator<Entry<String, Object>> iterable = map.entrySet().iterator();
        while (iterable.hasNext()) {
            Entry<String, Object> entry = iterable.next();
            Object value = entry.getValue();
            String key = entry.getKey();
            if (key.startsWith("$") && value instanceof Map) {
                BasicBSONList basicBSONList = new BasicBSONList();
                Map<String, Object> conditionsMap = (Map) value;
                Set<Entry<String, Object>> entries = conditionsMap.entrySet();
                for (Entry<String, Object> k : entries) {
                    Object conditionsValue = k.getValue();
                    if (conditionsValue instanceof Collection) {
                        conditionsValue = convertArray(conditionsValue);
                    }
                    DBObject dbObject2 = new BasicDBObject(k.getKey(), conditionsValue);
                    basicBSONList.add(dbObject2);
                }
                value = basicBSONList;
            } else if (value instanceof Collection) {
                value = convertArray(value);
            } else if (!key.startsWith("$") && value instanceof Map) {
                value = getMapped((Map) value);
            }
            dbObject.put(key, value);
        }
        return dbObject;
    }

    private static Object[] convertArray(Object value) {
        return ((Collection) value).toArray();
    }

    @Override
    public void add(String userName, String pwd, String bucketName, String objectId, String fileMetadatas) {
        if (validateUser(userName, pwd, bucketName)) {
            if (fileIsExist(objectId)) {
                throw new MetadataServerException("MetadataServer错误，添加元数据时出错：objectId为：" + objectId + "的数据已存在。");
            }
            JSONObject jsonObject = JSON.parseObject(fileMetadatas);
            jsonObject.put(idKey, objectId);
            DBObject meta = new BasicDBObject(jsonObject);
            mongoManager.getMetaCollection().insert(meta);
        }
    }

    @Override
    public void update(String userName, String pwd, String bucketName, String objectId, String fileMetadatas) {
        if (validateUser(userName, pwd, bucketName)) {
            if (!fileIsExist(objectId)) {
                throw new MetadataServerException("MetadataServer错误，更新元数据时出错：objectId为：" + objectId + "的数据不存在。 ");
            }
            JSONObject jsonObject = JSON.parseObject(fileMetadatas);
            jsonObject.put(idKey, objectId);
            DBObject oldMeta = new BasicDBObject(idKey, objectId);
            DBObject newMeta = new BasicDBObject(jsonObject);
            mongoManager.getMetaCollection().update(oldMeta, newMeta);
        }
    }

    @Override
    public void delete(String userName, String pwd, String bucketName, String objectId) {
        if (validateUser(userName, pwd, bucketName)) {
            if (!fileIsExist(objectId)) {
                throw new MetadataServerException("MetadataServer错误，删除元数据时出错：objectId为：" + objectId + " 的数据不存在。");
            }
            DBObject meta = new BasicDBObject(idKey, objectId);
            mongoManager.getMetaCollection().remove(meta);
        }
    }

    @Override
    public String getById(String userName, String pwd, String bucketName, String objectId) {
        String result = null;
        if (validateUser(userName, pwd, bucketName)) {
            DBObject meta = new BasicDBObject(idKey, objectId);
            DBCursor results = mongoManager.getMetaCollection().find(meta);
            if (results.hasNext()) {
                DBObject next = results.next();
                next.removeField(mongoId);
                result = next.toString();
            } else {
                throw new MetadataServerException("MetadataServer错误，根据ID获取元数据时出错：objectId为：" + objectId + "的数据不存在");
            }
        }
        return result;
    }

    @Override
    public List<String> getByCriteria(String userName, String pwd, String bucketName, String fsCriteria) {
        List<String> list = new ArrayList<String>();
        if (validateUser(userName, pwd, bucketName)) {
            Map<String, Object> criteriaMap = JSON.parseObject(fsCriteria, Map.class);
            Map<String, Object> criterions = (Map)criteriaMap.get("criterions");
            Map<String, Object> orders = (Map) criteriaMap.get("orders");
            int limit = (Integer)criteriaMap.get("limit");

            DBObject criteria = getMapped(criterions);
            DBObject dbOrders = new BasicDBObject(orders);
            DBCursor results = mongoManager.getMetaCollection().find(criteria).sort(dbOrders);
            if(limit > 0) {
                results.limit(limit);
            }

            while (results.hasNext()) {
                DBObject next = results.next();
                next.removeField(mongoId);
                list.add(next.toString());
            }
        }
        return list;
    }

    /**
     * 添加新用户
     *
     * @param userName 用户名
     * @param pwd      密码
     * @param buckets  用户拥有的buckets，格式“bucket1，bucket2，...”
     */
    @Override
    public void addUser(String userName, String pwd, String buckets) {
        if (userIsExist(userName)) {
            throw new MetadataServerException("MetadataServer错误，添加用户时出错：用户" + userName + "已存在。");
        }
        BasicDBObject user = new BasicDBObject(userNameKey, userName).append(pwdKey, pwd);
        if (StringUtils.isNotBlank(buckets)) {
            user.append(bucketKey, buckets.split(","));
        }
        mongoManager.getUserCollection().insert(user);
    }

    @Override
    public void updateUser(String userName, String pwd, String buckets) {
        if (!userIsExist(userName)) {
            throw new MetadataServerException("MetadataServer错误，更新用户时出错：用户" + userName + "不存在。");
        }
        BasicDBObject oldUser = new BasicDBObject(userNameKey, userName);
        BasicDBObject newUser = new BasicDBObject(userNameKey, userName).append(pwdKey, pwd);
        if (StringUtils.isNotBlank(buckets)) {
            newUser.append("buckets", buckets.split(","));
        }
        mongoManager.getUserCollection().update(oldUser, newUser);
    }

    @Override
    public String getUser(String userName) {
        String result;
        DBObject user = new BasicDBObject(userNameKey, userName);
        DBCursor results = mongoManager.getUserCollection().find(user);
        if (results.hasNext()) {
            result = results.next().toString();
        } else {
            throw new MetadataServerException("MetadataServer错误，获取用户时出错：用户" + userName + "不存在。");
        }
        return result;
    }

    @Override
    public void deleteUser(String userName) {
        if (!userIsExist(userName)) {
            throw new MetadataServerException("MetadataServer错误，删除用户时出错：用户" + userName + "不存在。");
        }
        DBObject user = new BasicDBObject(userNameKey, userName);
        mongoManager.getUserCollection().remove(user);
    }

    private boolean fileIsExist(String objectId) {
        DBObject meta = new BasicDBObject(idKey, objectId);
        DBCursor results = mongoManager.getMetaCollection().find(meta);
        return results.hasNext();
    }

    private boolean userIsExist(String userName) {
        DBObject user = new BasicDBObject(userNameKey, userName);
        DBCursor results = mongoManager.getUserCollection().find(user);
        return results.hasNext();
    }

    private boolean validateUser(String userName, String pwd, String bucketName) {
        DBObject user = new BasicDBObject(userNameKey, userName);
        DBCursor users = mongoManager.getUserCollection().find(user);
        if (!users.hasNext()) {
            throw new MetadataServerException("MetadataServer错误，校验用户时出错：用户" + userName + "不存在。");
        } else {
            DBObject next = users.next();
            String password = (String) next.get(pwdKey);
            List<String> buckets = (List<String>) next.get("buckets");
            if (!password.equals(pwd)) {
                throw new MetadataServerException("MetadataServer错误，校验用户时出错：用户密码错误");
            }
            if (!buckets.contains(bucketName)) {
                throw new MetadataServerException("MetadataServer错误，校验用户时出错：用户对名为" + bucketName + "的bucket无操作权限。");
            }
            return true;
        }
    }

    public void setMongoManager(MongoManager mongoManager) {
        this.mongoManager = mongoManager;
    }

}
