package cpoo2.s02.pont.impl;

import cpoo2.s02.pont.api.Messagerie;

/** Famille « système » : tient lieu de serveur de courriel. Chaque envoi <b>part vraiment</b> (ici, sur la console). */
public final class MessagerieConsole implements Messagerie {
	@Override
	public void envoyer(final String destinataire, final String texte) {
		System.out.println("[COURRIEL] à " + destinataire + " : " + texte);
	}
}
