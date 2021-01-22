package model.disasters;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CitizenAlreadyDeadException;
import model.infrastructure.ResidentialBuilding;

public class Collapse extends Disaster {

	public Collapse(int cycle, ResidentialBuilding target) {
		super(cycle, target);

	}
//this is sparta!
	public String toString() {
		String x;
		if (this.getTarget() != null) {
			x ="Active:("+this.isActive()+")"+" Collapse On Building @ "+this.getTarget().getLocation().toString();
		}else {
			x="Active:("+this.isActive()+")"+" Collapse";
		}
		return x;
	}

	@Override
	public void cycleStep() {
		if (getTarget() != null && this.isActive()) {

			ResidentialBuilding b = (ResidentialBuilding) this.getTarget();
			b.setFoundationDamage(b.getFoundationDamage() + 10);
		}
	}

	public void strike() throws BuildingAlreadyCollapsedException, CitizenAlreadyDeadException {
		super.strike();
	}
}
