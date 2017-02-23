package base.inAndOut;

import base.Neurone;
import javafx.event.Event;

public class Sortie extends Neurone {

	private Event eventResultant;

	/**
	 * 
	 * @param nom
	 * @param valeurImportance
	 * @param eventResultant
	 */
	public Sortie(String nom, double valeurImportance, Event eventResultant) {
		super(nom);
		setEventResultant(eventResultant);
	}

	public Event getEventResultant() {
		return this.eventResultant;
	}

	/**
	 * 
	 * @param eventResultant
	 */
	private void setEventResultant(Event eventResultant) {
		this.eventResultant = eventResultant;
	}

}