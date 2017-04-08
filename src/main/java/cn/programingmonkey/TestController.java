package cn.programingmonkey;

import cn.programingmonkey.Service.ImageService;
import cn.programingmonkey.Table.nearBy.Post;
import cn.programingmonkey.Utils.MongoUtil;
import cn.programingmonkey.Utils.Utils;
import com.mongodb.DBCollection;
import com.mongodb.client.FindIterable;
import org.junit.Test;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cai on 26/01/2017.
 */

public class TestController {

    @Test
    public void testGround() {

       double[] result =  Utils.getAround(39.00,129.00,1000);
       for (double re : result)
        System.out.println(re);
    }


    @Test
    public void  TestImageService() throws IOException {

        String basePath = this.getClass().getClassLoader().getResource("/").getPath();

        File  file = new File(basePath+"/WEB-INF/Resources/Images/head.jpg");
        ImageService.fingerPrint(file);

    }

    @Test
    public void TestPath()
    {
        String dir = System.getProperty("user.dir");
        System.out.println(dir);
    }


    @Test
    public void testMongoConnection(){

        System.out.println( MongoUtil.getInstanceDataStore().getCollection(Post.class));
    }

    @Test
    public void TestMongoCreate(){

        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        Map<String,String> map = new HashMap<String, String>();
        map.put("12312321312","测试图片，这里是测试");
        list.add(map);
        Post post = new Post();
        post.setTitle("sdfsdfsdfsdf");
        post.setImages(list);
        MongoUtil.getInstanceDataStore().save(post);
        System.out.println(post.getId());
    }

}
