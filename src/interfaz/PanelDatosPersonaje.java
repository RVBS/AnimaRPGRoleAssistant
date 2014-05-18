package interfaz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

import personaje.PNJ;
import personaje.Personaje;

public class PanelDatosPersonaje extends JPanel {
	
	private Personaje personaje;
	
	private JCheckBox afectado;
	
	private JTextArea nombre;
	private JSpinner hp_max;
	private JButton modificarHP;
	private JProgressBar hp_bar;
	private JSpinner daño;
	private JButton aplicarDaño;
	private JLabel critico;
	
	private JSpinner h_ata;
	private JSpinner h_def;
	private JSpinner turno_base;
	private JSpinner iniciativa;
	private JSpinner modificador;
	
	private JSpinner rE;
	private JSpinner rF;
	private JSpinner rV;
	private JSpinner rM;
	private JSpinner rP;
	
	private JPanel panelNombre;
	private JPanel panelCombate;
	private JPanel panelSalud;
	private JPanel panelResistencias;
	
	
	public PanelDatosPersonaje(Personaje pj){
		this.personaje = pj;
		this.setLayout(new GridLayout(0,4));
		
		panelNombre = new JPanel();
		panelNombre.setLayout(new FlowLayout());
		
		if (personaje == null){
			JLabel l = new JLabel("TIRADA");
			l.setPreferredSize(new Dimension(50,20));
			panelNombre.add(l);
		}
		else{
			afectado = new JCheckBox();
			afectado.setPreferredSize(new Dimension(50,20));
			panelNombre.add(afectado);
		}
		
		if (personaje == null){
			JLabel l = new JLabel("NOMBRE");
			l.setPreferredSize(new Dimension(100,20));
			panelNombre.add(l);
		}
		else{
			nombre = new JTextArea(personaje.getName());
			nombre.setPreferredSize(new Dimension(100,20));
			panelNombre.add(nombre);
		}
		
		this.add(panelNombre);
		panelCombate = getPanelCombate();
		this.add(panelCombate);
		panelSalud = getPanelSalud();
		this.add(panelSalud);
		panelResistencias = getPanelResistencias();
		this.add(panelResistencias);
	}
	
	private JPanel getPanelSalud(){
		JPanel panelVida = new JPanel(new FlowLayout());
		
		panelVida.setBorder(BorderFactory.createLineBorder(Color.black));
		
		if (personaje == null){
			JLabel l = new JLabel("PV MAX");
			l.setPreferredSize(new Dimension(60,20));
			panelVida.add(l);
		}
		else{
		
		hp_max = new JSpinner();
		hp_max.setModel(new SpinnerNumberModel(personaje.getPV_MAX(),0,5000, 5));
		hp_max.setPreferredSize(new Dimension(40,20));
		panelVida.add(hp_max);
		
		
		modificarHP = new JButton();
		panelVida.add(modificarHP);
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
			l.setPreferredSize(new Dimension(40,20));
			panelVida.add(l);
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
			panelVida.add(hp_bar);
		}
		
		if (personaje == null){
			JLabel l = new JLabel("DAÑO");
			l.setPreferredSize(new Dimension(80,20));
			panelVida.add(l);
		}
		else{
		
			daño = new JSpinner();
			daño.setModel(new SpinnerNumberModel(0,0,5000,1));
			daño.setPreferredSize(new Dimension(40,20));
			panelVida.add(daño);
		
			aplicarDaño = new JButton();
			panelVida.add(aplicarDaño);
			aplicarDaño.setToolTipText("Aplicar Daño");
			aplicarDaño.setIcon(new ImageIcon("src/icons/aplicarDano.png"));
			aplicarDaño.setPreferredSize(new Dimension(20,20));
			aplicarDaño.addActionListener(new ActionListener(){
	
				public void actionPerformed(ActionEvent arg0) {
					int vidaRestante;
					// Si es critico, lo indicamos en el boton
					if (personaje.aplicarDaño((int)daño.getValue())){
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
				
			});
		}
		
		if (personaje != null){
			critico = new JLabel();
			panelVida.add(critico);
			critico.setToolTipText("Ha sido critico");
			//critico.setIcon(new ImageIcon("src/icons/aplicarDano.png"));
			critico.setPreferredSize(new Dimension(20,20));		
		}
		
		return panelVida;
	}
	
	private JPanel getPanelCombate(){
		JPanel panelCombate = new JPanel();
		panelCombate.setBorder(BorderFactory.createLineBorder(Color.black));
		panelCombate.setLayout(new FlowLayout());
		
		if (personaje == null){
			JLabel l = new JLabel("INICIATIVA");
			l.setPreferredSize(new Dimension(40,20));
			panelCombate.add(l);
		}
		else{
		
			iniciativa = new JSpinner();
			iniciativa.setModel(new SpinnerNumberModel(0,0,5000,5));
			iniciativa.setPreferredSize(new Dimension(40,20));
			panelCombate.add(iniciativa);
		}
		
		if (personaje == null){
			JLabel l = new JLabel("H_ATQ");
			l.setPreferredSize(new Dimension(40,20));
			panelCombate.add(l);
		}
		
		else{
			h_ata = new JSpinner();
			h_ata.setModel(new SpinnerNumberModel(personaje.getHatq(),0,5000, 5));
			h_ata.setPreferredSize(new Dimension(40,20));
			panelCombate.add(h_ata);
		}
		
		if (personaje == null){
			JLabel l = new JLabel("H_DEF");
			l.setPreferredSize(new Dimension(40,20));
			panelCombate.add(l);
		}
		else{
			h_def = new JSpinner();
			h_def.setModel(new SpinnerNumberModel(personaje.getHdef(),0,5000, 5));
			h_def.setPreferredSize(new Dimension(40,20));
			panelCombate.add(h_def);
		}
		
		if (personaje == null){
			JLabel l = new JLabel("TURNO");
			l.setPreferredSize(new Dimension(40,20));
			panelCombate.add(l);
		}
		else{
			turno_base = new JSpinner();
			turno_base.setModel(new SpinnerNumberModel(personaje.getTurno(),0,5000, 5));
			turno_base.setPreferredSize(new Dimension(40,20));
			panelCombate.add(turno_base);
		}
			
		
		return panelCombate;
	}
	
	private JPanel getPanelResistencias(){
		JPanel panelResistencias = new JPanel();
		panelResistencias.setBorder(BorderFactory.createLineBorder(Color.black));
		panelResistencias.setLayout(new FlowLayout());
		
		if (personaje == null){
			JLabel l = new JLabel("RE");
			l.setPreferredSize(new Dimension(60,20));
			panelResistencias.add(l);
		}
		else {
			rE = new JSpinner();
			rE.setModel(new SpinnerNumberModel(personaje.getRe(),0,999, 5));
			rE.setPreferredSize(new Dimension(40,20));
			panelResistencias.add(rE);
		}
		
		if (personaje == null){
			JLabel l = new JLabel("RF");
			l.setPreferredSize(new Dimension(60,20));
			panelResistencias.add(l);
		}
		else {
			rF = new JSpinner();
			rF.setModel(new SpinnerNumberModel(personaje.getRf(),0,999, 5));
			rF.setPreferredSize(new Dimension(40,20));
			panelResistencias.add(rF);
		}
		
		if (personaje == null){
			JLabel l = new JLabel("RV");
			l.setPreferredSize(new Dimension(60,20));
			panelResistencias.add(l);
		}
		else {
			rV = new JSpinner();
			rV.setModel(new SpinnerNumberModel(personaje.getRv(),0,999, 5));
			rV.setPreferredSize(new Dimension(40,20));
			panelResistencias.add(rV);
		}
		
		if (personaje == null){
			JLabel l = new JLabel("RM");
			l.setPreferredSize(new Dimension(60,20));
			panelResistencias.add(l);
		}
		else {
			rM = new JSpinner();
			rM.setModel(new SpinnerNumberModel(personaje.getRm(),0,999, 5));
			rM.setPreferredSize(new Dimension(40,20));
			panelResistencias.add(rM);
		}
		
		if (personaje == null){
			JLabel l = new JLabel("RP");
			l.setPreferredSize(new Dimension(60,20));
			panelResistencias.add(l);
		}
		else {
			rP = new JSpinner();
			rP.setModel(new SpinnerNumberModel(personaje.getRp(),0,999, 5));
			rP.setPreferredSize(new Dimension(40,20));
			panelResistencias.add(rP);
		}
		
		return panelResistencias;
	}

	public void setPanelCombate(JPanel panelCombate) {
		this.panelCombate = panelCombate;
	}

	public void setPanelSalud(JPanel panelSalud) {
		this.panelSalud = panelSalud;
	}

	public void setPanelResistencias(JPanel panelResistencias) {
		this.panelResistencias = panelResistencias;
	}
	
	public JPanel getPNombre(){
		return panelNombre;
	}
	
	public JPanel getPCombate(){
		return panelCombate;
	}
	
	public JPanel getPIniciativa(){
		return panelSalud;
	}
	
	public JPanel getPResistencias(){
		return panelResistencias;
	}
	
	
	
	

}
