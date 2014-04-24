package com.onyas.mongo;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;

public class FirstMongo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			//1、创建一个MongoDB的数据库连接对象，它默认连接到当前机器的localhost地址，端口是27017。 此时应该打开了mongod.exe
			Mongo mongo = new Mongo();
			//循环打印现当前所有的数据库
			for(String name:mongo.getDatabaseNames()){
				System.out.println(name);
			}
			System.out.println("----------");
			//2、获得了一个test的数据库,mongoDB可以在没有创建这个数据库的情况下，完成数据的添加操作。
			DB test = mongo.getDB("test");
			
			for(String name:test.getCollectionNames()){
				System.out.println(name);
			}
			
			System.out.println("--------");
			//3、得到了db，下一步我们要获取一个“聚集集合DBCollection”，通过db对象的getCollection方法来完成。相当于我们的表
			DBCollection foo = test.getCollection("foo");
			//4、查询所有数据
			DBCursor cursor = foo.find();
			while(cursor.hasNext()){
				System.out.println(cursor.next());
			}
			
			System.out.println(cursor.count());
			System.out.println(cursor.getCursorId());
			System.out.println(JSON.serialize(cursor));
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
		
	}

}
