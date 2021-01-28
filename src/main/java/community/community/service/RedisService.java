package community.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author by wyc
 * @Date 2021/1/28.
 */
@Service
public class RedisService {

    @Autowired
    RedisTemplate redisTemplate;

    private static String MAP_QUESTION_VIEW="MAP_QUESTION_VIEW";

    public void saveViewForRedis(int id,int viewCount){
        redisTemplate.opsForHash().put(MAP_QUESTION_VIEW,id,viewCount);
    }

    public void incViewForRedis(int id){
        redisTemplate.opsForHash().increment(MAP_QUESTION_VIEW,id,1);
    }

    public int getViewForRedis(int id){
        Object o = redisTemplate.opsForHash().get(MAP_QUESTION_VIEW, id);
        if (o==null){
            return 0;
        }else {
            return (int)o;
        }
    }

    public HashMap<Integer,Integer> getAllViewForRedis(){
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(MAP_QUESTION_VIEW, ScanOptions.NONE);
        HashMap<Integer,Integer> hashMap=new HashMap<>();
        while (cursor.hasNext()){
            Map.Entry<Object, Object> map = cursor.next();
            int id=(int)map.getKey();
            hashMap.put(id,(int)map.getValue());
            redisTemplate.opsForHash().delete(MAP_QUESTION_VIEW,id);
        }
        return hashMap;
    }
}
