package com.kyivstar.webcam.model;

public class Image {
    private String id;
    private Long timestamp;
    private String cameraId;
    private String storeKey;

    public String getId() {
        return id;
    }

    public Image setId(String id) {
        this.id = id;
        return this;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public Image setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getCameraId() {
        return cameraId;
    }

    public Image setCameraId(String cameraId) {
        this.cameraId = cameraId;
        return this;
    }

    public String getStoreKey() {
        return storeKey;
    }

    public Image setStoreKey(String storeKey) {
        this.storeKey = storeKey;
        return this;
    }
}
