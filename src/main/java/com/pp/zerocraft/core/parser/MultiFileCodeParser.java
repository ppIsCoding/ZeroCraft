package com.pp.zerocraft.core.parser;

import cn.hutool.core.util.StrUtil;
import com.pp.zerocraft.ai.model.MultiFileCodeResult;
import com.pp.zerocraft.exception.BusinessException;
import com.pp.zerocraft.exception.ErrorCode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 多文件代码解析器（HTML + CSS + JS）
 *
 * @author pp
 */
public class MultiFileCodeParser implements CodeParser<MultiFileCodeResult> {

    private static final Pattern HTML_CODE_PATTERN = Pattern.compile("```html\\s*\\n([\\s\\S]*?)```", Pattern.CASE_INSENSITIVE);
    private static final Pattern CSS_CODE_PATTERN = Pattern.compile("```css\\s*\\n([\\s\\S]*?)```", Pattern.CASE_INSENSITIVE);
    private static final Pattern JS_CODE_PATTERN = Pattern.compile("```(?:js|javascript)\\s*\\n([\\s\\S]*?)```", Pattern.CASE_INSENSITIVE);
    // 兜底：模型有时会把样式/脚本内联在 HTML 中，而不是单独输出代码块
    private static final Pattern INLINE_STYLE_PATTERN = Pattern.compile("<style[^>]*>([\\s\\S]*?)</style>", Pattern.CASE_INSENSITIVE);
    private static final Pattern INLINE_SCRIPT_PATTERN = Pattern.compile("<script(?![^>]*\\bsrc\\s*=)[^>]*>([\\s\\S]*?)</script>", Pattern.CASE_INSENSITIVE);

    @Override
    public MultiFileCodeResult parseCode(String codeContent) {
        MultiFileCodeResult result = new MultiFileCodeResult();
        // 提取各类代码
        String htmlCode = extractCodeByPattern(codeContent, HTML_CODE_PATTERN);
        String cssCode = extractCodeByPattern(codeContent, CSS_CODE_PATTERN);
        String jsCode = extractCodeByPattern(codeContent, JS_CODE_PATTERN);
        // 兜底：从 HTML 中提取内联的样式和脚本
        if (StrUtil.isBlank(cssCode) && StrUtil.isNotBlank(htmlCode)) {
            cssCode = extractCodeByPattern(htmlCode, INLINE_STYLE_PATTERN);
        }
        if (StrUtil.isBlank(jsCode) && StrUtil.isNotBlank(htmlCode)) {
            jsCode = extractCodeByPattern(htmlCode, INLINE_SCRIPT_PATTERN);
        }
        // 严格校验，避免模型只输出部分代码块时静默保存为单文件
        if (StrUtil.isBlank(htmlCode) || StrUtil.isBlank(cssCode) || StrUtil.isBlank(jsCode)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,
                    "解析多文件代码失败：AI 输出格式不正确，缺少 HTML/CSS/JS 代码块");
        }
        result.setHtmlCode(htmlCode.trim());
        result.setCssCode(cssCode.trim());
        result.setJsCode(jsCode.trim());
        return result;
    }

    /**
     * 根据正则模式提取代码
     *
     * @param content 原始内容
     * @param pattern 正则模式
     * @return 提取的代码
     */
    private String extractCodeByPattern(String content, Pattern pattern) {
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
