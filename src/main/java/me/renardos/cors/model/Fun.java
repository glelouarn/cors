package me.renardos.cors.model;

/**
 * Simple entity used to represent a randomized percentage value.
 * 
 * @author gildas
 *
 */
public class Fun {
	private int percentage;

	private int indice;

	public Fun(int indice) {
		super();
		this.percentage = (int) (Math.random() * 100);
		this.indice = indice;
	}

	public int getPercentage() {
		return percentage;
	}

	public int getIndice() {
		return indice;
	}
}