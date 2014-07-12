package personaje;

import java.util.HashMap;

import arma.TipoAtaque;

public class Armadura {

	//ARMADURA PARA EL COMBATE
	HashMap<TipoAtaque,Integer> armadura;
	
	public Armadura(){
		armadura = new HashMap<TipoAtaque,Integer>();
	}
	
	public Armadura(int ta_fil, int ta_con, int ta_pen, int ta_cal, int ta_fri, int ta_ele, int ta_ene){
		armadura = new HashMap<TipoAtaque,Integer>();
		armadura.put(TipoAtaque.FIL, ta_fil);
		armadura.put(TipoAtaque.CON, ta_con);
		armadura.put(TipoAtaque.PEN, ta_pen);
		armadura.put(TipoAtaque.CAL, ta_cal);
		armadura.put(TipoAtaque.FRI, ta_fri);
		armadura.put(TipoAtaque.ELE, ta_ele);
		armadura.put(TipoAtaque.ENE, ta_ene);
	}
	
	public void setProteccion(TipoAtaque t, int n){
		armadura.put(t,n);
	}
	
	public int getProteccion(TipoAtaque t){
		if (armadura.get(t) == null)
			return 0;
		return armadura.get(t);
	}

}
