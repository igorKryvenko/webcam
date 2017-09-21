package com.kyivstar.webcam.repository;


import com.kyivstar.webcam.model.Image;

import org.springframework.data.mongodb.repository.MongoRepository;




public interface ImageRepository extends MongoRepository<Image,String> {
    Image findByCameraIdAndTimestamp(String cameraId, Long timestamp);
}
