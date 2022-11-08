package demo.project.persistence.redis;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.springframework.util.StringUtils;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class ClusterRedisEngine implements RedisEngine {

	
	private JedisCluster connection;
	
	
	public ClusterRedisEngine() {		
		
		Set<HostAndPort> nodesset = new HashSet<HostAndPort>();
		for(String node : RedisConfiguration.getClusteredNodes().split(",")) {
			nodesset.add(HostAndPort.parseString(node));
		}
		
		if(!StringUtils.isEmpty(RedisConfiguration.getClusterPassword())){
			connection = new  JedisCluster(nodesset,
					RedisConfiguration.getConnectionTimeOut(),
					RedisConfiguration.getConnectionTimeOut(),
					RedisConfiguration.getMaxAttempts(),
					RedisConfiguration.getClusterPassword(), RedisConfiguration.getPoolConfig());
		} else {
			connection = new JedisCluster(nodesset,
					RedisConfiguration.getConnectionTimeOut(),
					RedisConfiguration.getConnectionTimeOut(),
					RedisConfiguration.getMaxAttempts(), RedisConfiguration.getPoolConfig());
		}
	}
	
	
	@Override
	public void set(String key, int seconds, String value) {
		try {
			connection.setex(key,seconds, value);
		}
		finally {
			try {
				connection.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//
		
	}

	@Override
	public String get(String key) {
		String result = new String();
		try {
			result = connection.get(key);
		}
		finally {
			try {
				connection.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

}
