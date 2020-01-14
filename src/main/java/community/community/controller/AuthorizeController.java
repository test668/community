package community.community.controller;

import community.community.dto.Access_tokenDto;
import community.community.dto.GithubUser;
import community.community.mapper.UserMapper;
import community.community.model.User;
import community.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secrent}")
    private String clientSecrent;

    @Value("${github.redirect.uri}")
    private String RedirectUri;

    @Autowired
    private UserMapper UserMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletResponse response
    ){
        Access_tokenDto access_tokenDto = new Access_tokenDto();
        access_tokenDto.setClient_id(clientId);
        access_tokenDto.setClient_secret(clientSecrent);
        access_tokenDto.setCode(code);
        access_tokenDto.setRedirect_uri(RedirectUri);
        access_tokenDto.setState(state);
        String accessToken = githubProvider.getAccessToken(access_tokenDto);
        GithubUser githubUser = githubProvider.getUser(accessToken);

        if(githubUser!=null&&githubUser.getId()!=null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githubUser.getAvatar_url());
            UserMapper.insert(user);
            response.addCookie(new Cookie("token",token));
                  return "redirect:/";
        }
        else {
            return "redirect:/";
        }

    }
}