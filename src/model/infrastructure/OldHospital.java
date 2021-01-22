package model.infrastructure;

import simulation.Address;

public class OldHospital extends Hospital{
	
	public OldHospital(Address location, String name){
		super(location,20,name,HospitalType.PUBLIC);
	}
	

}
