package me.renardos.cors.rest;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import me.renardos.cors.model.Fun;

/**
 * Declare REST services provided for Fun resource.
 * 
 * @author gildas
 *
 */
@Path("/fun")
@RequestScoped
public interface FunResourceRESTService {

	@GET
	@RolesAllowed("DEMO")
	// @PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public Fun getLastFun();

	@OPTIONS
	@PermitAll
	// @DenyAll
	@Produces(MediaType.APPLICATION_JSON)
	public String handleOptions();
}
