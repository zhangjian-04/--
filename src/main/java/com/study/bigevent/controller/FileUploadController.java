package com.study.bigevent.controller;

import com.study.bigevent.pojo.Result;
import com.study.bigevent.util.AliOssUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {

    /**
     * 上传文件
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("upload")
    public Result<String> upload(MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();
        //这个uuid就是随机生成这个照片的名字用于他不被覆盖
        String fileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
        // 本地上传  这个就是添加的路径，但是在文件夹下面要添加一个\\就是表示当前市在这个目录下
//        file.transferTo(new File("C:\\Users\\zcj\\Desktop\\测试\\" + fileName));

        //上传到阿里云
        String url = AliOssUtil.uploadFile(fileName, file.getInputStream());
        return Result.success(url);
    }

}
