package com.kyivstar.webcam.model;

public class Image {
    private Long id;
    private byte[] data;

    public Long getId() {
        return id;
    }

    public Image setId(Long id) {
        this.id = id;
        return this;
    }

    public byte[] getData() {
        return data;
    }

    public Image setData(byte[] data) {
        this.data = data;
        return this;
    }
}
