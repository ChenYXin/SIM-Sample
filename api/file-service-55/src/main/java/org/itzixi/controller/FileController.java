package org.itzixi.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.itzixi.MinIOConfig;
import org.itzixi.MinIOUtils;
import org.itzixi.grace.result.GraceJSONResult;
import org.itzixi.grace.result.ResponseStatusEnum;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    private MinIOConfig minIOConfig;

    @PostMapping("/uploadFace1")
    public GraceJSONResult uploadFace1(@RequestParam("file") MultipartFile file,
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
        if (!newFile.getParentFile().exists()) {
            //如果目标文件所在目录不存在，则创建父级目录
            newFile.getParentFile().mkdirs();
        }
        //将内存中的数据写入磁盘
        file.transferTo(newFile);

        return GraceJSONResult.ok();
    }

    @PostMapping("/uploadFace")
    public GraceJSONResult uploadFace(@RequestParam("file") MultipartFile file,
                                      String userId,
                                      HttpServletRequest request) throws Exception {
        if (StringUtils.isBlank(userId)) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.FILE_UPLOAD_FAILD);
        }

        //获得文件原始名称
        String fileName = file.getOriginalFilename();
        if (StringUtils.isBlank(fileName)) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.FILE_UPLOAD_FAILD);
        }
        // File.separator 表示当前操作系统的文件路径分隔符
        // Windows 系统中，文件路径分隔符是\（反斜杠），而在 Unix/Linux 和 macOS 系统中，文件路径分隔符是/（正斜杠）
        fileName = "face" + File.separator + userId + File.separator + fileName;
        MinIOUtils.uploadFile(minIOConfig.getBucketName(),
                fileName,
                file.getInputStream());
        String faceUrl = minIOConfig.getFileHost()
                + "/"
                + minIOConfig.getBucketName()
                + "/"
                + fileName;
        return GraceJSONResult.ok(faceUrl);
    }
}
