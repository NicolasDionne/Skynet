package utilitaires;

public interface AddressString {

    /**
     * Méthode par défault qui, quand implémentée, permet d'obtenir l'adresse d'un objet même si on modifie son toString.
     *
     * @return Le nom de la classe et l'addresse de l'objet.
     */
    default String address() {
        return getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(this));
    }

}


