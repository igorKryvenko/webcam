package com.kyivstar.webcam.service;

import com.kyivstar.webcam.exception.StorageServiceException;
import com.kyivstar.webcam.model.Camera;
import com.kyivstar.webcam.model.DownloadResult;
import com.kyivstar.webcam.model.Image;
import com.kyivstar.webcam.model.StoreKey;
import com.kyivstar.webcam.repository.ImageRepository;
import com.kyivstar.webcam.storage.OpenStackService;
import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.openstack4j.model.common.DLPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.io.InputStream;
import java.util.UUID;

/** Created by igor on 20.09.17. */
@Service
@Transactional
public class ContentServiceImpl implements ContentService {
  private static final Logger LOGGER = LoggerFactory.getLogger(ContentServiceImpl.class);

  @Autowired private OpenStackService openStackService;
  @Autowired private ImageRepository imageRepository;

  @Override
  public void save(FileItemIterator fileItemIterator, String cameraId) {
    try {
      FileItemStream itemStream = fileItemIterator.next();
      String fileName = itemStream.getName();
      StoreKey storeKey =
          new StoreKey()
              .setUuid(UUID.randomUUID().toString())
              .setFileName(fileName)
              .setCameraId(cameraId);
      InputStream inputStream = itemStream.openStream();
      uploadSave(storeKey, inputStream, itemStream.getContentType());

    } catch (Exception e) {
      LOGGER.error("During upload", e);
      throw new StorageServiceException("Cant upload : " + e.getMessage(), e);
    }
  }

  @Override
  public DownloadResult download(String code) {
    final DownloadResult dr = new DownloadResult();

    dr.setImage(getEntity(code));
    dr.setPayload(getStoreObject(dr.getImage().getStoreKey()));
    return dr;
  }

  protected void uploadSave(StoreKey storeKey, InputStream inputStream, String contentType) {
    openStackService.upload(storeKey.getKey(), inputStream, contentType);
    try {
      Image image = new Image();
      image.setCameraId(storeKey.getCameraId());
      image.setTimestamp(System.currentTimeMillis());
      image.setStoreKey(storeKey.getKey());
      imageRepository.save(image);
    } catch (Exception e) {
      LOGGER.error("Error during saving.", e);
      openStackService.deleteObject(storeKey.getKey());
      throw new StorageServiceException("Database error", e);
    }
  }

  protected Image getEntity(final String shortCode) {
    final Image image = imageRepository.findOne(shortCode);
    try {
      if (null == image) {

        throw new StorageServiceException(
            String.format("Entity not found. shortCode = [%s]", shortCode));
      }
      return image;
    } catch (Exception e) {
      LOGGER.error("Can't get entity", e);
      throw new StorageServiceException(e);
    }
  }

  protected DLPayload getStoreObject(final String storeKey) {
    final DLPayload payload = openStackService.getObjectPayload(storeKey);
    if (null == payload) {
      throw new StorageServiceException(
          String.format("Resource not found. storeKey = [%s]", storeKey));
    }
    return payload;
  }
}
