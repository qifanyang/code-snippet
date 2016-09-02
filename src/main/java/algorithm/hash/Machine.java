package algorithm.hash;

import java.util.HashMap;
import java.util.Map;

/**
 * 缓存服务器
 *
 * @author yangqf
 * @version 1.0 2016/8/2
 */
public class Machine{

    private String id;
    private int hash;
    private Map<String, Object> cache = new HashMap<>();

    public Machine(String id){
        this.id = id;
        this.hash = this.id.hashCode();
    }

    public void put(String key, Object object){
        cache.put(key, object);
    }

    public Object get(String key){
        return cache.get(key);
    }

    public String getId(){
        return id;
    }

    public int getHash(){
        return hash;
    }

    public Map<String, Object> getCache(){
        return cache;
    }
}
