package model.disasters;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CitizenAlreadyDeadException;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;

public class GasLeak extends Disaster {

	public GasLeak(int cycle, ResidentialBuilding target) {
		super(cycle, target);
	}

	public String toString() {
		String x;
		if (this.getTarget() != null) {
			x = "Active:(" + this.isActive() + ")" + " GasLeak On Building @ "
					+ this.getTarget().getLocation().toString();
		} else {
			x = "Active:(" + this.isActive() + ")" + " GasLeak";
		}
		return x;
	}

	@Override
	public void cycleStep() {
		if (getTarget() != null && this.isActive()) {

			ResidentialBuilding b = (ResidentialBuilding) this.getTarget();
			b.setGasLevel(b.getGasLevel() + 15);
			
		}
	}

	public void strike() throws BuildingAlreadyCollapsedException, CitizenAlreadyDeadException {
		super.strike();
	}
}
