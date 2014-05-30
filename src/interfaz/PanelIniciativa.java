package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
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

public class PanelIniciativa extends PanelPersonajes{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<JToggleButton> botonesAtaque;
	private ArrayList<JToggleButton> botonesDefensa;
	private PanelCombate panelCombate;
	private JTabbedPane tb;
	
	
	public PanelIniciativa(Controlador c,PanelCombate panelCombate,JTabbedPane tb){
		this.c = c;
		this.tb = tb;
		this.panelCombate = panelCombate;
		this.setPreferredSize(new Dimension(800,400));
		this.setLayout(new BorderLayout());
		
		botonesAtaque = new ArrayList<>();
		botonesDefensa = new ArrayList<>();
		panelPersonajes = getPanelPersonajes();
		
		JScrollPane js = new JScrollPane(panelPersonajes);
		js.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		js.setWheelScrollingEnabled(true);
		this.add(js, BorderLayout.CENTER);
		this.add(getPanelInfo(), BorderLayout.SOUTH);
	}
	
	private JPanel getPanelPersonajes(){
		//ActionListenerBotonAtq a = new ActionListenerBotonAtq();
		JPanel panel = new JPanel();
		PanelIniPersonaje panelPj;
		panel.setPreferredSize(new Dimension(600,c.getPersonajes().size()*32));
		
		panel.add(new PanelIniPersonaje(null)); //cabecera
		
		panelPersonaje = new HashMap<Personaje,JPanel>();
		for (Personaje p: c.getPersonajes()){
			panelPj = new PanelIniPersonaje(p);
			panelPj.setPreferredSize(new Dimension(600,24));
			panelPersonaje.put(p,panelPj);
			panel.add(panelPj);
			panelPj.getBotonAtaque().addActionListener(new ActionListenerBotonAtq());
			panelPj.getBotonDefensa().addActionListener(new ActionListenerBotonDef());
			botonesAtaque.add(panelPj.getBotonAtaque());
			botonesDefensa.add(panelPj.getBotonDefensa());
		}
		return panel;
	}
	
	/**
	 * Devuelve un panel que permite escribir anotaciones en un text area
	 * @return
	 */
	protected JPanel getPanelAnotaciones(){
		JPanel p = new JPanel();
		p.setPreferredSize(new Dimension(150,100));
		p.setLayout(new BorderLayout());
		
		p.add(new JLabel("Anotaciones"),BorderLayout.NORTH);
		
		JTextArea txArea = new JTextArea();
		txArea.setLineWrap(true);
		p.add(txArea, BorderLayout.CENTER);
		
		return p;
	}
	
	@Override
	protected JPanel getPanelBotones() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JPanel panel2 = getPanelAnotaciones();
		
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(2,0));
		
		JButton b1 = new JButton();
		b1.setIcon(new ImageIcon("src/icons/calcular.png"));
		b1.setToolTipText("Ordena los personajes para el asalto. Calcula la iniciativa si está marcado.");
		b1.setPreferredSize(new Dimension(50,50));
		b1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				actualizarDatos();
				c.calcularIniciativas();
				informacion.setText(c.getInfo());
				for (Personaje p: c.getPersonajes()){
					((PanelIniPersonaje)panelPersonaje.get(p)).ini.setValue(p.getIni_Asalto());
				}
				actualizar();
			}
		});
		panel1.add(b1);
		
		b1 = new JButton();
		b1.setIcon(new ImageIcon("src/icons/combatir.png"));
		b1.setToolTipText("Prepara un combate para los personajes seleccionados");
		b1.setPreferredSize(new Dimension(50,50));
		b1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				prepararCombate();
			}
		});
		panel1.add(b1);
		
		panel.add(panel2,BorderLayout.CENTER);
		panel.add(panel1,BorderLayout.EAST);
		
		return panel;
	
	}
	
	/**
	 * Actualiza los datos de los personajes con lo introducido a través de la interfaz
	 */
	private void actualizarDatos(){
		
	}
	
	/**
	 * Actualiza el panel, creando los subpaneles de los nuevos personajes si no estaban ya
	 */
	public void actualizar(){
		
		PanelIniPersonaje panelPj;
		
		for (int i = 0; i < panelPersonajes.getComponentCount(); i++)
			panelPersonajes.remove(i);
		
		panelPersonajes.add(new PanelIniPersonaje(null)); //cabecera
		for (Personaje p: c.getPersonajes()){
			if (panelPersonaje.containsKey(p)){
				panelPersonajes.add(panelPersonaje.get(p));
			}
			else{
				panelPj = new PanelIniPersonaje(p);
				panelPj.setPreferredSize(new Dimension(600,24));
				panelPersonaje.put(p,panelPj);
				panelPersonajes.add(panelPj);
				panelPj.getBotonAtaque().addActionListener(new ActionListenerBotonAtq());
				panelPj.getBotonDefensa().addActionListener(new ActionListenerBotonDef());
				botonesAtaque.add(panelPj.getBotonAtaque());
				botonesDefensa.add(panelPj.getBotonDefensa());
			}
		}
		
		panelPersonajes.validate();
		
	}
	
	private void prepararCombate(){
		Personaje pj;
		ArrayList<Personaje> defensores = new ArrayList<>();
		PanelIniPersonaje panelPj;
		int ad = 0; //1 ataca, -1 defiende
		// Indicar atacante y defensores
		for (int i = 0; i < c.getPersonajes().size();i++){
			ad = 0; //1 ataca, -1 defiende
			pj = c.getPersonajes().get(i);
			panelPj = (PanelIniPersonaje) panelPersonaje.get(pj);
			if (panelPj.getBotonAtaque().isSelected()) ad++;
			if (panelPj.getBotonDefensa().isSelected()) ad--;
			
			switch (ad){
				case -1: defensores.add(pj); pj.setEsDefensor(true); break;
				case 1: c.setPjATQ(pj); pj.setEsAtacante(true); break;
				default: 
			}
		}
		
		c.setPjDEF(defensores);
		
		if (c.getPjATQ() != null && c.getPjDEF().size() > 0){
			panelCombate.actualizar();
			tb.setEnabledAt(1,true);
			tb.setSelectedIndex(1);
		}
		else{
			JOptionPane.showMessageDialog(null, "Selecciona un atacante y al menos un defensor",
					"Combatir", JOptionPane.INFORMATION_MESSAGE);
			tb.setEnabledAt(1,false);
		}
		
		
	
	}
	
	
	/**
	 * Clase para implementar un action listener que solo permita que
	 * uno de los botones de ataque esten seleccionados al mismo tiempo
	 * @author Brawl
	 *
	 */
	private class ActionListenerBotonAtq implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			JToggleButton tb = (JToggleButton) arg0.getSource();
			for (int i = 0; i < botonesAtaque.size(); i++){					
				
				if (botonesAtaque.get(i) != tb){
					botonesAtaque.get(i).setSelected(false);
				}
				else
					botonesDefensa.get(i).setSelected(false);
			}
		}
		
	}
	
	/**
	 * Atq y Def no pueden estar seleccionados al mismo tiempo
	 * @author Brawl
	 *
	 */
	private class ActionListenerBotonDef  implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			JToggleButton tb = (JToggleButton) arg0.getSource();
			for (int i = 0; i < botonesDefensa.size(); i++){					
				
				if (botonesDefensa.get(i) == tb){
					botonesAtaque.get(i).setSelected(false);
				}
			}
		}
		
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
		
		public JToggleButton getBotonAtaque(){
			return atq;
		}
		
		public JToggleButton getBotonDefensa(){
			return def;
		}
	
		
		/**
		 * Obtenemos los tres iconos para el combate la iniciativa
		 * @return
		 */
		private JPanel getSubPanelBotonesCombate(boolean ini){
			JPanel p = new JPanel();
			p.setLayout(new GridLayout(0,3));
			
			//ButtonGroup group = new ButtonGroup();
			
			atq = new JToggleButton();
			atq.setIcon(new ImageIcon("src/icons/atacar.png"));
			//group.add(atq);
			
			def = new JToggleButton();
			def.setIcon(new ImageIcon("src/icons/defender.png"));
			//group.add(def);
			
			tirarAuto = new JCheckBox();
			tirarAuto.setSelected(ini);
			
			p.add(atq);
			p.add(def);
			p.add(tirarAuto);
			
			return p;
			}
	}



	
}
