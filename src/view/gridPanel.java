package view;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.disasters.Collapse;
import model.disasters.GasLeak;
import model.disasters.Infection;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import model.units.Ambulance;
import model.units.Unit;

public class gridPanel extends JPanel {
	public ResidentialBuilding building;
	public ArrayList<Citizen> citizens;
	public ArrayList<Unit> units;

	public JLabel build;
	public JLabel disaster;
	public JLabel gas;
	public JLabel unt;

	public JLabel cit;
	public JLabel citdis;
	public ImageIcon imageIcon;
	public Image image;
	public Image newimg;

	public gridPanel() {
		super();

		disaster = new JLabel();
		build = new JLabel();
		cit = new JLabel();
		unt = new JLabel();
		citdis = new JLabel();
		gas = new JLabel();
		this.setLayout(null);
		citdis = new JLabel();
		citdis.setBounds(0, 20, 20, 20);
		citdis.setVisible(false);
		this.add(citdis);
		unt=new JLabel();
		unt.setBounds(75 - 30 - 20, 20, 20, 20);
		unt.setVisible(false);
		this.add(unt);
		disaster = new JLabel();
		disaster.setBounds(75 - 30 - 20, 0, 20, 20);
		disaster.setVisible(false);
		this.add(disaster);
		cit = new JLabel();
		cit.setBounds(0, 0, 25, 25);
		cit.setVisible(false);
		this.add(cit);

		this.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
		citizens = new ArrayList<>();
		units = new ArrayList<>();

	}

	public void updateView() {
		if (building == null) {
			if (citizens.size() == 0) {
				cit.setVisible(false);
				citdis.setVisible(false);
				remove(citdis);
			} else {
				remove(cit);
				imageIcon = new ImageIcon("citizens.png");
				image = imageIcon.getImage();
				newimg = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
				imageIcon = new ImageIcon(newimg);
				cit = new JLabel(imageIcon);
				cit.setBounds(0, 0, 25, 25);
				cit.setVisible(true);
				this.add(cit);
				Boolean is = false;
				for (Citizen cv : citizens) {
					if (cv.getHp() != 0 && (cv.getBloodLoss() != 0 || cv.getToxicity() != 0)) {
						is = true;
					}
				}
				if (is) {
					this.remove(citdis);
					imageIcon = new ImageIcon("cross.png"); // load the image to a imageIcon
					image = imageIcon.getImage(); // transform it
					newimg = image.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
					imageIcon = new ImageIcon(newimg); // transform it back
					citdis = new JLabel(imageIcon);
					citdis.setBounds(0, 20, 20, 20);
					citdis.setVisible(true);
					this.add(citdis);
					repaint();
					revalidate();

				} else {
					citdis.setVisible(false);
					remove(citdis);
				}
			}
		} else {
			if (building.getStructuralIntegrity() == 0) {
				this.remove(build);
				imageIcon = new ImageIcon("coll.png"); // load the image to a imageIcon
				image = imageIcon.getImage(); // transform it
				newimg = image.getScaledInstance(30, 55, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
				imageIcon = new ImageIcon(newimg); // transform it back
				build = new JLabel(imageIcon);
				build.setBounds(75 - 30, 0, 30, 55);
				build.setVisible(true);
				this.add(build);
				cit.setVisible(false);
				disaster.setVisible(false);
				remove(disaster);

			} else {
				this.remove(build);
				imageIcon = new ImageIcon("Building.png"); // load the image to a imageIcon
				image = imageIcon.getImage(); // transform it
				newimg = image.getScaledInstance(30, 55, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
				imageIcon = new ImageIcon(newimg); // transform it back
				build = new JLabel(imageIcon);
				build.setBounds(75 - 30, 0, 30, 55);
				build.setVisible(true);
				this.add(build);
				if (building.getFireDamage() > 0 || building.getGasLevel() > 0 || building.getFoundationDamage() > 0) {
					remove(disaster);
					imageIcon = new ImageIcon("help.png"); // load the image to a imageIcon
					image = imageIcon.getImage(); // transform it
					newimg = image.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
					imageIcon = new ImageIcon(newimg); // transform it back
					disaster = new JLabel(imageIcon);
					disaster.setBounds(75 - 30 - 20, 0, 20, 20);
					disaster.setVisible(true);
					
					add(disaster);
				}else {
					remove(disaster);
					disaster.setVisible(false);
				}
				if (building.getOccupants().size() == 0) {
					cit.setVisible(false);
					citdis.setVisible(false);
					remove(citdis);
				} else {
					remove(cit);
					imageIcon = new ImageIcon("citizens.png");
					image = imageIcon.getImage();
					newimg = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
					imageIcon = new ImageIcon(newimg);
					cit = new JLabel(imageIcon);
					cit.setBounds(0, 0, 25, 25);
					cit.setVisible(true);
					this.add(cit);
					Boolean tl = false;
					for (Citizen cv : building.getOccupants()) {
						if (cv.getHp() != 0 && (cv.getBloodLoss() != 0 || cv.getToxicity() != 0)) {
							tl = true;
						}
					}
					
					if (tl) {
						this.remove(citdis);
						imageIcon = new ImageIcon("cross.png"); // load the image to a imageIcon
						image = imageIcon.getImage(); // transform it
						newimg = image.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH); // scale it the smooth
																								// way
						imageIcon = new ImageIcon(newimg); // transform it back
						citdis = new JLabel(imageIcon);
						citdis.setBounds(0, 30, 20, 20);
						citdis.setVisible(true);
						this.add(citdis);
					} else {
						remove(citdis);
						citdis.setVisible(false);
						
						repaint();
						revalidate();
					}
				}
			}

		}
		if (units.size() == 0) {
			unt.setVisible(false);
			this.remove(unt);
			
		} else {
			imageIcon = new ImageIcon("Siren.png"); // load the image to a imageIcon
			image = imageIcon.getImage(); // transform it
			newimg = image.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
			imageIcon = new ImageIcon(newimg); // transform it back
			unt = new JLabel(imageIcon);
			unt.setBounds(75 - 30 - 20, 20, 20, 20);
			unt.setVisible(true);
			this.remove(unt);
			this.add(unt);
		
		}
		revalidate();
		repaint();
	}

	public boolean checkCitizenTroubled() {
		if (building == null) {
			for (Citizen c : citizens) {
				if (c.getState() == CitizenState.IN_TROUBLE) {
					return true;
				}
			}
		} else {
			for (Citizen c : building.getOccupants()) {
				if (c.getState() == CitizenState.IN_TROUBLE) {
					return true;
				}
			}
		}
		return false;
	}

	public void addCitizen(Citizen c) {
		citizens.add(c);
	}

	public void addUnit(Unit u) {
		units.add(u);
	}

	public ResidentialBuilding getBuilding() {
		return building;
	}

	public void setBuilding(ResidentialBuilding building) {
		this.building = building;
	}

	public ArrayList<Citizen> getCitizens() {
		return citizens;
	}

	public void setCitizens(ArrayList<Citizen> citizens) {
		this.citizens = citizens;
	}

	public ArrayList<Unit> getUnits() {
		return units;
	}

	public void setUnits(ArrayList<Unit> units) {
		this.units = units;
	}

	/*
	 * public void update() {
	 * 
	 * JTextArea a=new JTextArea("lol"); a.setBounds(5,5,10,10); this.add(a);
	 * 
	 * ImageIcon mg = new ImageIcon("building.png"); // load the image to a
	 * imageIcon Image image = mg.getImage(); // transform it Image newimg =
	 * image.getScaledInstance(50, 53, java.awt.Image.SCALE_SMOOTH); // scale it the
	 * smooth way mg = new ImageIcon(newimg); JLabel build = new JLabel(mg);
	 * build.setBounds(0, 0, 50, 53); this.add(build); ImageIcon img1 = new
	 * ImageIcon("Fire.png"); ImageIcon mg1 = new ImageIcon("Fire.png"); // load the
	 * image to a imageIcon Image image1 = mg1.getImage(); // transform it Image
	 * newimg1 = image1.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH); //
	 * scale it the smooth way mg1 = new ImageIcon(newimg1); JLabel dis = new
	 * JLabel(mg1); dis.setBounds(50, 0, 20, 20); this.add(dis); }
	 */
	/*
	 * public void addR(Rescuable r) { if (r instanceof Citizen) { Citizen c =
	 * (Citizen) r;
	 * 
	 * ImageIcon imageIcon = new ImageIcon("download.png"); image =
	 * imageIcon.getImage(); Image newimg = image.getScaledInstance(25, 25,
	 * java.awt.Image.SCALE_SMOOTH); imageIcon = new ImageIcon(newimg); cit = new
	 * JLabel(imageIcon); cit.setBounds(0, 0, 25, 25); cit.setVisible(true);
	 * this.add(cit); if (c.getDisaster() == null) { cit.setVisible(false);
	 * citdis.setVisible(false);
	 * 
	 * } else { if (c.getDisaster() instanceof Injury) { imageIcon = new
	 * ImageIcon("Injury.png"); image = imageIcon.getImage(); newimg =
	 * image.getScaledInstance(25, 20, java.awt.Image.SCALE_SMOOTH); // scale it the
	 * smooth way imageIcon = new ImageIcon(newimg); citdis = new JLabel(imageIcon);
	 * citdis.setBounds(0, 25, 25, 20); citdis.setVisible(true); this.add(citdis); }
	 * else { if (c.getDisaster() instanceof Infection) { imageIcon = new
	 * ImageIcon("Infection.png"); image = imageIcon.getImage(); newimg =
	 * image.getScaledInstance(25, 20, java.awt.Image.SCALE_SMOOTH); // way
	 * imageIcon = new ImageIcon(newimg); citdis = new JLabel(imageIcon);
	 * citdis.setBounds(0, 25, 25, 20); citdis.setVisible(true); this.add(citdis); }
	 * } } } if (r instanceof ResidentialBuilding) { ResidentialBuilding b =
	 * (ResidentialBuilding) r; if (b.getGasLevel() > 0) { ImageIcon imageIcon = new
	 * ImageIcon("GasLeak.png"); // load the image to a imageIcon Image image =
	 * imageIcon.getImage(); // transform it Image newimg =
	 * image.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH); // scale it the
	 * imageIcon = new ImageIcon(newimg); // transform it back gas = new
	 * JLabel(imageIcon); gas.setBounds(75 - 30 - 20, 20, 20, 20); this.add(gas);
	 * 
	 * } else { gas.setVisible(false); } ImageIcon imageIcon = new
	 * ImageIcon("building.png"); // load the image to a imageIcon Image image =
	 * imageIcon.getImage(); Image newimg = image.getScaledInstance(30, 55,
	 * java.awt.Image.SCALE_SMOOTH); imageIcon = new ImageIcon(newimg); build = new
	 * JLabel(imageIcon); build.setBounds(75 - 30, 0, 30, 55); this.add(build); if
	 * (b.getDisaster() == null) { disaster.setVisible(false); } else { if
	 * (b.getDisaster() instanceof Collapse && b.getDisaster().isActive()) {
	 * imageIcon = new ImageIcon("Collapse.png"); // load the image to a imageIcon
	 * image = imageIcon.getImage(); // transform it newimg =
	 * image.getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH); // scale it t
	 * smooth way imageIcon = new ImageIcon(newimg); disaster = new
	 * JLabel(imageIcon); disaster.setBounds(75 - 20 - 30, 0, 15, 15);
	 * disaster.setVisible(true); this.add(disaster); } else { if (b.getDisaster()
	 * instanceof Fire && b.getDisaster().isActive()) { imageIcon = new
	 * ImageIcon("Fire.png"); // load the image to a imageIcon image =
	 * imageIcon.getImage(); // transform it newimg = image.getScaledInstance(15,
	 * 15, java.awt.Image.SCALE_SMOOTH); // scale it the imageIcon = new
	 * ImageIcon(newimg); disaster = new JLabel(imageIcon); disaster.setBounds(75 -
	 * 20 - 30, 0, 15, 15); disaster.setVisible(true); this.add(disaster); }
	 * 
	 * } }
	 * 
	 * } this.repaint(); this.revalidate(); }
	 */
	public static void main(String[] args) {

		/*
		 * JFrame f = new JFrame("test"); f.setSize(175, 155); f.setVisible(true);
		 * f.setLayout(null); gridPanel g = new gridPanel(); g.setVisible(true);
		 * g.setBounds(0, 0, 75, 55); f.add(g);
		 * 
		 * ResidentialBuilding b = new ResidentialBuilding(null);
		 * 
		 * b.setFireDamage(1); g.updateView(); g.setBuilding(b);
		 * 
		 * GasLeak gl = new GasLeak(5, b); Collapse cl = new Collapse(4, b);
		 * 
		 * Citizen c = new Citizen(null, null, null, 8, null); Infection in = new
		 * Infection(4, c); c.setDisaster(in); c.setBloodLoss(1); in.setActive(true);
		 * b.getOccupants().add(c); Unit u = new Ambulance(null, null, 8, null);
		 * g.units.add(u); g.updateView(); g.units.remove(u); g.updateView();
		 * g.repaint(); g.revalidate();
		 */

	}

}
