package community.community.util;

import com.alibaba.fastjson.JSON;
import community.community.dto.Access_tokenDto;
import community.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubUtil {
    public String getAccessToken(Access_tokenDto access_tokenDto) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(access_tokenDto));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String[] split = string.split("&");
            String tokenStr = split[0];
            String token = tokenStr.split("=")[1];
            return token;

        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url("https://api.github.com/user?access_token=" + access_token)
//                .build();
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization","token "+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {

        }
        return null;

    }
}
