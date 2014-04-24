package com.onyas.mongo;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
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
		// ��ȡtemp DB,���û�У��򴴽�
		db = mongo.getDB("temp");
		// ��ȡuser collection,���û�У��򴴽�
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
	 * ��ѯuser���е���������
	 */
	private void queryAll() {
		System.out.println("��ѯuser���е���������");
		DBCursor cursor = users.find();
		while (cursor.hasNext()) {
			print(cursor.next());
		}
	}

	@Test
	public void add() {
		// �Ȳ�ѯ���е�����
		queryAll();
		print("count:" + users.count());

		DBObject user = new BasicDBObject();
		user.put("name", "zhangsan");
		user.put("age", 23);
		// users.save(user);�������getN()��ȡӰ�������
		// print("Ӱ�������:"+users.save(user).getN());

		// ��չ�ֶΣ�������ӣ���Ӱ�����е�����
		user.put("sex", "��");
		// print("Ӱ�������:"+users.save(user).getN());

		// ��Ӷ������ݣ�DBObject�ɱ����
		// users.insert(new BasicDBObject("name","lisi"),new
		// BasicDBObject("name","wangwu")).getN();

		// ���list����
		List<DBObject> list = new ArrayList<DBObject>();
		list.add(new BasicDBObject("age", 12));
		list.add(new BasicDBObject("phone", 222222));
		users.insert(list);

		queryAll();
		print("count:" + users.count());
	}

	@Test
	public void remove() {
		queryAll();
		// ɾ�����ֽ�zhangsan��
		print(users.remove(new BasicDBObject("name", "wangwu")).getN());
		// ɾ��������ڵ���23��
		print(users.remove(
				new BasicDBObject("age", new BasicDBObject("$gte", 23))).getN());
		queryAll();
	}

	@Test
	public void update() {
		queryAll();

		print(users.update(
				new BasicDBObject("_id", new ObjectId(
						"53587f519cd3fa0f1ea595e5")),
				new BasicDBObject("age", 26)).getN());
		
		print(users.update(
				new BasicDBObject("_id", new ObjectId(
						"53587f519cd3fa0f1ea595e0")),
				new BasicDBObject("age", "12")
				,true//������ݿ���û�����Ƿ����
				,false//����ж�����ֻ�޸ĵ�һ��
				).getN());
		
		print(users.update(
				new BasicDBObject("age", "12"),
				new BasicDBObject("age", "13")
				,true//������ݿ���û�����Ƿ����
				,true//����ж������Ͳ��޸�
				).getN());
		
		//�����ݿ��в����ھͲ��޸ģ���������ݣ����������ݾͲ��޸�
		print(users.updateMulti(
				new BasicDBObject("age", "12"),
				new BasicDBObject("age", "13")
				).getN());
		
		queryAll();
	}

}
