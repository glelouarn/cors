package me.renardos.cors.util;

import java.io.IOException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.spi.interception.MessageBodyWriterContext;
import org.jboss.resteasy.spi.interception.MessageBodyWriterInterceptor;

/**
 * Manage CORS headers.
 * 
 * @author gildas
 *
 */
@Provider
@ServerInterceptor
public class CorsInterceptor implements MessageBodyWriterInterceptor {

	/**
	 * The Access-Control-Allow-Origin header indicates which origin a resource
	 * it is specified for can be shared with. ABNF: Access-Control-Allow-Origin
	 * = "Access-Control-Allow-Origin" ":" source origin string | "*"
	 */
	private static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";

	/**
	 * The Access-Control-Allow-Methods header indicates, as part of the
	 * response to a preflight request, which methods can be used during the
	 * actual request. ABNF: Access-Control-Allow-Methods:
	 * "Access-Control-Allow-Methods" ":" #Method
	 */
	private static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";

	/**
	 * The Access-Control-Allow-Headers header indicates, as part of the
	 * response to a preflight request, which header field names can be used
	 * during the actual request. ABNF: Access-Control-Allow-Headers:
	 * "Access-Control-Allow-Headers" ":" #field-name
	 */
	private static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";

	/**
	 * The Access-Control-Max-Age header indicates how long the results of a
	 * preflight request can be cached in a preflight result cache. ABNF:
	 * Access-Control-Max-Age = "Access-Control-Max-Age" ":" delta-seconds
	 */
	private static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";

	@Override
	public void write(MessageBodyWriterContext context) throws IOException,
			WebApplicationException {
		// Access Control for Cross-Site Requests (CORS)
		// http://www.w3.org/TR/2008/WD-access-control-20080912/
		context.getHeaders().add(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		context.getHeaders().add(ACCESS_CONTROL_ALLOW_METHODS, "GET, OPTIONS");
		context.getHeaders()
				.add(ACCESS_CONTROL_ALLOW_HEADERS,
						"X-Requested-With, Origin, Content-type, Accept, Content-Length, Authorization");
		context.getHeaders().add(ACCESS_CONTROL_MAX_AGE, "20");
		context.proceed();
	}
}
