package com.kyivstar.webcam.service;

import com.kyivstar.webcam.model.DownloadResult;
import org.apache.tomcat.util.http.fileupload.FileItemIterator;

import java.io.InputStream;

/**
 * Created by igor on 20.09.17.
 */
public interface ContentService {
    void save(InputStream inputStream, String cameraId);
    DownloadResult download(String code);
}
