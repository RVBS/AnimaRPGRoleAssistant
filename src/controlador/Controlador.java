package controlador;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import jxl.*; 
import java.io.*; 

import arma.Arma;
import arma.ArmaCorta;
import arma.Desarmado;
import arma.Especial;
import arma.Maza;
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
	public void addPersonaje(Personaje p, String nombre){
		personajes.add(p.clonar(nombre));
	}
	
	/**
	 * Permite eliminar un personaje del combate
	 */
	public void eliminarPersonaje(Personaje p){
		personajes.remove(p);
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
			// Nos quedamos con la hoja de los personajes (hoja 0)
			Sheet hoja = archivoExcel.getSheet(0); 
			String tipo; 
			Personaje p;
			int indexArma = 0;
			int iArma; //indice traducido a la lista de armas cargada
			int indexPj = 1;
			int arg[] = new int[21];
			
			while (!hoja.getCell(0,indexPj).getContents().equals("")){ //mientras queden personajes
				//rellenamos todas las caracteristicas
				for (int i = 1; i < 21; i++)
					arg[i-1] = Integer.parseInt(hoja.getCell(i, indexPj).getContents());
				
				tipo = hoja.getCell(0, indexPj).getContents();
				//creamos el personaje
				p = new PNJ(tipo,tipo,arg[0],arg[1],arg[2],arg[3],arg[4],arg[5],arg[6],arg[7],arg[8], arg[9],
						arg[10],arg[11],arg[12],arg[13],arg[14],arg[15],arg[16],arg[17],arg[18],arg[19]);
				
				// Leemos las armas de las que dispone. Si no estan en blanco
				indexArma = 0;
				while (!hoja.getCell(indexArma+26,indexPj).getContents().equals("") && indexArma < 3){
				//for (int i = 0; i < 3 && !hoja.getCell(i,indexPj).getContents().equals(""); i++){
					iArma = Integer.parseInt(hoja.getCell(indexArma+26,indexPj).getContents());
					p.addArma(listaTodasArmas.get(iArma-2));
					if (indexArma == 0) //la primera es la arma equipada
						p.setArma(listaTodasArmas.get(iArma-2));
					indexArma++;
				}
				
				// añadimos el arma desarmado, que es la básica para todos y si no tiene otra equipada, tendrá desarmado
				p.addArma(listaTodasArmas.get(0));
				if (p.getArmaEquipada() == null)
					p.setArma(listaTodasArmas.get(0));
				listaTodosPersonajes.add(p);
				indexPj++;
			}
			
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
	}

	
	/**
	 * Permite cargar todas las armas desde un excel al arraylist de armas
	 */
	public void cargarArmas(String ruta){
		try { 
			Workbook archivoExcel = Workbook.getWorkbook(new File(ruta)); 
			// Nos quedamos con la hoja de las armas (hoja 1)
			Sheet hoja = archivoExcel.getSheet(1); 
			String nombre;
			int d,t,fr,ent,rot,pre;
			TipoAtaque cri1,cri2;
			int index = 1; // saltamos la primera fila por ser la de cabecera
			Arma arma;
			String str;
			
			while (!hoja.getCell(0,index).getContents().equals("")){
				
				nombre = hoja.getCell(0,index).getContents();
				d = Integer.parseInt(hoja.getCell(2,index).getContents());
				t = Integer.parseInt(hoja.getCell(3,index).getContents());
				fr = 0; // TODO NO se usa aún
				ent = Integer.parseInt(hoja.getCell(8,index).getContents());
				rot = Integer.parseInt(hoja.getCell(9,index).getContents());
				pre = Integer.parseInt(hoja.getCell(10,index).getContents());
				
				str = hoja.getCell(4,index).getContents();
				if (!str.equals(""))
					cri1 = TipoAtaque.valueOf(str);
				else
					cri1 = null;
				
				str = hoja.getCell(5,index).getContents();
				if (!str.equals(""))
					cri2 = TipoAtaque.valueOf(str);
				else
					cri2 = null;

				switch (hoja.getCell(1,index).getContents()){ // leemos el campo tipo de arma
					case "arma corta":
						arma = new ArmaCorta(nombre,d,t,fr,cri1,cri2,null,ent,rot,pre);
						break;
					case "maza":
						arma = new Maza(nombre,d,t,fr,cri1,cri2,null,ent,rot,pre);
						break;
					default:
						arma = new Desarmado(nombre,d,t);
				}
				// añadimos el arma a la lista de todas las armas
				listaTodasArmas.add(arma);
				index++;
				}

		}catch(Exception e){
			System.err.println(e.getMessage());
		}
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
	
	public int getResultadoUltimoCombate(){
		if (combate != null)
			return combate.getResultadoUltimoCombate();
		else return -999;
	}
	
	public int getDamageUltimoCombate(){
		if (combate != null)
			return combate.getDamageUltimoCombate();
		else return -999;
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
