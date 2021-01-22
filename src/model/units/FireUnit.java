package model.units;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import model.events.WorldListener;
import simulation.Address;
import simulation.Rescuable;

abstract public class FireUnit extends Unit{
	public FireUnit(String id, Address location, int stepsPerCycle,WorldListener worldListener) {
		super(id,location,stepsPerCycle,worldListener);
	}
	public void respond(Rescuable r) throws CannotTreatException, IncompatibleTargetException {
		super.respond(r);
	}
	
	public void treat() {
		super.treat();
	}

}
