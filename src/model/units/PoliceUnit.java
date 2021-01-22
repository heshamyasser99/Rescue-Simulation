package model.units;

import java.util.ArrayList;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import model.events.WorldListener;
import model.people.Citizen;
import simulation.Address;
import simulation.Rescuable;

abstract public class PoliceUnit extends Unit{
	private ArrayList<Citizen> passengers;
	private int maxCapacity;
	private int distanceToBase;
	public PoliceUnit(String id, Address location, int stepsPerCycle, WorldListener worldListener,int maxCapacity) {
		super(id,location,stepsPerCycle,worldListener);
		this.maxCapacity=maxCapacity;
		passengers=new ArrayList<Citizen>();
		distanceToBase=0;
		
	}
	public void respond(Rescuable r) throws CannotTreatException, IncompatibleTargetException {
		super.respond(r);
	}
	public int getDistanceToBase() {
		return distanceToBase;
	}
	public void setDistanceToBase(int distanceToBase) {
		this.distanceToBase = distanceToBase;
	}
	public int getMaxCapacity() {
		return maxCapacity;
	}
	public ArrayList<Citizen> getPassengers() {
		return passengers;
	}
	public void cycleStep() {
		super.cycleStep();
	}
	
	
}
