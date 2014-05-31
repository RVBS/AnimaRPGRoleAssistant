package interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

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
		p.add(getPanelHerramientas(),BorderLayout.NORTH);
		p.add(getPanelTabbed(),BorderLayout.CENTER);
		//p.add(getPanelAyuda(),BorderLayout.SOUTH);
		return p;
	}
	
	private JTabbedPane getPanelTabbed(){
		JTabbedPane tp = new JTabbedPane();
		panelCombate = new PanelCombate(c);
		panelIniciativa = new PanelIniciativa(c,panelCombate,tp);
		panelCombate.setPanelIniciativa(panelIniciativa);
		
		tp.addTab("Iniciativa",new ImageIcon("src/icons/panelIniciativa.png"), panelIniciativa,"Calcula iniciativas y prepara los enfrentamientos");
		tp.addTab("Combate",new ImageIcon("src/icons/panelCombate.png"), panelCombate,"Permite realizar enfrentamientos entre personajes");
		tp.setEnabledAt(1, false);
		return tp;
	}
	
	private JToolBar getPanelHerramientas()
	{
		 JToolBar toolBar = new JToolBar();
		 toolBar.setFloatable(false);
		 
		 JButton add = new JButton();
		 add.setPreferredSize(new Dimension(48,48));
		 add.setIcon(new ImageIcon("src/icons/addP.png"));
		 //add.addActionListener(l);
		 
		 JButton remove = new JButton();
		 remove.setPreferredSize(new Dimension(48,48));
		 remove.setIcon(new ImageIcon("src/icons/removeP.png"));
		 
		 
		 toolBar.add(add);
		 toolBar.add(remove);
		 
		 return toolBar;
	}
	
	private void cargarBBDD(String path){
		c.cargarArmas(path);
		c.cargarPersonajes(path);
		System.out.println("BASE CARGADA");
	}
	
	public void actualizar(){
		panelIniciativa.actualizar();
	}
	
	public static void main (String args[]){
		Controlador c = new Controlador();		
		VentanaPrincipal v = new VentanaPrincipal(c);
		v.cargarBBDD("src/BBDD.xls");
		c.addPersonaje(Controlador.getListatodospersonajes().get(0),"Guardia");
		c.addPersonaje(Controlador.getListatodospersonajes().get(2),"Roger");
		c.addPersonaje(Controlador.getListatodospersonajes().get(3),"Charlie");
		c.addPersonaje(Controlador.getListatodospersonajes().get(1),"Goblin1");
		c.addPersonaje(Controlador.getListatodospersonajes().get(1),"Goblin2");
		c.addPersonaje(Controlador.getListatodospersonajes().get(1),"Goblin3");
		v.actualizar();
		v.setSize(825, 545);
		v.setResizable(false);
		v.setVisible(true);
	}
	
	
	
}
