package interfaz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;

import personaje.Personaje;

public class PanelIniciativaPersonaje extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Personaje personaje;
	private JCheckBox afectado;
	
	private JTextArea nombre;
	private JSpinner turno_base;
	private JSpinner iniciativa;
	private JSpinner modificador;
	
	public PanelIniciativaPersonaje(Personaje pj){
		personaje = pj;
		this.setLayout(new FlowLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		if (personaje == null){
			JLabel l = new JLabel("TIRADA");
			l.setPreferredSize(new Dimension(50,20));
			this.add(l);
		}
		else{
			afectado = new JCheckBox();
			afectado.setSelected(pj.isPNJ());
			afectado.setPreferredSize(new Dimension(50,20));
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
			JLabel l = new JLabel("INICIATIVA");
			l.setPreferredSize(new Dimension(75,20));
			this.add(l);
		}
		else{
		
			iniciativa = new JSpinner();
			iniciativa.setModel(new SpinnerNumberModel(personaje.getIniciativa(),-999,999,5));
			iniciativa.setPreferredSize(new Dimension(75,20));
			this.add(iniciativa);
		}
		
		if (personaje == null){
			JLabel l = new JLabel("TURNO");
			l.setPreferredSize(new Dimension(40,20));
			this.add(l);
		}
		else{
			turno_base = new JSpinner();
			turno_base.setModel(new SpinnerNumberModel(personaje.getTurno(),0,999, 5));
			turno_base.setPreferredSize(new Dimension(40,20));
			this.add(turno_base);
		}
		
		if (personaje == null){
			JLabel l = new JLabel("MODIFICADOR");
			l.setPreferredSize(new Dimension(75,20));
			this.add(l);
		}
		else{
			modificador = new JSpinner();
			modificador.setModel(new SpinnerNumberModel(0,-999,999, 5));
			modificador.setPreferredSize(new Dimension(75,20));
			this.add(modificador);
		}
	}
	
	public void actualizarDatos(){
		personaje.setTurno((int)turno_base.getValue());
		personaje.setIniciativa((int)iniciativa.getValue());
		personaje.setNombre(nombre.getText());
		personaje.setModificador((int)modificador.getValue());
	}
	
	public void setValorCampoIniciativa(int i){
		iniciativa.setValue(i);
		this.validate();
	}
	
	public boolean tirarAuto(){
		return afectado.isSelected();
	}

}
