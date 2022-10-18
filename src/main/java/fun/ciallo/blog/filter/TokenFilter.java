package fun.ciallo.blog.filter;

import fun.ciallo.blog.common.annotations.UseToken;
import fun.ciallo.blog.common.enums.Status;
import fun.ciallo.blog.common.result.BlogException;
import fun.ciallo.blog.utils.AssertUtils;
import fun.ciallo.blog.utils.UserUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenFilter implements HandlerInterceptor {
    @Resource
    private AssertUtils assertUtils;
    @Resource
    private UserUtils userUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        UseToken flag = null;
        boolean pass = true;
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            flag = method.getMethodAnnotation(UseToken.class);
        }
        if (null != flag) {
            String token = request.getHeader("Authorization");
            assertUtils.notNull(token, new BlogException(Status.USER_ERROR));
            pass = userUtils.tokenValidator(token);
            assertUtils.isTrue(pass, new BlogException(Status.USER_ERROR));
            Long id = ((Integer) userUtils.tokenParser(token, "id")).longValue();
            request.setAttribute("id", id);
        }
        return pass;
    }
}
