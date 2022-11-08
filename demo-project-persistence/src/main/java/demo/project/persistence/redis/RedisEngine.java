package demo.project.persistence.redis;

public interface RedisEngine {
	public void set(String key,int seconds,String value);
	public String get(String key);	
}
