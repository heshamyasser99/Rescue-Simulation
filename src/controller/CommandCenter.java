package controller;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

import model.events.SOSListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.units.Unit;
import simulation.Rescuable;
import simulation.Simulator;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import view.MainView;
import view.gridPanel;
import view.nextCycleButtonListener;

public class CommandCenter implements SOSListener, nextCycleButtonListener {
	private Simulator engine;
	private ArrayList<ResidentialBuilding> visibleBuildings;
	private ArrayList<Citizen> visibleCitizens;
	private ArrayList<Unit> emergencyUnits;
	private ArrayList<ResidentialBuilding> builds;
	private ArrayList<Citizen> cits;
	private ArrayList<Unit> units;
	public MainView v;

	public gridPanel[][] grid;

	public Simulator getEngine() {
		return engine;
	}

	public void setEngine(Simulator engine) {
		this.engine = engine;
	}

	public CommandCenter() throws Exception {



		engine = new Simulator(this);
		visibleBuildings = new ArrayList<>();
		visibleCitizens = new ArrayList<>();
		emergencyUnits = new ArrayList<>();
		builds = engine.getBuildings();
		cits = engine.getCitizens();
		units = engine.getEmergencyUnits();
		v = new MainView(this, units);
		v.setVisible(true);
		for (Citizen c1 : engine.getCitizens()) {
			v.visibleCitizens.add(c1);
		}
		grid = v.grids;
		engine.gridsim = grid;
		v.ActiveDisasters = engine.getExecutedDisasters();
		v.emergencyUnits = engine.getEmergencyUnits();
		initialize();

	}

	public void initialize() {
		int x;
		int y;
		for (Citizen c : cits) {
			if (!CitizenInB(c)) {
				x = c.getLocation().getX();
				y = c.getLocation().getY();
				grid[x][y].citizens.add(c);
			}
		}

		for (ResidentialBuilding b : builds) {

			x = b.getLocation().getX();
			y = b.getLocation().getY();
			grid[x][y].setBuilding(b);
		}
		for (Unit u : units) {
			grid[0][0].units.add(u);
		}
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				grid[i][j].updateView();
			}
		}
		v.revalidate();
		v.repaint();
	}

	public boolean CitizenInB(Citizen c) {
		for (ResidentialBuilding b : builds) {
			for (Citizen c1 : b.getOccupants()) {
				if (c == c1) {
					return true;
				}
			}
		}
		return false;
	}

	public void PlaySound() throws Exception {
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("hh.wav").getAbsoluteFile());
		Clip clip = AudioSystem.getClip();
		clip.open(audioInputStream);
		clip.start();

	}

	public void receiveSOSCall(Rescuable r) {

		if (r instanceof Citizen) {
			try {
				PlaySound();

			} catch (Exception e) {

			}

			/*
			 * String bip = "hh.mp3"; Media hit = new Media(new
			 * File(bip).toURI().toString()); MediaPlayer mediaPlayer = new
			 * MediaPlayer(hit);
			 * 
			 * mediaPlayer.setVolume(99.99); mediaPlayer.play();
			 */
			Citizen c = (Citizen) r;
			visibleCitizens.add(c);

		} else {
			ResidentialBuilding b = (ResidentialBuilding) r;
			visibleBuildings.add(b);

		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("next")) {
			game();
		}
		v.repaint();
		v.revalidate();

	}

	public void game() {
		if (engine.checkGameOver()) {
			JOptionPane.showMessageDialog(v, "Game Over. The Number Of Casualties is: " + engine.calculateCasualties());
			System.exit(0);
		} else {
			engine.nextCycle();
			v.WhoDis();
			v.iDied();
			v.gridInfo.p.removeAll();
			v.info.j.setText(null);
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					grid[i][j].updateView();
				}
			}
			v.deadC.setText(engine.calculateCasualties() + "");
			v.curC.setText("Current Cycle: " + engine.getCurrentCycle());
			v.updateUntis();
			v.repaint();
			v.revalidate();
			if (engine.checkGameOver()) {
				JOptionPane.showMessageDialog(v,"Game Over. The Number Of Casualties is: " + engine.calculateCasualties());
				
				System.exit(0);
			}

		}
		v.repaint();
		v.revalidate();
	}

	public static void main(String[] args) throws Exception {

		CommandCenter c = new CommandCenter();

	}

}
