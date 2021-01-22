package view;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.disasters.Collapse;
import model.infrastructure.ResidentialBuilding;

public class buildPanel extends JPanel {
	public ResidentialBuilding bil;
	public JLabel build;
	public JLabel disaster;
	public JLabel gas;
	public ImageIcon imageIcon;
	public Image image;
	public Image newimg;

	public buildPanel(ResidentialBuilding bi) {
		super();
		bil = bi;
		this.setLayout(null);
		disaster=new JLabel();
		gas=new JLabel();
		imageIcon = new ImageIcon("building.png"); // load the image to a imageIcon
		image = imageIcon.getImage(); // transform it
		newimg = image.getScaledInstance(50, 40, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		imageIcon = new ImageIcon(newimg); // transform it back
		build = new JLabel(imageIcon);
		build.setBounds(0, 0, 50, 40);
		build.setVisible(true);
		this.add(build);
		if (bil.getStructuralIntegrity() == 100) {
			this.setBackground(Color.GREEN);
		} else {
			if (bil.getStructuralIntegrity() >= 60) {
				this.setBackground(Color.ORANGE);
			}else {
				this.setBackground(Color.RED);
			}
		}
		if (bil.getGasLevel() > 0) {
			imageIcon = new ImageIcon("GasLeak.png"); // load the image to a imageIcon
			image = imageIcon.getImage(); // transform it
			newimg = image.getScaledInstance(40, 20, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
			imageIcon = new ImageIcon(newimg); // transform it back
			gas = new JLabel(imageIcon);
			gas.setBounds(50, 0, 40, 20);
			gas.setVisible(true);
			this.add(gas);
		} else {
			gas.setVisible(false);
		}
		if (bil.getFireDamage() > 0) {
			imageIcon = new ImageIcon("Fire.png"); // load the image to a imageIcon
			image = imageIcon.getImage(); // transform it
			newimg = image.getScaledInstance(40, 20, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
			imageIcon = new ImageIcon(newimg); // transform it back
			disaster = new JLabel(imageIcon);
			disaster.setBounds(50, 20, 40, 20);
			disaster.setVisible(true);
			this.add(disaster);
		} else {

			if (bil.getFoundationDamage()>0) {
				imageIcon = new ImageIcon("Collapse.png"); // load the image to a imageIcon
				image = imageIcon.getImage(); // transform it
				newimg = image.getScaledInstance(40, 20, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
				imageIcon = new ImageIcon(newimg); // transform it back
				disaster = new JLabel(imageIcon);
				disaster.setBounds(50, 20, 40, 20);
				disaster.setVisible(true);
				this.add(disaster);
			} else {
				disaster.setVisible(false);
			}
		}

	}
}
