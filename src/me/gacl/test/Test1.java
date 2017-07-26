package me.gacl.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


import jdk.management.resource.internal.inst.SocketOutputStreamRMHooks;
import me.gacl.domain.User;

public class Test1 {
	
	
	
	
	public static void main(String []args) throws IOException {
		//mybatis的配置文件
		String resource = "conf.xml";
		//使用类加载器加载mybatis的配置文件（它也加载关联的映射文件）
		InputStream is = Test1.class.getClassLoader().getResourceAsStream(resource);
		//构建SQLSession的工厂
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
		//只用mybatis提供的Resources类加载mybatis的配置文件（他也加载关联的映射文件）
		//Reader = reader = Resources。getResourceAsReader（Resource）；
		//构建sqlSession的工厂
		//SQLSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
		//创建能执行映射文件中sql的sqlSession
		SqlSession session = sessionFactory.openSession();
		/*
		 * 映射sql的表示字符串
		 * me.gacl.mapping.userMapper是userMapper.xml文件中mapper标签的namespace属性的值
		 * getUser是select标签的id属性值，通过select标签的id属性值就可以找到要执行的sql
		 * */
		String statement = "me.gacl.mapping.userMapper.getUser";//映射sql的标识字符串
		//执行查询返回一个唯一user对象的sql
		User user = session.selectOne(statement, 2);
	    System.out.println(user);
	    
	    showAll(session);
	    //addUser(session, "丹丹", 18);
	    
	    showAll(session);
	    deleteUser(session, "丹丹");
	    
	    
	    
	    
	}
	
	public static void addUser(SqlSession session,String name, int age) {
		User addU = new User();
	    addU.setName(name);
	    addU.setAge(age);
	    //System.out.println("ADD: " + addU);
	    String insert = "me.gacl.mapping.userMapper.addUser";
	    session.insert(insert, addU);
	    session.commit();
	    
	    
	}
	
	
	public static void showAll(SqlSession session) {
		System.out.println("showALL----------");
		
		String statement2 = "me.gacl.mapping.userMapper.getAll";
	    
	    List<User> users = session.selectList(statement2);
	    for (User u:users)
	    {
	    		System.out.println(u);
	    }
	}
	
	public static void deleteUser(SqlSession session, String name)
	{
		String sql = "me.gacl.mapping.userMapper.deleteUser";
		
		session.delete(sql, name);
		session.commit();
	}
	
}
