package model.infrastructure;

import java.util.ArrayList;
import java.util.Random;

import model.disasters.Collapse;
import model.disasters.Disaster;
import model.disasters.Fire;
import model.disasters.GasLeak;
import model.events.SOSListener;
import model.people.Citizen;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;

public class ResidentialBuilding implements Rescuable, Simulatable {
	private Address location;
	private int structuralIntegrity;
	private int fireDamage;
	private int gasLevel;
	private int foundationDamage;
	private ArrayList<Citizen> occupants;
	private Disaster disaster;
	private SOSListener emergencyService;

	public ResidentialBuilding(Address location, int foundationDamage) {
		this.location = location;
		occupants = new ArrayList<Citizen>();
		structuralIntegrity = 100;
		fireDamage = 0;
		gasLevel = 0;
		this.foundationDamage = foundationDamage;
	}

	public SOSListener getEmergencyService() {
		return emergencyService;
	}

	public void setLocation(Address location) {
		this.location = location;
	}

	public void setOccupants(ArrayList<Citizen> occupants) {
		this.occupants = occupants;
	}

	public void setDisaster(Disaster disaster) {
		this.disaster = disaster;
	}

	public void setEmergencyService(SOSListener emergencyService) {
		this.emergencyService = emergencyService;
	}

	public ResidentialBuilding(Address location) {
		this.location = location;
		occupants = new ArrayList<Citizen>();
		structuralIntegrity = 100;
		fireDamage = 0;
		gasLevel = 0;
		foundationDamage = 0;
	}

	public int getStructuralIntegrity() {
		return structuralIntegrity;
	}

	public void setStructuralIntegrity(int structuralIntegrity) {
		if (structuralIntegrity <= 0) {
			this.structuralIntegrity = 0;
			KillCitizens();
			return;
		}
		this.structuralIntegrity = structuralIntegrity;
	}

	public int getFireDamage() {
		return fireDamage;
	}

	public void setFireDamage(int fireDamage) {
		if (fireDamage >= 100) {
			this.fireDamage = 100;
			return;
		}
		if (fireDamage <= 0) {
			this.fireDamage = 0;
			return;
		}

		this.fireDamage = fireDamage;
	}

	public int getGasLevel() {
		return gasLevel;
	}

	public void setGasLevel(int gasLevel) {
		if (gasLevel >= 100) {
			this.gasLevel = 100;
			KillCitizens();
			return;
		}
		if (gasLevel <= 0) {
			this.gasLevel = 0;
			return;
		}
		this.gasLevel = gasLevel;
	}

	public int getFoundationDamage() {
		return foundationDamage;
	}

	public void setFoundationDamage(int foundationDamage) {
		if (foundationDamage >= 100) {
			this.foundationDamage = 100;
			this.setStructuralIntegrity(0);
		}
		if (foundationDamage <= 0) {
			this.foundationDamage = 0;
			return;
		}

		this.foundationDamage = foundationDamage;
	}

	public Address getLocation() {
		return location;
	}

	public ArrayList<Citizen> getOccupants() {
		return occupants;
	}

	public Disaster getDisaster() {
		return disaster;
	}

	public void struckBy(Disaster d) {
		disaster = d;
		if (this != null && emergencyService != null) {
			emergencyService.receiveSOSCall(this);
		}

	}

	@Override
	public void cycleStep() {
		if (foundationDamage > 0) {
			Random r = new Random();
			int damage = r.nextInt((10 - 5) + 1) + 5;
			setStructuralIntegrity(getStructuralIntegrity() - damage);
		}
		if (getFireDamage() > 0 && getFireDamage() < 30) {
			setStructuralIntegrity(getStructuralIntegrity() - 3);
		}
		if (getFireDamage() >= 30 && getFireDamage() < 70) {
			setStructuralIntegrity(getStructuralIntegrity() - 5);
		}
		if (getFireDamage() >= 70) {
			setStructuralIntegrity(getStructuralIntegrity() - 7);
		}
	}

	public void KillCitizens() {
		for (Citizen c : occupants) {
			c.setHp(0);
		}
	}

	public String toString() {
		String x = "Location: " + "(" + this.location.getX() + "," + this.getLocation().getY() + ")" + "\n"
				+ "Structural Integrity: " + this.structuralIntegrity + "\n" + "Fire Damage: " + this.fireDamage + "\n"
				+ "Gas Level: " + this.gasLevel + "\n" + "Foundation Damage: " + this.foundationDamage + "\n";

		if (this.disaster instanceof Collapse) {
			x += "Suffers Form Collapse\n";

		}
		if (this.disaster instanceof Fire) {
			x += "Suffers Form Fire\n";

		}
		if (this.gasLevel > 0) {
			x += "Suffers Form Gas Leak\n";

		}

		x += "Number Of Occupants: " + this.occupants.size() + "\n" + "Occupants Info: " + "\n" + "-----------" + "\n";
		for (Citizen c : this.occupants) {
			x += c.toString();
			x += "\n" + "*******" + "\n";
		}

		return x;

	}

	public static void main(String[] args) {

	}

}
