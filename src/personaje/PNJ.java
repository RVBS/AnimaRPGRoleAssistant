package personaje;

import dado.Dado100;

public class PNJ extends Personaje{
	

	
	public PNJ(int pv_max, int f, int d, int a, int c, int i, int pod, int v, int per, String nombre){
		iniciativa = 0;
		this.nombre = nombre;
		
		this.pv_max = pv_max;
		this.pv = pv_max;
		
		_fue = f;
		_des = d;
		_agi = a;
		_con = c;
		_int = i;
		_pod = pod;
		_vol = v;
		_per = per;
	}
	
	
	public int calcularResistencia(int res){
		
		int x = 0;
		switch (res){
			case RF:
				x = rf;
				break;
			case RE: 
				x = re;
				break;
			case RV: 
				x = rv;
				break;
			case RM: 
				x = rm;
				break;
			case RP: 
				x = rp;
				break;
		}
		
		dado.tirarDado(modificador,false);
		return x+dado.getResultado();
	}


	@Override
	public boolean isPNJ() {
		return true;
	}
}


