package cn.bobdeng.base.rbac.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestControllerAdvice
@Slf4j
public class RbacExceptionHandler {
    @ExceptionHandler(PermissionDeniedException.class)
    public void onPermissionDenied(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getOutputStream().write("无权限".getBytes(StandardCharsets.UTF_8));
    }
}