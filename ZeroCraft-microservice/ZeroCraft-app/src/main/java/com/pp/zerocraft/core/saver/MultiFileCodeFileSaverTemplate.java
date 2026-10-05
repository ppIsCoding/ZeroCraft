package com.pp.zerocraft.core.saver;

import cn.hutool.core.util.StrUtil;
import com.pp.zerocraft.ai.model.MultiFileCodeResult;
import com.pp.zerocraft.ai.model.enums.CodeGenTypeEnum;
import com.pp.zerocraft.exception.BusinessException;
import com.pp.zerocraft.exception.ErrorCode;

/**
 * 多文件代码保存器
 *
 * @author pp
 */
public class MultiFileCodeFileSaverTemplate extends CodeFileSaverTemplate<MultiFileCodeResult> {

    @Override
    public CodeGenTypeEnum getCodeType() {
        return CodeGenTypeEnum.MULTI_FILE;
    }

    @Override
    protected void saveFiles(MultiFileCodeResult result, String baseDirPath) {
        // 保存 HTML 文件
        writeToFile(baseDirPath, "index.html", result.getHtmlCode());
        // 保存 CSS 文件
        writeToFile(baseDirPath, "style.css", result.getCssCode());
        // 保存 JavaScript 文件
        writeToFile(baseDirPath, "script.js", result.getJsCode());
    }

    @Override
    protected void validateInput(MultiFileCodeResult result) {
        super.validateInput(result);
        // 多文件模式下 HTML、CSS、JS 缺一不可，避免静默只保存部分文件
        if (StrUtil.isBlank(result.getHtmlCode()) || StrUtil.isBlank(result.getCssCode()) || StrUtil.isBlank(result.getJsCode())) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "多文件代码不完整，HTML/CSS/JS 内容均不能为空");
        }
    }
}
