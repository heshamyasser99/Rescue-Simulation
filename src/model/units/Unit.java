package model.units;

import java.util.ArrayList;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import model.disasters.Collapse;
import model.events.SOSResponder;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;

abstract public class Unit implements Simulatable, SOSResponder {

	private String unitID;
	private UnitState state;
	private Address location;
	private Rescuable target;
	private int distanceToTarget;
	private int stepsPerCycle;
	private WorldListener worldListener;

	public Unit(String id, Address location, int stepsPerCycle, WorldListener worldListener) {
		this.unitID = id;
		this.location = location;
		this.stepsPerCycle = stepsPerCycle;
		state = UnitState.IDLE;
		this.worldListener = worldListener;

	}

	public boolean canTreat(Rescuable r) {
		if (this instanceof FireTruck) {
			ResidentialBuilding b = (ResidentialBuilding) r;
			if (b != null) {
				if (b.getFireDamage() == 0) {
					return false;
				} else {
					return true;
				}
			}
		}
		////////////////////////////////
		if (this instanceof GasControlUnit) {
			ResidentialBuilding b = (ResidentialBuilding) r;
			if (b != null) {
				if (b.getGasLevel() == 0) {
					return false;
				} else {
					return true;
				}
			}
		}
		///////////////////////////////////
		if (this instanceof Ambulance) {
			Citizen c = (Citizen) r;
			if (c != null) {
				if (c.getBloodLoss() == 0 || c.getState() == CitizenState.SAFE
						|| c.getState() == CitizenState.DECEASED) {
					return false;
				} else {
					return true;
				}
			}
		}
///////////////////////////////////
		if (this instanceof DiseaseControlUnit) {
			Citizen c = (Citizen) r;
			if (c != null) {
				if (c.getToxicity() == 0 || c.getState() == CitizenState.SAFE
						|| c.getState() == CitizenState.DECEASED) {
					return false;
				} else {
					return true;
				}
			}
		}
		/// EVACUATOR
		ResidentialBuilding b = (ResidentialBuilding) r;
		if (b != null) {
			if (b.getDisaster() == null || !(b.getDisaster() instanceof Collapse)) {
				return false;
			} else {
				if (b.getStructuralIntegrity() == 0) {
					return false;
				}
				return true;
			}
		}
		return false;

	}

	public static Boolean checkdead(ArrayList<Citizen> x) {
		for (Citizen c : x) {
			if (c.getState() != CitizenState.DECEASED) {
				return false;
			}
		}
		return true;
	}

	public WorldListener getWorldListener() {
		return worldListener;
	}

	public void setWorldListener(WorldListener worldListener) {
		this.worldListener = worldListener;
	}

	public UnitState getState() {
		return state;
	}

	public void setState(UnitState state) {
		this.state = state;
	}

	public String getUnitID() {
		return unitID;
	}

	public Rescuable getTarget() {
		return target;
	}

	public int getStepsPerCycle() {
		return stepsPerCycle;
	}

	public Address getLocation() {
		return location;
	}

	public void setLocation(Address location) {
		this.location = location;
	}

	public void respond(Rescuable r) throws CannotTreatException, IncompatibleTargetException {
		if (this instanceof Ambulance || this instanceof DiseaseControlUnit) {
			if (!(r instanceof Citizen)) {
				throw new IncompatibleTargetException(this, r, "This Unit, "+this.getClass().getSimpleName()+" ,Cannot respond To This Kind Of Rescuable, "+r.getClass().getSimpleName()+".");
			}
		} else {
			if (this instanceof Evacuator || this instanceof FireTruck || this instanceof GasControlUnit) {
				if (!(r instanceof ResidentialBuilding)) {
					throw new IncompatibleTargetException(this, r,  "This Unit, "+this.getClass().getSimpleName()+" ,Cannot respond To This Kind Of Rescuable, "+r.getClass().getSimpleName()+".");
				}
			}
		}
		if (!canTreat(r)) {
			if(r instanceof Citizen) {
				throw new CannotTreatException(this, r, "The Unit With ID: "+this.getUnitID()+ " Cannot Treat This Citizen With ID: "+((Citizen)r).getNationalID() );
			}else {
				throw new CannotTreatException(this, r, "The Unit With ID: "+this.getUnitID()+ " Cannot Treat This Building In Location: "+((ResidentialBuilding)r).getLocation().toString() );
			
			
		}}

		if (this instanceof Evacuator) {

			Evacuator e = (Evacuator) this;
			target = r;
			distanceToTarget = getDist(r);
			e.setState(UnitState.RESPONDING);

		} else {
			if (target == null) {
				target = r;

			} else {
				if (target.getDisaster() != null) {
					target.getDisaster().setActive(true);
				}

				if (r instanceof Citizen) {
					Citizen c = (Citizen) r;
					if (c.getState() == CitizenState.RESCUED) {
						target = r;
						if (c.getDisaster() != null) {
							c.getDisaster().setActive(false);
						}
					} else {
						target = r;
						if (c.getDisaster() != null) {
							c.getDisaster().setActive(true);
						}
					}

				} else {
					ResidentialBuilding b = (ResidentialBuilding) r;
					if (b.getDisaster() != null && b.getDisaster().isActive()) {
						target = r;

					} else {
						if (b.getDisaster() != null) {
							b.getDisaster().setActive(true);
						}
						target = r;
					}

				}

			}
			this.setState(UnitState.RESPONDING);
			distanceToTarget = getDist(r);
			this.setDistanceToTarget(getDist(r));
		}
	}

	public int getDist(Rescuable r) {
		return Math.abs(this.getLocation().getX() - r.getLocation().getX())
				+ Math.abs(this.getLocation().getY() - r.getLocation().getY());
	}

	public void setDistanceToTarget(int distanceToTarget) {
		this.distanceToTarget = distanceToTarget;
	}

	public void cycleStep() {
		if (state == UnitState.IDLE)
			return;
		if (this instanceof Evacuator) {
			Evacuator e = (Evacuator) this;

			if (e.getState() == UnitState.TREATING) {
				e.treat();
			} else {
				if (distanceToTarget == 0) {
					e.setState(UnitState.TREATING);
					e.treat();
					e.setDistanceToBase(e.getTarget().getLocation().getX() + e.getTarget().getLocation().getY());
				}

				else {
					if (e.getState() == UnitState.RESPONDING) {
						if (distanceToTarget - stepsPerCycle <= 0) {
							distanceToTarget = 0;
							// e.setState(UnitState.TREATING);
							worldListener.assignAddress(e, e.getTarget().getLocation().getX(),
									e.getTarget().getLocation().getY());
							e.setDistanceToBase(
									e.getTarget().getLocation().getX() + e.getTarget().getLocation().getY());
						} else {
							distanceToTarget -= stepsPerCycle;

						}
					}

				}
			}
		}

		// END OF EVACUATOR//
		else {
			if (distanceToTarget == 0) {
				this.setState(UnitState.TREATING);
				this.getWorldListener().assignAddress(this, this.getTarget().getLocation().getX(),
						this.getTarget().getLocation().getY());
				treat();

			} else {

				if (this.getState() == UnitState.RESPONDING) {

					if (distanceToTarget - stepsPerCycle <= 0) {

						distanceToTarget = 0;

						this.getWorldListener().assignAddress(this, this.getTarget().getLocation().getX(),
								this.getTarget().getLocation().getY());
					} else {
						distanceToTarget -= stepsPerCycle;
					}

				}
			}
		}

	}

	public void treat() {

		if (this instanceof Evacuator) {

			Evacuator e = (Evacuator) this;
			ResidentialBuilding b = (ResidentialBuilding) e.getTarget();
			System.out.println(e + " " + b);
			ArrayList<Citizen> occ = b.getOccupants();
			ArrayList<Citizen> pass = e.getPassengers();

		
			// NO PASSANGERS SO EITHER GOING TO BUILDING OR LOADING FROM BUILDING
			if (pass.size() == 0) {
				if (distanceToTarget == 0) {
					e.getWorldListener().assignAddress(e, b.getLocation().getX(), b.getLocation().getY());
					e.setDistanceToBase(b.getLocation().getX() + b.getLocation().getY());
					int l = 0;
					a: for (int i = 0; i < occ.size(); i++) {
						if (b.getStructuralIntegrity() != 0 && occ.get(i).getState() != CitizenState.DECEASED) {
							l++;
							pass.add(occ.get(i));
							occ.remove(i);
							i--;
						}
						if (l == e.getMaxCapacity()) {
							break a;
						}
					}

					if (l == 0) {
						jobsDone();
						return;
					}
				} else {

					if (distanceToTarget - stepsPerCycle <= 0) {
						e.getWorldListener().assignAddress(e, b.getLocation().getX(), b.getLocation().getY());
						distanceToTarget = 0;
						e.setDistanceToBase(e.getTarget().getLocation().getX() + e.getTarget().getLocation().getY());
						e.setState(UnitState.TREATING);

					} else {
						distanceToTarget -= stepsPerCycle;

					}
				}

			} else {
				// EVACUATOR HAS PASSENGERS AND WILL GO TO BASE OR UNLOAD
				if (e.getDistanceToBase() == 0) {
					e.getWorldListener().assignAddress(e, 0, 0);
					unload();
				} else {
					if (e.getDistanceToBase() - stepsPerCycle <= 0) {
						unload();
						e.setDistanceToBase(0);
						e.getWorldListener().assignAddress(e, 0, 0);
						distanceToTarget = e.getTarget().getLocation().getX() + e.getTarget().getLocation().getY();
					} else {
						e.setDistanceToBase(e.getDistanceToBase() - stepsPerCycle);

					}
				}
			}
		} // end of evacuator
	}

//revise logic
	public void jobsDone() {
		if (this instanceof Evacuator) {
			this.setState(UnitState.IDLE);
			target = null;
			return;
		}
		if (this.target != null) {
			if (this.getTarget() instanceof Citizen) {
				Citizen c = (Citizen) this.target;
				this.setState(UnitState.IDLE);
				this.target = null;
				if (c.getDisaster() != null) {
					c.getDisaster().setActive(false);
				}
				return;
			}
			if (this.getTarget() instanceof ResidentialBuilding) {
				ResidentialBuilding b = (ResidentialBuilding) this.target;
				this.setState(UnitState.IDLE);
				this.target = null;
				if (b.getDisaster() != null) {
					b.getDisaster().setActive(false);

				}
				return;
			}

		}

	}

	public void unload() {
		Evacuator e = (Evacuator) this;
		ArrayList<Citizen> pass = e.getPassengers();
		while (!pass.isEmpty()) {
			Citizen c = pass.get(0);
			if (c.getState() == CitizenState.DECEASED) {
			} else {
				c.setState(CitizenState.RESCUED);
			}
			pass.remove(0);
			c.getWorldListener().assignAddress(c, 0, 0);

		}
	}

}
