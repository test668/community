package community.community.controller;

import community.community.dto.Access_tokenDto;
import community.community.dto.GithubUser;
import community.community.enums.UserTypeEnum;
import community.community.model.User;
import community.community.util.GithubUtil;
import community.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubUtil githubUtil;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secrent}")
    private String clientSecrent;

    @Value("${github.redirect.uri}")
    private String RedirectUri;


    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response
    ) {
        Access_tokenDto access_tokenDto = new Access_tokenDto();
        access_tokenDto.setClient_id(clientId);
        access_tokenDto.setClient_secret(clientSecrent);
        access_tokenDto.setCode(code);
        access_tokenDto.setRedirect_uri(RedirectUri);
        access_tokenDto.setState(state);
        String accessToken = githubUtil.getAccessToken(access_tokenDto);
        GithubUser githubUser = githubUtil.getUser(accessToken);

        if (githubUser != null && githubUser.getId() != null) {
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatar_url());
            user.setType(UserTypeEnum.GITHUB_USER.getType());
            userService.createOrUpdate(user);
            Cookie cookie=new Cookie("token",token);
            cookie.setMaxAge(60*60*24);
            response.addCookie(cookie);
            return "redirect:/";
        } else {
            return "redirect:/";
        }

    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response
    ) {
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
