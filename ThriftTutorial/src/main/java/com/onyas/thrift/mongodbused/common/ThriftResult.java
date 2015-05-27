package com.onyas.thrift.mongodbused.common;

/**
 * @ClassName: ThriftResult
 * @Description: thrift服务端调用后返回的结果对象，返回是调用toJson转换为json字符串
 */
public class ThriftResult {

    //调用是否成功
    private boolean isSuccess;
    //若调用要返回相关数据保存在此属性中（返回时转换成json）
    private Object data;
    //返回调用相关的提示信息
    private String message;

    public ThriftResult() {
    }

    public ThriftResult(boolean isSuccess, String message, Object data) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
