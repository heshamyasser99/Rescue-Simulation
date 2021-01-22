package model.people;

import controller.CommandCenter;
import model.disasters.Disaster;
import model.disasters.Injury;
import model.events.SOSListener;
import model.events.WorldListener;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;


public class Citizen implements Rescuable, Simulatable {
	private CitizenState state;
	private Disaster disaster;
	private Address location;
	private String nationalID;
	private String name;
	private int age;
	private int hp;
	private int bloodLoss;
	private int toxicity;
	private SOSListener emergencyService;
	private WorldListener worldListener;
	

	public WorldListener getWorldListener() {
		return worldListener;
	}
	

	public void setWorldListener(WorldListener worldListener) {
		this.worldListener = worldListener;
	}

	public void setEmergencyService(SOSListener emergencyService) {
		this.emergencyService = emergencyService;
	}

	public Citizen(Address location, String nationalID, String name, int age, WorldListener worldListener) {
		state = CitizenState.SAFE;
		this.location = location;
		this.nationalID = nationalID;
		this.name = name;
		this.age = age;
		hp = 100;
		bloodLoss = 0;
		toxicity = 0;
		this.worldListener = worldListener;

	}

	public SOSListener getEmergencyService() {
		return emergencyService;
	}

	public void setDisaster(Disaster disaster) {
		this.disaster = disaster;
	}

	public void setNationalID(String nationalID) {
		this.nationalID = nationalID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public CitizenState getState() {
		return state;
	}

	public void setState(CitizenState state) {
		this.state = state;
	}

	public Address getLocation() {
		return location;
	}

	public void setLocation(Address location) {
		this.location = location;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		if (hp >= 100) {
			this.hp = 100;

		} else {
			if (hp <= 0) {
				this.hp = 0;
				this.setState(CitizenState.DECEASED);
				((CommandCenter)emergencyService).v.updateUntis();

			} else {
				this.hp = hp;
			}
		}

	}

	public int getBloodLoss() {
		return bloodLoss;
	}

	public void setBloodLoss(int bloodLoss) {
		if (bloodLoss >= 100) {
			this.bloodLoss = 100;
			setHp(0);
			return;

		}
		if (bloodLoss <= 0) {
			this.bloodLoss = 0;
			return;
		}
		this.bloodLoss = bloodLoss;
	}

	public int getToxicity() {
		return toxicity;
	}

	public void setToxicity(int toxicity) {
		if (toxicity >= 100) {
			this.toxicity = 100;
			setHp(0);
			return;
		}
		if (toxicity <= 0) {
			this.toxicity = 0;
			return;
		}
		this.toxicity = toxicity;

	}

	public Disaster getDisaster() {
		return disaster;
	}

	public String getNationalID() {
		return nationalID;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public void struckBy(Disaster d) {
		this.disaster = d;
		setState(CitizenState.IN_TROUBLE);

		if (emergencyService != null) {
			emergencyService.receiveSOSCall(this);
		}
	}

	public void cycleStep() {
		if (getBloodLoss() > 0 && getBloodLoss() < 30) {
			setHp(getHp() - 5);
			return;
		}
		if (getBloodLoss() >= 30 && getBloodLoss() < 70) {
			setHp(getHp() - 10);
			return;
		}
		if (getBloodLoss() >= 70) {
			setHp(getHp() - 15);
			return;
		}
		if (getToxicity() > 0 && getToxicity() < 30) {
			setHp(getHp() - 5);
			return;
		}
		if (getToxicity() >= 30 && getToxicity() < 70) {
			setHp(getHp() - 10);
			return;
		}
		if (getToxicity() >= 70) {
			setHp(getHp() - 15);
			return;
		}

	}

	public String toString() {
		String x = "Name: " + this.name + "\n" + "Age: " + this.age + "\n" + "ID: " + this.nationalID + "\n" + "HP: "
				+ this.hp + "\n" + "Blood Loss: " + this.bloodLoss + "\n" + "Toxicity: " + this.toxicity + "\n"
				+ "State: " + this.state + "\n";
		x+="Location: "+this.getLocation().toString()+"\n";
		if (this.disaster != null) {
			if (this.disaster.isActive()) {
				if (disaster instanceof Injury) {
					x += "Suffers From Injury";
				} else {
					x += "Suffers From Infection";
				}
			}
		}
		return x;
	}

	public static void main(String[] args) {
	
	}

}
