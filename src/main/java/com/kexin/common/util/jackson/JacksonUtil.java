package com.kexin.common.util.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kexin.admin.entity.vo.redis.RedisMessage;

/**
 * json和实体类之间的相互转换
 */
public class JacksonUtil {
    /**
     * 将对象转换为json格式字符串
     *
     * @param
     * @return json string
     */
    public static String toJSON(Object obj) {
        ObjectMapper om = new ObjectMapper();
        try {
            String json = om.writeValueAsString(obj);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * 将json形式字符串转换为java实体类
     *
     */
    public static <T> T parse(String jsonStr, Class<T> clazz) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        RedisMessage redisMessage = mapper.readValue(jsonStr, RedisMessage.class);
        return (T) redisMessage;
    }

}
