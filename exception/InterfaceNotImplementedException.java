package exception;

/**
 * L'exception <code>InterfaceNotImplementedException</code> est utilisée
 * lorsque la classe dont est issus l'objet n'implémente pas une interface.
 * 
 * @see Exception
 */
public class InterfaceNotImplementedException extends Exception {

	private static final long serialVersionUID = 8828731079274073129L;

	/**
	 * Construit une nouvelle exception de type
	 * <code>InterfaceNotImplementedException</code> avec <code>null</code>
	 * comme message. La cause n'est pas initialisée, et peut plus-tard l'être
	 * avec un appel de la méthode {@link Throwable#initCause(Throwable)}.
	 */
	public InterfaceNotImplementedException() {
		super();
	}

	/**
	 * Construit une nouvelle exception de type
	 * <code>InterfaceNotImplementedException</code> avec le message spécifique
	 * comme détails. La cause n'est pas initialisée, et peut plus-tard l'être
	 * avec un appel de la méthode {@link Throwable#initCause(Throwable)}.
	 * 
	 * @param message
	 *            <code>String</code>, le message des détails.
	 */
	public InterfaceNotImplementedException(String message) {
		super(message);
	}

	/**
	 * Construit une nouvelle exception de type
	 * <code>InterfaceNotImplementedException</code> avec le message spécifique
	 * comme détails. La cause n'est pas initialisée, et peut plus-tard l'être
	 * avec un appel de la méthode {@link Throwable#initCause(Throwable)}. Il
	 * faut cependant noter que <b>cause</b> n'est <i>pas</i> automatiquement
	 * fusionné au message.
	 * 
	 * @param message
	 *            <code>String</code>, le message des détails.
	 * @param cause
	 *            <code>Throwable</code>, la cause.
	 */
	public InterfaceNotImplementedException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Construit une nouvelle exception de type
	 * <code>InterfaceNotImplementedException</code> avec le message et la cause
	 * spécifiés et la suppression et le stack trace écrit activés ou désactivés
	 * selon les paramètres spécifiés.
	 * 
	 * @param message
	 *            <code>String</code>, le message des détails.
	 * @param cause
	 *            <code>Throwable</code>, la cause.
	 * @param enableSuppression
	 *            <code>boolean</code>, si la suppression est activée ou non.
	 * @param writableStackTrace
	 *            <code>boolean</code>, si le stack trace est écrit ou non.
	 */
	public InterfaceNotImplementedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Construit une nouvelle exception de type
	 * <code>InterfaceNotImplementedException</code> avec la cause spécifiée et
	 * le message de détails étant
	 * <code>(cause == null ? null : cause.toString())</code> (qui contient
	 * normalement le message).
	 * 
	 * @param cause
	 */
	public InterfaceNotImplementedException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
