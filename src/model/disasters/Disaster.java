package model.disasters;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CitizenAlreadyDeadException;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Rescuable;
import simulation.Simulatable;

abstract public class Disaster implements Simulatable {
	private int startCycle;
	private Rescuable target;
	private boolean active;

	public Disaster(int startCycle, Rescuable target) {
		this.startCycle = startCycle;
		this.target = target;
		active = false;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getStartCycle() {
		return startCycle;
	}

	public Rescuable getTarget() {
		return target;
	}

	public void strike() throws BuildingAlreadyCollapsedException, CitizenAlreadyDeadException {

		this.setActive(true);
		if (target instanceof Citizen) {
			Citizen c = (Citizen) target;
			if (c.getState() == CitizenState.DECEASED) {
				throw new CitizenAlreadyDeadException(this, "You Are Trying To Strike a Citizen That Is Already Dead");
			} else {
				this.setActive(true);
				c.struckBy(this);
				if (this instanceof Injury) {
					c.setBloodLoss(c.getBloodLoss() + 30);
					
				} else {
					c.setToxicity(c.getToxicity() + 25);
				}
			}
		} else {
			if (target instanceof ResidentialBuilding) {
				ResidentialBuilding b = (ResidentialBuilding) target;
				if (b.getStructuralIntegrity() == 0) {
					throw new BuildingAlreadyCollapsedException(this,
							"You Are Trying To Strike a Building That Is Already Collapsed");
				} else {
					b.struckBy(this);

					if (this instanceof Collapse) {
						b.setFoundationDamage(b.getFoundationDamage() + 10);
					} else {
						if (this instanceof Fire) {
							b.setFireDamage(b.getFireDamage() + 10);
						} else {
							if (this instanceof GasLeak) {
								b.setGasLevel(b.getGasLevel() + 10);
							}
						}
					}
				}
			}
		}
	}

	public void cycleStep() {

	}

}
