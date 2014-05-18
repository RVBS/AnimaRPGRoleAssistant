package personaje;

import arma.Arma;
import arma.ArmaCorta;
import arma.Especial;
import arma.TipoAtaque;
import dado.Dado100;

public abstract class Personaje {
	
	//Dado por defecto
	protected Dado100 dado = new Dado100(90,3,0);
	
	protected String nombre;
	
	protected int iniciativa; //turno + tirada de dados
	
	protected int pv_max;
	protected int pv;
	
	protected int _fue;
	protected int _des;
	protected int _agi;
	protected int _con;
	protected int _int;
	protected int _pod;
	protected int _vol;
	protected int _per;
	
	// TODO dependen del arma equipada
	protected Arma armas = new ArmaCorta("Arma1",30,0,0,TipoAtaque.FILO,null,5,5,5);
	protected int hatq;
	protected int hdef;
	protected int turno;
	protected int modificador;
	protected int _TA;
	
	// Resitencias
	protected int rf;
	protected int re;
	protected int rv;
	protected int rm;
	protected int rp;
	
	
	public static final int RF = 0;
	public static final int RE = 1;
	public static final int RV = 2;
	public static final int RM = 3;
	public static final int RP = 4;

	protected int calcularBonificador(int i){
		switch (i){
			case 1: return -30;
			case 2: return -20;
			case 3: return -10;
			case 4: return -5;
			case 6: return 5;
			case 7: return 5;
			case 8: return 10;
			case 9: return 10;
			case 10: return 15;
			case 11: return 20;
			case 12: return 20;
			case 13: return 25;
			case 14: return 25;
			case 15: return 30;
			case 16: return 35;
			case 17: return 35;
			case 18: return 40;
			case 19: return 40;
			case 20: return 45;
			default: return 0;
		}
	}
	
	public int getBonificadorFuerza(){
		return calcularBonificador(_fue);
	}
	
	public String getName(){
		return nombre;
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

	public int getIniciativa() {
		return iniciativa;
	}

	public void setIniciativa(int iniciativa) {
		this.iniciativa = iniciativa;
	}

	public int get_fue() {
		return _fue;
	}

	public void set_fue(int _fue) {
		this._fue = _fue;
	}

	public int get_des() {
		return _des;
	}

	public void set_des(int _des) {
		this._des = _des;
	}

	public int get_agi() {
		return _agi;
	}

	public void set_agi(int _agi) {
		this._agi = _agi;
	}

	public int get_con() {
		return _con;
	}

	public void set_con(int _con) {
		this._con = _con;
	}

	public int get_int() {
		return _int;
	}

	public void set_int(int _int) {
		this._int = _int;
	}

	public int get_pod() {
		return _pod;
	}

	public void set_pod(int _pod) {
		this._pod = _pod;
	}

	public int get_vol() {
		return _vol;
	}

	public void set_vol(int _vol) {
		this._vol = _vol;
	}

	public int get_per() {
		return _per;
	}

	public void set_per(int _per) {
		this._per = _per;
	}

	public int getHatq() {
		return hatq;
	}

	public void setHatq(int hatq) {
		this.hatq = hatq;
	}

	public int getHdef() {
		return hdef;
	}

	public void setHdef(int hdef) {
		this.hdef = hdef;
	}

	public int getTurno() {
		return turno;
	}

	public void setTurno(int turno) {
		this.turno = turno;
	}

	public int getModificador() {
		return modificador;
	}

	public void setModificador(int modificador) {
		this.modificador = modificador;
	}

	public int getRf() {
		return rf;
	}

	public void setRf(int rf) {
		this.rf = rf;
	}

	public int getRe() {
		return re;
	}

	public void setRe(int re) {
		this.re = re;
	}

	public int getRv() {
		return rv;
	}

	public void setRv(int rv) {
		this.rv = rv;
	}

	public int getRm() {
		return rm;
	}

	public void setRm(int rm) {
		this.rm = rm;
	}

	public int getRp() {
		return rp;
	}

	public void setRp(int rp) {
		this.rp = rp;
	}
	
	public void setPVMAX(int pvMax){
		this.pv_max = pvMax;
		this.pv = pvMax;
	}

	public void setAtributosArma(int hatq, int hdef, int turn, int mod){
		this.hatq = hatq;
		this.hdef = hdef;
		this.turno = turn;
		this.modificador = mod;
	}
	
	public void setResistencias(int rf, int re, int rv, int rm, int rp){
		this.rf = rf;
		this.re = re;
		this.rv = rv;
		this.rm = rm;
		this.rp = rp;
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
	
	//TODO
	public boolean cargarDatos(){
		return false;
	}
	
	public int calcularIniciativa(){
		dado.tirarDado(modificador, true);
		iniciativa = turno + dado.getResultado();
		System.out.println(iniciativa);
		return iniciativa;
	}
	
	public int calcularHabilidadAtaque(){
		dado.tirarDado(modificador, true);
		return hatq + dado.getResultado();
	}
	
	public int calcularHabilidadDefensa(){
		dado.tirarDado(modificador, true);
		return hdef + dado.getResultado();
	}
	
	public int getTA(){
		return _TA;
	}
	
	public void setTA(int ta){
		_TA = ta;
	}
	
	public abstract boolean isPNJ();
	
	public int getDañoArma(){
		return armas.getDañoArma();
	}
	
	public void setDañoArma(int d){
		armas.setDañoArma(d);
	}

}
