package com.group3.racingbot.shop;

import java.util.concurrent.ThreadLocalRandom;

import org.bson.codecs.pojo.annotations.BsonCreator;

import com.group3.racingbot.Car;
import com.group3.racingbot.Car.CarBuilder;
import com.group3.racingbot.ComponentFactory.Component;
import com.group3.racingbot.gameservice.GameplayHandler;
import com.group3.racingbot.inventory.Inventory;

/**
 * Concrete class extending abstract class Shop. Sells low quality components.
 * @author Maciej Bregisz
 *
 */
public class Junkyard extends Shop {

	
	@BsonCreator
	/**
	 * Junkyard store constructor
	 */
	public Junkyard() {
		setCarsForSale(new Inventory<Car>());
		setComponentsForSale(new Inventory<Component>());
		setId(1);
		setName("Junkyard");
		setDescription("What you see is what you get, pal! Rusty parts and even rustier components, some hidden gems too!");
	}
	
	/**
	 * Junkyard store constructor with GameplayHandler parameter
	 * @param gh Reference to the GameplayHandler
	 */
	public Junkyard(GameplayHandler gph) {
		setCarsForSale(new Inventory<Car>());
		setComponentsForSale(new Inventory<Component>());
		setId(1);
		setName("Junkyard");
		setDescription("What you see is what you get, pal! Rusty parts and even rustier components, some hidden gems too!");
		gph.subscribe(this);
	}

	/**
	 * Generates new components and cars for sale using the abstract factory and builder.
	 */
	public void update() {
		System.out.println("Updating Junkyard Store");
		getComponentsForSale().getItems().clear();
		getComponentsForSale().add(getFactory().createComponent("engine", ThreadLocalRandom.current().nextInt(151, 300 + 1)));
		getComponentsForSale().add(getFactory().createComponent("wheel", ThreadLocalRandom.current().nextInt(151, 300 + 1)));
		getComponentsForSale().add(getFactory().createComponent("suspension", ThreadLocalRandom.current().nextInt(151, 300 + 1)));
		getComponentsForSale().add(getFactory().createComponent("chassis", ThreadLocalRandom.current().nextInt(151, 300 + 1)));
		getComponentsForSale().add(getFactory().createComponent("transmission", ThreadLocalRandom.current().nextInt(151, 300 + 1)));
		
		getCarsForSale().getItems().clear();
		CarBuilder cb = new CarBuilder();
		cb.addEngine(getFactory().createComponent("engine", ThreadLocalRandom.current().nextInt(151, 300 + 1)))
		.addWheels(getFactory().createComponent("wheel", ThreadLocalRandom.current().nextInt(151, 300 + 1)))
		.addSuspension(getFactory().createComponent("suspension", ThreadLocalRandom.current().nextInt(151, 300 + 1)))
		.addChassis(getFactory().createComponent("chassis", ThreadLocalRandom.current().nextInt(151, 300 + 1)))
		.addTransmission(getFactory().createComponent("transmission", ThreadLocalRandom.current().nextInt(151, 300 + 1)));
		getCarsForSale().add(cb.build());
		
		
	}
	/**
	 *  Calculates the Objects hash code
	 * @return int object hash code
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	/**
	 * Compare and determine whether or not the two objects are identical or the same object.
	 * @param obj the Object being compared to
	 * @return boolean whether or not objects are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}
}
