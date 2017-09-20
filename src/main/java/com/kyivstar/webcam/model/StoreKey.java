package com.kyivstar.webcam.model;

/**
 * Created by igor on 20.09.17.
 */
public class StoreKey {
    private String cameraId;
    private String uuid;
    private String fileName;

    public String getUuid() {
        return uuid;
    }

    public StoreKey setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }


    public String getFileName() {
        return fileName;
    }

    public String getCameraId() {
        return cameraId;
    }

    public StoreKey setCameraId(String cameraId) {
        this.cameraId = cameraId;
        return this;
    }

    public StoreKey setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }
    public String getKey() {
        return String.format("camera/%s/%s/%s",cameraId,uuid,fileName);
    }
}
