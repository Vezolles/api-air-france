package com.airfrance.api.config;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.util.StreamUtils;

/**
 * Content body http servlet request
 * Used by ContentCachingFilter to cache HTTP request for multiple use
 * @author Vezolles
 */
public class CachedBodyHttpServletRequest extends HttpServletRequestWrapper {
	
	/**
	 * Body in cache
	 */
	private byte[] cachedBody;

	/**
	 * Default constructor for CachedBodyHttpServletRequest
	 * 
	 * @param request the HttpServletRequest to set in cache
	 * @throws IOException
	 */
    public CachedBodyHttpServletRequest(HttpServletRequest request) throws IOException {
        super(request);
        this.cachedBody = StreamUtils.copyToByteArray(request.getInputStream());
    }
    
    /**
     * Retrieves the body of the request as binary data using a
     * {@link ServletInputStream}. Either this method or {@link #getReader} may
     * be called to read the body, not both.
     *
     * @return a {@link ServletInputStream} object containing the body of the request
     * @exception IllegalStateException if the {@link #getReader} method has already been called for this request
     * @exception IOException if an input or output exception occurred
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new CachedBodyServletInputStream(this.cachedBody);
    }
    
    /**
     * Retrieves the body of the request as character data using a
     * <code>BufferedReader</code>. The reader translates the character data
     * according to the character encoding used on the body. Either this method
     * or {@link #getInputStream} may be called to read the body, not both.
     *
     * @return a <code>BufferedReader</code> containing the body of the request
     * @exception IOException if an input or output exception occurred
     * @see #getInputStream
     */
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(this.cachedBody)));
    }

}
