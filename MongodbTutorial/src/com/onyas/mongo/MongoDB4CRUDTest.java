package com.onyas.mongo;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class MongoDB4CRUDTest {

	private Mongo mongo = null;
	private DB db;
	private DBCollection users;

	@Before
	public void init() {
		try {
			mongo = new Mongo();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
		// 获取temp DB,如果没有，则创建
		db = mongo.getDB("temp");
		// 获取user collection,如果没有，则创建
		users = db.getCollection("user");
	}

	@After
	public void destory() {
		if (mongo != null) {
			mongo.close();
		}
		mongo = null;
		db = null;
		users = null;
	}

	public void print(Object o) {
		System.out.println(o);
	}

	/**
	 * 查询user表中的所有数据
	 */
	private void queryAll(){
		System.out.println("查询user表中的所有数据");
		DBCursor cursor = users.find();
		while(cursor.hasNext()){
			print(cursor.next());
		}
	}
	
	@Test
	public void add(){
		//先查询所有的数据
		queryAll();
		print("count:"+users.count());
		
		DBObject user = new BasicDBObject();
		user.put("name", "zhangsan");
		user.put("age", 23);
		//users.save(user);保存对象，getN()获取影响的行数
//		print("影响的行数:"+users.save(user).getN());
		
		//扩展字段，随意添加，不影响现有的数据
		user.put("sex", "男");
//		print("影响的行数:"+users.save(user).getN());
		
		//添加多条数据，DBObject可变参数
//		users.insert(new BasicDBObject("name","lisi"),new BasicDBObject("name","wangwu")).getN();
		
		//添加list集合
		List<DBObject> list = new ArrayList<DBObject>();
		list.add(new BasicDBObject("age",12));
		list.add(new BasicDBObject("phone",222222));
		users.insert(list);
		
		queryAll();
		print("count:"+users.count());
	}

	@Test
	public void remove(){
		queryAll();
		//删除名字叫zhangsan的
		print(users.remove(new BasicDBObject("name","wangwu")).getN());
		//删除年龄大于等于23的
		print(users.remove(new BasicDBObject("age",new BasicDBObject("$gte",23))).getN());
		queryAll();
	}
	

}













