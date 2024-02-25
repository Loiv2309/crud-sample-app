package hr.comping.crud.interceptors;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 *  A custom filter for logging request and response information.
 */
@Slf4j
@Component
public class LoggingFilter extends OncePerRequestFilter {

    private static final int MAX_BODY_SIZE = 10000; //Max body size in bytes

    /**
     * Overrides the doFilterInternal method to implement custom request and response logging.
     * @param request The HTTP request being processed.
     * @param response The HTTP response being generated.
     * @param filterChain The filter chain for invoking the next filter in the chain.
     * @throws ServletException If an error occurs during servlet processing.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        long startTimeMethod = System.currentTimeMillis();
        filterChain.doFilter(requestWrapper, responseWrapper);
        long methodTimeTaken = System.currentTimeMillis() - startTimeMethod;

        String requestBody = "TRUNCATED";
        String responseBody = "TRUNCATED";
        if (requestWrapper.getContentAsByteArray().length < MAX_BODY_SIZE) {
            requestBody = getStringValue(requestWrapper.getContentAsByteArray(),
                    request.getCharacterEncoding());
        }
        if (requestWrapper.getContentAsByteArray().length < MAX_BODY_SIZE) {
            responseBody = getStringValue(responseWrapper.getContentAsByteArray(),
                    response.getCharacterEncoding());
        }

        responseWrapper.copyBodyToResponse();

        Map<String, String> headers = getHeaders(request);
        String parameters = request.getQueryString();
        String uri = request.getRequestURI();
        String method = request.getMethod();
        int responseStatus = response.getStatus();

        log.info("\n--------------------------------------\nREQUEST:\nURI:{}\nMethod:{}\nParameters:{}\nHeaders:{}\nRequest Body:{}\n\nRESPONSE:\nResponseBody:{}\nResponse Status:{}\n\nTime taken:{}\n--------------------------------------\n", uri, method, parameters, headers, requestBody, responseBody, responseStatus, methodTimeTaken);
    }

    /**
     * Helper function to map request and response body to String.
     * @param contentAsByteArray The body content.
     * @param characterEncoding The character encoding.
     * @return Request or response body mapped to String.
     */
    private String getStringValue(byte[] contentAsByteArray, String characterEncoding) {
        try {
            return new String(contentAsByteArray, characterEncoding);
        } catch (UnsupportedEncodingException e) {
            log.error("Exception in logging filter:", e);
        }
        return "ERROR OCCURRED";
    }

    /**
     * Helper function to return headers from a request.
     *
     * @param request The request.
     * @return A map containing header name as key and header value as value.
     */
    private Map<String, String> getHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> headers = new HashMap<>();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                if (headerName != null) {
                    headers.put(headerName, request.getHeader(headerName));
                }
            }
        }
        return headers;
    }

}