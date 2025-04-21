package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.impl.MinioSysFileServiceImpl;
import com.sky.vo.FileVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/admin/common")
@Slf4j
@Api(tags = "通用接口")
public class CommonController {

    @Autowired
    private MinioSysFileServiceImpl uploadService;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(@RequestParam("file")MultipartFile file) throws IOException {
        log.info("文件上传：{}",file);
        FileVO upload = uploadService.upload(file);
        return Result.success(upload.getUrl());
    }
}
