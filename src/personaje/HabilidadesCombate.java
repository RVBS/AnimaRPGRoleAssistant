package personaje;

import java.util.ArrayList;

import arma.Arma;

public class HabilidadesCombate {
	protected Arma armaEquipada;
	protected ArrayList<Arma> armasDisponibles;
	
	// TODO dependen del arma equipada
	protected int hatq; 
	protected int hdef;
	protected int hesq;
	protected int turno; //Base, modificada por el arma
	protected int modificador;
	protected int _TA;
	
	public HabilidadesCombate(int h_a, int h_d, int h_e, int turno, int ta){
		armasDisponibles = new ArrayList<>(); //TODO combate desarmado
		hatq = h_a;
		hdef = h_d;
		hesq = h_e;
		this.turno = turno;
		_TA = ta;
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

	public int get_TA() {
		return _TA;
	}

	public void set_TA(int _TA) {
		this._TA = _TA;
	}
	
	
	public int getHesqBase() {
		return hesq;
	}

	public void setHesqBase(int hesq) {
		this.hesq = hesq;
	}
	

	public int getTurno(){
		return turno + armaEquipada.getTurnoArma(); //atributos arma seleccionada
	}
	
	public int getIndexArmaEquipada(){
		return armasDisponibles.indexOf(armaEquipada);
	}
	
	
}
