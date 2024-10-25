import services.BirthRecordService;
import services.impl.BirthRecordServiceImperativeImpl;

/**
 * Hauptstartklasse für die imperative Geburtsstatistik-Anwendung.
 * <p>
 * Diese Klasse initialisiert eine Instanz des {@link BirthRecordServiceImperativeImpl}
 * und startet die {@link App}-Klasse, welche die Interaktion mit dem Benutzer über das Konsolenmenü steuert.
 *
 * @author levin-fankhauser
 * @author ToppTobi
 * @version 1.0
 */
public class ImperativeAppRunner {

	public static void main(String[] args) {
		BirthRecordService birthRecordService = new BirthRecordServiceImperativeImpl("data.json");
		App app = new App(birthRecordService);
		app.start();
	}

}
