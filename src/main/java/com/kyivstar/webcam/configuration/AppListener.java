package com.kyivstar.webcam.configuration;


import com.kyivstar.webcam.webcam.WebcamTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;


import java.util.concurrent.*;


public class AppListener implements ApplicationListener<ApplicationReadyEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppListener.class);
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        LOGGER.info("Start capture images");
        ScheduledExecutorService executorService =  Executors.newScheduledThreadPool(5);
        executorService.schedule(new WebcamTask(),5, TimeUnit.SECONDS); //suppose WebcamTask is taken from queue with webcam devices
    }
}
