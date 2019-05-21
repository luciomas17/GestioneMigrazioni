package it.polito.tdp.borders.model;

public class Evento implements Comparable<Evento> {

	private int t;
	private int n; // numero di migranti arrivati che si sposteranno
	private Country stato; // paese in cui arrivano i migranti e da cui si sposteranno
	
	public Evento(int t, int n, Country stato) {
		this.t = t;
		this.n = n;
		this.stato = stato;
	}

	public int getT() {
		return t;
	}

	public int getN() {
		return n;
	}

	public Country getStato() {
		return stato;
	}

	@Override
	public int compareTo(Evento other) {
		return this.t - other.t;
	}
	
}
