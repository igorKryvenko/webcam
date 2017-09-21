package com.kyivstar.webcam.model;

import org.openstack4j.model.common.DLPayload;

/**
 * Created by igor on 20.09.17.
 */
public class DownloadResult {

    private DLPayload payload;



    public DLPayload getPayload() {
        return payload;
    }

    public DownloadResult setPayload(DLPayload payload) {
        this.payload = payload;
        return this;
    }
}
