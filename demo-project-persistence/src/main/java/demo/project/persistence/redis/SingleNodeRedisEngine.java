package demo.project.persistence.redis;


import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Protocol;
public class SingleNodeRedisEngine implements RedisEngine  {

	
	private JedisPool connection;
	
	
	public SingleNodeRedisEngine() {		
		
		if(StringUtils.isEmpty(RedisConfiguration.getPassword()))
			connection = new  JedisPool(RedisConfiguration.getPoolConfig(), RedisConfiguration.getHost(),RedisConfiguration.getPort(),RedisConfiguration.getConnectionTimeOut());
		else
			connection = new  JedisPool(RedisConfiguration.getPoolConfig(), RedisConfiguration.getHost(),RedisConfiguration.getPort(),RedisConfiguration.getConnectionTimeOut(),RedisConfiguration.getPassword(),Protocol.DEFAULT_DATABASE);	
	}
	
	
	@Override
	public void set(String key, int seconds, String value) {
		connection.getResource().setex(key,seconds, value);		
	}

	@Override
	public String get(String key) {
		return connection.getResource().get(key);
	}
	
}
