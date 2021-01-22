package model.disasters;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CitizenAlreadyDeadException;
import model.infrastructure.ResidentialBuilding;

public class Fire extends Disaster {

	public Fire(int cycle, ResidentialBuilding target) {
		super(cycle,target);
		
	}
	public String toString() {
		String x;
		if (this.getTarget() != null) {
			x ="Active:("+this.isActive()+")"+" Fire On Building @ "+this.getTarget().getLocation().toString();
		}else {
			x="Active:("+this.isActive()+")"+" Fire";
		}
		return x;
	}

	public void cycleStep() {
		if (getTarget() != null&&this.isActive()) {

			ResidentialBuilding b = (ResidentialBuilding) this.getTarget();
			b.setFireDamage(b.getFireDamage()+10);
		}
	}
	public void strike() throws BuildingAlreadyCollapsedException, CitizenAlreadyDeadException{
		super.strike();
	}
}
