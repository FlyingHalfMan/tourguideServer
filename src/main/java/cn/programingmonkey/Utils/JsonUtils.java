package cn.programingmonkey.Utils;

import cn.programingmonkey.Service.SIFT.Point;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cai on 2017/4/13.
 */
public class JsonUtils {

    public final static ObjectMapper mapper = new ObjectMapper();

    @SuppressWarnings("unchecked")
    public static List parseJson(String jsonString) throws Exception{
        JavaType javaType = getCollectionType(ArrayList.class, Point.class);
        List<Point> lst = mapper.readValue(jsonString, javaType);
        return lst;
    }
    /**
     * 获取泛型的Collection Type
     * @param collectionClass 泛型的Collection
     * @param elementClasses 元素类
     * @return JavaType Java类型
     * @since 1.0
     */
    private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }
}
