package personaje;

import java.util.ArrayList;

import arma.Arma;
import arma.TipoArma;
import arma.TipoAtaque;

public class HabilidadesCombate {
	
	protected Arma armaEquipada;
	protected ArrayList<Arma> armasDisponibles;
	
	// TODO dependen del arma equipada
	protected int hatq; 
	protected int hdef;
	protected int hesq;
	protected int turno; //Base, modificada por el arma
	protected int modificador;
	protected int num_defensas;
	protected int num_ataques;
	protected Armadura armadura;
	
	public HabilidadesCombate(int h_a, int h_d, int h_e, int turno, Armadura a){
		armasDisponibles = new ArrayList<>(); //TODO combate desarmado
		hatq = h_a;
		hdef = h_d;
		hesq = h_e;
		this.turno = turno;
		armadura = a;
		num_defensas = 1;
		num_ataques = 1;
	}
	
	public void addArma(Arma a){
		armasDisponibles.add(a);
	}
	
	public void removeArma(Arma a){
		armasDisponibles.remove(a);
	}
	
	public boolean equiparArma(Arma a){
		if (!armasDisponibles.contains(a))
			return false;
		//else
		armaEquipada = a;
		return true;
	}
	
	public Arma getArmaEquipada(){
		return armaEquipada;
	}
	
	public ArrayList<Arma> getArmasDisponibles() {
		return armasDisponibles;
	}

	public int getHatqBase() {
		return hatq;
	}

	public void setHatqBase(int hatq) {
		this.hatq = hatq;
	}

	public int getHdefBase() {
		return hdef;
	}

	public void setHdefBase(int hdef) {
		this.hdef = hdef;
	}

	public int getTurnoBase() {
		return turno;
	}

	public void setTurnoBase(int turno) {
		this.turno = turno;
	}

	public int getModificador() {
		return modificador;
	}

	public void setModificador(int modificador) {
		this.modificador = modificador;
	}

	public int get_TA(TipoAtaque t) {
		return armadura.getProteccion(t);
	}

	public void set_TA(TipoAtaque t,int ta) {
		armadura.setProteccion(t, ta);
	}
	
	
	public int getHesqBase() {
		return hesq;
	}

	public void setHesqBase(int hesq) {
		this.hesq = hesq;
	}
	

	public int getTurno(){
		num_defensas = 1;
		return turno + armaEquipada.getTurnoArma(); //atributos arma seleccionada
	}
	
	public int getPenalizacionDefensasAdicionales(){
		int pen = 0;
		switch(num_defensas){
			case 0: pen = 0; break;
			case 1: pen = 0; break;
			case 2: pen = -30; break;
			case 3: pen = -50; break;
			case 4: pen = -70; break;
			default: pen = -90; //5+
		}
		return pen;
	}
	
	public int getIndexArmaEquipada(){
		return armasDisponibles.indexOf(armaEquipada);
	}
	
	
}
