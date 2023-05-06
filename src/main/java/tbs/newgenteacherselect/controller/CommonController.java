package tbs.newgenteacherselect.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tbs.utils.AOP.controller.ApiProxy;
import tbs.utils.AOP.controller.IAction;
import tbs.utils.Async.AsyncMethod;
import tbs.utils.Results.NetResult;

import javax.annotation.Resource;

@RestController
public class CommonController {
    @Resource
    AsyncMethod asyncMethod;

    @Resource
    ApiProxy proxy;

    @RequestMapping("result/{key}")
    public Object getResult(@PathVariable String key) throws Exception {
        return asyncMethod.get(key);
    }

}
