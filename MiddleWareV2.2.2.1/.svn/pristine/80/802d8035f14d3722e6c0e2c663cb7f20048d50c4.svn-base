package com.zkhk.mongodao;


import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.web.multipart.MultipartFile;
import com.mongodb.gridfs.GridFSDBFile;
/**
 * 
 * @author bit
 *
 */
public interface MongoEntityDao {
	/**
	 * 保存某个对象
	 * @param object
	 * @throws Exception
	 */
	public void saveOrUpdate(Object object) throws Exception;
	/**
	 * 通过主键获取对象
	 * @param entityClass
	 * @param pk
	 * @return
	 * @throws Exception
	 */
	public <T> T getObject(Class<T> entityClass, Object pk) throws Exception;
	/**
	 * 查询某个对象的全部
	 * @param entityClass
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> findAll(Class<T> entityClass) throws Exception;	
	/**
	 * 删除对象
	 * @param object
	 * @throws Exception
	 */
	public void delete(Object object) throws Exception;
	
	/**
	 * 通过属性查询某个对象
	 * @param entityClass
	 * @param propertyName
	 * @param propertyValue
	 * @return
	 * @throws Exception
	 */
	public <T> T getObjectByProperty(Class<T> entityClass, String propertyName,Object propertyValue) throws Exception;
	/**
	 * 通过属性查询某个对象的集合
	 * @param entityClass
	 * @param propertyName
	 * @param propertyValue
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> listByProperty(Class<T> entityClass, String propertyName,Object propertyValue) throws Exception;
	/**
	 * 通过多属性查询某个对象的集合
	 * @param entityClass
	 * @param propertyName
	 * @param propertyValue
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> listByPropertysAndValues(Class<T> entityClass, String[] propertyNames,Object[] objects) throws Exception;
	
	/**
	    * 存储文件 
	    * @param collectionName 集合名 
	    * @param file 文件 
	    * @param fileid 文件id 
	    * @param companyid 文件的公司id 
	    * @param filename 文件名称
	    */
	public String saveFile(String collectionName, MultipartFile mFile,String inComing) throws Exception;
	
	
	/**
	 * 取出文件
	 * @param collectionName
	 * @param filename
	 * @return
	 * @throws Exception
	 */
    public GridFSDBFile retrieveFileOne(String collectionName, ObjectId id) throws Exception;
    /**
     * 删除文件
     * @param collectionName
     * @param id
     * @return
     * @throws Exception
     */
    public GridFSDBFile removeFile(String collectionName, ObjectId id) throws Exception;
    /**
     * 保存字节数组
     * @param mdbFile
     * @param bytes
     * @param inComing
     */
	public  String saveFile(String collectionName, byte[] bytes, String inComing) throws Exception;
	/**
	 * 保存文件
	 * @param string
	 * @param headImg
	 * @return
	 */
	public String saveFile(String string, MultipartFile headImg)throws Exception;
}
