package com.onyas.thrift.mongodbused.common;

import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ConfigProperties 读取配置文件类
 */
public class ConfigProperties {

	private static transient Logger LOGGER = LoggerFactory.getLogger(ConfigProperties.class);
	
	//属性配置文件名称
	public static final String ENCODING = "utf-8";
	public static final String CONFIG_FILENAME = "config.properties";
	
	public static final char LIST_DELIMITER = '@';
	
	//是否已启用FileChangedReloadingStrategy策略
	private static boolean strategyReloaded=false;

	private static PropertiesConfiguration CONFIG_PROPERTIES=new PropertiesConfiguration();
	
	/**
	 * 私有构造函数，以防止实例化
	 */
	private ConfigProperties() {
	}
	
	public static void init(){}

	static {
		URL url=ClassLoaderUtils.getResource(CONFIG_FILENAME, ConfigProperties.class);
		if(url!=null){
			try {
				//防止中文出现乱码
				CONFIG_PROPERTIES.setEncoding(ENCODING);
				CONFIG_PROPERTIES.setListDelimiter(LIST_DELIMITER);
				CONFIG_PROPERTIES.load(CONFIG_FILENAME);
				LOGGER.info(new StringBuffer().append("加载属性配置文件[").append(CONFIG_FILENAME).append("]成功!").toString());
			} catch (ConfigurationException e) {
				LOGGER.error(new StringBuffer().append("加载属性配置文件[").append(CONFIG_FILENAME).append("]出错!").toString(),e);
			}
		}else{
			LOGGER.warn(new StringBuffer().append("属性配置文件[").append(CONFIG_FILENAME).append("]不存在!").toString());
		}
	}
	
	/**
	 * 手工启用Automatic Reloading(自动重新加载)策略
	 */
	public synchronized static void setReloadingStrategy(){
		if(!strategyReloaded){
			CONFIG_PROPERTIES.setReloadingStrategy(new FileChangedReloadingStrategy());
			strategyReloaded=true;
		}
	}
	
	/**
	 * 加载属性配置文件
	 * @param class1 类的class
	 * @param propertiesFileName 属性配置文件名称
	 * @return
	 */
	public static Properties getProperties(Class<?> class1,String propertiesFileName) {
	   	InputStream is = null;
	   	Properties properties = new Properties();
		try {
			is = class1.getResourceAsStream(propertiesFileName);
			properties.load(is);
		} catch (Exception e) {
			LOGGER.error("加载类["+class1.getName()+"]相关的属性配置文件["+propertiesFileName+"]失败！",e);		    
		} finally{
			IOUtils.close(is);
		}
		return properties;
	}
	
	/**
	 * 判断属性键是否存在
	 * @param configuration	org.apache.commons.configuration.PropertiesConfiguration对象
	 * @param strKey	属性键
	 * @return	存在返回true,否则返回false
	 */
	public static boolean containsKey(PropertiesConfiguration configuration,String strKey) {
		return configuration.containsKey(strKey);
	}

	/**
	 * 根据属性键获取属性值
	 * @param configuration	org.apache.commons.configuration.PropertiesConfiguration对象
	 * @param strKey	属性键
	 * @return	字符串
	 */
	public static String getPropertyByKey(PropertiesConfiguration configuration,String strKey) {
		if(configuration.containsKey(strKey)){		
			return configuration.getString(strKey, "");				
		}
		LOGGER.warn("配置文件中不存在键["+strKey+"]的配置项，取默认值[]");
		return "";
	}
	
	/**
	 * 根据属性键获取属性值
	 * @param configuration	org.apache.commons.configuration.PropertiesConfiguration对象
	 * @param strKey	属性键
	 * @return	字符串数组
	 */
	public static String[] getArrayValueByKey(PropertiesConfiguration configuration,String strKey) {
		if(configuration.containsKey(strKey)){			
			return configuration.getStringArray(strKey);
		}
		return new String[]{};
	}

	/**
	 * 根据属性键获取属性值
	 * @param configuration	org.apache.commons.configuration.PropertiesConfiguration对象
	 * @param strKey	属性键
	 * @param defaultValue	默认值
	 * @return	int整形
	 */
	public static int getIntPropertyByKey(PropertiesConfiguration configuration,String strKey,int defaultValue) {
		if(configuration.containsKey(strKey)){
			return configuration.getInt(strKey, defaultValue);
		}
		LOGGER.warn("配置文件中不存在键["+strKey+"]的配置项,取默认值["+defaultValue+"]");
		return defaultValue;
	}
	
	/**
	 * 根据属性键获取属性值
	 * @param configuration	org.apache.commons.configuration.PropertiesConfiguration对象
	 * @param strKey	属性键
	 * @return	int整形，若key不存在，则返回-1
	 */
	public static int getIntPropertyByKey(PropertiesConfiguration configuration,String strKey) {
		return getIntPropertyByKey(configuration,strKey,-1);
	}
	
	/**
	 * 根据属性键获取属性值
	 * @param configuration	org.apache.commons.configuration.PropertiesConfiguration对象
	 * @param strKey	属性键
	 * @param defaultValue	默认值
	 * @return	double双浮点型
	 */
	public static double getDoublePropertyByKey(PropertiesConfiguration configuration,String strKey,double defaultValue) {
		if(configuration.containsKey(strKey)){
			return configuration.getDouble(strKey, defaultValue);
		}
		LOGGER.warn("配置文件中不存在键["+strKey+"]的配置项,取默认值["+defaultValue+"]");
		return defaultValue;
	}
	
	/**
	 * 根据属性键获取属性值
	 * @param configuration	org.apache.commons.configuration.PropertiesConfiguration对象
	 * @param strKey	属性键
	 * @param defaultValue	默认值
	 * @return	float单浮点型
	 */
	public static float getFloatPropertyByKey(PropertiesConfiguration configuration,String strKey,float defaultValue) {
		if(configuration.containsKey(strKey)){
			return configuration.getFloat(strKey, defaultValue);
		}
		LOGGER.warn("配置文件中不存在键["+strKey+"]的配置项,取默认值["+defaultValue+"]");
		return defaultValue;
	}
	
	/**
	 * 根据属性键获取属性值
	 * @param configuration	org.apache.commons.configuration.PropertiesConfiguration对象
	 * @param strKey	属性键
	 * @param defaultValue	默认值
	 * @return	long长整型
	 */
	public static long getLongPropertyByKey(PropertiesConfiguration configuration,String strKey,long defaultValue) {
		if(configuration.containsKey(strKey)){
			return configuration.getLong(strKey, defaultValue);
		}
		LOGGER.warn("配置文件中不存在键["+strKey+"]的配置项,取默认值["+defaultValue+"]");
		return defaultValue;
	}
	
	/**
	 * 根据属性键获取属性值
	 * @param configuration	org.apache.commons.configuration.PropertiesConfiguration对象
	 * @param strKey	属性键
	 * @param defaultValue	默认值
	 * @return	boolean类型
	 */
	public static boolean getBooleanPropertyByKey(PropertiesConfiguration configuration,String strKey,boolean defaultValue){
		if(configuration.containsKey(strKey)){
			return configuration.getBoolean(strKey, defaultValue);
		}
		LOGGER.warn("配置文件中不存在键["+strKey+"]的配置项,取默认值["+defaultValue+"]");
		return defaultValue;
	}
	
	/**
	 * 判断属性键是否存在
	 * @param strKey	属性键
	 * @return	字符串
	 */
	public static boolean containsKey(String key) {
		return containsKey(CONFIG_PROPERTIES,key);
	}
	
	/**
	 * 根据属性键获取属性值
	 * @param strKey	属性键
	 * @return	字符串
	 */
	public static String getPropertyValue(String key) {
		return getPropertyByKey(CONFIG_PROPERTIES,key);
	}
	
	/**
	 * 根据属性键获取属性值
	 * @param strKey	属性键
	 * @return	字符串数组
	 */
	public static String[] getPropertiesArrayValue(String key){
		return getArrayValueByKey(CONFIG_PROPERTIES,key);
	}
	
	/**
	 * 根据属性键获取属性值
	 * @param strKey	属性键
	 * @param defaultValue	默认值
	 * @return	int整型
	 */
	public static int getIntPropertyValue(String key,int defaultValue) {
		return getIntPropertyByKey(CONFIG_PROPERTIES,key,defaultValue);
	}
	
	/**
	 * 根据属性键获取属性值
	 * @param strKey	属性键
	 * @param defaultValue	默认值
	 * @return	double双浮点型
	 */
	public static double getDoublePropertyValue(String key,double defaultValue) {
		return getDoublePropertyByKey(CONFIG_PROPERTIES,key,defaultValue);
	}
	
	/**
	 * 根据属性键获取属性值
	 * @param strKey	属性键
	 * @param defaultValue	默认值
	 * @return	float单浮点型
	 */
	public static float getFloatPropertyValue(String key,float defaultValue) {
		return getFloatPropertyByKey(CONFIG_PROPERTIES,key,defaultValue);
	}
	
	/**
	 * 根据属性键获取属性值
	 * @param strKey	属性键
	 * @param defaultValue	默认值
	 * @return	long长整型
	 */
	public static long getLongPropertyValue(String key,long defaultValue) {
		return getLongPropertyByKey(CONFIG_PROPERTIES,key,defaultValue);
	}
	
	/**
	 * 根据属性键获取属性值
	 * @param strKey	属性键
	 * @param defaultValue	默认值
	 * @return	boolean类型
	 */
	public static boolean getBooleanPropertyValue(String key){
		return getBooleanPropertyByKey(CONFIG_PROPERTIES,key,false);
	}
	
	/**
	 * 保存属性配置文件
	 * @param configuration
	 * @throws ConfigurationException
	 */
	public static void saveProperties(String propertiesFileName,Map<String,Object> map) throws ConfigurationException{
		if(map==null||map.size()==0){
			throw new UdsException("属性配置文件["+propertiesFileName+"]无需要保存的属性列表!");
		}
		PropertiesConfiguration configuration = new PropertiesConfiguration(propertiesFileName);
		for(Entry<String, Object> entry:map.entrySet()){
			configuration.setProperty(entry.getKey(), entry.getValue());
		}
		configuration.save();
	}
	
	/**
	 * 保存属性配置文件
	 * @param configuration
	 * @throws ConfigurationException
	 */
	public static void savePropertiesAsNew(String propertiesFileName,Map<String,Object> map,String newPropertiesFileName) throws ConfigurationException{
		if(map==null||map.size()==0){
			throw new UdsException("属性配置文件["+propertiesFileName+"]无需要保存的属性列表!");
		}
		if(StringUtils.isBlank(newPropertiesFileName)){
			throw new UdsException("另存为的属性配置文件名不能为空!");
		}
		PropertiesConfiguration configuration = new PropertiesConfiguration(propertiesFileName);
		for(Entry<String, Object> entry:map.entrySet()){
			configuration.setProperty(entry.getKey(), entry.getValue());
		}
		configuration.save(newPropertiesFileName);
	}

}