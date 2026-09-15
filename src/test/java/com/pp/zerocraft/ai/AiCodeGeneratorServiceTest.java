package com.pp.zerocraft.ai;

import com.pp.zerocraft.ai.model.HtmlCodeResult;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AiCodeGeneratorServiceTest {

    @Resource
    private AiCodeGeneratorService aiCodeGeneratorService;

    @Test
    public void testGenerate() {
        HtmlCodeResult htmlCode = aiCodeGeneratorService.generateHtmlCode("设计一个博客网站，不超过50行");
        Assertions.assertNotNull(htmlCode);
    }
}
