package controlador;
import java.util.ArrayList;

import arma.TipoAtaque;

import personaje.Personaje;


public class AsaltoCombate {

	/**
	 * TODO
	 * - Hay que saber cual es el tipo del ataque (en base al critico de su arma) para saber
	 *  que TA hay que aplicarle
	 */
	
	public static final int ABSORCION_NAT = 20;
	// Para situaciones en las que un mismo atacante afecte a varios personajes
	private Personaje atacante;
	private ArrayList<Personaje> defensores; //todo generalizar a esquiva
	private String info;
	private boolean critico;
	private int res;
	private int dam;
	
	public AsaltoCombate(Personaje atacante, ArrayList<Personaje> defensores){
		this.atacante = atacante;
		this.defensores = defensores;
	}
	
	
	public String calcularCombate()
	{
		String info_general;
		TipoAtaque tipoDeAtaque= atacante.getArmaEquipada().getTipoAtaque(true);
		int contra;
		int acum;
		info = "";
		int hab_ataque;
		ArrayList<Integer> hab_defensa = new ArrayList<>();
		Personaje d;
		int resultado;
		float porcentaje;
		int d_efectivo;
		int d_total;

		/* Comprobamos las tiradas que hay que realizar para los combatientes 
		 * solo si esta activada la opcion
		 */
		if (atacante.isTirarAutomatico()){
			info += "Atacante "+atacante.getNombre()+
					" tira. Tiradas: "+atacante.calcularHabilidadAtaque()+
					" HA base: "+atacante.getHatqBase()+
					" Tipo Ataque: "+tipoDeAtaque+
					" Res: "+atacante.getHa_Asalto()+"\n";
		}
		hab_ataque = atacante.getHa_Asalto();
		// Calculamos las habilidades defensoras
		for (Personaje p: defensores){
			if (p.isTirarAutomatico()){
				info += "Defensor "+p.getNombre()+
						"penalizacion defensa: "+p.getPenalizacionDefensa()+
						" tira. Tiradas: "+p.calcularHabilidadDefensa()+
						" HD base: "+p.getHdefBase()+
						" Res: "+p.getHd_Asalto()+"\n";
			}
			hab_defensa.add(p.getHd_Asalto());
		}
		
		for (int i = 0; i < defensores.size(); i++){
			d = defensores.get(i);
			// Resultado directo HA - HD
			resultado = hab_ataque - hab_defensa.get(i);
			info += "Resultado del combate "+resultado+"\n";
			// Calculo de la absorcion
			resultado = resultado - (ABSORCION_NAT + (10*d.getTA(tipoDeAtaque)));
			info += "Despues de absorber, con TA "+d.getTA(tipoDeAtaque)+ " contra "+tipoDeAtaque+
					", el resultado efectivo es "+resultado+"\n";
			
			res = resultado;
				
			if (resultado >= 10){
				//porcentaje = (float)resultado/100;
				int num = resultado/10;
				porcentaje = (float)num/10;
				info += "Se produce un daño del "+porcentaje*100+"%\n";
				d_total = atacante.getDañoArmaEquipada()+atacante.getBonificadorArma();
				info += "El daño base total del atacante es: "+ d_total+"\n";
				d_efectivo = (int)(porcentaje*d_total);
				info += "El daño efectivo final que recibe "+d.getNombre()+" es "+d_efectivo+"\n";
				if(d.aplicarDaño(d_efectivo)){
					info += "El ataque contra "+d.getNombre()+" ha sido crítico \n";
				}
				dam = d_efectivo;
			}
			else if (resultado < 0){
				info_general = info += "Oportunidad de contra para "+d.getNombre()+" de ";
				// parte del contrataque
				acum = (-resultado)/10;
				contra = acum*5;
				info = info_general + contra;
					
			}
		}
		

			
		return info;
	}
		
	/**
	 * Informacion acerca de las tiradas y los resultados
	 * @return
	 */
	public String getInformacion(){
		return info;
	}
	
	public int getResultadoUltimoCombate(){
		return res;
	}
	
	public int getDamageUltimoCombate(){
		return dam;
	}
}
	