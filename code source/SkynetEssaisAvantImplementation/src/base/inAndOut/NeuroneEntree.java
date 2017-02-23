package base.inAndOut;

import base.NeuroneAvecImportance;
import javafx.event.Event;

public class NeuroneEntree extends NeuroneAvecImportance {

	private Event eventEntre;

	/**
	 * 
	 * @param nom
	 * @param valeurImportance
	 */
	public NeuroneEntree(String nom, double valeurImportance, Event eventEntree) {
		super(nom, valeurImportance);
		setEventEntre(eventEntree);
	}

	public Event getEventEntre() {
		return this.eventEntre;
	}

	/**
	 * 
	 * @param eventEntre
	 */
	private void setEventEntre(Event eventEntre) {
		this.eventEntre = eventEntre;
	}

}