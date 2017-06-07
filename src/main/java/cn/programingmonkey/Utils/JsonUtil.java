package cn.programingmonkey.Utils;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Map;


public class JsonUtil {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String CHARSET_UTF8 = "UTF-8";

    static {
        mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
        mapper.getSerializationConfig().withDateFormat(new SimpleDateFormat(DATE_FORMAT));
//        mapper.getSerializationConfig().setSerializationInclusion(Inclusion.NON_NULL);
    }

    public static <T> T fromJson(String json, Class<T> t) {

        if (json == null) {
            return null;
        }
        try {
            return mapper.readValue(json, t);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return null;
        }
    }

    public static <T> T fromJsonWithException(String json, Class<T> t) {
        try {
            return mapper.readValue(json, t);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromMap(Map<?, ?> map, Class<T> t) {

        if (map == null) {
            return null;
        }
        try {
            return mapper.readValue(toJson(map), t);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return null;
    }

    public static <T> T fromFile(String file, Class<T> t) {

        File f = new File(file);

        if (!f.exists() || !f.isFile()) {
            System.err.println("File[" + file + "] does not exist.");
            return null;
        }

        return fromJson(FileUtil.loadFileAsString(file), t);
    }

    public static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return "{}";
    }

    public static <T> T fromUTF8Json(byte[] jsonBytes, Class<T> t) {
        try {
            String json = new String(jsonBytes, CHARSET_UTF8);
            return mapper.readValue(json, t);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            throw new RuntimeException(e);
        }
    }

    public static byte[] toUTF8Json(Object obj) {
        try {
            String json = mapper.writeValueAsString(obj);
            return json.getBytes(CHARSET_UTF8);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            throw new RuntimeException(e);
        }
    }
}
