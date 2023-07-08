package tbs.newgenteacherselect.config.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tbs.framework.interfaces.IAccessInput;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
public class CookiesAccessInputer implements IAccessInput {
    @Override
    public String tokenValue(String field, HttpServletRequest request) {
        Cookie[] cookies=request.getCookies();
        if(cookies == null)
            return null;
        for (Cookie cookie : cookies) {
            if(field.equals(cookie.getName()))
            {
                return cookie.getValue();
            }
        }
        return null;
    }
}
