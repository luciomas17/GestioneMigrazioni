package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

public class Simulatore {
	
	// Modello -> Stato del sistema ad ogni passo
	private Graph<Country, DefaultEdge> grafo;
	
	// Tipi di evento / coda prioritaria
	private PriorityQueue<Evento> queue;
	
	// Parametri della simulazione
	private int N_MIGRANTI = 1000;
	private Country partenza;
	
	// Valori in output
	private int T;
	private Map<Country, Integer> stanziali;

	public void init(Country partenza, Graph<Country, DefaultEdge> grafo) {
		// Ricezione parametri
		this.partenza = partenza;
		this.grafo = grafo;
		
		// Impostazione stato iniziale
		this.T = 1;
		this.stanziali = new HashMap<>();
		for(Country c : this.grafo.vertexSet())
			stanziali.put(c, 0);
		this.queue = new PriorityQueue<>();
		
		// Inserimento primo evento
		this.queue.add(new Evento(T, N_MIGRANTI, this.partenza));
	}
	
	public void run() {
		// Estrazione evento dalla coda fino a svuotamento
		Evento e;
		while((e = this.queue.poll()) != null) {
			// Esecuzione evento
			this.T = e.getT();
			
			int nPersone = e.getN();
			Country stato = e.getStato();
			List<Country> confinanti = Graphs.neighborListOf(this.grafo, stato);
			
			int migranti = (nPersone / 2) / confinanti.size();
			if(migranti > 0) {
				// Spostamento persone
				for(Country confinante : confinanti)
					this.queue.add(new Evento(e.getT() + 1, migranti, confinante));
			}
			
			int stanziali = nPersone - (migranti * confinanti.size());
			this.stanziali.put(stato, this.stanziali.get(stato) + stanziali);
		}
	}

	public int getLastT() {
		return this.T;
	}
	
	public Map<Country, Integer> getStanziali() {
		return this.stanziali;
	}
	
}
