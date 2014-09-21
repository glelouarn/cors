package me.renardos.cors.rest;

import me.renardos.cors.model.Fun;

/**
 * Implementation of FUn resource REST service.
 * 
 * @author gildas
 *
 */
public class FunResourceRESTServiceImpl implements FunResourceRESTService {

	private static int indice = 1;

	@Override
	public Fun getLastFun() {
		return new Fun(indice++);
	}

	@Override
	public String handleOptions() {
		return "";
	}
}
