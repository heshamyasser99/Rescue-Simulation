package model.infrastructure;

import simulation.Address;

public class NewHospital extends Hospital{
	public NewHospital(Address location, String name, HospitalType type){
		super(location,0,name,type);
	}
	

}
