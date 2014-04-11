package com.onyas.dynamiccompiling;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Arrays;
import java.util.Scanner;

import javax.tools.JavaCompiler;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

public class Calculator {

	public static void main(String[] args) throws Exception {
		System.out.println("请输入要计算的表达式");
		Scanner s = new Scanner(System.in);
		String expr = s.next();
		System.out.println(calculate(expr));
	}

	/**
	 * @param args
	 */
	public static double calculate(String expr) throws Exception {

		String className = "CalculatorMain";
		String methodName = "calculate";
		String source = "public class " + className + "{public static double "
				+ methodName + "() " + "{return " + expr + ";}}";

		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

		StandardJavaFileManager fileManager = compiler.getStandardFileManager(
				null, null, null);

		StringSourceJavaObject sourceObject = new Calculator.StringSourceJavaObject(
				className, source);

		Iterable<StringSourceJavaObject> fileObjects = Arrays
				.asList(sourceObject);

		// 获取编译类根路径，不然会报找不到类的错误
		String path = Class.class.getClass().getResource("/").getPath();
		Iterable<String> options = Arrays.asList("-d", path);

		CompilationTask task = compiler.getTask(null, fileManager, null,
				options, null, fileObjects);

		boolean result = task.call();
		if (result) {
			System.out.println("success");
			ClassLoader loader = Calculator.class.getClassLoader();
			Class<?> clazz = loader.loadClass(className);
			Method method = clazz.getMethod(methodName, new Class<?>[] {});
			Object value = method.invoke(null, new Object[] {});
			return (Double) value;
		} else {
			throw new IllegalArgumentException("错误的表达式");
		}
	}

	static class StringSourceJavaObject extends SimpleJavaFileObject {

		private String content = null;

		protected StringSourceJavaObject(String name, String content)
				throws Exception {
			super(URI.create("string:///" + name.replace(".", "/")
					+ Kind.SOURCE.extension), Kind.SOURCE);
			// super(new URI(name), Kind.SOURCE);
			this.content = content;
		}

		@Override
		public CharSequence getCharContent(boolean ignoreEncodingErrors)
				throws IOException {
			return content;
		}

	}

}
