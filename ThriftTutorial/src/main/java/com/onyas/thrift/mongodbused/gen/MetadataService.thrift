namespace java com.onyas.thrift.mongodbused.gen

service ThriftMetadataService{

	string createFileMetadata(1:string userName, 2:string pwd, 3:string bucketName, 4:string objectId, 5:string fileMetadatas)

    string updateFileMetadata(1:string userName, 2:string pwd, 3:string bucketName, 4:string objectId, 5:string fileMetadatas)

    string deleteFileMetadata(1:string userName, 2:string pwd, 3:string bucketName, 4:string objectId)

	string getFileMetadata(1:string userName, 2:string pwd, 3:string bucketName, 4:string objectId)

	string getFileMetadatas(1:string userName, 2:string pwd, 3:string bucketName, 4:string fsCriteria)

	string addUser(1:string userName, 2:string pwd, 3:string buckets)

    string updateUser(1:string userName, 2:string pwd, 3:string buckets)

    string getUser(1:string userName)

    string deleteUser(1:string userName)

}