package model.units;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import model.disasters.Fire;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Rescuable;

public class FireTruck extends FireUnit {
	public FireTruck(String id, Address location, int stepsPerCycle, WorldListener worldListener) {
		super(id, location, stepsPerCycle, worldListener);

	}
	public void respond(Rescuable r) throws CannotTreatException, IncompatibleTargetException {
		super.respond(r);
	}
	public String toString() {
		String x="Type: FireTruck\n"
				+"ID: "+this.getUnitID()
				+"\nLocation: "+this.getLocation().toString()
				+"\nSteps Per Cycle: "+this.getStepsPerCycle();
		
		x+="\nState: "+this.getState();
		if(this.getTarget()!=null) {
			x+="\nCurrent Target: \n"+((ResidentialBuilding)(this.getTarget())).toString();
		}
		return x;
		
	}

	public void treat() {
		ResidentialBuilding b = (ResidentialBuilding) this.getTarget();
		if (b!= null) {
			if (b.getStructuralIntegrity() == 0||b.getFireDamage()==0) {
				jobsDone();
				return;
			}
			if(b.getDisaster()!=null&& b.getDisaster() instanceof Fire) {
				b.getDisaster().setActive(false);
				
			}
			b.setFireDamage(b.getFireDamage()-10);
			if (b.getStructuralIntegrity() == 0||b.getFireDamage()==0) {
				jobsDone();
				return;
			}
			
			
			
		}
	}
	/*
	 * if (this.getTarget() != null) {
	 * this.getTarget().getDisaster().setActive(false);
	 * 
	 * if (this.getTarget() instanceof ResidentialBuilding) { if
	 * (((ResidentialBuilding) this.getTarget()).getDisaster() instanceof Fire) {
	 * ResidentialBuilding temp = (ResidentialBuilding) this.getTarget(); if
	 * (temp.getStructuralIntegrity() == 0) { jobsDone(); return; }
	 * temp.setFireDamage(temp.getFireDamage() - 10); if (temp.getFireDamage() <= 0)
	 * { //jobsDone(); this.setState(UnitState.IDLE); } }
	 * 
	 * } } }
	 */
}
