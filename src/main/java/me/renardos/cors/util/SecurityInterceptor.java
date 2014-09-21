package me.renardos.cors.util;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.InternalServerErrorException;
import org.jboss.resteasy.spi.UnauthorizedException;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;
import org.jboss.resteasy.util.Base64;

@Provider
@ServerInterceptor
public class SecurityInterceptor implements PreProcessInterceptor {

	private static final String AUTHORIZATION_PROPERTY = "Authorization";
	private static final String AUTHENTICATION_SCHEME = "Basic";

	@Inject
	private Logger log;

	@Override
	public ServerResponse preProcess(HttpRequest request,
			ResourceMethod methodInvoked) throws Failure,
			WebApplicationException {
		// Invocation method
		Method method = methodInvoked.getMethod();

		// Access allowed for all
		if (method.isAnnotationPresent(PermitAll.class)) {
			return null;
		}
		// Access denied for all
		if (method.isAnnotationPresent(DenyAll.class)) {
			throw new UnauthorizedException(
					"Forbidden because of @DenyAll anotation");
		}

		// Get request headers
		final HttpHeaders headers = request.getHttpHeaders();

		// Fetch authorization header
		final List<String> authorization = headers
				.getRequestHeader(AUTHORIZATION_PROPERTY);

		// If no authorization information present; block access
		if (authorization == null || authorization.isEmpty()) {
			throw new UnauthorizedException("Authentication is empty");
		}

		// Get encoded username and password
		final String encodedUserPassword = authorization.get(0).replaceFirst(
				AUTHENTICATION_SCHEME + " ", "");

		// Decode username and password
		String usernameAndPassword;
		try {
			usernameAndPassword = new String(Base64.decode(encodedUserPassword));
		} catch (IOException e) {
			throw new InternalServerErrorException(
					"Can't decode authentication content", e);
		}

		// Split username and password tokens
		final StringTokenizer tokenizer = new StringTokenizer(
				usernameAndPassword, ":");
		final String username = tokenizer.nextToken();
		final String password = tokenizer.nextToken();

		log.info("Checking authorisation for user " + username);

		// Verify user access
		if (method.isAnnotationPresent(RolesAllowed.class)) {
			RolesAllowed rolesAnnotation = method
					.getAnnotation(RolesAllowed.class);
			Set<String> rolesSet = new HashSet<String>(
					Arrays.asList(rolesAnnotation.value()));

			// Is user valid?
			if ((rolesSet.contains("DEMO")) && (!username.equals(password))) {
				throw new UnauthorizedException(
						"Unauthorized access for user : " + username);
			}
		}

		// Return null to continue request processing
		log.fine("Access granted for user " + username);
		return null;
	}
}
