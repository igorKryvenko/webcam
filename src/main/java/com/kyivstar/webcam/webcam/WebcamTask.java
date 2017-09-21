package com.kyivstar.webcam.webcam;

import com.github.sarxos.webcam.WebcamUtils;
import com.kyivstar.webcam.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.sarxos.webcam.Webcam;

import java.io.ByteArrayInputStream;


/**
 * Created by igor on 21.09.17.
 */
public class WebcamTask implements Runnable {



    @Autowired
    private ContentService contentService;
    @Override
    public void run() {
        Webcam webcam = Webcam.getDefault();

        webcam.open();

        if (!webcam.isOpen()) {
            return;
        }

        byte[] bytes = WebcamUtils.getImageBytes(webcam, "jpg");

        contentService.save(new ByteArrayInputStream(bytes),webcam.getName());

    }
}
