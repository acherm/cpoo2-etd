package cpoo2.s02.pont.api;

/** Envoyer un message à quelqu'un. En production, c'est un courriel ; en test, il ne doit rien partir. */
public interface Messagerie {
	void envoyer(String destinataire, String texte);
}
