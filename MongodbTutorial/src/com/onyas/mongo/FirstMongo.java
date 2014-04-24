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
			//1������һ��MongoDB�����ݿ����Ӷ�����Ĭ�����ӵ���ǰ������localhost��ַ���˿���27017�� ��ʱӦ�ô���mongod.exe
			Mongo mongo = new Mongo();
			//ѭ����ӡ�ֵ�ǰ���е����ݿ�
			for(String name:mongo.getDatabaseNames()){
				System.out.println(name);
			}
			System.out.println("----------");
			//2�������һ��test�����ݿ�,mongoDB������û�д���������ݿ������£�������ݵ���Ӳ�����
			DB test = mongo.getDB("test");
			
			for(String name:test.getCollectionNames()){
				System.out.println(name);
			}
			
			System.out.println("--------");
			//3���õ���db����һ������Ҫ��ȡһ�����ۼ�����DBCollection����ͨ��db�����getCollection��������ɡ��൱�����ǵı�
			DBCollection foo = test.getCollection("foo");
			//4����ѯ��������
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
