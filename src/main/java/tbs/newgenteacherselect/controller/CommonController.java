package tbs.newgenteacherselect.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tbs.framework.async.AsyncMethod;
import tbs.framework.controller.BaseController;


import javax.annotation.Resource;

@RestController
@RequestMapping("")
public class CommonController extends BaseController {

    @RequestMapping("result/{key}")
    public Object getResult(@PathVariable String key) throws Throwable {
        return success(getAsyncResult(key));
    }

}
