package com.onyas.thrift.mongodbused.server;


public class BootStartMetadataServer {

    private BootStartMetadataServer() {
    }

    public static void main(String[] args) {
        MetadataServer server = new MetadataServer();
        server.start();
    }
}
