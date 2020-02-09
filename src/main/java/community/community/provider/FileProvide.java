package community.community.provider;

import com.obs.services.ObsClient;
import com.obs.services.model.HttpMethodEnum;
import com.obs.services.model.TemporarySignatureRequest;
import com.obs.services.model.TemporarySignatureResponse;
import org.springframework.stereotype.Service;
import java.io.InputStream;
import java.util.UUID;

@Service
public class FileProvide {
    public String myprovide(InputStream picture,String fileName){
        //不定期修改
        String endPoint = "https://obs.cn-north-4.myhuaweicloud.com";
        String ak = "QR4Z7W3CL9D7B8LLTSOO";
        String sk = "fDKqjHEwnlusiQUJhhFVZs94zCYzjIlIzmkROgNz";
// 创建ObsClient实例
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);
        String generatedFileName;
        String[] filepaths=fileName.split("\\.");
        if (filepaths.length>1){
            generatedFileName= UUID.randomUUID().toString()+"."+filepaths[filepaths.length-1];
        }else {
            return null;
        }
        try {
            //URL有效期，3600秒,此处需要修改
            long expireSeconds = 3600L;
            TemporarySignatureRequest request = new TemporarySignatureRequest(HttpMethodEnum.GET, expireSeconds);
            request.setBucketName("my-community");
            request.setObjectKey(generatedFileName);
            TemporarySignatureResponse response = obsClient.createTemporarySignature(request);
            obsClient.putObject("my-community", generatedFileName, picture);
            return response.getSignedUrl();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
