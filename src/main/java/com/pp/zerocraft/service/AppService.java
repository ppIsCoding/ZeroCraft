package com.pp.zerocraft.service;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.pp.zerocraft.model.dto.app.AppAddRequest;
import com.pp.zerocraft.model.dto.app.AppQueryRequest;
import com.pp.zerocraft.model.entity.App;
import com.pp.zerocraft.model.entity.User;
import com.pp.zerocraft.model.vo.AppVO;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * 应用 服务层。
 *
 * @author <a href="https://github.com/ppiscoding">pp</a>
 */
public interface AppService extends IService<App> {

    AppVO getAppVO(App app);

    QueryWrapper getQueryWrapper(AppQueryRequest appQueryRequest);

    List<AppVO> getAppVOList(List<App> appList);

    Flux<String> chatToGenCode(Long appId, String message, User loginUser);

    String deployApp(Long appId, User loginUser);

    void generateAppScreenshotAsync(Long appId, String appUrl);

    Long createApp(AppAddRequest appAddRequest, User loginUser);
}
