package com.xzit.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author 31507
 */
public interface IOssService {
    String upload(MultipartFile  file);
    void delete(String url);
}
