package personaje;

public class Resistencias {
	
	public enum Resistencia{RF,RE,RV,RM,RP};
	
	// Resitencias
	protected int rf;
	protected int re;
	protected int rv;
	protected int rm;
	protected int rp;
	
	public Resistencias(int f, int e, int v, int m, int p){
		rf = f;
		re = e;
		rv = v;
		rm = m;
		rp = p;
	}
	
	public int getResistencia(Resistencia r){
		switch (r){
			case RF: return rf;
			case RE: return re;
			case RV: return rv;
			case RM: return rm;
			default: return rp;
		}
	}


	public void setRf(int rf) {
		this.rf = rf;
	}


	public void setRe(int re) {
		this.re = re;
	}

	public void setRv(int rv) {
		this.rv = rv;
	}

	public void setRm(int rm) {
		this.rm = rm;
	}


	public void setRp(int rp) {
		this.rp = rp;
	}
	
	
}
