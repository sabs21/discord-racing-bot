package com.group3.racingbot.driverstate;

import com.group3.racingbot.Car;
import com.group3.racingbot.Driver;
import com.group3.racingbot.RaceEvent;
import com.group3.racingbot.racetrack.RaceTrack;
import java.util.concurrent.ThreadLocalRandom;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;

/**
 * A state which the driver stays in one place on the track until they recover.
 * @author Nick Sabia
 *
 */
//@BsonDiscriminator(value="Crashed", key="_cls")
public class Crashed extends Racing {
	
	/**
	 * Constructs the crashed state
	 * @param driver the driver's recovery skill governs how likely they are to escape this state
	 * @param car the car which is damaged by entering this state
	 * @param raceEvent the driver stays put in one of the track nodes on the track in this event
	 */
	public Crashed(Driver driver, Car car, RaceEvent raceEvent) {
		super(driver, car, raceEvent);
	}

	@Override
	public void rollDriverState() {
		// TODO Auto-generated method stub
		int lowerBound = (int) Math.floor(this.getDriver().getRecovery()/5);
		int recoveryRoll = ThreadLocalRandom.current().nextInt(lowerBound, 100);
		if (recoveryRoll > 75) {
			this.getDriver().setState(new Normal(this.getDriver(), this.getCar(), this.getRaceEvent()));
		}
	}

	@Override
	public void raceStep(Driver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void crash(Car car) {
		// TODO Auto-generated method stub
		// Do nothing
	}
	
	@Override
	public String toString() {
		return "DNF";
	}
}