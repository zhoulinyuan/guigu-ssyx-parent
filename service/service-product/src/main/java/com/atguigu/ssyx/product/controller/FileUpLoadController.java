package com.atguigu.ssyx.product.controller;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.product.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin/product")
@CrossOrigin
public class FileUpLoadController {
    @Autowired
    private FileUploadService fileUploadService;

    //图片上传方法
    @PostMapping("fileUpload")
    public Result fileUpload(MultipartFile file){
        String url=fileUploadService.uploadFile(file);
        return Result.ok(url);
    }
}
