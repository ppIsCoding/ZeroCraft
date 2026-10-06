package com.pp.zerocraft.service;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;


public interface ScreenshotService {
    String generateAndUploadScreenshot(String webUrl);
}
