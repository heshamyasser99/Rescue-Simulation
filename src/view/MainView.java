package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import controller.CommandCenter;
import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import model.disasters.Disaster;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.units.Unit;
import model.units.UnitState;
import simulation.Rescuable;

public class MainView extends JFrame
		implements gridPanelListener,citizenPanelListener, unitPanelListener, buildPanelListener {

	public ArrayList<ResidentialBuilding> visibleBuildings;
	public ArrayList<Citizen> visibleCitizens;
	public ArrayList<Unit> emergencyUnits;
	public gridPanel[][] grids;
	public ArrayList<Disaster> ActiveDisasters;
	public gridInfoPanel gridInfo;
	public displayPanel disasterP;
	public displayPanel info;
	public JTextArea curC;
	public displayPanel Log;
	public nextCycleButtonListener ls;

	public Unit UnitClicked;
	public Rescuable rescClicked;
	public gridInfoPanel ResUnits;
	public gridInfoPanel AvailUnits;

	public JTextArea deadC;
	public MainView(nextCycleButtonListener ls,ArrayList<Unit> lol) {

		
		super("PLS A+ TE3EBNA AWY :(");
		this.ls=ls;
		emergencyUnits=lol;
		this.setResizable(true);
		visibleCitizens=new ArrayList<>();
		grids = new gridPanel[10][10];
		UnitClicked = null;
		deadC=new JTextArea("nein");
		this.setSize(1200, 700);
		setLocationRelativeTo(null);
		this.setVisible(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		this.repaint();
		this.revalidate();
		Font font = new Font("SansSerif", Font.BOLD, 20);

		

		JLabel infoT = new JLabel("Building/Citizen/Unit", SwingConstants.CENTER);
		infoT.setFont(font);
		infoT.setBounds(10, 0, 200, 30);
		add(infoT);
		// use info.j (the JTextArea to write text
		info = new displayPanel();
		info.setBounds(10, 30, 200, 170);
		add(info);

		////////////////////////// grid button info
		// use gridInfo.p to add component
		// set it to visible
		// and preferred size to 90,50
		gridInfo = new gridInfoPanel();
		gridInfo.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		gridInfo.setBounds(10, 170 + 30 + 30, 200, 200);
		add(gridInfo);
		repaint();
		revalidate();
		Font font1 = new Font("SansSerif", Font.BOLD, 17);
		JLabel gridInfoT = new JLabel("Grid Units & Rescuables", SwingConstants.CENTER);
		gridInfoT.setFont(font1);
		gridInfoT.setBounds(10, 170 + 30, 200, 30);
		add(gridInfoT);

		//// for active disasters///////////////////////////////////////
		disasterP = new displayPanel();
		disasterP.setBackground(Color.YELLOW);
		disasterP.setBounds(10, 460, 200, 170);
		add(disasterP);

		JLabel disasterPT = new JLabel("Active Disasters", SwingConstants.CENTER);
		disasterPT.setFont(font);
		disasterPT.setBounds(10, 430, 200, 30);
		add(disasterPT);
		//////////////////////////////////////////////////

		/// for available units

		AvailUnits = new gridInfoPanel();
		AvailUnits.setBackground(Color.BLUE);
		AvailUnits.setBounds(980, 30, 200, 170);
		add(AvailUnits);
		JLabel AvailUnitsT = new JLabel("Available Units", SwingConstants.CENTER);
		AvailUnitsT.setFont(font);
		AvailUnitsT.setBounds(980, 0, 200, 30);
		add(AvailUnitsT);

		/// for responding units units
		ResUnits = new gridInfoPanel();
		ResUnits.setBackground(Color.BLUE);
		ResUnits.setBounds(980, 230, 200, 200);
		add(ResUnits);

		JLabel ResUnitsT = new JLabel("Busy Units", SwingConstants.CENTER);
		ResUnitsT.setFont(font);
		ResUnitsT.setBounds(980, 200, 200, 30);
		add(ResUnitsT);

		// unit info
		Log = new displayPanel();
		Log.setBackground(Color.YELLOW);
		Log.setBounds(980, 460, 200, 170);
		add(Log);

		JLabel TreUnitsT = new JLabel("The Log", SwingConstants.CENTER);
		TreUnitsT.setFont(font);
		TreUnitsT.setBounds(980, 430, 200, 30);
		add(TreUnitsT);

		//// number of dead citizens

		deadC = new JTextArea();
		deadC.setBackground(Color.YELLOW);
		deadC.setBounds(220 + 25 + 200 + 50 + 50 + 200, 560 + 20 + 50, 200, 30);
		add(deadC);
		JLabel dead = new JLabel("Casualties", SwingConstants.CENTER);
		dead.setFont(font);
		dead.setBounds(220 + 25 + 200 + 50 + 50 + 200, 560 + 20 - 30 + 50, 200, 30);
		add(dead);

		// next cycle button
		nextCycleButton nextB = new nextCycleButton();
		nextB.addActionListener(ls);
		nextB.setBounds(220 + 25, 560 + 50, 200, 50);
		add(nextB);

		// current cycle display 
		curC = new JTextArea(); 
		curC.setText("0");
		curC.setBackground(Color.YELLOW);
		curC.setBounds(220 + 25 + 200 + 50, 560 + 20 + 50, 200, 30);
		add(curC);

		JLabel cycle = new JLabel("Current Cycle", SwingConstants.CENTER);
		cycle.setFont(font);
		cycle.setBounds(220 + 25 + 200 + 50, 560 + 20 - 30 + 50, 200, 30);
		add(cycle);

		JLabel grid = new JLabel("The Map", SwingConstants.CENTER);
		grid.setFont(font);
		grid.setBounds(500, 0, 200, 30);
		add(grid);
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {

				gridPanel b = new gridPanel();
				b.addMouseListener(this);
				grids[i][j] = b;
				b.setOpaque(true);
				b.setBounds(220 + 75 * i, 30 + 55 * j, 75, 55);
				add(b);
			}
		}

		this.repaint();
		this.revalidate();
		for(Unit un:emergencyUnits) {
			if(un.getState()==UnitState.IDLE) {
				unitPanel unpl=new unitPanel(un);
				unpl.addMouseListener(this);
				unpl.setVisible(true);
				unpl.setPreferredSize(new Dimension(90,50));
				AvailUnits.p.add(unpl);
			}else {
				unitPanel unpl=new unitPanel(un);
				unpl.addMouseListener(this);
				unpl.setVisible(true);
				unpl.setPreferredSize(new Dimension(90,50));
				ResUnits.p.add(unpl);
			}
		}

	}

	public static void main(String[] args) throws Exception {
		CommandCenter cc = new CommandCenter();

	}

	public void updateUntis() {
		AvailUnits.p.removeAll();
		ResUnits.p.removeAll();
		for(Unit un:emergencyUnits) {
			if(un.getState()==UnitState.IDLE) {
				unitPanel unpl=new unitPanel(un);
				unpl.addMouseListener(this);
				unpl.setVisible(true);
				unpl.setPreferredSize(new Dimension(90,50));
				AvailUnits.p.add(unpl);
			}else {
				unitPanel unpl=new unitPanel(un);
				unpl.addMouseListener(this);
				unpl.setVisible(true);
				unpl.setPreferredSize(new Dimension(90,50));
				ResUnits.p.add(unpl);
			}
		}
		
	}
	public void WhoDis() {
		disasterP.j.setText(null);
		for(Disaster d:ActiveDisasters) {
			disasterP.j.append("Cycle: "+d.getStartCycle()+" "+d.toString()+"\n");
		}
	}
	public void iDied() {
		for(int i=0;i<visibleCitizens.size();i++) {
			Citizen c1=visibleCitizens.get(i);
			if(c1.getHp()==0) {
				visibleCitizens.remove(i);
				Log.j.append("\nCycle: "+((CommandCenter)(ls)).getEngine().getCurrentCycle()+" Citizen With ID: "+ c1.getNationalID()+" Died At Location: "+c1.getLocation().toString());
				i--;
			}
		}
		
	}
	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getSource() instanceof gridPanel) {
			info.j.setText(null);
			Dimension d = new Dimension(90, 50);
			gridPanel temp = (gridPanel) arg0.getSource();
			gridInfo.p.removeAll();
			for (Unit u : temp.units) {
				unitPanel ll = new unitPanel(u);
				ll.setVisible(true);
				ll.setPreferredSize(d);
				ll.addMouseListener(this);
				gridInfo.p.add(ll);
			}
			if (temp.building == null) {
				for (Citizen cv : temp.citizens) {
					citizenPanel cc = new citizenPanel(cv);
					cc.setVisible(true);
					cc.setPreferredSize(d);
					cc.addMouseListener(this);
					gridInfo.p.add(cc);
				}

			} else {
				for (Citizen cv : temp.building.getOccupants()) {
					citizenPanel cc = new citizenPanel(cv);
					cc.setVisible(true);
					cc.setPreferredSize(d);
					cc.addMouseListener(this);
					gridInfo.p.add(cc);
				}
				buildPanel pp = new buildPanel(temp.building);
				pp.addMouseListener(this);
				pp.setVisible(true);
				pp.setPreferredSize(d);
				gridInfo.p.add(pp);
			}
			repaint();
			revalidate();
		}

		if (arg0.getSource() instanceof citizenPanel) {
			info.j.setText(null);
			info.j.setText(((citizenPanel) (arg0.getSource())).cit.toString());
			rescClicked = ((citizenPanel) (arg0.getSource())).cit;
			if (UnitClicked != null) {
				try {
					UnitClicked.respond(rescClicked);
					rescClicked = null;
					UnitClicked = null;
					this.updateUntis();
				} catch (IncompatibleTargetException e) {
					JOptionPane.showMessageDialog(this, e.getMessage());
					rescClicked = null;
					UnitClicked = null;

				} catch (CannotTreatException e) {
					JOptionPane.showMessageDialog(this, e.getMessage());
					rescClicked = null;
					UnitClicked = null;
				}
			}

		}

		if (arg0.getSource() instanceof unitPanel) {
			UnitClicked = ((unitPanel) (arg0.getSource())).u;
			info.j.setText(null);
			info.j.setText(((unitPanel) (arg0.getSource())).u.toString());
		}

		if (arg0.getSource() instanceof buildPanel) {
			rescClicked = ((buildPanel) (arg0.getSource())).bil;
			info.j.setText(null);
			info.j.setText(((buildPanel) (arg0.getSource())).bil.toString());
			if (UnitClicked != null) {
				try {
					UnitClicked.respond(rescClicked);
					this.updateUntis();
					rescClicked = null;
					UnitClicked = null;
				} catch (IncompatibleTargetException e) {
					JOptionPane.showMessageDialog(this, e.getMessage());
					rescClicked = null;
					UnitClicked = null;

				} catch (CannotTreatException e) {
					JOptionPane.showMessageDialog(this, e.getMessage());
					rescClicked = null;
					UnitClicked = null;
				}
			}

		}

		repaint();
		revalidate();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		

	}
	

}
