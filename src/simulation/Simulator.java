package simulation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CitizenAlreadyDeadException;
import model.disasters.Collapse;
import model.disasters.Disaster;
import model.disasters.Fire;
import model.disasters.GasLeak;
import model.disasters.Infection;
import model.disasters.Injury;
import model.events.SOSListener;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import model.units.Ambulance;
import model.units.DiseaseControlUnit;
import model.units.Evacuator;
import model.units.FireTruck;
import model.units.GasControlUnit;
import model.units.Unit;
import model.units.UnitState;
import view.gridPanel;

public class Simulator implements WorldListener {
	private int currentCycle;
	public gridPanel[][] gridsim;

	public int getCurrentCycle() {
		return currentCycle;
	}

	public ArrayList<ResidentialBuilding> getBuildings() {
		return buildings;
	}

	public void setBuildings(ArrayList<ResidentialBuilding> buildings) {
		this.buildings = buildings;
	}

	public ArrayList<Citizen> getCitizens() {
		return citizens;
	}

	public void setCitizens(ArrayList<Citizen> citizens) {
		this.citizens = citizens;
	}

	public ArrayList<Disaster> getPlannedDisasters() {
		return plannedDisasters;
	}

	public void setPlannedDisasters(ArrayList<Disaster> plannedDisasters) {
		this.plannedDisasters = plannedDisasters;
	}

	public ArrayList<Disaster> getExecutedDisasters() {
		return executedDisasters;
	}

	public void setExecutedDisasters(ArrayList<Disaster> executedDisasters) {
		this.executedDisasters = executedDisasters;
	}

	public Address[][] getWorld() {
		return world;
	}

	public void setWorld(Address[][] world) {
		this.world = world;
	}

	public SOSListener getEmergencyService() {
		return emergencyService;
	}

	public void setCurrentCycle(int currentCycle) {
		this.currentCycle = currentCycle;
	}

	public void setEmergencyUnits(ArrayList<Unit> emergencyUnits) {
		this.emergencyUnits = emergencyUnits;
	}

	private ArrayList<ResidentialBuilding> buildings;
	private ArrayList<Citizen> citizens;
	private ArrayList<Unit> emergencyUnits;
	private ArrayList<Disaster> plannedDisasters;
	private ArrayList<Disaster> executedDisasters;
	private Address[][] world;
	private SOSListener emergencyService;

	public ArrayList<Unit> getEmergencyUnits() {
		return emergencyUnits;
	}

	public Simulator(SOSListener emergencyService) throws Exception {
		this.emergencyService = emergencyService;
		world = new Address[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				world[i][j] = new Address(i, j);
			}
		}

		buildings = new ArrayList<>();
		executedDisasters = new ArrayList<>();
		citizens = new ArrayList<>();
		emergencyUnits = new ArrayList<>();
		plannedDisasters = new ArrayList<>();
		loadUnits("units.csv");
		loadBuildings("buildings.csv");
		loadCitizens("citizens.csv");
		loadDisasters("disasters.csv");

	}

	public gridPanel[][] getGridsim() {
		return gridsim;
	}

	public void setGridsim(gridPanel[][] gridsim) {
		this.gridsim = gridsim;
	}

	private void loadBuildings(String filePath) throws Exception {
		String currentLine = "";
		FileReader fileReader = new FileReader(filePath); // "buildings.csv"
		BufferedReader br = new BufferedReader(fileReader);
		while ((currentLine = br.readLine()) != null) {
			String[] s = currentLine.split(",");
			int x = Integer.parseInt(s[0]);
			int y = Integer.parseInt(s[1]);
			ResidentialBuilding b = new ResidentialBuilding(world[x][y]);
			b.setEmergencyService(this.emergencyService);
			buildings.add(b);

		}
	}

	/*
	 * private void loadDisasters(String filePath) throws Exception { String
	 * currentLine = ""; FileReader fileReader = new FileReader(filePath); //
	 * "buildings.csv" BufferedReader br = new BufferedReader(fileReader); while
	 * ((currentLine = br.readLine()) != null) { String[] s =
	 * currentLine.split(","); String x = s[1]; int y = Integer.parseInt(s[0]); if
	 * (x.equals("INF")) { String n = s[2]; for (Citizen c : citizens) { if
	 * (c.getNationalID().equals(n)) { plannedDisasters.add(new Infection(y, c)); }
	 * } } if (x.equals("INJ")) { String n = s[2]; for (Citizen c : citizens) { if
	 * (c.getNationalID().equals(n)) { plannedDisasters.add(new Injury(y, c)); } }
	 * 
	 * }
	 * 
	 * if (x.equals("FIR")) { int x1 = Integer.parseInt(s[2]); int y1 =
	 * Integer.parseInt(s[3]); for (ResidentialBuilding b : buildings) { if
	 * (b.getLocation().getX() == x1 && b.getLocation().getY() == y1) {
	 * plannedDisasters.add(new Fire(y, b)); } } } if (x.equals("GLK")) {
	 * 
	 * int x1 = Integer.parseInt(s[2]); int y1 = Integer.parseInt(s[3]); for
	 * (ResidentialBuilding b : buildings) { if (b.getLocation().getX() == x1 &&
	 * b.getLocation().getY() == y1) { plannedDisasters.add(new GasLeak(y, b)); } }
	 * } } for(Disaster dm:plannedDisasters) { System.out.println(dm.toString()); }
	 * 
	 * }
	 */
	private Citizen getCitizenByID(String id) {
		for (int i = 0; i < citizens.size(); i++) {
			if (citizens.get(i).getNationalID().equals(id))
				return citizens.get(i);
		}
		return null;
	}
	private ResidentialBuilding getBuildingByLocation(Address location) {
		for (int i = 0; i < buildings.size(); i++) {
			if (buildings.get(i).getLocation().getX() == location.getX()&&buildings.get(i).getLocation().getY()==location.getY())
				return buildings.get(i);
		}
		return null;
	}

	private void loadDisasters(String path) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(path));
		String line = br.readLine();
		while (line != null) {
			String[] info = line.split(",");
			int startCycle = Integer.parseInt(info[0]);
			ResidentialBuilding building = null;
			Citizen citizen = null;
			if (info.length == 3)
				citizen = getCitizenByID(info[2]);
			else {
				int x = Integer.parseInt(info[2]);
				int y = Integer.parseInt(info[3]);
				building = getBuildingByLocation(world[x][y]);
			}
			switch (info[1]) {
			case "INJ":
				plannedDisasters.add(new Injury(startCycle, citizen));
				break;
			case "INF":
				plannedDisasters.add(new Infection(startCycle, citizen));
				break;
			case "FIR":
				plannedDisasters.add(new Fire(startCycle, building));
				break;
			case "GLK":
				plannedDisasters.add(new GasLeak(startCycle, building));
				break;
			}
			line = br.readLine();
		}
		for(Disaster dm:plannedDisasters) {
			System.out.println(dm);
		}
	
		br.close();
	}

	private void loadCitizens(String filePath) throws Exception {
		String currentLine = "";
		FileReader fileReader = new FileReader(filePath);
		BufferedReader br = new BufferedReader(fileReader);
		while ((currentLine = br.readLine()) != null) {
			String[] s = currentLine.split(",");
			int x = Integer.parseInt(s[0]);
			int y = Integer.parseInt(s[1]);
			int a = Integer.parseInt(s[4]);
			Citizen c = new Citizen(world[x][y], s[2], s[3], a, this);

			c.setEmergencyService(emergencyService);
			citizens.add(c);

			for (ResidentialBuilding b : buildings) {
				if ((b.getLocation().getX() == x) && (b.getLocation().getY() == y)) {
					b.getOccupants().add(c);
				}
			}

		}
	}

	private void loadUnits(String filePath) throws Exception {
		String currentLine = "";
		FileReader fileReader = new FileReader(filePath);
		BufferedReader br = new BufferedReader(fileReader);
		while ((currentLine = br.readLine()) != null) {
			String[] s = currentLine.split(",");
			String x = s[0];
			if (x.equals("AMB")) {

				Unit t = new Ambulance(s[1], world[0][0], Integer.parseInt(s[2]), this);
				emergencyUnits.add(t);
			} else {
				if (x.equals("DCU")) {
					Unit t = new DiseaseControlUnit(s[1], world[0][0], Integer.parseInt(s[2]), this);
					emergencyUnits.add(t);
				} else {
					if (x.equals("EVC")) {
						Unit t = new Evacuator(s[1], world[0][0], Integer.parseInt(s[2]), this, Integer.parseInt(s[3]));
						emergencyUnits.add(t);
					} else {
						if (x.equals("FTK")) {
							Unit t = new FireTruck(s[1], world[0][0], Integer.parseInt(s[2]), this);
							emergencyUnits.add(t);
						} else {
							Unit t = new GasControlUnit(s[1], world[0][0], Integer.parseInt(s[2]), this);
							emergencyUnits.add(t);
						}
					}
				}
			}

		}

	}

	public void setEmergencyService(SOSListener emergencyService) {
		this.emergencyService = emergencyService;
	}

	@Override
	public void assignAddress(Simulatable sim, int x, int y) {
		if (sim instanceof Citizen) {
			Citizen c = (Citizen) sim;
			int x1 = c.getLocation().getX();
			int y1 = c.getLocation().getY();
			if (gridsim[x1][y1].building == null) {
				gridsim[x1][y1].citizens.remove(c);
			}
			if (gridsim[x][y].building == null) {
				gridsim[x][y].citizens.add(c);
			}
			c.setLocation(world[x][y]);
			return;
		}
		Unit u = (Unit) sim;
		int x1 = u.getLocation().getX();
		int y1 = u.getLocation().getY();
		gridsim[x1][y1].units.remove(u);
		gridsim[x1][y1].updateView();
		gridsim[x][y].units.add(u);
		

		u.setLocation(world[x][y]);

	}

	public boolean checkGameOver() {

		if (plannedDisasters.size() != 0)
			return false;

		for (int i = 0; i < executedDisasters.size(); i++) {
			if (executedDisasters.get(i).isActive()) {

				Disaster d = executedDisasters.get(i);
				Rescuable r = d.getTarget();
				if (r instanceof Citizen) {
					Citizen c = (Citizen) r;
					if (c.getState() != CitizenState.DECEASED)
						return false;
				} else {
					
					

					ResidentialBuilding b = (ResidentialBuilding) r;
					if (b.getStructuralIntegrity() != 0) {
						return false;
					}
					
				}
				
			}

			

		}
				
			

		for (int  i = 0; i < emergencyUnits.size(); i++) {
			if (emergencyUnits.get(i).getState() != UnitState.IDLE) {
				return false;
			
			}
		}

		return true;
	}

	/*
	 * public boolean checkGameOver() { return (activeDisaster()) && (checkUnits())
	 * && (plannedDisasters.size() == 0); }
	 */
	public boolean activeDisaster() {

		for (int i = 0; i < executedDisasters.size(); i++) {
			if (executedDisasters.get(i).isActive()) {

				Disaster d = executedDisasters.get(i);
				Rescuable r = d.getTarget();
				if (r instanceof Citizen) {
					Citizen c = (Citizen) r;
					if (c.getState() != CitizenState.DECEASED)
						return false;
				} else {

					ResidentialBuilding b = (ResidentialBuilding) r;
					if (b.getStructuralIntegrity() != 0)
						return false;
				}

			}

		}

		return true;
	}

	public boolean checkUnits() {
		for (Unit u : emergencyUnits) {
			if (u.getState() != UnitState.IDLE) {
				return false;
			}
		}
		return true;
	}

	public int calculateCasualties() {
		int count = 0;
		for (Citizen c : citizens) {
			if (c.getState() == CitizenState.DECEASED) {
				count++;
			}
		}
		return count;
	}

	public void nextCycle() {

		currentCycle++;

		for (int i = 0; i < plannedDisasters.size(); i++) {
			Disaster d = plannedDisasters.get(i);
			if (d.getStartCycle() == currentCycle) {
				plannedDisasters.remove(d);
				i--;
				if (d instanceof Fire)
					handleFire(d);
				else if (d instanceof GasLeak)
					handleGas(d);
				else {
					try {
						d.strike();
						executedDisasters.add(d);
					} catch (BuildingAlreadyCollapsedException | CitizenAlreadyDeadException e) {
						System.out.println(e.getMessage());
					}

				}
			}
		}

		for (int i = 0; i < buildings.size(); i++) {
			ResidentialBuilding b = buildings.get(i);
			if (b.getFireDamage() >= 100) {
				b.getDisaster().setActive(false);
				b.setFireDamage(0);
				Collapse c = new Collapse(currentCycle, b);
				try {
					c.strike();
					executedDisasters.add(c);
				} catch (BuildingAlreadyCollapsedException | CitizenAlreadyDeadException e) {
					System.out.println(e.getMessage());
				}

			}
		}

		for (int i = 0; i < emergencyUnits.size(); i++) {
			emergencyUnits.get(i).cycleStep();
		}

		for (int i = 0; i < executedDisasters.size(); i++) {
			Disaster d = executedDisasters.get(i);
			if (d.getStartCycle() < currentCycle && d.isActive())
				d.cycleStep();
		}

		for (int i = 0; i < buildings.size(); i++) {
			buildings.get(i).cycleStep();
		}

		for (int i = 0; i < citizens.size(); i++) {
			citizens.get(i).cycleStep();
		}

	}

	private void handleGas(Disaster d) {
		ResidentialBuilding b = (ResidentialBuilding) d.getTarget();
		if (b.getFireDamage() != 0) {
			b.setFireDamage(0);
			Collapse c = new Collapse(currentCycle, b);
			try {
				c.strike();
				executedDisasters.add(c);
			} catch (BuildingAlreadyCollapsedException | CitizenAlreadyDeadException e) {
				System.out.println(e.getMessage());
			}

		} else {
			try {
				d.strike();
				executedDisasters.add(d);
			} catch (BuildingAlreadyCollapsedException | CitizenAlreadyDeadException e) {
				System.out.println(e.getMessage());
			}

		}
	}

	private void handleFire(Disaster d) {
		ResidentialBuilding b = (ResidentialBuilding) d.getTarget();
		if (b.getGasLevel() == 0) {
			try {
				d.strike();
				executedDisasters.add(d);
			} catch (BuildingAlreadyCollapsedException e) {
				System.out.println(e.getMessage());
			} catch (CitizenAlreadyDeadException e) {
				System.out.println(e.getMessage());
			}

		} else if (b.getGasLevel() < 70) {
			b.setFireDamage(0);
			Collapse c = new Collapse(currentCycle, b);
			try {
				c.strike();
				executedDisasters.add(c);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else
			b.setStructuralIntegrity(0);

	}

	/*
	 * currentCycle = currentCycle + 1; //////////// PLANNED
	 * DISASTERS/////////////////////////////// for (int i = 0; i <
	 * plannedDisasters.size(); i++) {
	 * 
	 * Disaster d = plannedDisasters.get(i); if (d.getStartCycle() == currentCycle)
	 * { plannedDisasters.remove(i); if (d.getTarget() instanceof
	 * ResidentialBuilding) { ResidentialBuilding b = (ResidentialBuilding)
	 * d.getTarget(); if (b.getDisaster() != null && b.getDisaster() instanceof
	 * GasLeak && d instanceof Fire) { if (b.getGasLevel() == 0) { i--; try {
	 * d.strike();
	 * 
	 * executedDisasters.add(d); }catch(DisasterException e1) {
	 * System.out.println(e1.getMessage() + "111"); }
	 * 
	 * 
	 * } else { if (b.getGasLevel() > 0 && b.getGasLevel() < 70) { Collapse col =
	 * new Collapse(currentCycle, b); i--; b.setFireDamage(0); try { col.strike();
	 * 
	 * executedDisasters.add(col); }catch(DisasterException e2) {
	 * System.out.println(e2.getMessage()+"2222222222"); }
	 * 
	 * 
	 * } else { if (b.getGasLevel() >= 70) { b.setStructuralIntegrity(0); i--; } } }
	 * } else { if (b.getDisaster() != null && b.getDisaster() instanceof Fire && d
	 * instanceof GasLeak) { Collapse col = new Collapse(currentCycle, b);
	 * b.setFireDamage(0); i--; try { col.strike();
	 * 
	 * executedDisasters.add(col); }catch(DisasterException e2) {
	 * System.out.println(e2.getMessage()+"3333333333"); } } else { i--; try {
	 * 
	 * d.strike(); executedDisasters.add(d); }catch(DisasterException e3) {
	 * System.out.println(e3.getMessage()+"444444"); } }
	 * 
	 * }
	 * 
	 * } ///// END OF RESIDENTIALBUILDING else { i--; try {
	 * 
	 * d.strike(); executedDisasters.add(d); }catch(DisasterException e4) {
	 * System.out.println(e4.getMessage()+"5555555"); } }
	 * 
	 * 
	 * } else { i--; try { d.strike();
	 * 
	 * }catch(DisasterException e5) { System.out.println(e5.getMessage()+"6666666");
	 * }
	 * 
	 * }
	 * 
	 * 
	 * } ////////////////////////////////////////////////////////// for (int i = 0;
	 * i < buildings.size(); i++) { ResidentialBuilding bl = buildings.get(i); if
	 * (bl.getFireDamage() >= 100) { Collapse col = new Collapse(currentCycle, bl);
	 * bl.setFireDamage(0); try { col.strike(); executedDisasters.add(col); } catch
	 * (DisasterException e7) { System.out.println(e7.getMessage()+"7777777"); }
	 * 
	 * } }
	 * 
	 * //////////////// UNITS ////////////////// for (int i = 0; i <
	 * emergencyUnits.size(); i++) { Unit u = emergencyUnits.get(i); u.cycleStep();
	 * } ///////////////////// DISASTERS //////////// /* for (int i = 0; i <
	 * executedDisasters.size(); i++) { Disaster ds = executedDisasters.get(i); if
	 * (ds.getStartCycle() != currentCycle && ds.isActive()) { ds.cycleStep(); } }
	 * 
	 * 
	 * for (int i = 0; i < executedDisasters.size(); i++) { Disaster d =
	 * executedDisasters.get(i); if (d.getStartCycle() < currentCycle &&
	 * d.isActive()) d.cycleStep(); } ///////////////////// CITIZENS &
	 * BUILDINGS///////////////// for (int i = 0; i < citizens.size(); i++) {
	 * Citizen c = citizens.get(i); System.out.println("citizen cycle step");
	 * c.cycleStep();
	 * 
	 * } for (int i = 0; i < buildings.size(); i++) { ResidentialBuilding bn =
	 * buildings.get(i); bn.cycleStep();
	 * 
	 * } //////////////////////////////////////////////////
	 */

	public static void main(String[] args) throws Exception {
		//Simulator s=new Simulator(null);
	}

	public ResidentialBuilding CitizenInBuilding(Citizen c) {
		for (ResidentialBuilding b : buildings) {
			for (Citizen c1 : b.getOccupants()) {
				if (c1 == c) {
					return b;
				}
			}
		}
		return null;
	}

}
