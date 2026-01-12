package com.xzit.service.impl;

import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.xzit.config.OSSConfig;
import com.xzit.mapper.UserMapper;
import com.xzit.service.IOssService;
import com.xzit.service.IUserService;
import com.xzit.utils.FileUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author 31507
 */
@Service
public class OssServiceImpl implements IOssService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private OSSConfig ossConfig;
    @Override
    public String upload(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        String fileName = FileUtils.getFilePath(originalFileName);
        OSS ossClient=new OSSClientBuilder().build(
                ossConfig.getEndpoint(),
                ossConfig.getAccessKeyId(),
                ossConfig.getAccessKeySecret()
        );
        try {
            ossClient.putObject(ossConfig.getBucketName(),fileName,file.getInputStream());

            String url = "https://"+ossConfig.getBucketName()+"."+ossConfig.getEndpoint()+"/"+fileName;
            return url;
        } catch (IOException e) {
            throw new RuntimeException("图片上传失败");
        }finally {
            ossClient.shutdown();
        }
    }

    @Override
    public void delete(String url) {
        //    https://auto-test-gule.oss-cn-chengdu.aliyuncs.com/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20250512210144.jpg
        OSS ossClient=new OSSClientBuilder().build(
                ossConfig.getEndpoint(),
                ossConfig.getAccessKeyId(),
                ossConfig.getAccessKeySecret()
        );
        String host="https://"+ossConfig.getBucketName()+"."+ossConfig.getEndpoint()+"/";
//        %E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20250512210144.jpg
        String objectName= StrUtil.removePrefix(url,host);
        try{
            ossClient.deleteObject(ossConfig.getBucketName(),objectName);
        } catch (OSSException e) {
            throw new RuntimeException(e);
        } catch (ClientException e) {
            throw new RuntimeException(e);
        }finally {
            ossClient.shutdown();
        }

    }
}
