package com.onyas.thrift.mongodbused.common;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @ClassName: ThriftUtil
 * @Description:
 */
public class ThriftUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThriftUtil.class);

    private ThriftUtil() {
    }

    public static String wrapSuccess() {
        ThriftResult result = new ThriftResult();
        result.setSuccess(true);
        return toJson(result);
    }

    public static String wrapSuccess(Object data) {
        ThriftResult result = new ThriftResult();
        result.setSuccess(true);
        result.setData(data);
        return toJson(result);
    }

    public static String wrapSuccess(String message, Object data) {
        ThriftResult result = new ThriftResult(true, message, data);
        return toJson(result);
    }

    public static String wrapFail(String message) {
        ThriftResult result = new ThriftResult();
        result.setSuccess(false);
        result.setMessage(message);
        return toJson(result);
    }

    public static String wrapResult(ThriftResult result) {
        return toJson(result);
    }

    public static ThriftResult parseResult(String result) {
        ObjectMapper objectMapper = new ObjectMapper();
        ThriftResult thriftResult = null;
        try {
            thriftResult = objectMapper.readValue(result, ThriftResult.class);
        } catch (IOException e) {
            LOGGER.error("parseResult to json error:", e);
        }
        return thriftResult;
    }

    private static String toJson(ThriftResult result) {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(result);
        } catch (IOException e) {
            LOGGER.error("convert data to json error:", e);
        }
        return json;
    }
}
