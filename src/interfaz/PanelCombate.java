package interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;

import personaje.Personaje;

import controlador.Controlador;

public class PanelCombate extends PanelPersonajes{
	
	private static final long serialVersionUID = 1L;
	private JPanel panelPersonajes;
	private JTextArea informacion;
	private HashMap<Personaje,JPanel> panelPersonaje;
	/** para poder obtener los datos de los personajes **/
	private Controlador c;
	private JSpinner result;
	private JSpinner damage;
	
	
	public PanelCombate(Controlador c){
		this.c = c;
		this.setPreferredSize(new Dimension(800,400));
		this.setLayout(new BorderLayout());
		
		panelPersonajes = getPanelPersonajes();
		
		JScrollPane js = new JScrollPane(panelPersonajes);
		js.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		js.setWheelScrollingEnabled(true);
		this.add(js, BorderLayout.CENTER);
		this.add(getPanelInfo(), BorderLayout.SOUTH);
	}
	
	private JPanel getPanelPersonajes(){
		/* En este caso solo se crean para los personajes que combaten directamente */
		
		JPanel panel = new JPanel();
		PanelCombPersonaje panelPj;
		panel.setPreferredSize(new Dimension(600,(c.getPjDEF().size()+1)*32));
		
		panel.add(new PanelCombPersonaje(null)); //cabecera
		
		panelPersonaje = new HashMap<Personaje,JPanel>();
		
		//atacante
		panelPj = new PanelCombPersonaje(c.getPjATQ());
		panelPj.setPreferredSize(new Dimension(600,24));
		panelPersonaje.put(c.getPjATQ(),panelPj);
		panel.add(panelPj);
		//defensores
		for (Personaje p: c.getPjDEF()){
			panelPj = new PanelCombPersonaje(p);
			panelPj.setPreferredSize(new Dimension(600,24));
			panelPersonaje.put(p,panelPj);
			panel.add(panelPj);
		}
		return panel;
	}
	
	protected JPanel getPanelResultado(){
		JPanel p = new JPanel();
		p.setPreferredSize(new Dimension(150,100));
		p.setLayout(new GridLayout(4,0));
		
		p.add(new JLabel("Resultado"));
		
		result = new JSpinner();
		result.setModel(new SpinnerNumberModel(0,-999,999,5));
		p.add(result);
		
		p.add(new JLabel("Da�o efectivo"));
		
		damage = new JSpinner();
		damage.setModel(new SpinnerNumberModel(0,-999,999,5));
		p.add(damage);
		
		return p;
	}
	
	@Override
	protected JPanel getPanelBotones() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JPanel panel2 = getPanelResultado();
		
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(2,0));
		
		JButton b1 = new JButton();
		b1.setIcon(new ImageIcon("src/icons/calcular.png"));
		b1.setToolTipText("Calcula el resultado del combate. Tira los dados si est� marcado");
		b1.setPreferredSize(new Dimension(50,50));
		//b1.addActionListener();
		panel1.add(b1);
		
		b1 = new JButton();
		b1.setIcon(new ImageIcon("src/icons/aplicar_combate.png"));
		b1.setToolTipText("Aplica los resultados del combate a la salud de los personajes involucrados");
		b1.setPreferredSize(new Dimension(50,50));
		//b1.addActionListener();
		panel1.add(b1);
		
		panel.add(panel2,BorderLayout.CENTER);
		panel.add(panel1,BorderLayout.EAST);
		
		return panel;
	}
	
	/**
	 * Actualiza el panel con la informacion nueva sobre los atacantes y defensores
	 */
	public void actualizar(){
		panelPersonajes.removeAll();
		
		PanelCombPersonaje panelPj;
		panelPersonajes.setPreferredSize(new Dimension(750,(c.getPjDEF().size()+1)*32));
		
		panelPj = new PanelCombPersonaje(null);
		panelPj.setPreferredSize(new Dimension(750,24));
		panelPersonajes.add(panelPj); //cabecera
		
		panelPersonaje = new HashMap<Personaje,JPanel>();
		
		//atacante
		panelPj = new PanelCombPersonaje(c.getPjATQ());
		panelPj.setPreferredSize(new Dimension(750,24));
		panelPersonaje.put(c.getPjATQ(),panelPj);
		panelPersonajes.add(panelPj);
		//defensores
		for (Personaje p: c.getPjDEF()){
			panelPj = new PanelCombPersonaje(p);
			panelPj.setPreferredSize(new Dimension(750,24));
			panelPersonaje.put(p,panelPj);
			panelPersonajes.add(panelPj);
		}		
		this.validate();
	}
	
	/**
	 * Clase para tener una fila con los datos de los personajes para el combate
	 * @author Brawl
	 *
	 */
	private class PanelCombPersonaje extends JPanel{
		
		//indica si el personaje esta atacando o defendiendo
		JLabel ad;
		//check box para saber si realiza tirada automatica
		JCheckBox tirarAuto;
		//H_combate final
		JSpinner ha_final;
		//H_def final
		JSpinner hd_final;
		//H_esq final
		JSpinner he_final;
		
		//TODO falta un radio button para indicar si se defiende o se esquiva
		
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

		public PanelCombPersonaje(Personaje p){
			
			
			
			if (p == null) //cabecera
			{
				this.setLayout(new GridLayout(0,9));
				JTextField f;
				
				JPanel panel = new JPanel();
				panel.setLayout(new GridLayout(0,2));
				
				f = new JTextField("A/D");
				f.setEditable(false);
				panel.add(f);
				
				f = new JTextField("TIR");
				f.setEditable(false);
				panel.add(f);
				
				this.add(panel);
				
				f = new JTextField("TIPO");
				f.setEditable(false);
				this.add(f);
				
				f = new JTextField("NOMBRE");
				f.setEditable(false);
				this.add(f);
				
				f = new JTextField("HA_FINAL");
				f.setEditable(false);
				this.add(f);
				
				f = new JTextField("HD_FINAL");
				f.setEditable(false);
				this.add(f);
				
				f = new JTextField("HE_FINAL");
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
				this.setLayout(new GridLayout(0,9));
				
				JPanel panel = new JPanel();
				panel.setLayout(new GridLayout(0,2));
				
				ad = new JLabel();
				if (p.isEsAtacante())
					ad.setIcon(new ImageIcon("src/icons/atacar.png"));
					
				else if (p.isEsDefensor())
					ad.setIcon(new ImageIcon("src/icons/defender.png"));
				panel.add(ad);
				
				tirarAuto = new JCheckBox();
				tirarAuto.setSelected(true);
				panel.add(tirarAuto);
				
				this.add(panel);
				
				tipoPj = new JTextField();
				tipoPj.setText(p.getTipo());
				tipoPj.setEditable(false);
				this.add(tipoPj);
				
				nombre = new JTextField();
				nombre.setText(p.getNombre());
				nombre.setEditable(true);
				this.add(nombre);
				
				ha_final = new JSpinner();
				ha_final.setModel(new SpinnerNumberModel(0,-999,999,5));
				this.add(ha_final);
				
				hd_final = new JSpinner();
				hd_final.setModel(new SpinnerNumberModel(0,-999,999,5));
				this.add(hd_final);
				
				he_final = new JSpinner();
				he_final.setModel(new SpinnerNumberModel(0,-999,999,5));
				this.add(he_final);
				
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
	}
	


}
