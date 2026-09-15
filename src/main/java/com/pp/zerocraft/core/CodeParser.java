package com.pp.zerocraft.core;

import cn.hutool.core.util.StrUtil;
import com.pp.zerocraft.ai.model.HtmlCodeResult;
import com.pp.zerocraft.ai.model.MultiFileCodeResult;
import com.pp.zerocraft.exception.BusinessException;
import com.pp.zerocraft.exception.ErrorCode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 代码解析器--因为我们的流式模型输出是不支持格式化输出的，所以要用代码解析器
 * 提供静态方法解析不同类型的代码内容
 *
 * @author pp
 */
public class CodeParser {

    private static final Pattern HTML_CODE_PATTERN = Pattern.compile("```html\\s*\\n([\\s\\S]*?)```", Pattern.CASE_INSENSITIVE);
    private static final Pattern CSS_CODE_PATTERN = Pattern.compile("```css\\s*\\n([\\s\\S]*?)```", Pattern.CASE_INSENSITIVE);
    private static final Pattern JS_CODE_PATTERN = Pattern.compile("```(?:js|javascript)\\s*\\n([\\s\\S]*?)```", Pattern.CASE_INSENSITIVE);
    // 兜底：模型有时会把样式/脚本内联在 HTML 中，而不是单独输出代码块
    private static final Pattern INLINE_STYLE_PATTERN = Pattern.compile("<style[^>]*>([\\s\\S]*?)</style>", Pattern.CASE_INSENSITIVE);
    private static final Pattern INLINE_SCRIPT_PATTERN = Pattern.compile("<script(?![^>]*\\bsrc\\s*=)[^>]*>([\\s\\S]*?)</script>", Pattern.CASE_INSENSITIVE);

    /**
     * 解析 HTML 单文件代码
     */
    public static HtmlCodeResult parseHtmlCode(String codeContent) {
        HtmlCodeResult result = new HtmlCodeResult();
        // 提取 HTML 代码
        String htmlCode = extractHtmlCode(codeContent);
        if (htmlCode != null && !htmlCode.trim().isEmpty()) {
            result.setHtmlCode(htmlCode.trim());
        } else {
            // 如果没有找到代码块，将整个内容作为HTML
            result.setHtmlCode(codeContent.trim());
        }
        return result;
    }

    /**
     * 解析多文件代码（HTML + CSS + JS）
     */
    public static MultiFileCodeResult parseMultiFileCode(String codeContent) {
        MultiFileCodeResult result = new MultiFileCodeResult();
        // 提取各类代码
        String htmlCode = extractCodeByPattern(codeContent, HTML_CODE_PATTERN);
        String cssCode = extractCodeByPattern(codeContent, CSS_CODE_PATTERN);
        String jsCode = extractCodeByPattern(codeContent, JS_CODE_PATTERN);
        // 校验代码块完整性，避免后续写入 null 内容
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
     * 提取HTML代码内容
     *
     * @param content 原始内容
     * @return HTML代码
     */
    private static String extractHtmlCode(String content) {
        Matcher matcher = HTML_CODE_PATTERN.matcher(content);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    /**
     * 根据正则模式提取代码
     *
     * @param content 原始内容
     * @param pattern 正则模式
     * @return 提取的代码
     */
    private static String extractCodeByPattern(String content, Pattern pattern) {
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
