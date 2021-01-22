package model.infrastructure;

import simulation.Address;

abstract public class Hospital extends ResidentialBuilding{
	private String name;
	private HospitalType type;
	
	
	public Hospital(Address location, int foundationDamage, String name, HospitalType type) {
		super(location,foundationDamage);
		this.name=name;
		this.type=type;
	}


	public String getName() {
		return name;
	}


	public HospitalType getType() {
		return type;
	}
	

}
