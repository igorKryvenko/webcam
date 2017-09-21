package com.kyivstar.webcam.configuration;

import com.kyivstar.webcam.webcam.WebcamTask;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.concurrent.*;

/**
 * Created by igor on 21.09.17.
 */
public class AppListener implements ApplicationListener<ApplicationReadyEvent> {


    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        ScheduledExecutorService executorService =  Executors.newScheduledThreadPool(5);
        executorService.schedule(new WebcamTask(),5, TimeUnit.SECONDS);

    }
}
