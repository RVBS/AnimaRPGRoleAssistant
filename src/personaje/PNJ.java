package personaje;
import arma.Arma;
import personaje.Resistencias.Resistencia;

public class PNJ extends Personaje{
	
	public PNJ(String tipo, String nombre, int turno, int pv_max, int ha, int hd, int he, 
			int agi, int des, int con, int fue,int per, int in, int vol, int pod,
			int ta_fil, int ta_con, int ta_pen, int ta_cal, int ta_fri, int ta_ele, int ta_ene){
		//por defecto
		tirarAutomatico = true;
		
		ini_Asalto = 0;
		ha_Asalto = 0;
		hd_Asalto = 0;
		he_Asalto = 0;
		
		this.nombre = nombre;
		this.tipo = nombre;
		
		this.pv_max = pv_max;
		this.pv = pv_max;
		cs = new Caracteristicas(fue,des,agi,con,in,pod,vol,per);
		hc = new HabilidadesCombate(ha,hd,he,turno,
				new Armadura(ta_fil,ta_con,ta_pen,ta_cal,ta_fri,ta_ele,ta_ene));
	}
	
	public int calcularResistencia(Resistencia r){
		dado.tirarDado(modificador,false);
		return rs.getResistencia(r)+dado.getResultado();
	}

	@Override
	public boolean isPNJ() {
		return true;
	}

	@Override
	public Personaje clonar(String nombre) {
		Personaje clon;
		clon = new PNJ(this.tipo,this.tipo,this.getTurnoBase(), this.pv_max, this.getHatqBase(),
				this.getHdefBase(), this.getHEsqBase(),cs._agi,cs._des,cs._con,cs._fue,cs._per,cs._int,cs._vol,
				cs._pod,0,0,0,0,0,0,0);
		for (Arma a : this.getArmas()){
			clon.addArma(a);
		}
		
		clon.setArma(this.getArmaEquipada());
		clon.setNombre(nombre);
		
		return clon;
			
	}
	
	
}


