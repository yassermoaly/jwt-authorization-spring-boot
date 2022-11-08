package demo.project.persistence.interfaces;

import org.springframework.stereotype.Service;

@Service
public interface PersistenceService {
	<T> void set(String key,int seconds,T data);
	<T> T get(String key);	
}
