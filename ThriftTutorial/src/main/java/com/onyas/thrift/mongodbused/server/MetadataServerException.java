package com.onyas.thrift.mongodbused.server;

/**
 * @ClassName: MetadataServerException
 * @Description:
 */
public class MetadataServerException extends RuntimeException {
    public MetadataServerException() {
    }

    public MetadataServerException(String message) {
        super(message);
    }

    public MetadataServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public MetadataServerException(Throwable cause) {
        super(cause);
    }
}
