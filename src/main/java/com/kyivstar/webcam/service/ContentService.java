package com.kyivstar.webcam.service;

import com.kyivstar.webcam.model.DownloadResult;
import org.apache.tomcat.util.http.fileupload.FileItemIterator;

/**
 * Created by igor on 20.09.17.
 */
public interface ContentService {
    void save(FileItemIterator fileItemIterator, String cameraId);
    DownloadResult download(String code);
}
