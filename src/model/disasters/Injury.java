package model.disasters;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CitizenAlreadyDeadException;
import model.people.Citizen;

public class Injury extends Disaster {

	public Injury(int cycle, Citizen target) {
		super(cycle, target);
	}
	public String toString() {
		String x;
		if (this.getTarget() != null) {
			x ="Active:("+this.isActive()+")"+" Injury On Citizen With ID: "+((Citizen)(this.getTarget())).getNationalID() + " @ " +this.getTarget().getLocation().toString();
		}else {
			x="Active:("+this.isActive()+")"+" Injury";
		}
		return x;
	}

	@Override
	public void cycleStep() {
		if (getTarget() != null && this.isActive()) {

			Citizen b = (Citizen) this.getTarget();
			b.setBloodLoss(b.getBloodLoss() + 10);
			System.out.println(b.getBloodLoss());
		}
	}
	public void strike() throws BuildingAlreadyCollapsedException, CitizenAlreadyDeadException{
		super.strike();
	}

}
