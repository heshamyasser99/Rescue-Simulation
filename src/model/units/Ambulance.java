package model.units;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import model.events.WorldListener;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Rescuable;

public class Ambulance extends MedicalUnit {
	public Ambulance(String id, Address location, int stepsPerCycle, WorldListener worldListener) {
		super(id, location, stepsPerCycle, worldListener);
	}

	public void cycleStep() {

		super.cycleStep();

	}
	public String toString() {
		String x="Type: Ambulance\n"
				+"ID: "+this.getUnitID()
				+"\nLocation: "+this.getLocation().toString()
				+"\nSteps Per Cycle: "+this.getStepsPerCycle();
		
		x+="\nState: "+this.getState();
		if(this.getTarget()!=null) {
			x+="\nCurrent Target: \n"+((Citizen)(this.getTarget())).toString();
		}
		return x;
		
	}

	public void respond(Rescuable r) throws CannotTreatException, IncompatibleTargetException {
		super.respond(r);
	}

	public void treat() {

		Citizen c = (Citizen) this.getTarget();
		if (c != null) {
			if (c.getState() == CitizenState.DECEASED) {
				jobsDone();
				return;
			}
			if (c.getDisaster() != null) {
				c.getDisaster().setActive(false);
			}
			if (c.getBloodLoss() == 0) {
				c.setState(CitizenState.RESCUED);
				
				heal();
			} else {
				c.setBloodLoss(c.getBloodLoss() - this.getTreatmentAmount());
				if (c.getBloodLoss() == 0) {
					c.setState(CitizenState.RESCUED);
					heal();
				}
			}
		}

	}

	public void heal() {
		// in MedicalUnit
		super.heal();
	}
}
