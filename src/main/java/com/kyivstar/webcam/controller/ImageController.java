package com.kyivstar.webcam.controller;


import com.kyivstar.webcam.model.DownloadResult;
import com.kyivstar.webcam.model.Image;
import com.kyivstar.webcam.repository.ImageRepository;
import com.kyivstar.webcam.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class ImageController {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    ContentService contentService;

    @GetMapping(value = "/image")
    public ResponseEntity<InputStreamResource> getImage(@RequestParam("id") String cameraId, @RequestParam("timestamp") Long timestamp) {
        Image image = imageRepository.findByCameraIdAndTimestamp(cameraId, timestamp);
        if(null == image) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        DownloadResult downloadResult = contentService.download(image.getStoreKey());

        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl("no-cache, no-store, must-revalidate");
        headers.setPragma("no-cache");
        headers.setExpires(0L);


        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType(
                        "image/"
                )).body(
                        new InputStreamResource(
                                downloadResult.getPayload().getInputStream()
                        )
                );
    }
}
