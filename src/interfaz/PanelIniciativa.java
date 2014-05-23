package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;

import arma.Arma;
import arma.ArmaCorta;
import arma.TipoAtaque;

import controlador.Controlador;
import personaje.PNJ;
import personaje.Personaje;

public class PanelIniciativa extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panelPersonajes;
	private JTextArea informacion;
	private HashMap<Personaje,JPanel> panelPersonaje;
	/** para poder obtener los datos de los personajes **/
	private Controlador c;
	
	
	public PanelIniciativa(Controlador c){
		this.c = c;
		this.setPreferredSize(new Dimension(800,400));
		this.setLayout(new BorderLayout());
		
		panelPersonajes = getPanelPersonajes();
		
		this.add(panelPersonajes, BorderLayout.CENTER);
		this.add(getPanelInfo(), BorderLayout.SOUTH);
	}
	
	private JPanel getPanelPersonajes(){
		JPanel panel = new JPanel();
		PanelIniPersonaje panelPj;
		
		panel.add(new PanelIniPersonaje(null)); //cabecera
		
		panelPersonaje = new HashMap<Personaje,JPanel>();
		for (Personaje p: c.getPersonajes()){
			panelPj = new PanelIniPersonaje(p);
			panelPj.setPreferredSize(new Dimension(600,24));
			panelPersonaje.put(p,panelPj);
			panel.add(panelPj);
		}
		return panel;
	}
	
	private JScrollPane getPanelInfo(){
		JScrollPane p = new JScrollPane();
		informacion = new JTextArea();
		informacion.setEditable(false);
		p.add(informacion);
		return p;
	}
	
	
	/**
	 * Clase para tener una fila con los datos de los personajes para la iniciativa
	 * @author Brawl
	 *
	 */
	private class PanelIniPersonaje extends JPanel{
		
		//botones para el combate
		JToggleButton atq;
		JToggleButton def;
		//check box para saber si realiza tirada automatica
		JCheckBox tirarAuto;
		//Iniciativa
		JSpinner ini;
		//tipo del personaje (Goblin, humano, monstruo...etc)
		JTextField tipoPj;
		//Nombre del pj
		JTextField nombre;
		//JComboBox para saber el arma seleccionada
		JComboBox<String> armaSel;
		//Vida restante
		JProgressBar hp;
		//JTextField para el modificador
		JSpinner modificador;
		
		private static final long serialVersionUID = 1L;

		public PanelIniPersonaje(Personaje p){
			
			
			
			if (p == null) //cabecera
			{
				this.setLayout(new GridLayout(0,7));
				JPanel pa = new JPanel();
				pa.setLayout(new GridLayout(0,3));
				
				JTextField f;
				
				f = new JTextField("ATQ");
				f.setEditable(false);
				pa.add(f);
				f = new JTextField("DEF");
				f.setEditable(false);
				pa.add(f);
				f = new JTextField("TIR");
				f.setEditable(false);
				pa.add(f);

				this.add(pa);
				
				f = new JTextField("TIPO");
				f.setEditable(false);
				this.add(f);
				
				f = new JTextField("NOMBRE");
				f.setEditable(false);
				this.add(f);
				
				f = new JTextField("INICIATIVA");
				f.setEditable(false);
				this.add(f);
				
				f = new JTextField("ARMA EQ.");
				f.setEditable(false);
				this.add(f);
				
				f = new JTextField("HP");
				f.setEditable(false);
				this.add(f);
				
				f = new JTextField("MODIFIC.");
				f.setEditable(false);
				this.add(f);
				
			}
			else{
				this.setLayout(new GridLayout(0,7));
				this.add(getSubPanelBotonesCombate(p.isPNJ()));
				
				tipoPj = new JTextField();
				tipoPj.setText(p.getTipo());
				tipoPj.setEditable(false);
				this.add(tipoPj);
				
				nombre = new JTextField();
				nombre.setText(p.getNombre());
				nombre.setEditable(true);
				this.add(nombre);
				
				ini = new JSpinner();
				ini.setModel(new SpinnerNumberModel(0,-999,999,5));
				this.add(ini);
				
				armaSel = new JComboBox<String>(p.getListaArmas());
				armaSel.setSelectedIndex(p.getIndexArmaEquipada());
				//armaSel.addActionListener(this);
				this.add(armaSel);
				
				hp = new JProgressBar();
				hp.setMaximum(p.getPV_MAX());
				hp.setMinimum(0);
				hp.setValue(p.getPV());
				hp.setStringPainted(true);
				hp.setForeground(getColorPV(p.getPV(),p.getPV_MAX()));
				this.add(hp);
				
				modificador = new JSpinner();
				modificador.setModel(new SpinnerNumberModel(0,-999,999,5));
				this.add(modificador);
			}
			
		}
		
		private Color getColorPV(int pv, int pv_max){
			if (pv < 0.25*pv_max)
				return Color.red;
			if (pv < 0.5*pv_max)
				return Color.yellow;
			return Color.green;
		}
		
		public JToggleButton getBotonAtaque(){
			return atq;
		}
		
		/**
		 * Obtenemos los tres iconos para el combate la iniciativa
		 * @return
		 */
		private JPanel getSubPanelBotonesCombate(boolean ini){
			JPanel p = new JPanel();
			p.setLayout(new GridLayout(0,3));
			
			ButtonGroup group = new ButtonGroup();
			
			atq = new JToggleButton();
			atq.setIcon(new ImageIcon("src/icons/atacar.png"));
			group.add(atq);
			
			def = new JToggleButton();
			def.setIcon(new ImageIcon("src/icons/defender.png"));
			group.add(def);
			
			tirarAuto = new JCheckBox();
			tirarAuto.setSelected(ini);
			
			p.add(atq);
			p.add(def);
			p.add(tirarAuto);
			
			return p;
			}
	}
	
	public static void main(String args[]){
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
		
		JFrame f = new JFrame();
		f.setSize(new Dimension(100,100));
		f.setVisible(true);
		JPanel p = new JPanel();
		p.add(new PanelIniciativa(c));
		f.setContentPane(p);
	}
	
	
}
