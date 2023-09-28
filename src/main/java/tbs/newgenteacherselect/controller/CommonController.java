package tbs.newgenteacherselect.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tbs.framework.process.controller.BaseController;

@RestController
@RequestMapping("")
@Api(tags = "通用接口")
public class CommonController extends BaseController {

    @GetMapping("result/{key}")
    @ApiOperation("异步结果查询")
    public Object getResult(@PathVariable @ApiParam("结果密钥") String key) throws Throwable {
        return success(getAsyncResult(key));
    }

}
