package com.onyas.thrift.mongodbused.server;

import javax.annotation.PreDestroy;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onyas.thrift.mongodbused.gen.ThriftMetadataService;

public class MetadataServer{

	private static final Logger LOGGER = LoggerFactory.getLogger(MetadataServer.class);
	
    private MetadataServerProcessor processor;

    private TServer server;

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void start() {
        int port = 7988;
        try {
            TServerSocket serverTransport = new TServerSocket(port);
            TBinaryProtocol.Factory factory = new TBinaryProtocol.Factory();
            processor = new MetadataServerProcessor();
            TProcessor tProcessor = new ThriftMetadataService.Processor(processor);
            TThreadPoolServer.Args args = new TThreadPoolServer.Args(serverTransport).processor(tProcessor).protocolFactory(factory);
            server = new TThreadPoolServer(args);
            LOGGER.info("MetadataServer thrift server has started, listened on " + port);
            server.serve();
        } catch (TTransportException e) {
            LOGGER.error("error on start nameserver thrift server, detail:", e);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @PreDestroy
    public void stopServer() {
        LOGGER.info("MetaServer thrift server is stopping ");
        while (isServerRunning()) {
            server.stop();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                LOGGER.error("error :", e);
            }
        }
        LOGGER.info("MetaServer thrift server is stoped ");
    }

    public boolean isServerRunning() {
        if (server == null) {
            return false;
        } else {
            return server.isServing();
        }
    }


}
