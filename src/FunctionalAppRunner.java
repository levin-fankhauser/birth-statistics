import services.BirthRecordService;
import services.impl.BirthRecordServiceFunctionalImpl;

/**
 * Hauptstartklasse für die funktionale Geburtsstatistik-Anwendung.
 * <p>
 * Diese Klasse initialisiert eine Instanz des {@link BirthRecordServiceFunctionalImpl}
 * und startet die {@link App}-Klasse, welche die Interaktion mit dem Benutzer über das Konsolenmenü steuert.
 *
 * @author levin-fankhauser
 * @author ToppTobi
 * @version 2.0
 */
public class FunctionalAppRunner {

	public static void main(String[] args) {
		BirthRecordService birthRecordService = new BirthRecordServiceFunctionalImpl("data.json");
		App app = new App(birthRecordService);
		app.start();
	}
}