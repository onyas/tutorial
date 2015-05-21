package com.onyas.mina.filetransferused.helper;

import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.handler.stream.StreamIoHandler;

public class Constant {

	public final static AttributeKey KEY_IN = new AttributeKey(StreamIoHandler.class, "in");

	public final static AttributeKey KEY_OUT = new AttributeKey(StreamIoHandler.class, "out");

	public final static AttributeKey KEY_FILE = new AttributeKey(Constant.class, "file");

	public final static AttributeKey KEY_TASK = new AttributeKey(Constant.class, "task");

	public final static String FILTER_OBJ = "object";
}
