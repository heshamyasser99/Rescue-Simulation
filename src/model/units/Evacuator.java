package model.units;

import java.util.ArrayList;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Rescuable;

public class Evacuator extends PoliceUnit {
	public Evacuator(String id, Address location, int stepsPerCycle, WorldListener worldListener, int maxCapacity) {
		super(id, location, stepsPerCycle, worldListener, maxCapacity);
	}

	public void cycleStep() {
		super.cycleStep();

	}
	public String toString() {
		String x="Type: Evacuator\n"
				+"ID: "+this.getUnitID()
				+"\nLocation: "+this.getLocation().toString()
				+"\nSteps Per Cycle: "+this.getStepsPerCycle();
		
		x+="\nState: "+this.getState();
		if(this.getTarget()!=null) {
			x+="\nCurrent Target: \n"+((ResidentialBuilding)(this.getTarget())).toString();
		}
		x+="\n";
		x+="Number of Passengers on board is: "+this.getPassengers().size()+"\n";
		x+="Citizens on board:\n***************\n";
		for(int i=0;i<this.getPassengers().size();i++) {
			x+=this.getPassengers().get(i).toString();
			x+="\n";
		}
		return x;
		
	}

	public void treat() {
		super.treat();
	}
	public void respond(Rescuable r) throws CannotTreatException, IncompatibleTargetException {
		super.respond(r);
	}

}
