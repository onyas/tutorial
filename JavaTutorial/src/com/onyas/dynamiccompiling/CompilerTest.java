package com.onyas.dynamiccompiling;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

public class CompilerTest {

	/**
	 * @param args
	 *            ∂ØÃ¨±‡“Î ‰≥ˆ"hello,world"
	 */
	public static void main(String[] args) throws Exception {

		String source = "public class Main{public static void main(String[] args) "
				+ "{System.out.println(\"Hello,world\");}}";

		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

		StandardJavaFileManager fileManager = compiler.getStandardFileManager(
				null, null, null);

		StringSourceJavaObject sourceObject = new CompilerTest.StringSourceJavaObject(
				"Main", source);

		Iterable<StringSourceJavaObject> fileObjects = Arrays
				.asList(sourceObject);

		CompilationTask task = compiler.getTask(null, fileManager, null, null,
				null, fileObjects);

		boolean result = task.call();
		if (result) {
			System.out.println("success");
		}
	}

	static class StringSourceJavaObject extends SimpleJavaFileObject {

		private String content = null;

		protected StringSourceJavaObject(String name, String content) {
			super(URI.create("string:///" + name.replace(".", "/")
					+ Kind.SOURCE.extension), Kind.SOURCE);

			this.content = content;
		}

		public CharSequence getCharContent(boolean ignoreEncodingErrors)
				throws IOException {
			return content;
		}
	}

}
