package controlador;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import jxl.*; 
import java.io.*; 

import arma.Arma;
import arma.ArmaCorta;
import arma.TipoAtaque;
import personaje.PNJ;
import personaje.Personaje;
import personaje.Resistencias;

/**
 * Clase que lleva todo el control de la aplicacion, como calculos, orden, etc
 * @author Brawl
 *
 */
public class Controlador {
	
	/* Lista de todos los elementos seleccionables */
	private static final ArrayList<Personaje> listaTodosPersonajes = new ArrayList<>();
	private static final ArrayList<Arma> listaTodasArmas = new ArrayList<>();
	
	private ArrayList<Personaje> pjDEF;
	private Personaje pjATQ;
	
	//lista de personajes que actuan
	private ArrayList<Personaje> personajes;
	// Parte encargada de calcular los resultados de los combates y sus reglas
	private AsaltoCombate combate;
	
	private String info;
	
	public Controlador(){
		personajes = new ArrayList<>();
		pjDEF = new ArrayList<>();
	}
	
	public void nuevoCombate(Personaje atacante,ArrayList<Personaje> defensores){
		//setHabilidadesCombateAsalto -> para los que no tiran dados
		//setModificadores -> por si hay modificaciones añadidas
		combate = new AsaltoCombate(atacante,defensores);
		info = combate.calcularCombate();
	}
	
	public void setHabilidadesCombateAsalto(Personaje p, int ha_asalto, 
			int hd_asalto, int he_asalto, int ini_asalto){
		p.setHa_Asalto(ha_asalto);
		p.setHd_Asalto(hd_asalto);
		p.setHe_Asalto(he_asalto);
		p.setIni_Asalto(ini_asalto);
	}
	
	public void setModificadores(Personaje p, int mod){
		p.setModificador(mod);
	}
	
	
	
	/**
	 * Permite añadir un personaje al combate
	 */
	public void addPersonaje(Personaje p){
		personajes.add(p);
	}
	
	/**
	 * Permite eliminar un personaje del combate
	 */
	public void eliminarPersonaje(Personaje p){
		personajes.remove(p);
	}
	
	/**
	 * Permite cargar todas las armas
	 */
	public void añadirArmaPersonaje(Personaje p, Arma a){
		
	}
	
	public void eliminarArmaPersonaje(Personaje p, Arma a){
		
	}
	
	/* Permite calcular las resistencias de un personaje*/
	public void calcularResistencias(Personaje p, Resistencias r){
		
	}
	
	public void setIniciativaPersonaje(Personaje p, int iniciativa){
		p.setIni_Asalto(iniciativa);
	}
	
	public int getIniciativaPersonaje(Personaje p){
		return p.getIni_Asalto();
	}
	
	public Personaje getPersonajeOrdenadoIniciativa(int i){
		return personajes.get(i);
	}
	
	public void calcularIniciativas(){
		//TODO Si se pifia, calcular las correctas pifias atendiendo a valores fijos
		
		//Borramos la informacion
		info = "";
		//se calculan las iniciativas de los personajes que tiren dados automaticos
		for (Personaje p : personajes){
			if (p.isTirarAutomatico())
				info += "Tira "+p.getNombre()+
					",Tiradas: "+p.calcularIniciativa()+
					",TurnoBase: " + p.getTurnoBase()+
					",TurnoConArmaEq: " + p.getTurno()+
					",Resultado: "+p.getIni_Asalto()+"\n";
		}
		
		//Ordenamos el arrayList
		Collections.sort(personajes, new Comparator<Personaje>(){
			public int compare(Personaje p1, Personaje p2) {
				if (p1.getIni_Asalto() == p2.getIni_Asalto())
					return 0;
				if (p1.getIni_Asalto() < p2.getIni_Asalto())
					return 1;
				//else
					return -1;
			}
		});
	}
	
	/**
	 *  Permite cargar todos los personajes disponibles desde un excel */
	public void cargarPersonajes(String ruta){
		try { 
			Workbook archivoExcel = Workbook.getWorkbook(new File(ruta)); 
			System.out.println("Número de Hojas\t" + archivoExcel.getNumberOfSheets()); 
		}catch(Exception e){
			
		}
	}
	
	/**
	 * Permite cargar todas las armas desde un excel
	 */
	public void cargarArmas(String ruta){
		
	}
	
	public String getInfo(){
		return info;
	}
	
	public String getOrdenPersonajes(){
		String inf = "";
		for (Personaje p : personajes){
			inf += p.getNombre()+", ";
		}
		inf +="\n";
		return inf;
	}
	
	public ArrayList<Personaje> getPersonajes(){
		return personajes;
	}
	
	public static void main(String arg[]){
		Personaje at;
		Personaje df;
		Controlador c = new Controlador();
		//String nombre, int daño, int turno, int fue_requerida, 
		//TipoAtaque critico,Especial[] especial, int entereza, int rotura, int presencia)
		Arma garrote = new ArmaCorta("garrote",40,-10,5,TipoAtaque.CONTUNDENTE,null,5,5,5);
		Arma daga = new ArmaCorta("daga",30,20,5,TipoAtaque.FILO,null,5,5,5);
		
		Personaje pj1 = new PNJ(128, 5, 6, 3, 4, 5, 6, 7, 3, "Goblin",60,40,10,50,1);
		pj1.addArma(garrote);
		pj1.setArma(garrote);
		
		Personaje pj2 = new PNJ(74, 5, 6, 3, 4, 5, 6, 7, 3, "Goblin Ladron",20,20,45,80,0);
		pj2.addArma(daga);
		pj2.setArma(daga);
		
		Personaje pj3 = new PNJ(245, 5, 6, 3, 4, 5, 6, 7, 3, "Goblin Armado",80,80,0,40,2);
		pj3.addArma(daga);
		pj3.addArma(garrote);
		pj3.setArma(garrote);
		
		c.addPersonaje(pj1);
		c.addPersonaje(pj2);
		c.addPersonaje(pj3);
		
		System.out.println("----------PRIMER ASALTO-------------");
		c.calcularIniciativas();
		System.out.println(c.getInfo());
		System.out.println("ORDEN: "+c.getOrdenPersonajes());
		at = c.getPersonajeOrdenadoIniciativa(0);
		df = c.getPersonajeOrdenadoIniciativa(1);
		ArrayList<Personaje> def = new ArrayList<>(); def.add(df);
		c.nuevoCombate(at, def);
		System.out.println(c.getInfo());
		System.out.println("----------SEGUNDO ASALTO-------------");
		System.out.println(pj3.getNombre()+" cambia de arma a "+daga.getNombreArma());
		pj3.setArma(daga);
		c.calcularIniciativas();
		System.out.println(c.getInfo());
		System.out.println("ORDEN: "+c.getOrdenPersonajes());
		at = c.getPersonajeOrdenadoIniciativa(0);
		df = c.getPersonajeOrdenadoIniciativa(1);
		def = new ArrayList<>(); def.add(df);
		c.nuevoCombate(at, def);
		System.out.println(c.getInfo());
	}

	public Personaje getPjATQ() {
		return pjATQ;
	}

	public void setPjATQ(Personaje pjATQ) {
		this.pjATQ = pjATQ;
	}

	public ArrayList<Personaje> getPjDEF() {
		return pjDEF;
	}

	public void setPjDEF(ArrayList<Personaje> pjDEF) {
		this.pjDEF = pjDEF;
	}

	public static ArrayList<Arma> getListatodasarmas() {
		return listaTodasArmas;
	}

	public static ArrayList<Personaje> getListatodospersonajes() {
		return listaTodosPersonajes;
	}
	
}
