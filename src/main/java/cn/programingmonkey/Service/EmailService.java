package cn.programingmonkey.Service;

import cn.programingmonkey.Exception.InvalidException;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * Created by cai on 29/01/2017.
 */
public class EmailService {

    public static void sendEmailVerifyCode(String address, String subject,String body) throws ClientException,InvalidException {
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "YUWa6JyS0EwLZfP1","V6T1YtMJDFoJzB6OrtCYHUJkU558CG");
        IAcsClient client = new DefaultAcsClient(profile);
        SingleSendMailRequest request = new SingleSendMailRequest();
        try {
            request.setAccountName("programingmonkey");
            request.setFromAlias("畅游福大");
            request.setAddressType(1);
            request.setTagName("Programingmonkey");
            request.setReplyToAddress(true);
            request.setToAddress(address);
            request.setSubject(subject);
            request.setHtmlBody(body);
            SingleSendMailResponse httpResponse = client.getAcsResponse(request);
            return;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        throw new InvalidException(500,"邮件发送失败");
    }
}
