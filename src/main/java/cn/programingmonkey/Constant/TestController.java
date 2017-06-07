package cn.programingmonkey.Constant;

import cn.programingmonkey.Utils.JsonUtil;
import cn.programingmonkey.Utils.Utils;
import org.junit.Test;

import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by cai on 2017/4/19.
 */
public class TestController {

    @Test
    public void test(){

        try {
            File file =new File("/Users/caihongfeng/Downloads/316def1ed588ce6d82c5e9776604985b.jpg");
            Socket socket = new Socket("10.211.55.3",10000);
            socket.setSoTimeout(60 * 10000);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(new FileInputStream(file));
            byte[] bytes = new byte[dis.available()];
            while (dis.read(bytes,0,bytes.length) != -1){
                dos.write(bytes,0,bytes.length);
                dos.flush();
            }

            InputStream ips = socket.getInputStream();
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String s = "";
            while((s = br.readLine()) != null)
                System.out.println(s);
            socket.close();

            dos.close();
            ips.close();
            dis.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testDate(){

        System.out.println(Utils.getCurrentTime());
        System.out.println(Utils.getDateWithDuration(60 *1000));
    }


}
