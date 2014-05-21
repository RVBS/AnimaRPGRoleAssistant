package personaje;

public class Caracteristicas {
	
	public enum Caract {FUE,DES,AGI,CON,INT,POD,VOL,PER};
	
	protected int _fue;
	protected int _des;
	protected int _agi;
	protected int _con;
	protected int _int;
	protected int _pod;
	protected int _vol;
	protected int _per;
	
	public Caracteristicas(int f, int d, int a, int c, int i, int po, int v, int pe){
		_fue = f;
		_des = d;
		_agi = a;
		_con = c;
		_int = i;
		_pod = po;
		_vol = v;
		_per = pe;
	}
	
	/**
	 * Nos devuelve el bonificador de una caracteristica
	 * @param c
	 * @return
	 */
	public int getBonificadorCaracteristica(Caract c){
		switch (c){
			case FUE: return calcularBonificador(_fue);
			case DES: return calcularBonificador(_des);
			case AGI: return calcularBonificador(_agi);
			case CON: return calcularBonificador(_con);
			case INT: return calcularBonificador(_int);
			case POD: return calcularBonificador(_pod);
			case VOL: return calcularBonificador(_vol);
			case PER: return calcularBonificador(_per);
			default: return 0;
		}

	}
	
	/**
	 * Calcula el bonificador dependiendo del valor de la caracteristica
	 * @param i
	 * @return
	 */
	private int calcularBonificador(int i){
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
	
	
}
