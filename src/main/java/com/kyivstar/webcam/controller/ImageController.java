package com.kyivstar.webcam.controller;


import com.kyivstar.webcam.model.Image;
import com.kyivstar.webcam.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class ImageController {

    @Autowired
    ImageRepository imageRepository;


    @PostMapping(value = "image")
    public ResponseEntity sendImage(@RequestBody Image image) {
        imageRepository.save(image);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/image")
    public ResponseEntity<Image> getImage(@RequestParam("id") long id) {
        Image image = imageRepository.findOne(id);
        if(null == image) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(image,HttpStatus.OK);
    }
}
