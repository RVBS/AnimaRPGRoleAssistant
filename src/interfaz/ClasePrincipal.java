package interfaz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EtchedBorder;

import personaje.PNJ;
import personaje.Personaje;

public class ClasePrincipal extends JFrame implements ActionListener{
	
	private JPanel panelIniciativas;
	private JPanel panelCombate;
	private JPanel panelResistencias;
	private JPanel panelCombatientes;
	JTabbedPane panelPersonajes;
	private HashMap<Personaje,PanelIniciativaPersonaje> panelesIniPersonajes;
	private HashMap<Personaje,PanelCombatePersonaje> panelesComPersonajes;
	private ArrayList<Personaje> personajes;
	
	public ClasePrincipal(){
		personajes = new ArrayList<>();
		panelCombatientes = null;
		panelesIniPersonajes = new HashMap<>();
		panelesComPersonajes = new HashMap<>();
		this.setContentPane(getPanelPrincipal());
		this.setSize(750, 500);
	}
	
	private JPanel getPanelPrincipal(){
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add(getToolbar(), BorderLayout.NORTH);
		
		panelPersonajes = new JTabbedPane();
		
		panelIniciativas = getPanelIniciativas();
		panelCombate = getPanelCombate();
		
		panelPersonajes.addTab("Iniciativas", panelIniciativas);
		panelPersonajes.addTab("Combate", panelCombate);
		
		p.add(panelPersonajes);
		//panelPersonajes.addTab("Combate", getPanelCombate());
		//panelPersonajes.addTab("Resistencias", getPanelResistencias());
		
		return p;
	}
	
	private JPanel getPanelIniciativas(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(10,0));
		
		p.add(new PanelIniciativaPersonaje(null));
		
		for (Personaje per : personajes){
			p.add(panelesIniPersonajes.get(per));
		}
		panel.add(p, BorderLayout.CENTER);
		
		panel.add(getPanelBotonesIniciativa(), BorderLayout.SOUTH);
		
		return panel;
	}
	
	private void actualizarPanelIniciativas(){
		JPanel p = (JPanel) panelIniciativas.getComponent(0);
		
		for (Personaje per : personajes){
			p.remove(panelesIniPersonajes.get(per));
			panelesIniPersonajes.put(per,new PanelIniciativaPersonaje(per));
			p.add(panelesIniPersonajes.get(per));
		}
	}
	
	private void actualizarPanelCombate(){
		JPanel p = (JPanel) panelCombate.getComponent(0);
		PanelCombatePersonaje pcp;
		ButtonGroup buttonGroupA = new ButtonGroup();  
		ButtonGroup buttonGroupD = new ButtonGroup();  
		
		for (Personaje per : personajes){
			p.remove(panelesComPersonajes.get(per));
			pcp = new PanelCombatePersonaje(per);
			pcp.getCheckBoxAtq().addActionListener(this);
			pcp.getCheckBoxDef().addActionListener(this);
			buttonGroupA.add(pcp.getCheckBoxAtq());
			buttonGroupD.add(pcp.getCheckBoxDef());
			panelesComPersonajes.put(per,pcp);
			p.add(panelesComPersonajes.get(per));
		}

	}
	
	private JPanel getPanelBotonesIniciativa(){
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout());
		
		JButton b = new JButton();
		b.setIcon(new ImageIcon("src/icons/tirarIniciativa.png"));
		b.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				calcularIniciativas();
				//actualizarDatosCombate();
			}
			
		});
		
		p.add(b);
		
		return p;
	}
	
	private JPanel getPanelBotonesCombate(){
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout());

		JButton b = new JButton();
		b.setToolTipText("Actualizar Datos");
		b.setIcon(new ImageIcon("src/icons/combate.png"));
		b.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				actualizarDatosCombate();
				actualizarPanelIniciativas();
				actualizarPanelCombate();
			}
			
		});
		
		p.add(b);
		
		return p;
	}
	
	private void actualizarDatosCombate(){
		PanelCombatePersonaje pcp;
		for (Personaje p : personajes){
			pcp = panelesComPersonajes.get(p);
			pcp.actualizarDatos();
		}
		
	}
	
	private JPanel getPanelCombate(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(10,0));
		p.setBorder(BorderFactory.createSoftBevelBorder(0));
		
		p.add(new PanelCombatePersonaje(null));
		
		for (Personaje per : personajes){
			p.add(panelesComPersonajes.get(per));
		}
		
		panel.add(p, BorderLayout.CENTER);
		panel.add(getPanelBotonesCombate(),BorderLayout.SOUTH);
		
		return panel;
	}
	
	private JPanel getToolbar(){
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout());
		p.setBorder( BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		
		
		return p;
	}

	
	public boolean añadirPersonaje(){
		PanelCombatePersonaje pcp;
		Personaje pj = new PNJ(122, 5, 6, 3, 4, 5, 6, 7, 3, "Goblin");
		pj.setAtributosArma(60, 85, 60, 0);
		pj.setResistencias(35, 40, 45, 45, 45);
		personajes.add(pj);
		panelesIniPersonajes.put(pj,new PanelIniciativaPersonaje(pj));
		pcp = new PanelCombatePersonaje(pj);		
		panelesComPersonajes.put(pj,pcp);
		
		 actualizarPanelIniciativas();
		 actualizarPanelCombate();
		
		panelPersonajes.validate();
		
		return true;
	}
	
	public boolean eliminarPersonaje(){
		return false;
	}
	
	public void calcularIniciativas(){
		PanelIniciativaPersonaje pip;
		for (Personaje p : personajes){
			pip = panelesIniPersonajes.get(p);
			if (pip.tirarAuto()){
				pip.setValorCampoIniciativa(p.calcularIniciativa());	
			}
			pip.actualizarDatos();
			
		}
		//ORDENAR ARRAYLIST
		Collections.sort(personajes, new Comparator<Personaje>(){
			 
			@Override
			public int compare(Personaje arg0, Personaje arg1) {
				
				if (arg0.getIniciativa() == arg1.getIniciativa())
					return 0;
				
				if (arg0.getIniciativa() < arg1.getIniciativa())
					return 1;
				
				//else
					return -1;
			}
		});
				
		actualizarPanelIniciativas();
		actualizarPanelCombate();
		panelPersonajes.validate();
		
	}
	
	
	public void calcularResistencias(){
		
	}
	
	public static final void main(String[] args){
		ClasePrincipal p = new ClasePrincipal();
		p.setVisible(true);
		p.añadirPersonaje();
		p.añadirPersonaje();
		p.añadirPersonaje();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		actualizarDatosCombate();
		Personaje atq = null;
		Personaje def = null;
		PanelCombatePersonaje pa = null;
		PanelCombatePersonaje pd = null;
		
		for (int i = 0; i < personajes.size(); i++){
			if (panelesComPersonajes.get(personajes.get(i)).getCheckBoxAtq().isSelected()){
				atq = personajes.get(i);
				pa = panelesComPersonajes.get(atq);
			}
			else if (panelesComPersonajes.get(personajes.get(i)).getCheckBoxDef().isSelected()){
				def = personajes.get(i);
				pd = panelesComPersonajes.get(def);
			}
		}
		
		
		if (atq != def && atq != null && def != null){
			//crear panel
			JPanel p = (JPanel) panelCombate.getComponent(1);
			if (panelCombatientes != null){
				p.remove(panelCombatientes);
			}
			panelCombatientes = new PanelCombatientes(atq,pa,def,pd);
			p.add(panelCombatientes);
			panelCombate.validate();
		}
		else{
			JOptionPane.showMessageDialog(null,"Seleccionar un atacante y un defensor");
		}
	
	}


}
