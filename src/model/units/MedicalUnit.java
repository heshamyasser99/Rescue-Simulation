package model.units;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import model.events.WorldListener;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Rescuable;

abstract public class MedicalUnit extends Unit {
	private int healingAmount;
	private int treatmentAmount;

	public MedicalUnit(String id, Address location, int stepsPerCycle, WorldListener worldListener) {
		super(id, location, stepsPerCycle, worldListener);
		treatmentAmount = 10;
		healingAmount = 10;

	}
	public void respond(Rescuable r) throws CannotTreatException, IncompatibleTargetException {
		super.respond(r);
	}

	public int getTreatmentAmount() {
		return treatmentAmount;
	}

	public void treat() {
		super.treat();
	}

	public void heal() {
		

		Citizen c = (Citizen) this.getTarget();
		
		if (c != null) {
			if (c.getHp() == 100) {
				jobsDone();

			} else {
				c.setHp(c.getHp() + healingAmount);
				if(c.getHp()==100) {
					jobsDone();
				}
			}
		}
		

	}

	public void jobsDone() {
		super.jobsDone();
	}
}
