package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.units.Unit;

public class gridInfoPanel extends JPanel{
	public JPanel p;
	public JScrollPane sc;
	public gridInfoPanel() {
		super();
		
		p=new JPanel();
		p.setVisible(true);
		p.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
		p.setLayout(new FlowLayout());
		
		/*
		 * Citizen c=new Citizen(null,null,null,8,null); Disaster i=new Injury(4,c);
		 * i.setActive(true); c.setDisaster(i); citizenPanel pp=new citizenPanel(c);
		 * pp.setSize(90,50); p.add(pp);
		 */
		sc=new JScrollPane(p);
		//sc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.setLayout(new BorderLayout());
		this.add(sc,BorderLayout.CENTER);
		
		repaint();
		revalidate();
		

		
	}
	public void addi(Citizen c) {
		p.add(new citizenPanel(c));
	}
	
	public void addCitizens(ArrayList<Citizen> arr) {
		
	}
	public void addUnits(ArrayList<Unit> arr) {
		
	}
	public void addBuilding(ResidentialBuilding b) {
		
	}
}
