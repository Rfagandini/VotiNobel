package it.polito.tdp.nobel.model;

import java.util.*;

import it.polito.tdp.nobel.db.EsameDAO;

public class Model {

	EsameDAO allexams = new EsameDAO();
	HashSet<Esame> SetProva;
	private double MediaSoluzione;
	private List<Esame> ListEsame;
	
	public Model() {
		SetProva = new HashSet<Esame>();
		this.ListEsame = allexams.getTuttiEsami();
		EsameDAO allexams = new EsameDAO();
		MediaSoluzione = 0;
	}


	public Set<Esame> calcolaSottoinsiemeEsami(int numeroCrediti) {
		HashSet<Esame> SetVuoto = new HashSet<Esame>();
		if(SetProva.size()!=0) {
			SetProva.clear();
		}
		calcolando2(SetVuoto,numeroCrediti,0);
		//calcolando(SetVuoto,numeroCrediti,0,ListEsame);
		
		return SetProva;	
	}

	
	public double calcolaMedia(Set<Esame> esami) {
		
		int crediti = 0;
		int somma = 0;
		
		for(Esame e : esami){
			crediti += e.getCrediti();
			somma += (e.getVoto() * e.getCrediti());
		}
		
		if(esami.size()==0) {
			return 0;
		}
		
		return somma/crediti;
	}
	
	public int sommaCrediti(Set<Esame> esami) {
		int somma = 0;
		
		for(Esame e : esami)
			somma += e.getCrediti();
		
		return somma;
	}
	
	private void calcolando2(Set<Esame> perro, int numcfu, int livello) {
		
		int crediti = sommaCrediti(perro);
		double mediaparziale = calcolaMedia(perro);
		
		if(crediti > numcfu) {
			return;
		}
		
		if(crediti==numcfu) {
		
			if(mediaparziale>MediaSoluzione) {
				SetProva = new HashSet<>(perro);
				MediaSoluzione = mediaparziale;
			}
			
			return;
		}
		
		if(livello == ListEsame.size()) {
			return;
		}
		
		perro.add(ListEsame.get(livello));
		calcolando2(perro, numcfu, livello+1);
		perro.remove(ListEsame.get(livello));
		calcolando2(perro, numcfu, livello+1);
	}
	
	private void calcolando(Set<Esame> perro, int numcfu, int livello,List<Esame> ListEsame){
		
		List<Esame> Gato = new ArrayList<Esame>(SetProva);
		int crediti = sommaCrediti(perro);
		double mediaparziale = calcolaMedia(perro);
		
		if(crediti > numcfu) {
			return;
		}
		
		if(crediti==numcfu) {
		
			if(mediaparziale>MediaSoluzione) {
				SetProva = new HashSet<>(perro);
				MediaSoluzione = mediaparziale;
			}
			
			return;
		}
		
		if(livello == ListEsame.size()) {
			return;
		}
		for(Esame e: ListEsame) {
			
			//if(!perro.contains(e)) {
				
				if(crediti<numcfu) {
					perro.add(e);
					Gato.remove(e);
					calcolando(perro,numcfu,livello+1,Gato);
					Gato.add(e);
					perro.remove(e);
				}
				
			//}
			
		}
	}
}
