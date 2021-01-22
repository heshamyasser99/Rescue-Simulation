package model.units;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import model.disasters.Infection;
import model.events.WorldListener;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Rescuable;

public class DiseaseControlUnit extends MedicalUnit {
	public DiseaseControlUnit(String id, Address location, int stepsPerCycle, WorldListener worldListener) {
		super(id, location, stepsPerCycle, worldListener);
	}

	public void cycleStep() {
		super.cycleStep();
	}
	public String toString() {
		String x="Type: Disease Control Unit\n"
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
			if (c.getToxicity() == 0) {
				c.setState(CitizenState.RESCUED);
				// heal needs jobsDone
				heal();
			} else {
				c.setToxicity(c.getToxicity() - this.getTreatmentAmount());
				if (c.getToxicity() == 0) {
					c.setState(CitizenState.RESCUED);
					heal();
				}
			}
		}

		/*
		 * if (this.getTarget() != null) {
		 * this.getTarget().getDisaster().setActive(false); if (this.getTarget()
		 * instanceof Citizen && this.getTarget().getDisaster() instanceof Infection) {
		 * 
		 * Citizen temp = ((Citizen) this.getTarget()); if (temp.getState() ==
		 * CitizenState.DECEASED) { jobsDone(); return; }
		 * 
		 * if (temp.getToxicity() == 0) { temp.setState(CitizenState.RESCUED); heal(); }
		 * else { if (temp.getToxicity() > 0) { temp.setToxicity(temp.getToxicity() -
		 * getTreatmentAmount()); if(temp.getToxicity()==0) {
		 * temp.setState(CitizenState.RESCUED); heal(); }
		 * 
		 * } } }
		 * 
		 * }
		 */
	}

	public void heal() {
		super.heal();
	}

}
