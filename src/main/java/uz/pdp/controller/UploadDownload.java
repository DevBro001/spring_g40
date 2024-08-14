package uz.pdp.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.UploadDTO;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Controller
public class UploadDownload {
    Path rootPath = Path.of("C:\\Users\\bobur\\Desktop\\projects\\g40\\SpringCore\\src\\main\\resources\\imageStore");

    @GetMapping(value = "/upload")
    public String upload(){
        return "upload";
    }
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public String upload(UploadDTO uploadDTO) throws IOException {
        System.out.println(uploadDTO);
        MultipartFile multipartFile = uploadDTO.getFile();
        Path path = rootPath.resolve(multipartFile.getOriginalFilename()); // .png .doc .mp4 .mp3
        multipartFile.transferTo(path);

        /*byte[] bytes = multipartFile.getBytes();
        InputStream inputStream = multipartFile.getInputStream();
        Files.write(path,bytes, StandardOpenOption.CREATE);*/
        return "upload";
    }
}
