package demo.project.persistence.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.project.persistence.interfaces.PersistenceService;
import demo.project.persistence.redis.RedisEngine;
import demo.project.utility.interfaces.Serializer;

@Service
public class PersistenceServiceImp implements PersistenceService {
	
	@Autowired
	RedisEngine redisEngine;
	
	@Autowired
	Serializer serializer;
	
	
	public <T> void set(String key,int seconds, T data) {		
		String strData  = serializer.serialize(data);
		redisEngine.set(key, seconds, strData);		
	}
	
	public <T> T get(String key) {					
		String value = redisEngine.get(key);
		return serializer.<T>deserialize(value);
	}

}
