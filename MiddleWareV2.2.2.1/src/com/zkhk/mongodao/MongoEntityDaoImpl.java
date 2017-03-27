package com.zkhk.mongodao;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.zkhk.util.GzipUtil;

public class MongoEntityDaoImpl implements MongoEntityDao{
	
	@Resource(name="mongoTemplate")
	private MongoTemplate mongoTemplate;
	
	
	public void saveOrUpdate(Object object) throws Exception{
		mongoTemplate.save(object);		
	}
	
	public <T> T getObject(Class<T> entityClass, Object pk) throws Exception{
		return	mongoTemplate.findById(pk, entityClass);
	}

	public <T> List<T> findAll(Class<T> entityClass) throws Exception{
		return mongoTemplate.findAll(entityClass);
	}
	

	public  void delete(Object object) throws Exception {		
		mongoTemplate.remove(object);
		
	}
	
	public <T> T getObjectByProperty(Class<T> entityClass, String propertyName,Object propertyValue) throws Exception{
		Query query = new Query();
		query.addCriteria(Criteria.where(propertyName).is(propertyValue));
		return mongoTemplate.findOne(query, entityClass);
	}
	
	public <T> List<T> listByProperty(Class<T> entityClass, String propertyName,Object propertyValue) throws Exception{
		Query query = new Query();
		query.addCriteria(Criteria.where(propertyName).is(propertyValue));		
		return mongoTemplate.find(query, entityClass);
	}

	public <T> List<T> listByPropertysAndValues(Class<T> entityClass,
			String[] propertyNames, Object[] objects) throws Exception {		
		Query query = new Query();
		for(int i=0;i<propertyNames.length;i++){
		query.addCriteria(Criteria.where(propertyNames[i]).is(objects[i]));	
		}
		return mongoTemplate.find(query, entityClass);
	}
	
	

	public String saveFile(String collectionName, MultipartFile mFile ,String inComing) throws Exception {
	             
	        // 存储fs的根节点
	            GridFS gridFS = new GridFS( mongoTemplate.getDb(), collectionName);
	            GridFSInputFile gfs = gridFS.createFile(GzipUtil.gzip(mFile.getBytes()));
	            gfs.put("filename", mFile.getOriginalFilename());
	            //gfs.put("contentType", mFile.getOriginalFilename().substring(mFile.getOriginalFilename().lastIndexOf(".")));
	            gfs.put("contentType", "gzip");
	            if(inComing!=null){
	            gfs.put("inComing", inComing);
	            }
	            gfs.save();
	          //  gfs.
	            return  gfs.getId().toString();
	      
	    }
	 
	
	    public GridFSDBFile retrieveFileOne(String collectionName, ObjectId id) throws Exception{	       
	            // 获取fs的根节点 
	            GridFS gridFS = new GridFS(mongoTemplate.getDb(), collectionName);
	            GridFSDBFile dbfile = gridFS.find(id);    
	            if (dbfile != null) {
	                return dbfile;
	            }
	       
	        return null;
	    }

	    public GridFSDBFile removeFile(String collectionName, ObjectId id) throws Exception{	       
            // 获取fs的根节点 
            GridFS gridFS = new GridFS(mongoTemplate.getDb(), collectionName);
            gridFS.remove(id);    
   
       
        return null;
    }

		public String saveFile(String collectionName, byte[] bytes, String inComing) throws Exception {
			// 存储fs的根节点
            GridFS gridFS = new GridFS( mongoTemplate.getDb(), collectionName);
            GridFSInputFile gfs = gridFS.createFile(GzipUtil.gzip(bytes));
            gfs.put("filename", "webFile");
            //gfs.put("contentType", mFile.getOriginalFilename().substring(mFile.getOriginalFilename().lastIndexOf(".")));
            gfs.put("contentType", "gzip");
            if(inComing!=null){
            gfs.put("inComing", inComing);
            }
            gfs.save();
            return  gfs.getId().toString();
			
		}

		public String saveFile(String string, MultipartFile headImg) throws Exception {
			// 存储fs的根节点
            GridFS gridFS = new GridFS( mongoTemplate.getDb(), string);
            GridFSInputFile gfs = gridFS.createFile(headImg.getBytes());          
            gfs.put("contentType", headImg.getOriginalFilename().substring(headImg.getOriginalFilename().lastIndexOf(".")));          
            gfs.save();
            return  gfs.getId().toString();
		}

	
	
}
