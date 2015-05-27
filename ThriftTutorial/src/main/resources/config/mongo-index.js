/**
 * 下面的脚本为创建索引的脚本，直接复制粘帖到mongo控制台即可。
 * <p>
 *     MongoDB使用 ensureIndex() 方法来创建索引。语法如下：
 *     db.COLLECTION_NAME.ensureIndex({KEY:1})
 *     语法中 KEY 值为你要创建的索引字段名称，1为指定按升序创建索引，如果你想按降序来创建索引指定为-1即可
 * </p>
 */

db.metadata.ensureIndex({"objectId": 1 })
db.metadata.ensureIndex({"MD5": 1})
db.metadata.ensureIndex({"logicPath": 1})
db.metadata.ensureIndex({"physicPath": 1})
db.metadata.ensureIndex({"systemMetadatas.folderId": 1})
db.metadata.ensureIndex({"systemMetadatas.sysCode": 1})
db.metadata.ensureIndex({"systemMetadatas.documentId": 1})
db.metadata.ensureIndex({"systemMetadatas.versionId": 1})