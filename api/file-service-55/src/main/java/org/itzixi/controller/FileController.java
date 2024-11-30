package org.itzixi.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.itzixi.grace.result.GraceJSONResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {

    @PostMapping("/uploadFace")
    public GraceJSONResult uploadFace(@RequestParam("file") MultipartFile file,
                                      String userId,
                                      HttpServletRequest request) throws IOException {
        //获得文件原始名称
        String fileName = file.getOriginalFilename();
        //从最后一个.开始截取
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //文件的新名称
        String newFileName = userId + suffixName;
        //设置文件存储路径，可以存放到任意到指定路径
        String rootPath = "/Users/chenyuexin/Downloads/1" + File.separator;
        String filePath = rootPath + File.separator + "face" + File.separator + newFileName;
        File newFile = new File(filePath);
        //判断目标文件所有目录是否存在
        if(!newFile.getParentFile().exists()){
            //如果目标文件所在目录不存在，则创建父级目录
            newFile.getParentFile().mkdirs();
        }
        //将内存中的数据写入磁盘
        file.transferTo(newFile);

        return GraceJSONResult.ok();
    }
}
