package com.onyas.mina.calculatordemo;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

@SuppressWarnings("restriction")
public class CalculatorHandler extends IoHandlerAdapter {
	private static Logger LOGGER = Logger.getLogger(CalculatorHandler.class);

	private ScriptEngine jsEngine = null;

	public CalculatorHandler() {
		ScriptEngineManager sfm = new ScriptEngineManager();
		jsEngine = sfm.getEngineByName("JavaScript");
		if (jsEngine == null) {
			throw new RuntimeException("找不到 JavaScript 引擎。");
		}
	}

	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		LOGGER.warn(cause.getMessage(), cause);
	}

	public void messageReceived(IoSession session, Object message)
			throws Exception {
		String expression = message.toString();
		if ("quit".equalsIgnoreCase(expression.trim())) {
			session.close(true);
			return;
		}
		try {
			Object result = jsEngine.eval(expression);
			session.write(result.toString());
		} catch (ScriptException e) {
			LOGGER.warn(e.getMessage(), e);
			session.write("Wrong expression, try again.");
		}
	}
}