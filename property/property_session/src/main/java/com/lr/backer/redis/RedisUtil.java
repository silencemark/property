package com.lr.backer.redis;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis操作工具类
 * 
 * @author HYL
 * 
 */
public class RedisUtil {
	private static JedisPoolConfig config;
	private static JedisPool pool;
	private static final Logger LOGGER = Logger.getLogger(RedisUtil.class);

	public static void deleteRedis(String userid) {
		Jedis jedis = getJedis();
		Set<String> s = jedis.keys(userid + "*");
		Iterator<String> it = s.iterator();
		try {
			while (it.hasNext()) {
				String key = (String) it.next();
				remove(key);
			}
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
			e.printStackTrace();
		}
		recycleJedisOjbect(jedis);
	}

	/**
	 * 检索redis中数据
	 */
	public static Map<String, Object> search(String input) {
		Jedis jedis = getJedis();
		Set<String> s = jedis.keys("*");
		Iterator<String> it = s.iterator();

		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println(s.size());
		while (it.hasNext()) {
			String key = (String) it.next();
			// System.out.println("key"+key);
			// map.put(key, jedis.hgetAll(key));
			System.out.println(key);
			// remove(key);
		}
		recycleJedisOjbect(jedis);
		return map;
	}

	public static void main(String[] args) {

		deleteRedis("81PC");
		// InputStream in =
		// RedisUtil.class.getClassLoader().getResourceAsStream("com/hk/backer/redis/redis.properties");
		// Properties p = new Properties();
		// try {
		// p.load(in);
		// } catch (IOException e) {
		// LOGGER.error("redis.properties解析异常");
		// e.printStackTrace();
		// }
		// int maxidle =
		// Integer.parseInt(String.valueOf(p.get("redis.maxidle")));
		// int maxwaitmillis =
		// Integer.parseInt(String.valueOf(p.get("redis.maxwaitmillis")));
		// String redishost = String.valueOf(p.get("redis.redishost"));
		// int redispost =
		// Integer.parseInt(String.valueOf(p.get("redis.redispost")));
		// int timeout =
		// Integer.parseInt(String.valueOf(p.get("redis.timeout")));
		// String redispass = String.valueOf(p.get("redis.redispass"));
		//
		// config = new JedisPoolConfig();
		// config.setMaxIdle(maxidle);
		// config.setTestOnBorrow(true);
		// config.setTestOnReturn(true);
		// config.setMaxWaitMillis(maxwaitmillis);
		// pool = new JedisPool(config,redishost,redispost,timeout,redispass);
		// System.out.println(pool.getResource());
	}

	/**
	 * 相当于session保存用户信息
	 * 
	 * @param key
	 * @param map
	 */
	public static void setMap(String key, Map<String, Object> map) {
		remove(key);
		Jedis jedis = getJedis();
		if (map != null && key != null && jedis != null) {
			Set<String> keys = map.keySet();
			for (String _key : keys) {
				try {
					jedis.hset(key, _key, String.valueOf(map.get(_key)));
				} catch (Exception e) {
					pool.returnBrokenResource(jedis);
					e.printStackTrace();
				}
			}
			jedis.expire(key, 86400 * 365);
		}
		recycleJedisOjbect(jedis);
	}

	/**
	 * 相当于session保存用户信息
	 * 
	 * @param key
	 * @param map
	 * @param age
	 *            时间 1 为 1 天
	 */
	public static void setMap(String key, Map<String, Object> map, Object age) {
		remove(key);
		Jedis jedis = getJedis();
		if (map != null && key != null && jedis != null) {
			Set<String> keys = map.keySet();
			for (String _key : keys) {
				jedis.hset(key, _key, String.valueOf(map.get(_key)));
			}
			int A = 1;
			try {
				A = Integer.parseInt(String.valueOf(age));
			} catch (Exception e) {
				A = 1;
			}
			jedis.expire(key, 86400 * A);
		}
		recycleJedisOjbect(jedis);
	}

	/**
	 * 将对象保存在reids中
	 * 
	 * @param key
	 * @param map
	 *            要保存的Map对象
	 * @param age
	 *            时间 1 为0.5h
	 */
	public static void setObject(String key, Map<String, Object> map,
			Integer age) {
		remove(key);
		if (age == null) {
			age = 1;
		}
		Jedis jedis = getJedis();
		if (map != null && key != null && jedis != null) {
			Set<String> keys = map.keySet();
			for (String _key : keys) {
				jedis.hset(key, _key, map.get(_key).toString());
			}
			jedis.expire(key, 1800 * age);
		}
		recycleJedisOjbect(jedis);
	}

	/**
	 * 获取保存在jedis中的值
	 * 
	 * @param key
	 * @return
	 */
	public static Map<String, Object> getObject(String key) {
		Jedis jedis = getJedis();
		Map<String, Object> user = new HashMap<String, Object>();
		if (key != null && jedis != null) {
			try {
				Map<String, String> jedismap_1 = jedis.hgetAll(key);
				user.putAll(jedismap_1);
			} catch (Exception e) {
				pool.returnBrokenResource(jedis);
				e.printStackTrace();
			} finally {
				recycleJedisOjbect(jedis);
			}
			return user;
		} else
			return null;
	}

	/**
	 * 若用户已登陆,取得所登陆用户的信息
	 * 
	 * @param key
	 * @return
	 */
	public static Map<String, Object> getMap(String key) {
		Jedis jedis = getJedis();
		Map<String, Object> user = new HashMap<String, Object>();
		if (key != null && jedis != null) {
			try {
				Map<String, String> jedismap_1 = jedis.hgetAll(key);
				user.putAll(jedismap_1);
			} catch (Exception e) {
				pool.returnBrokenResource(jedis);
				e.printStackTrace();
			} finally {
				recycleJedisOjbect(jedis);
			}
			return user;
		} else
			return null;
	}

	/**
	 * 用户退出,则删除reids中用户信息
	 * 
	 * @param key
	 * @return
	 */
	public static Long remove(String key) {
		Jedis jedis = getJedis();
		long rt = 0L;
		if (key != null && jedis != null) {
			try {
				if (exist(key)) {
					rt = jedis.del(key);
				}
			} catch (Exception e) {
				pool.returnBrokenResource(jedis);
				e.printStackTrace();
			} finally {
				recycleJedisOjbect(jedis);
			}
		}
		return rt;
	}

	/**
	 * 判断redis中是否还存在该用户信息
	 * 
	 * @param key
	 * @return
	 */
	public static boolean exist(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.exists(key);
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
			e.printStackTrace();
			return false;
		} finally {
			recycleJedisOjbect(jedis);
		}
	}

	/**
	 * 归还redis对象到连接池中
	 * 
	 * @param jedis
	 */
	public static void recycleJedisOjbect(Jedis jedis) {
		if (jedis != null && pool != null) {
			try {
				pool.returnResource(jedis);
			} catch (Exception e) {
				try {
					pool.returnBrokenResource(jedis);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}
	}

	/**
	 * 得到jedis
	 */
	private static Jedis getJedis() {
		if (pool == null) {
			InputStream in = RedisUtil.class
					.getClassLoader()
					.getResourceAsStream("com/lr/backer/redis/redis.properties");
			Properties p = new Properties();
			try {
				p.load(in);
			} catch (IOException e) {
				LOGGER.error("redis.properties解析异常");
				e.printStackTrace();
			}
			int maxidle = Integer.parseInt(String.valueOf(p
					.get("redis.maxidle")));
			int maxwaitmillis = Integer.parseInt(String.valueOf(p
					.get("redis.maxwaitmillis")));
			String redishost = String.valueOf(p.get("redis.redishost"));
			int redispost = Integer.parseInt(String.valueOf(p
					.get("redis.redispost")));
			int timeout = Integer.parseInt(String.valueOf(p
					.get("redis.timeout")));
			String redispass = String.valueOf(p.get("redis.redispass"));

			config = new JedisPoolConfig();
			config.setMaxIdle(maxidle);
			config.setTestOnBorrow(true);
			config.setTestOnReturn(true);
			config.setMaxWaitMillis(maxwaitmillis);
			pool = new JedisPool(config, redishost, redispost, timeout,
					redispass);
		}
		try {
			return pool.getResource();
		} catch (Exception e) {
			LOGGER.error("redis连接池异常");
			return null;
		}
	}

	/**
	 * 向一个已经存在的redis对象中存放信息的值
	 * 
	 * @param key
	 *            redis的key
	 * @param objKey
	 *            参数名
	 * @param objValue
	 *            参数值
	 */
	public static synchronized void putKeys(String key, String objKey, Object objValue) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap = RedisUtil.getMap(key);  
		dataMap.put(objKey, objValue);
		RedisUtil.setMap(key, dataMap);
		
	}

 
	
	
}
