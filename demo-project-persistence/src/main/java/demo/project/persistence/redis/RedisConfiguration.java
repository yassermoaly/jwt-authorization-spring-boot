package demo.project.persistence.redis;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.JedisPoolConfig;

	
@Configuration
@PropertySource("classpath:redis.properties")
public class RedisConfiguration {	
	
	private static String host;
	
	private static String port;
	
	private static String password;
	
	private static String maxTotal;
	
	private static String maxIdle;
	
	private static String minIdle;
	
	private static String testOnBorrow;
	
	private static String testOnReturn;
	
	private static String testWhileIdle;
	
	private static String minEvictableIdleTimeMillis;
	
	private static String timeBetweenEvictionRunsMillis;
	
	private static String numTestsPerEvictionRun;
	
	private static String blockWhenExhausted;
	
	private static String connectionTimeOut;
	
	private static String clusteredNodes;
	
	private static String clusterPassword;
	
	private static String maxAttempts;
	
	private static String clustered;
	
	private static String valueExpireyTimeSeconds;
	
	
	public static String getHost() {
		return host;
	}
	@Value("${redis.host}")
	public void setHost(String host) {
		RedisConfiguration.host = host;
	}

	public static int getPort() {
		return Integer.parseInt(port);
	}
	@Value("${redis.port}")
	public void setPort(String port) {
		RedisConfiguration.port = port;
	}

	public static String getPassword() {
		return password;
	}
	@Value("${redis.password}")
	public void setPassword(String password) {
		RedisConfiguration.password = password;
	}

	public static int getMaxTotal() {
		return Integer.parseInt(maxTotal);
	}
	@Value("${redis.max-total}")
	public void setMaxTotal(String maxTotal) {
		RedisConfiguration.maxTotal = maxTotal;
	}

	public static int getMaxIdle() {
		return Integer.parseInt(maxIdle);
	}
	@Value("${redis.max-idle}")
	public void setMaxIdle(String maxIdle) {
		RedisConfiguration.maxIdle = maxIdle;
	}

	public static int getMinIdle() {
		return Integer.parseInt(minIdle);
	}
	@Value("${redis.min-idle}")
	public void setMinIdle(String minIdle) {
		RedisConfiguration.minIdle = minIdle;
	}

	public static boolean getTestOnBorrow() {
		return Boolean.parseBoolean(testOnBorrow);
	}
	@Value("${redis.test-on-borrow}")
	public void setTestOnBorrow(String testOnBorrow) {
		RedisConfiguration.testOnBorrow = testOnBorrow;
	}

	public static boolean getTestOnReturn() {
		return Boolean.parseBoolean(testOnReturn);
	}
	@Value("${redis.test-on-return}")
	public void setTestOnReturn(String testOnReturn) {
		RedisConfiguration.testOnReturn = testOnReturn;
	}

	public static boolean getTestWhileIdle() {
		return Boolean.parseBoolean(testWhileIdle);
	}
	@Value("${redis.test-while-idle}")
	public void setTestWhileIdle(String testWhileIdle) {
		RedisConfiguration.testWhileIdle = testWhileIdle;
	}

	public static int getMinEvictableIdleTimeMillis() {
		return Integer.parseInt(minEvictableIdleTimeMillis);
	}
	@Value("${redis.min-evictable-idle-time-millis}")
	public void setMinEvictableIdleTimeMillis(String minEvictableIdleTimeMillis) {
		RedisConfiguration.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}

	public static int getTimeBetweenEvictionRunsMillis() {
		return Integer.parseInt(timeBetweenEvictionRunsMillis);
	}
	@Value("${redis.time-between-eviction-runs-millis}")
	public void setTimeBetweenEvictionRunsMillis(String timeBetweenEvictionRunsMillis) {
		RedisConfiguration.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}

	public static int getNumTestsPerEvictionRun() {
		return Integer.parseInt(numTestsPerEvictionRun);
	}
	@Value("${redis.num-tests-per-eviction-run}")
	public void setNumTestsPerEvictionRun(String numTestsPerEvictionRun) {
		RedisConfiguration.numTestsPerEvictionRun = numTestsPerEvictionRun;
	}

	public static String getBlockWhenExhausted() {
		return blockWhenExhausted;
	}
	@Value("${redis.block-when-exhausted}")
	public void setBlockWhenExhausted(String blockWhenExhausted) {
		RedisConfiguration.blockWhenExhausted = blockWhenExhausted;
	}

	public static int getConnectionTimeOut() {
		return Integer.parseInt(connectionTimeOut);
	}
	@Value("${redis.connection-timeOut}")
	public void setConnectionTimeOut(String connectionTimeOut) {
		RedisConfiguration.connectionTimeOut = connectionTimeOut;
	}

	public static String getClusteredNodes() {
		return clusteredNodes;
	}
	@Value("${redis.clustered-nodes}")
	public void setClusteredNodes(String clusteredNodes) {
		RedisConfiguration.clusteredNodes = clusteredNodes;
	}

	public static String getClusterPassword() {
		return clusterPassword;
	}
	@Value("${redis.cluster-password}")
	public void setClusterPassword(String clusterPassword) {
		RedisConfiguration.clusterPassword = clusterPassword;
	}

	public static int getMaxAttempts() {
		return Integer.parseInt(maxAttempts);
	}
	@Value("${redis.max-attempts}")
	public void setMaxAttempts(String maxAttempts) {
		RedisConfiguration.maxAttempts = maxAttempts;
	}

	public static boolean getClustered() {
		return Boolean.parseBoolean(clustered);
	}
	@Value("${redis.clustered}")
	public void setClustered(String clustered) {
		RedisConfiguration.clustered = clustered;
	}
	
	public static int getvalueExpireyTimeSeconds() {
		return Integer.parseInt(valueExpireyTimeSeconds);
	}
	@Value("${redis.value-expirey-time-seconds}")
	public void setvalueExpireyTimeSeconds(String valueExpireyTimeSeconds) {
		RedisConfiguration.valueExpireyTimeSeconds = valueExpireyTimeSeconds;
	}
	
	
		
	public static JedisPoolConfig getPoolConfig() {
		final JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(Integer.parseInt(maxTotal));
		poolConfig.setMaxIdle(Integer.parseInt(maxIdle));
		poolConfig.setMinIdle(Integer.parseInt(minIdle));
		poolConfig.setTestOnBorrow(Boolean.valueOf(testOnBorrow));
		poolConfig.setTestOnReturn(Boolean.valueOf(testOnReturn));
		poolConfig
				.setTestWhileIdle(Boolean.valueOf(testWhileIdle));
		poolConfig.setMinEvictableIdleTimeMillis(Duration
				.ofSeconds(Integer
						.parseInt(minEvictableIdleTimeMillis))
				.toMillis());
		poolConfig.setTimeBetweenEvictionRunsMillis(Duration
				.ofSeconds(Integer
						.parseInt(timeBetweenEvictionRunsMillis))
				.toMillis());
		poolConfig.setNumTestsPerEvictionRun(
				Integer.parseInt(numTestsPerEvictionRun));
		poolConfig.setBlockWhenExhausted(
				Boolean.valueOf(blockWhenExhausted));
		return poolConfig;
	}
	
	@Bean
	public RedisEngine getRedisEngine(){
		if (Boolean.parseBoolean(clustered)) {
			return new ClusterRedisEngine();
		}
		else {
			return new SingleNodeRedisEngine();
		}
	}
	
	
}
