package com.onyas.hadoop.tutorial;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class MyWordCount {

	/***
	 *1、Mapper类，第一个参数为输入的Key类型 ,第二个为输入的value类型
	 *第三个为输出的Key类型,第四个为输出的value类型
	 */
	static class MyMapper extends Mapper<LongWritable, Text, Text,IntWritable>{

		private Text word = new Text();
		private static final IntWritable one = new IntWritable();
		
		@Override
		protected void map(LongWritable key, Text value,Context context)
				throws IOException, InterruptedException {
			//得到文件的每一行
			String wordline = value.toString();
			//对得到每一行字符串进行分割
			StringTokenizer stringTokenizer = new StringTokenizer(wordline);
			while(stringTokenizer.hasMoreTokens()){
				//得到分割的每一个单 词
				String str = stringTokenizer.nextToken();

				//通过上下文对象，输入Map<Text,IntWritable>，这就是上面定义的输出类型 
				word.set(str);
				context.write(word, one);
			}
		}
		
	}
	
	/***
	 * 2、Reducer类 ，第一个参数为输入的Key类型 ,第二个为输入的value类型（这两个也就是Mapper类的输出类型）
	 * 第三个为输出的Key类型,第四个为输出的value类型
	 */
	static class MyReduce extends Reducer<Text,IntWritable,Text,IntWritable>{
		
		private IntWritable result = new IntWritable();
		
		//Reducer收到Map传来的结果时，已经把相同Key的数据放在一起，比如 zhangsan-->1,1,2这样的数据，所以我们只需要遍历出现的次数，然后在累加就可以了
		@Override
		protected void reduce(Text key, Iterable<IntWritable> values,Context context)
				throws IOException, InterruptedException {
			//用于累加
			int sum = 0;
			//循环遍历Iterable
			for(IntWritable value:values){
				sum +=value.get();
			}
			result.set(sum);
			context.write(key, result);
		}
		
	}
	
	/***
	 * 3、client类
	 * @param args
	 */
	public static void main(String[] args)throws Exception {
		 Configuration conf = new Configuration();
		    String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		    if (otherArgs.length != 2) {
		      System.err.println("Usage: MyWordCount <in> <out>");
		      System.exit(2);
		    }
		    Job job = new Job(conf, "word count");
		    //1、设置Job运行的类
		    job.setJarByClass(MyWordCount.class);
		    //2、设置 Mapper与Reducer类
		    job.setMapperClass(MyMapper.class);
		    job.setCombinerClass(MyReduce.class);
		    job.setReducerClass(MyReduce.class);
		    //3、设置输出Map的类型 
		    job.setOutputKeyClass(Text.class);
		    job.setOutputValueClass(IntWritable.class);
		    //4、设置输入输出路径
		    FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		    FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		    //5、提交Job，并等待完成
		    System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
	
}
