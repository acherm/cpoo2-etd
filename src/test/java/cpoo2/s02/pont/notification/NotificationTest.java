package cpoo2.s02.pont.notification;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * S02 ex. 4, Q16 : l'oracle du Pont. Cinq tests, cinq rouges sur le squelette.
 *
 * <p>Les quatre premiers <b>croisent</b> les deux hiérarchies : une même
 * notification part sur deux canaux, un même canal porte deux notifications.
 * Le cinquième regarde la <b>structure</b> : c'est l'abstraction, et elle
 * seule, qui tient le canal, par son interface.
 */
class NotificationTest {

	@Test
	void uneStandardPartTelleQuelleUneFois() {
		final CanalEnMemoire canal = new CanalEnMemoire();
		new Standard(canal).envoyer("alice", "rendre le TP");
		assertEquals(1, canal.transmissions().size());
		assertEquals("alice", canal.transmissions().get(0).destinataire());
		assertEquals("rendre le TP", canal.transmissions().get(0).texte());
	}

	@Test
	void uneUrgentePrefixeEtRelance() {
		final CanalEnMemoire canal = new CanalEnMemoire();
		new Urgente(canal).envoyer("bob", "réunion");
		assertEquals(2, canal.transmissions().size(), "le texte, puis la relance");
		assertEquals(Urgente.PREFIXE + "réunion", canal.transmissions().get(0).texte());
		assertEquals(Urgente.RELANCE + "réunion", canal.transmissions().get(1).texte());
		assertTrue(canal.transmissions().stream().allMatch(t -> t.destinataire().equals("bob")));
	}

	@Test
	void laMemeNotificationChangeDeCanalSansChangerDeClasse() {
		final CanalSms sms = new CanalSms();
		final Notification n = new Standard(sms);   // même classe qu'au premier test, autre canal
		n.envoyer("alice", "rendre le TP");
		assertEquals(1, sms.journalOperateur().size());
		assertTrue(sms.journalOperateur().get(0).contains("rendre le TP"));
	}

	@Test
	void leSmsTronqueMemeUneUrgente() {
		final CanalSms sms = new CanalSms();
		final String long200 = "x".repeat(200);
		new Urgente(sms).envoyer("bob", long200);
		assertEquals(2, sms.journalOperateur().size(), "la relance part aussi par SMS");
		for (final String s : sms.journalOperateur()) {
			assertTrue(s.length() <= CanalSms.LIMITE, "un SMS fait 160 caractères au plus, reçu " + s.length());
			assertTrue(s.contains("URGENT"), "le préfixe survit à la troncature");
		}
	}

	@Test
	void lAbstractionTientLeCanalParSonInterface() {
		final boolean tientUnCanal = Arrays.stream(Notification.class.getDeclaredFields())
				.anyMatch(f -> f.getType() == Canal.class && !Modifier.isStatic(f.getModifiers()));
		assertTrue(tientUnCanal, "Notification doit détenir un champ de type Canal (l'interface, pas une classe concrète)");
		assertEquals(0, Standard.class.getDeclaredFields().length, "Standard ne tient rien de plus : le canal est dans l'abstraction");
		assertEquals(0, Arrays.stream(Urgente.class.getDeclaredFields()).filter(f -> !Modifier.isStatic(f.getModifiers())).count(),
				"Urgente ne tient rien de plus : le canal est dans l'abstraction");
	}
}
