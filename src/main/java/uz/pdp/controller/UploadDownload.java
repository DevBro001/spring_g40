package uz.pdp.controller;


import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.File;
import uz.pdp.UploadDTO;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.List;

@Controller
public class UploadDownload {
    List<File> fileDB = new ArrayList<>();
    Path rootPath = Path.of("C:\\Users\\bobur\\Desktop\\projects\\g40\\SpringCore\\src\\main\\resources\\imageStore");

    @GetMapping(value = "/upload")
    public String upload(){
        return "upload";
    }
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public String upload(UploadDTO uploadDTO) throws IOException {
        System.out.println(uploadDTO);
        MultipartFile multipartFile = uploadDTO.getFile();
        String contentType = multipartFile.getContentType();
        MediaType mediaType = MediaType.parseMediaType(contentType);
        // image/jpg
        String originalFilename = multipartFile.getOriginalFilename(); // rasm.jpg
        String uuid = UUID.randomUUID().toString(); // ckuyrgr42grcv3uy14v5k12yt4gv23
        Path path = rootPath.resolve(uuid+"."+ StringUtils.getFilenameExtension(originalFilename)); // /imageStore/ckuyrgr42grcv3uy14v5k12yt4gv23+.jpg
        multipartFile.transferTo(path);
        File build = File.builder()
                .name(StringUtils.getFilename(originalFilename))
                .originalFilename(originalFilename)
                .uuid(uuid)
                .contentType(contentType)
                .build();

        fileDB.add( build);
        //save DB
        return "upload";
    }
    @RequestMapping(value = "/download/{fileUuid}",method = RequestMethod.GET)
    public  ResponseEntity<FileSystemResource> download(@PathVariable("fileUuid") String fileUuid) throws IOException {
        Optional<File> first = fileDB.stream().filter((f) -> Objects.equals(fileUuid, f.getUuid())).findFirst();
        File file = first.orElseThrow(FileNotFoundException::new);
        String uuid = file.getUuid();
        String contentType = file.getContentType();
        String originalFilename = file.getOriginalFilename();
        Path resolve = rootPath.resolve(uuid + "." + StringUtils.getFilenameExtension(originalFilename));
//        byte[] bytes = Files.readAllBytes(resolve);
        FileSystemResource fileSystemResource = new FileSystemResource(resolve);
        ResponseEntity<FileSystemResource> body = ResponseEntity
                .accepted()
                .contentType(MediaType.parseMediaType(contentType))
                .header("Content-Disposition","attachment; fileName="+originalFilename)
                .body(fileSystemResource);
        return body;
    }
    @RequestMapping(value = "/show",method = RequestMethod.GET)
    public  String download(Model model) throws IOException {
        model.addAttribute("files",fileDB);
        return "files";
    }
}
