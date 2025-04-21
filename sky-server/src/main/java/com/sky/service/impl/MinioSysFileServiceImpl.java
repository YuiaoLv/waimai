package com.sky.service.impl;
import com.sky.constant.MessageConstant;
import com.sky.vo.FileVO;
import com.sky.config.MinioConfiguration;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.rmi.ServerException;

@Slf4j
@Service
public class MinioSysFileServiceImpl {

    // 注入minio配置文件
    @Autowired
    private MinioConfiguration minioConfig;

    // 注入minio client
    @Autowired
    private MinioClient client;

    public FileVO upload(MultipartFile file) throws ServerException {
        FileVO fileVO = new FileVO();
        try {
            // 获取文件真实名称
            String originalFilename = file.getOriginalFilename();
            // 获取文件的扩展名 例如.jpg .doc
            String extname = originalFilename.substring(originalFilename.lastIndexOf("."));
            // 构建文件上传相关信息
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .object(originalFilename)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build();
            // 将文件上传到minio服务器
            client.putObject(args);
            log.info("文件上传成功");
            // 组装文件信息，返回前端 或者存入数据路
            String url = minioConfig.getUrl()+"/" + minioConfig.getBucketName() + "/" + originalFilename;
            fileVO.setUrl(url);
            fileVO.setSize(file.getSize());
            fileVO.setFileName(originalFilename);
            fileVO.setExtname(extname);
        } catch (Exception e) {
            throw new ServerException(MessageConstant.UPLOAD_FAILED + ":" + e.getCause().toString());
        }
        return fileVO;
    }
}
