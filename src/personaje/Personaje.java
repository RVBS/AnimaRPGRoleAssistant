package personaje;

import java.util.ArrayList;

import personaje.Caracteristicas.Caract;

import arma.Arma;
import arma.ArmaCorta;
import arma.Especial;
import arma.TipoAtaque;
import dado.Dado100;

public abstract class Personaje {
	
	protected boolean esAtacante;
	protected boolean esDefensor;

	protected boolean tirarAutomatico;
	//Dado por defecto
	protected Dado100 dado = new Dado100(90,3,0);
	
	protected String nombre;
	protected String tipo;
	
	protected int pv_max;
	protected int pv;
	
	/** Recalculadas en cada asalto **/
	protected int ini_Asalto;
	protected int ha_Asalto;
	protected int hd_Asalto;
	protected int he_Asalto;
	
	protected Caracteristicas cs;
	protected Resistencias rs;
	protected HabilidadesCombate hc;
	
	protected int modificador;
	
	public String getTipo(){
		return tipo;
	}
	
	public int getPV_MAX(){
		return pv_max;
	}
	public int getPV(){
		return pv;
	}
	
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setDado(int abierta, int pifia){
		dado = new Dado100(abierta,pifia,0);
	}


	/**
	 * Devuelve la habilidad de ataque actual con el arma seleccionada actualmente
	 * @return
	 */
	public int getHatqBase() {
		return hc.getHatqBase();
	}

	/**
	 * Permite setear la habilidad de ataque base
	 * @param hatq
	 */
	public void setHatqBase(int hatq) {
		hc.setHatqBase(hatq);
	}

	/**
	 * Permite obtener la habilidad de defensa actual con el arma equipada
	 * @return
	 */
	public int getHdefBase() {
		return hc.getHdefBase();
	}

	/**
	 * Permite setear la habilidad de defensa base
	 * @param hdef
	 */
	public void setHdef(int hdef) {
		hc.setHdefBase(hdef);
	}

	/**
	 * Permite obtener la habilidad de turno actual con el arma equipada
	 * @return
	 */
	public int getTurno() {
		return hc.getTurno();
	}

	/**
	 * Permite setear la habilidad de turno base
	 * @param turno
	 */
	public void setTurno(int turno) {
		hc.setTurnoBase(turno);
	}

	public int getModificador() {
		return modificador;
	}

	public void setModificador(int modificador) {
		this.modificador = modificador;
		hc.setModificador(modificador); //afecta tambien al combate
	}

	public void setPVMAX(int pvMax){
		this.pv_max = pvMax;
		this.pv = pvMax;
	}

	public void addArma(Arma a){
		hc.addArma(a);
	}
	
	public void setArma(Arma a){
		hc.equiparArma(a);
	}
	
	public boolean estaMuerto(){
		return (pv <= 0);
	}
	
	/** 
	 * aplica daño y devuelve un booleano para indicar si ha sido un ataque critico
	 * @param r
	 * @return
	 */
	public boolean aplicarDaño(int r){
		boolean critico = (r >= pv/2);		
		pv -= r;
		return critico;
	}
	
	/**
	 * Calcula la iniciativa y devuelve un arraylist con las tiradas
	 * @return
	 */
	public ArrayList<Integer> calcularIniciativa(){
		ArrayList<Integer> tiradas = dado.tirarDado(modificador, true);
		ini_Asalto = hc.getTurno() + dado.getResultado();
		return tiradas;
	}
	
	/**
	 * Calcula la habilidad de ataque y devuelve las tiradas
	 * @return
	 */
	public ArrayList<Integer> calcularHabilidadAtaque(){
		ArrayList<Integer> tiradas = dado.tirarDado(modificador, true);
		ha_Asalto = hc.getHatqBase()+ dado.getResultado();
		return tiradas;
	}
	
	public ArrayList<Integer> calcularHabilidadDefensa(){
		ArrayList<Integer> tiradas = dado.tirarDado(modificador, true);
		hd_Asalto = hc.getHdefBase() + dado.getResultado();
		return tiradas;
	}
	
	public ArrayList<Integer> calcularHabilidadEsquiva(){
		ArrayList<Integer> tiradas = dado.tirarDado(modificador, true);
		he_Asalto = hc.getHesqBase() + dado.getResultado();
		return tiradas;
	}
	
	public int getTA(){
		return hc.get_TA();
	}
	
	public void setTA(int ta){
		hc.set_TA(ta);
	}
	
	public abstract boolean isPNJ();
	
	public int getDañoArmaEquipada(){
		return hc.getArmaEquipada().getDañoArma();
	}

	public int getIni_Asalto() {
		return ini_Asalto;
	}

	public void setIni_Asalto(int ini_Asalto) {
		this.ini_Asalto = ini_Asalto;
	}

	public int getHa_Asalto() {
		return ha_Asalto;
	}

	public void setHa_Asalto(int ha_Asalto) {
		this.ha_Asalto = ha_Asalto;
	}

	public int getHd_Asalto() {
		return hd_Asalto;
	}

	public void setHd_Asalto(int hd_Asalto) {
		this.hd_Asalto = hd_Asalto;
	}

	public int getHe_Asalto() {
		return he_Asalto;
	}
	
	public int getTurnoBase(){
		return hc.getTurnoBase();
	}

	public void setHe_Asalto(int he_Asalto) {
		this.he_Asalto = he_Asalto;
	}

	public boolean isTirarAutomatico() {
		return tirarAutomatico;
	}

	public void setTirarAutomatico(boolean tirarAutomatico) {
		this.tirarAutomatico = tirarAutomatico;
	}
	
	public int getBonificadorArma(){
		return cs.getBonificadorCaracteristica(Caract.FUE);
	}
	
	public String[] getListaArmas(){
		Arma[] armas = getArmas();
		String[] nombresArmas = new String[armas.length];
		for (int i = 0; i < armas.length; i++){
			nombresArmas[i] = armas[i].getNombreArma();
		}
		return nombresArmas;
	}
	
	public Arma[] getArmas(){
		Object[] ob = hc.armasDisponibles.toArray();
		Arma[] armas = new Arma[ob.length];
		for (int i = 0; i < ob.length; i++)
			armas[i] = (Arma)ob[i];
		return armas;
		
	}
	
	public Arma getArmaEquipada(){
		return hc.getArmaEquipada();
	}
	
	public int getIndexArmaEquipada(){
		return hc.getIndexArmaEquipada();
	}
	
	public boolean isEsAtacante() {
		return esAtacante;
	}

	public void setEsAtacante(boolean esAtacante) {
		this.esAtacante = esAtacante;
	}

	public boolean isEsDefensor() {
		return esDefensor;
	}

	public void setEsDefensor(boolean esDefensor) {
		this.esDefensor = esDefensor;
	}

}
