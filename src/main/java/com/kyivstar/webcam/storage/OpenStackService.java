package com.kyivstar.webcam.storage;

import org.openstack4j.model.common.DLPayload;

import java.io.InputStream;

/**
 * Created by igor on 20.09.17.
 */
public interface OpenStackService {
    void deleteObject(String storeKey);
    String upload(String storeKey, InputStream inputStream, String contentType);
    DLPayload getObjectPayload(String storeKey);
}
