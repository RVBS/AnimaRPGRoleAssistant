package controlador;
import java.util.ArrayList;

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
	
	public AsaltoCombate(Personaje atacante, ArrayList<Personaje> defensores){
		this.atacante = atacante;
		this.defensores = defensores;
	}
	
	
	public String calcularCombate()
	{
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
					" Res: "+atacante.getHa_Asalto()+"\n";
		}
		hab_ataque = atacante.getHa_Asalto();
		
		for (Personaje p: defensores){
			if (p.isTirarAutomatico()){
				info += "Defensor "+p.getNombre()+
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
			resultado = resultado - (ABSORCION_NAT + (10*d.getTA()));
			info += "Despues de absorber, con TA "+d.getTA()+
					", el resultado efectivo es "+resultado+"\n";
				
			if (resultado >= 10){
				porcentaje = (float)resultado/100;
				info += "Se produce un daño del "+resultado+"%\n";
				d_total = atacante.getDañoArmaEquipada()+atacante.getBonificadorArma();
				info += "El daño total del atacante es: "+ d_total+"\n";
				d_efectivo = (int)(porcentaje*d_total);
				info += "El daño final que recibe "+d.getNombre()+" es"+d_efectivo+"\n";
				if(d.aplicarDaño(d_efectivo)){
					info += "El ataque contra "+d.getNombre()+" ha sido crítico \n";
				}
			}
			else if (resultado < 0){
				info += "Oportunidad de contra para "+d.getNombre()+"\n";
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
}
	