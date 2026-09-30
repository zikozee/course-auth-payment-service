package com.zee.course.auth.payment.authpaymentservice.config;


import com.zee.course.auth.payment.authpaymentservice.datatype.MessageType;
import com.zee.course.auth.payment.authpaymentservice.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.web.AuthenticationEntryPoint;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @dev : Ezekiel Eromosei
 * @date : 29 Sep, 2026
 */

public class CsstomAuthenticationEntryPoint  implements AuthenticationEntryPoint {

    @Override
    public void commence(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
                         AuthenticationException exception) throws IOException {

        String errorCode = "invalid_token";
        String message = exception.getLocalizedMessage();

        if (exception instanceof OAuth2AuthenticationException oauthException) {
            OAuth2Error error = oauthException.getError();

            errorCode = error.getErrorCode();

            if (error.getDescription() != null) {
                message = error.getDescription();
            }
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiResponse<String> apiResponse = ApiResponse.<String>builder()
                .messageType(MessageType.ERROR)
                .successful(false)
                .errorCode(errorCode)
                .errorMessage(message)
                .build();

        String jsonResponse = new ObjectMapper().writeValueAsString(apiResponse);
        response.getWriter().write(jsonResponse);
    }
}
