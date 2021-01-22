package model.disasters;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CitizenAlreadyDeadException;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;

public class Infection extends Disaster {

	public Infection(int cycle, Citizen target) {
		super(cycle,target);
	}
	
	public String toString() {
		String x;
		if (this.getTarget() != null) {
			x ="Active:("+this.isActive()+")"+" Infection On Citizen With ID: "+((Citizen)(this.getTarget())).getNationalID() + " @ " +this.getTarget().getLocation().toString();
		}else {
			x="Active:("+this.isActive()+")"+" Infection";
		}
		return x;
	}

	@Override
	public void cycleStep() {
		if (getTarget() != null&&this.isActive()) {

			Citizen b = (Citizen) this.getTarget();
			b.setToxicity(b.getToxicity()+15);
		}
	}
	public void strike() throws BuildingAlreadyCollapsedException, CitizenAlreadyDeadException{
		super.strike();
	}
}
