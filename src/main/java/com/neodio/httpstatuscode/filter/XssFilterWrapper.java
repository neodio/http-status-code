package com.neodio.httpstatuscode.filter;

import com.nhncorp.lucy.security.xss.XssSaxFilter;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class XssFilterWrapper extends HttpServletRequestWrapper {

    private XssSaxFilter xssSaxFilter;
    private byte[] rawData;
    private HttpServletRequest request;
    private ResettableServletInputStream servletInputStream;

    public XssFilterWrapper(ServletRequest request, XssSaxFilter xssSaxFilter) throws IOException {
        super((HttpServletRequest) request);
        this.xssSaxFilter = xssSaxFilter;
        this.request = (HttpServletRequest) request;
        this.servletInputStream = new ResettableServletInputStream();

        resetInputStream();
    }

    private void resetInputStream() throws IOException {
        if (!isMultipartRequest()) {
            String requestBody = IOUtils.toString(getReader());
            if (StringUtils.isNotEmpty(requestBody)) {
                String contentType = request.getContentType();
                if (contentType.equals("application/json")) {
                    boolean includeDoubleQuotJson = false;
                    if (requestBody.indexOf("\\\"") > -1) {
                        includeDoubleQuotJson = true;
                    }

                    if (includeDoubleQuotJson) {
                        requestBody = StringUtils.replace(requestBody, "\"", "#quot#");
                        requestBody = StringUtils.replace(requestBody, "\\#quot#", "\"");
                    }
                    requestBody = doFilter("", requestBody);
                    if (includeDoubleQuotJson) {
                        requestBody = StringUtils.replace(requestBody, "\"", "\\\"");
                        requestBody = StringUtils.replace(requestBody, "#quot#", "\"");
                    }
                } else {
                    requestBody = doFilter("", requestBody);
                }
            }
            servletInputStream.inputStream = new ByteArrayInputStream(requestBody.getBytes("UTF-8"));
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (rawData == null) {
            rawData = IOUtils.toByteArray(this.request.getReader(), "UTF-8");
            servletInputStream.inputStream = new ByteArrayInputStream(rawData);
        }

        return servletInputStream;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        if (rawData == null) {
            rawData = IOUtils.toByteArray(this.request.getReader(), "UTF-8");
            servletInputStream.inputStream = new ByteArrayInputStream(rawData);
        }

        return new BufferedReader(new InputStreamReader(servletInputStream, "UTF-8"));
    }

    @Override
    public String getParameter(String paramName) {
        String value = super.getParameter(paramName);
        return doFilter(paramName, value);
    }

    /**
     * 원래 없던 메소드를 Lucy 필터에서 차용.
     *
     * @param paramName
     * @return
     */
    @Override
    public String[] getParameterValues(String paramName) {
        String values[] = super.getParameterValues(paramName);
        if (values == null) {
            return values;
        }
        for (int index = 0; index < values.length; index++) {
            values[index] = doFilter(paramName, values[index]);
        }
        return values;
    }

    /**
     * 원래 없던 메소드를 Lucy 필터에서 차용.
     *
     * @param paramName
     * @param value
     * @return
     */
    private String doFilter(String paramName, String value) {
        String cleanValue = xssSaxFilter.doFilter(value);
        return cleanValue;
    }

    /**
     * http request method 가 multipart request 인지를 판별한다. <br>
     * <ul>
     *     <li><b>true:</b> multipart request</li>
     *     <li><b>false:</b> none multipart request</li>
     * </ul>
     *
     * @return
     */
    private boolean isMultipartRequest() {
        return "POST".equalsIgnoreCase(request.getMethod())
                && request.getContentType() != null
                && request.getContentType().toLowerCase().startsWith("multipart/");
    }

    /**
     * 재사용 가능한 servlet input stream
     */
    private class ResettableServletInputStream extends ServletInputStream {
        private InputStream inputStream;

        @Override
        public int read() throws IOException {
            return inputStream.read();
        }

        /**
         * Returns true when all the data from the stream has been read else
         * it returns false.
         *
         * @return <code>true</code> when all data for this particular request
         * has been read, otherwise returns <code>false</code>.
         * @since Servlet 3.1
         */
        @Override
        public boolean isFinished() {
            return false;
        }

        /**
         * Returns true if data can be read without blocking else returns
         * false.
         *
         * @return <code>true</code> if data can be obtained without blocking,
         * otherwise returns <code>false</code>.
         * @since Servlet 3.1
         */
        @Override
        public boolean isReady() {
            return false;
        }

        /**
         * Instructs the <code>ServletInputStream</code> to invoke the provided
         * {@link ReadListener} when it is possible to read
         *
         * @param readListener the {@link ReadListener} that should be notified
         *                     when it's possible to read.
         * @throws IllegalStateException if one of the following conditions is true
         *                               <ul>
         *                               <li>the associated request is neither upgraded nor the async started
         *                               <li>setReadListener is called more than once within the scope of the same request.
         *                               </ul>
         * @throws NullPointerException  if readListener is null
         * @since Servlet 3.1
         */
        @Override
        public void setReadListener(ReadListener readListener) {

        }
    }
}