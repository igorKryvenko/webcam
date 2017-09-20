package com.kyivstar.webcam.repository;


import com.kyivstar.webcam.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface ImageRepository extends JpaRepository<Image,Long> {
}
