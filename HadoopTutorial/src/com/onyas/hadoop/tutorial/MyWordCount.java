package com.onyas.hadoop.tutorial;

import java.io.IOException;

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

		@Override
		protected void map(LongWritable key, Text value,Context context)
				throws IOException, InterruptedException {
			super.map(key, value, context);
		}
		
	}
	
	/***
	 * 2、Reducer类 ，第一个参数为输入的Key类型 ,第二个为输入的value类型（这两个也就是Mapper类的输出类型）
	 * 第三个为输出的Key类型,第四个为输出的value类型
	 */
	static class MyReduce extends Reducer<Text,IntWritable,Text,IntWritable>{

		@Override
		protected void reduce(Text key, Iterable<IntWritable> value,Context context)
				throws IOException, InterruptedException {
			super.reduce(key, value, context);
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
		    job.setJarByClass(MyWordCount.class);
		    job.setMapperClass(MyMapper.class);
		    job.setCombinerClass(MyReduce.class);
		    job.setReducerClass(MyReduce.class);
		    job.setOutputKeyClass(Text.class);
		    job.setOutputValueClass(IntWritable.class);
		    FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		    FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		    System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
	
}
