package interfaz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;

import personaje.Personaje;

public class PanelCombatePersonaje extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Personaje personaje;
	
	private JCheckBox afectado;
	private JCheckBox ataca;
	private JCheckBox defiende;
	
	private JTextArea nombre;
	private JSpinner hp_max;
	private JButton modificarHP;
	private JProgressBar hp_bar;
	private JSpinner daño;
	private JButton aplicarDaño;
	private JLabel critico;
	
	private JSpinner h_ata;
	private JSpinner h_def;
	private JSpinner _TA;
	private JSpinner modificador;
	
	public PanelCombatePersonaje(Personaje pj){
		personaje = pj;
		this.setLayout(new FlowLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		if (personaje == null){
			JLabel l = new JLabel("ATQ");
			l.setPreferredSize(new Dimension(30,20));
			this.add(l);
		}
		else{
			ataca = new JCheckBox();
			ataca.setPreferredSize(new Dimension(30,20));
			this.add(ataca);
		}
		
		if (personaje == null){
			JLabel l = new JLabel("DEF");
			l.setPreferredSize(new Dimension(30,20));
			this.add(l);
		}
		else{
			defiende = new JCheckBox();
			defiende.setPreferredSize(new Dimension(30,20));
			this.add(defiende);
		}
		
		
		if (personaje == null){
			JLabel l = new JLabel("TIRADA");
			l.setPreferredSize(new Dimension(50,20));
			this.add(l);
		}
		else{
			afectado = new JCheckBox();
			afectado.setPreferredSize(new Dimension(50,20));
			afectado.setSelected(pj.isPNJ());
			this.add(afectado);
		}
		
		if (personaje == null){
			JLabel l = new JLabel("NOMBRE");
			l.setPreferredSize(new Dimension(100,20));
			this.add(l);
		}
		else{
			nombre = new JTextArea(personaje.getName());
			nombre.setPreferredSize(new Dimension(100,20));
			this.add(nombre);
		}
		
		if (personaje == null){
			JLabel l = new JLabel("PV MAX");
			l.setPreferredSize(new Dimension(60,20));
			this.add(l);
		}
		else{
		
		hp_max = new JSpinner();
		hp_max.setModel(new SpinnerNumberModel(personaje.getPV_MAX(),0,5000, 5));
		hp_max.setPreferredSize(new Dimension(40,20));
		this.add(hp_max);
		
		
		modificarHP = new JButton();
		this.add(modificarHP);
		modificarHP.setToolTipText("Modificar PV");
		modificarHP.setIcon(new ImageIcon("src/icons/modificarHP.png"));
		modificarHP.setPreferredSize(new Dimension(20,20));
		modificarHP.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				personaje.setPVMAX((int)hp_max.getValue());
				hp_bar.setMaximum(personaje.getPV_MAX());
				hp_bar.setValue(personaje.getPV_MAX());
				hp_bar.setString(personaje.getPV()+"");
				hp_bar.setForeground(Color.green);
			}
			
		});
		
		}
		
		if (personaje == null){
			JLabel l = new JLabel("PV");
			l.setPreferredSize(new Dimension(60,20));
			this.add(l);
		}
		else{
			hp_bar = new JProgressBar();
			hp_bar.setMaximum(personaje.getPV_MAX());
			hp_bar.setMinimum(0);
			hp_bar.setStringPainted(true);
			hp_bar.setString(personaje.getPV()+"");
			hp_bar.setForeground(Color.green);
			hp_bar.setValue(personaje.getPV());
			hp_bar.setPreferredSize(new Dimension(40,20));
			this.add(hp_bar);
		}
		
		if (personaje != null){
			critico = new JLabel();
			this.add(critico);
			critico.setToolTipText("Ha sido critico");
			//critico.setIcon(new ImageIcon("src/icons/aplicarDano.png"));
			critico.setPreferredSize(new Dimension(20,20));		
		}
		
		if (personaje == null){
			JLabel l = new JLabel("H_ATQ");
			l.setPreferredSize(new Dimension(40,20));
			this.add(l);
		}
		
		else{
			h_ata = new JSpinner();
			h_ata.setModel(new SpinnerNumberModel(personaje.getHatq(),-999,999, 5));
			h_ata.setPreferredSize(new Dimension(40,20));
			this.add(h_ata);
		}
		
		if (personaje == null){
			JLabel l = new JLabel("H_DEF");
			l.setPreferredSize(new Dimension(40,20));
			this.add(l);
		}
		else{
			h_def = new JSpinner();
			h_def.setModel(new SpinnerNumberModel(personaje.getHdef(),-999,999, 5));
			h_def.setPreferredSize(new Dimension(40,20));
			this.add(h_def);
		}
		
		if (personaje == null){
			JLabel l = new JLabel("TA");
			l.setPreferredSize(new Dimension(40,20));
			this.add(l);
		}
		else{
			_TA = new JSpinner();
			_TA.setModel(new SpinnerNumberModel(personaje.getTA(),0,10, 1));
			_TA.setPreferredSize(new Dimension(40,20));
			this.add(_TA);
		}
		
		
		if (personaje == null){
			JLabel l = new JLabel("DAÑO B");
			l.setPreferredSize(new Dimension(70,20));
			this.add(l);
		}
		else{
		
			daño = new JSpinner();
			daño.setModel(new SpinnerNumberModel(personaje.getDañoArma(),0,5000,1));
			daño.setPreferredSize(new Dimension(70,20));
			this.add(daño);
		
			/*aplicarDaño = new JButton();
			this.add(aplicarDaño);
			aplicarDaño.setToolTipText("Aplicar Daño");
			aplicarDaño.setIcon(new ImageIcon("src/icons/aplicarDano.png"));
			aplicarDaño.setPreferredSize(new Dimension(20,20));
			aplicarDaño.addActionListener(new ActionListener(){
	
				public void actionPerformed(ActionEvent arg0) {
					aplicarDaño((int)daño.getValue());
				}
				
			});*/
		}
		
		if (personaje == null){
			JLabel l = new JLabel("MODIFICADOR");
			l.setPreferredSize(new Dimension(70,20));
			this.add(l);
		}
		
		else{
			modificador = new JSpinner();
			modificador.setModel(new SpinnerNumberModel(0,-999,999, 5));
			modificador.setPreferredSize(new Dimension(70,20));
			this.add(modificador);
		}
		
		
	}
	
	public void actualizarDatos(){
		personaje.setHatq((int)h_ata.getValue());
		personaje.setHdef((int)h_def.getValue());
		personaje.setModificador((int)modificador.getValue());
		personaje.setNombre(nombre.getText());
		actualizarDañoArma();
	}
	
	public void setValorCampoDaño(int i){
		daño.setValue(i);
		this.validate();
	}
	
	public boolean tirarAuto(){
		return afectado.isSelected();
	}
	
	public JCheckBox getCheckBoxAtq(){
		return ataca;
	}
	
	public JCheckBox getCheckBoxDef(){
		return defiende;
	}
	
	public void aplicarDaño(int d){
		int vidaRestante;
		// Si es critico, lo indicamos en el boton
		if (personaje.aplicarDaño(d)){
			critico.setIcon(new ImageIcon("src/icons/critico.png"));
		}
		else{
			critico.setIcon(null);
		}
		
		vidaRestante = personaje.getPV();
		if (vidaRestante < (personaje.getPV_MAX()/4))
			hp_bar.setForeground(Color.red);
		else if (vidaRestante < (personaje.getPV_MAX()/2))
			hp_bar.setForeground(Color.yellow);
		
		hp_bar.setValue(personaje.getPV());
		hp_bar.setString(personaje.getPV()+"");
		
		
	}
	
	public void actualizarDañoArma(){
		personaje.setDañoArma((int)daño.getValue());
	}

}
