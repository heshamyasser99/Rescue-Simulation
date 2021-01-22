package view;

import java.awt.Color;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.disasters.Disaster;
import model.disasters.Infection;
import model.disasters.Injury;
import model.people.Citizen;
import model.people.CitizenState;

public class citizenPanel extends JPanel {
	public Citizen cit;
	public ImageIcon imageIcon;
	public Image image;
	public Image newimg;
	public JLabel jl1;
	public JLabel jl;

	/*
	 * ImageIcon imageIcon=new ImageIcon("citizen.png"); // load the image to a
	 * imageIcon Image image = imageIcon.getImage(); // transform it Image newimg=
	 * image.getScaledInstance(45, 40, java.awt.Image.SCALE_SMOOTH); // scale it the
	 * smooth way imageIcon = new ImageIcon(newimg); // transform it back
	 * 
	 * 
	 */
	public citizenPanel(Citizen c) {

		super();
		this.setVisible(true);
		jl1 = new JLabel();
		jl1.setVisible(false);
		cit = c;
		setSize(90, 50);
		setLayout(null);
		this.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));

	
		if (cit.getState() == CitizenState.DECEASED) {
			this.setBackground(Color.black);
			imageIcon = new ImageIcon("dead.png"); // load the image to a imageIcon
			image = imageIcon.getImage(); // transform it
			newimg = image.getScaledInstance(45, 40, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
			imageIcon = new ImageIcon(newimg); // transform it back
			jl = new JLabel(imageIcon);
			jl.setBounds(0, 0, 45, 40);
			jl.setVisible(true);
			this.add(jl);
			jl1.setVisible(false);
		} else {
			if (cit.getHp() == 100) {
				this.setBackground(Color.GREEN);
			} else {
				if (cit.getHp() >= 60) {
					this.setBackground(Color.ORANGE);
				} else {
					this.setBackground(Color.RED);
				}
			}
			imageIcon = new ImageIcon("citizen.png"); // load the image to a imageIcon
			image = imageIcon.getImage(); // transform it
			newimg = image.getScaledInstance(45, 40, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
			imageIcon = new ImageIcon(newimg); // transform it back
			jl = new JLabel(imageIcon);
			jl.setBounds(0, 0, 45, 40);
			jl.setVisible(true);
			this.add(jl);
			if (cit.getBloodLoss() > 0) {

				imageIcon = new ImageIcon("Injury.png"); // load the image to a imageIcon
				image = imageIcon.getImage(); // transform it
				newimg = image.getScaledInstance(45, 40, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
				imageIcon = new ImageIcon(newimg); // transform it back
				jl1 = new JLabel(imageIcon);
				jl1.setBounds(45, 0, 45, 40);
				jl1.setVisible(true);
				this.add(jl1);
			} else {

				if (cit.getToxicity() > 0) {
					

					imageIcon = new ImageIcon("Infection.png"); // load the image to a imageIcon
					image = imageIcon.getImage(); // transform it
					newimg = image.getScaledInstance(45, 40, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
					imageIcon = new ImageIcon(newimg); // transform it back
					jl1 = new JLabel(imageIcon);
					jl1.setBounds(45, 0, 45, 40);
					jl1.setVisible(true);
					this.add(jl1);
				} else {
					jl1.setVisible(false);
				}
			}

		
		}
	}

	public static void main(String[] args) {
	}

}
