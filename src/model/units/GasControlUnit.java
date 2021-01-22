package model.units;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import model.disasters.Fire;
import model.disasters.GasLeak;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import simulation.Address;
import simulation.Rescuable;

public class GasControlUnit extends FireUnit {
	public GasControlUnit(String id, Address location, int stepsPerCycle, WorldListener worldListener) {
		super(id, location, stepsPerCycle, worldListener);
	}
	public void respond(Rescuable r) throws CannotTreatException, IncompatibleTargetException {
		super.respond(r);
	}
	public String toString() {
		String x="Type: Gas Control Unit\n"
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
			if (b.getStructuralIntegrity() == 0||b.getGasLevel()==0) {
				jobsDone();
				return;
			}
			if(b.getDisaster()!=null&& b.getDisaster() instanceof GasLeak) {
				b.getDisaster().setActive(false);
				
			}
			b.setGasLevel(b.getGasLevel()-10);
			
			if (b.getStructuralIntegrity() == 0||b.getGasLevel()==0) {
				jobsDone();
				return;
			}
			
			
			
		}
	

	}
}
