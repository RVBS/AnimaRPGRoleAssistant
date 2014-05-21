package personaje;
import personaje.Resistencias.Resistencia;

public class PNJ extends Personaje{
	
	public PNJ(int pv_max, int f, int d, int a, int c, int i, int pod, int v, int per, String nombre,
			int h_a, int h_d, int h_e, int t, int ta){
		//por defecto
		tirarAutomatico = true;
		
		ini_Asalto = 0;
		ha_Asalto = 0;
		hd_Asalto = 0;
		he_Asalto = 0;
		
		this.nombre = nombre;
		
		this.pv_max = pv_max;
		this.pv = pv_max;
		cs = new Caracteristicas(f,d,a,c,i,pod,v,per);
		hc = new HabilidadesCombate(h_a,h_d,h_e,t,ta);
	}
	
	public int calcularResistencia(Resistencia r){
		dado.tirarDado(modificador,false);
		return rs.getResistencia(r)+dado.getResultado();
	}

	@Override
	public boolean isPNJ() {
		return true;
	}
}


