package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.disasters.Disaster;
import model.disasters.Infection;
import model.people.Citizen;
import model.units.Ambulance;
import model.units.DiseaseControlUnit;
import model.units.Evacuator;
import model.units.FireTruck;
import model.units.GasControlUnit;
import model.units.Unit;
import model.units.UnitState;

public class unitPanel extends JPanel {
	public Unit u;
	public ImageIcon imageIcon;
	public Image image;
	public Image newimg;
	public JLabel jl;

	public unitPanel(Unit u1) {
		super();
		u = u1;
		this.setVisible(true);
		JLabel jl1 = new JLabel();
		jl1.setVisible(false);
		setSize(90, 50);
		setLayout(null);
		this.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
		if (u instanceof Ambulance) {
			imageIcon = new ImageIcon("Ambulance.png");
		} else {
			if (u instanceof Evacuator) {
				imageIcon = new ImageIcon("Evacuator.png");
			} else {
				if (u instanceof GasControlUnit) {
					imageIcon = new ImageIcon("GasControlUnit.png");
				} else {
					if (u instanceof FireTruck) {
						imageIcon = new ImageIcon("FireTruck.png");
					} else {
						if (u instanceof DiseaseControlUnit) {
							imageIcon = new ImageIcon("InfectionControl.png");
						}
					}
				}
			}
		}

		// imageIcon = new ImageIcon("citizen.png"); // load the image to a imageIcon
		image = imageIcon.getImage(); // transform it
		newimg = image.getScaledInstance(90, 40, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		imageIcon = new ImageIcon(newimg); // transform it back
		JLabel jl = new JLabel(imageIcon);
		jl.setBounds(0, 0, 90, 40);
		jl.setVisible(true);
		this.add(jl);
		if (u.getState() == UnitState.IDLE) {
			this.setBackground(Color.black);
		} else {
			if (u.getState() == UnitState.RESPONDING) {
				this.setBackground(Color.RED);
			} else {
				if (u.getState() == UnitState.TREATING) {
					this.setBackground(Color.green);
				}
			}
		}

	}

	public static void main(String[] args) {
		/*
		 * JFrame jr = new JFrame("test"); jr.setVisible(true); jr.setSize(500, 500);
		 * jr.setLayout(new BorderLayout()); Unit t = new Ambulance(null, null, 1,
		 * null); t.setState(UnitState.RESPONDING); unitPanel un = new unitPanel(t);
		 * 
		 * jr.add(un); jr.repaint(); jr.revalidate();
		 */

	}
}
