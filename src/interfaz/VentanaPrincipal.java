package interfaz;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import personaje.PNJ;
import personaje.Personaje;
import arma.Arma;
import arma.ArmaCorta;
import arma.TipoAtaque;

import controlador.Controlador;

public class VentanaPrincipal extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private PanelIniciativa panelIniciativa;
	private PanelCombate panelCombate; //sólo apareceran los seleccionados en iniciativa
	private PanelInfo panelInformacion; //permite modificar estadisticas de un personaje
	
	// Logica del programa
	private Controlador c;
	
	public VentanaPrincipal(Controlador c){
		this.c = c;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setContentPane(getPanelPrincipal());
	}
	
	private JPanel getPanelPrincipal(){
		JPanel p = new JPanel();
		//p.add(getPanelHerramientas(),BorderLayout.NORTH);
		p.add(getPanelTabbed(),BorderLayout.CENTER);
		//p.add(getPanelAyuda(),BorderLayout.SOUTH);
		return p;
	}
	
	private JTabbedPane getPanelTabbed(){
		JTabbedPane tp = new JTabbedPane();
		panelCombate = new PanelCombate(c);
		panelIniciativa = new PanelIniciativa(c,panelCombate);
		tp.addTab("Iniciativa",new ImageIcon("src/icons/panelIniciativa.png"), panelIniciativa,"Calcula iniciativas y prepara los enfrentamientos");
		tp.addTab("Combate",new ImageIcon("src/icons/panelCombate.png"), panelCombate,"Permite realizar enfrentamientos entre personajes");
		return tp;
	}
	
	public static void main (String args[]){
		Personaje at;
		Personaje df;
		Controlador c = new Controlador();
		//String nombre, int daño, int turno, int fue_requerida, 
		//TipoAtaque critico,Especial[] especial, int entereza, int rotura, int presencia)
		Arma garrote = new ArmaCorta("garrote",40,-10,5,TipoAtaque.CONTUNDENTE,null,5,5,5);
		Arma daga = new ArmaCorta("daga",30,20,5,TipoAtaque.FILO,null,5,5,5);
		
		//Personaje pj1 = new PNJ(128, 5, 6, 3, 4, 5, 6, 7, 3, "Goblin",60,40,10,50,1);
		//pj1.addArma(garrote);
		//pj1.setArma(garrote);
		
		/*Personaje pj2 = new PNJ(74, 5, 6, 3, 4, 5, 6, 7, 3, "Goblin Ladron",20,20,45,80,0);
		pj2.addArma(daga);
		pj2.setArma(daga);
		
		Personaje pj3 = new PNJ(245, 5, 6, 3, 4, 5, 6, 7, 3, "Goblin Armado",80,80,0,40,2);
		pj3.addArma(daga);
		pj3.addArma(garrote);
		pj3.setArma(garrote);*/
		
		Personaje pj1;
		for (int i = 0; i < 4; i++){
			pj1 = new PNJ(128, 5, 6, 3, 4, 5, 6, 7, 3, "Goblin",60,40,10,50,1);
			pj1.setNombre("Goblin"+i);
			pj1.addArma(garrote);
			pj1.setArma(garrote);
			c.addPersonaje(pj1);
		}
		
		//c.addPersonaje(pj2);
		//c.addPersonaje(pj3);
		
		VentanaPrincipal v = new VentanaPrincipal(c);
		v.setVisible(true);
	}
	
	
	
}
