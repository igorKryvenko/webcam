package com.kyivstar.webcam.model;

/**
 * Created by igor on 20.09.17.
 */
public class StoreKey {
    private String cameraId;
    private String uuid;


    public String getUuid() {
        return uuid;
    }

    public StoreKey setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getCameraId() {
        return cameraId;
    }

    public StoreKey setCameraId(String cameraId) {
        this.cameraId = cameraId;
        return this;
    }

    public String getKey() {
        return String.format("camera/%s/%s",cameraId,uuid);
    }
}
