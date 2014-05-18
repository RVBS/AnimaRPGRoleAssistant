package interfaz;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;

import personaje.Personaje;

public class PanelCombatientes extends JPanel{
	
	public static final int ABSORCION_NATURAL = 20;
	
	PanelCombatePersonaje panelAtacante;
	PanelCombatePersonaje panelDefensor;
	
	Personaje atacante;
	Personaje defensor;
	
	JTextArea nombreAtacante;
	JTextArea nombreDefensor;
	
	JSpinner ataque;
	JSpinner da�o_base_atacante;
	JSpinner defensa;
	
	JSpinner resultado;
	JSpinner da�o;
	
	JButton aplicar;
	
	public PanelCombatientes(Personaje a, PanelCombatePersonaje pa,
			Personaje d, PanelCombatePersonaje pd){
		this.setLayout(new GridLayout(0,3));
		this.setPreferredSize(new Dimension(500,80));
		atacante = a;
		defensor = d;
		
		panelAtacante = pa;
		panelDefensor = pd;
		
		JPanel habilidadAt = new JPanel();
		JPanel habilidadDf = new JPanel();

		
		habilidadAt.setLayout(new GridLayout(3,2));
		habilidadAt.setBorder(BorderFactory.createBevelBorder(0));
		habilidadDf.setLayout(new GridLayout(2,2));
		habilidadDf.setBorder(BorderFactory.createBevelBorder(0));

		
		JLabel l = new JLabel("ATACANTE");
		l.setPreferredSize(new Dimension(80,20));
		nombreAtacante = new JTextArea(atacante.getName());
		habilidadAt.add(l);
		habilidadAt.add(nombreAtacante);
		
		l = new JLabel("DA�O BASE");
		l.setPreferredSize(new Dimension(60,20));
		habilidadAt.add(l);
		
		da�o_base_atacante = new JSpinner();
		da�o_base_atacante.setModel(new SpinnerNumberModel(atacante.getDa�oArma(),0,5000, 5));
		da�o_base_atacante.setPreferredSize(new Dimension(40,20));
		habilidadAt.add(da�o_base_atacante);
		
		l = new JLabel("H_AT_FINAL");
		l.setPreferredSize(new Dimension(60,20));
		habilidadAt.add(l);
		ataque = new JSpinner();
		ataque.setModel(new SpinnerNumberModel(0,-999,999, 5));
		ataque.setPreferredSize(new Dimension(40,20));
		habilidadAt.add(ataque);
		
		
		
		l = new JLabel("DEFENSOR");
		l.setPreferredSize(new Dimension(100,20));
		nombreDefensor = new JTextArea(defensor.getName());

		habilidadDf.add(l);
		habilidadDf.add(nombreDefensor);
		
		l = new JLabel("H_DF_FINAL");
		l.setPreferredSize(new Dimension(100,20));
		
		defensa = new JSpinner();
		defensa.setModel(new SpinnerNumberModel(0,-999,999, 5));
		defensa.setPreferredSize(new Dimension(40,20));
		
		
		habilidadDf.add(l);
		habilidadDf.add(defensa);
		
		this.add(habilidadAt);
		this.add(habilidadDf);		
		this.add(getPanelResultados());
	}
	
	private JPanel getPanelResultados(){
		JPanel resultados = new JPanel();
		resultados.setLayout(new GridLayout(3,2));
		resultados.setBorder(BorderFactory.createBevelBorder(0));
		
		//JPanel p1 = new JPanel();
		//p1.setLayout(new GridLayout(0,2));
		
		JLabel l = new JLabel("RESULTADO");
		//l.setPreferredSize(new Dimension(40,20));
		resultado = new JSpinner();
		resultado.setModel(new SpinnerNumberModel(0,-999,999, 5));
		//resultado.setPreferredSize(new Dimension(40,20));
		
		resultados.add(l);
		resultados.add(resultado);
		
		//JPanel p2 = new JPanel();
		resultados.setLayout(new GridLayout(0,2));
		
		JLabel l2 = new JLabel("DA�O EF.");
		//l2.setPreferredSize(new Dimension(40,20));
		da�o = new JSpinner();
		da�o.setModel(new SpinnerNumberModel(atacante.getDa�oArma(),-999,999, 5));
		//da�o.setPreferredSize(new Dimension(40,20));
		
		resultados.add(l2);
		resultados.add(da�o);
		
		//JPanel p3 = new JPanel();
		//p1.setLayout(new GridLayout(0,2));
		
		JButton b = new JButton();
		b.setIcon(new ImageIcon("src/icons/tirarIniciativa.png"));
		b.setPreferredSize(new Dimension(20,20));
		b.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				calcularCombate();
			}
			
		});
		resultados.add(b);
		
		b = new JButton();
		b.setIcon(new ImageIcon("src/icons/aplicarDano.png"));
		b.setPreferredSize(new Dimension(20,20));
		b.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				aplicarCombate();
			}
			
		});
		resultados.add(b);
		
		//resultados.add(p1);
		//resultados.add(p2);
		//resultados.add(p3);
		
		return resultados;
	}
	
	private void calcularCombate(){
		
		int hab_ataque_final;
		int hab_defensa_final;
		int resul;
		float porcentaje;
		int da�o_efectivo = 0;
		//actualizamos datos
		panelAtacante.actualizarDatos();
		panelDefensor.actualizarDatos();
		
		/*Solo tenemos que calcular los finales si esta activada la opcion
		 * si no, se usan los introducidos por el usuario
		 */
		if (panelAtacante.tirarAuto()){
			ataque.setValue(atacante.calcularHabilidadAtaque());
		}
		
		if (panelDefensor.tirarAuto()){
			defensa.setValue(defensor.calcularHabilidadDefensa());
		}
		
		//Ahora calculamos normal
		hab_ataque_final = (int)ataque.getValue();
		hab_defensa_final = (int)defensa.getValue();
		
		resul = hab_ataque_final - hab_defensa_final;
		
		resultado.setValue(resul);
		
		//calculo del da�o restando la absorcion
		
		resul = resul - (ABSORCION_NATURAL + (10*defensor.getTA())); 
		//provoca da�o de verdad
		if (resul >= 10){
			porcentaje = (float)resul/100;
			System.out.println(porcentaje);
			da�o_efectivo = (int)(porcentaje*((int)da�o_base_atacante.getValue()+atacante.getBonificadorFuerza()));
		}
		
		da�o.setValue(da�o_efectivo);
		
	}
	
	private void aplicarCombate(){
		//panelDefensor.setValorCampoDa�o((int)da�o.getValue());
		panelDefensor.aplicarDa�o((int)da�o.getValue());
	}

}
