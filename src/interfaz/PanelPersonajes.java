package interfaz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import personaje.Personaje;
import controlador.Controlador;

public abstract class PanelPersonajes extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/* Panel en el que se muestran todos los personajes */
	protected JPanel panelPersonajes;
	/* Area donde se muestra informacion */
	protected JTextArea informacion;
	/* Subpanel de cada personaje en concreto */
	protected HashMap<Personaje,JPanel> panelPersonaje;
	/* datos de los personajes */
	protected Controlador c;
	
	

	protected JPanel getPanelInfo(){
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		//panel para botones
		JPanel panelBotones = getPanelBotones();
		panelBotones.setPreferredSize(new Dimension(200,100));
		//panel de informacion
		informacion = new JTextArea();
		JScrollPane sp = new JScrollPane(informacion);
		sp.setPreferredSize(new Dimension(460,100));
		informacion.setEditable(false);
		
		panel.add(panelBotones);
		panel.add(sp);
		
		return panel;
	}
	
	protected Color getColorPV(int pv, int pv_max){
		if (pv < 0.25*pv_max)
			return Color.red;
		if (pv < 0.5*pv_max)
			return Color.yellow;
		return Color.green;
	}
	
	protected abstract JPanel getPanelBotones();

}
