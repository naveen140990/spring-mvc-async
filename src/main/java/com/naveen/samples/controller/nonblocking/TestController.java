package com.naveen.samples.controller.nonblocking;

import org.asynchttpclient.*;
import org.asynchttpclient.util.HttpConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import javax.annotation.PostConstruct;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

/**
 * @author naveen on 25/8/18
 */
@RestController
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private AsyncTaskExecutor asyncTaskExecutor;

    BoundRequestBuilder getRequest;

    @PostConstruct
    public void init(){
        AsyncHttpClient client = Dsl.asyncHttpClient();
        getRequest = client.prepareGet("http://localhost:8090/sleep/1000");
    }

    @GetMapping(value = "/blockingRequestProcessing")
    public String blockingRequestProcessing() {
        logger.debug("Blocking Request processing Triggered");
        String url = "http://localhost:8090/sleep/1000";
        new RestTemplate().getForObject(url, Boolean.TYPE);
        return "blocking...";
    }

    @GetMapping(value = "/asyncBlockingRequestProcessing")
    public CompletableFuture<String> asyncBlockingRequestProcessing(){
        return CompletableFuture.supplyAsync(() -> {
            logger.debug("Async Blocking Request processing Triggered");
            String url = "http://localhost:8090/sleep/1000";
            new RestTemplate().getForObject(url, Boolean.TYPE);
            return "Async blocking...";
        },asyncTaskExecutor);
        /*return () -> {
            String url = "http://localhost:8090/sleep/1000";
            new RestTemplate().getForObject(url, Boolean.TYPE);
            return "Async blocking...";
        };
*/
    }

    @GetMapping(value = "/asyncNonBlockingRequestProcessing")
    public CompletableFuture<String> asyncNonBlockingRequestProcessing(){
            ListenableFuture<String> listenableFuture = getRequest.execute(new AsyncCompletionHandler<String>() {
                @Override
                public String onCompleted(Response response) throws Exception {
                    logger.debug("Async Non Blocking Request processing completed");
                    return "Async Non blocking...";
                }
            });
            return listenableFuture.toCompletableFuture();
    }


}
