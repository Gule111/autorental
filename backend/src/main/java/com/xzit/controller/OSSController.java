package com.xzit.controller;

import com.xzit.service.IOssService;
import com.xzit.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 31507
 */
@RestController
@RequestMapping("/auto/oss")
public class OSSController {
    @Resource
    private IOssService ossService;
    @PostMapping("/upload")
    public Result upload(MultipartFile file){
        return Result.success(ossService.upload(file));
    }
}
