package community.community.controller;

import community.community.dto.FileDto;
import community.community.provider.FileProvide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

@Controller
public class FileController {

    @Autowired
    private FileProvide fileProvide;

    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDto upload(HttpServletRequest request){
        MultipartHttpServletRequest multipartHttpRequest=(MultipartHttpServletRequest) request;
        MultipartFile file=multipartHttpRequest.getFile("editormd-image-file");
        try {
            String myprovide = fileProvide.myprovide(file.getInputStream(), file.getOriginalFilename());
            FileDto fileDto = new FileDto();
            fileDto.setSuccess(1);
            fileDto.setUrl(myprovide);
            return fileDto;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }
}
