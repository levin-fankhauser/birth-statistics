import services.BirthRecordService;
import types.BirthCounts;
import types.TimeRange;

import java.util.Map;
import java.util.Scanner;


/**
 * Hauptklasse der Geburtsstatistik-Anwendung.
 * <p>
 * Diese Klasse bietet eine Konsolenapplikation für die Analyse von Geburtenstatistiken.
 * Sie nutzt verschiedene Funktionen des {@link BirthRecordService}-Interfaces,
 * um Geburtenzahlen basierend auf benutzerdefinierten Eingaben und Filtern anzuzeigen.
 * <p>
 * Diese Klasse muss mit der gewünschten Implementierung des {@link BirthRecordService} initialisiert werden, um die App imperativ oder funktional auszuführen!
 *
 * @author levin-fankhauser
 * @version 0.0
 */
public class App {

	private final BirthRecordService birthRecordService;

	public App(BirthRecordService birthRecordService) {
		this.birthRecordService = birthRecordService;
	}

	public void start() {
		Scanner scanner = new Scanner(System.in);

		boolean appRunning = true;

		while (appRunning) {
			System.out.println("\n\n\nFunktionen:");
			System.out.println("===========================================================================================================");
			System.out.println("  1: Anzahl männlicher, weiblicher und totaler Geburten innerhalb des ausgewählten Zeitraumes");
			System.out.println("===========================================================================================================");
			System.out.println("  2: Anzahl der Geburten nach Wohnviertel innerhalb des ausgewählten Zeitraumes");
			System.out.println("===========================================================================================================");
			System.out.println("  3: Anzahl der Geburten, welche nicht Einzelgeburten sind innerhalb des ausgewählten Zeitraumes");
			System.out.println("===========================================================================================================");
			System.out.println("  4: Durchschnittliche Anzahl Kinder pro Geburt nach Nationalität innerhalb des ausgewählten Zeitraumes");
			System.out.println("===========================================================================================================");
			System.out.println("  5: Anzahl der Geburten an verschiedenen Wochentagen");
			System.out.println("===========================================================================================================\n");

			System.out.print("Welche funktion wollen sie nutzen? (1-5 | 0 = Exit) : ");
			int chosenFunction = scanner.nextInt();
			switch (chosenFunction) {
				case 1:
					functionOne();
					break;
				case 2:
					functionTwo();
					break;
				case 3:
					functionThree();
					break;
				case 4:
					functionFour();
					break;
				case 5:
					functionFive();
					break;
				case 0:
					appRunning = false;
					break;

				default:
					System.out.println("Ungültige Eingabe!!!");
					break;
			}
		}

	}

	private void functionOne() {
		TimeRange timeRange = getTimeRange();
		BirthCounts birthCounts = this.birthRecordService.getBirthCountsByDateRange(timeRange.startDate(), timeRange.endDate());

		System.out.println("\n======================================");
		System.out.println("     Ergebnisse für den Zeitraum:");
		System.out.println("======================================\n");
		System.out.printf("Männliche Geburten:\t %d\n", birthCounts.maleCount());
		System.out.printf("Weibliche Geburten:\t %d\n", birthCounts.femaleCount());
		System.out.printf("Totale Geburten:\t %d\n", birthCounts.totalCount());
		System.out.println("===============================\n");
	}

	private void functionTwo() {
		TimeRange timeRange = getTimeRange();
		Map<String, Integer> birthByNeighbourhood = this.birthRecordService.getBirthsByNeighbourhoodByDateRange(
				timeRange.startDate(),
				timeRange.endDate());

		System.out.println("\n======================================");
		System.out.println("     Ergebnisse für den Zeitraum:");
		System.out.println("======================================\n");
		birthByNeighbourhood.forEach((neighbourhood, count) -> System.out.println(neighbourhood + ": " + count));
		System.out.println("===============================\n");
	}

	private void functionThree() {
		Scanner scanner = new Scanner(System.in);
		TimeRange timeRange = getTimeRange();
		System.out.print("Art der Geburt (2 = Zwillinge | 3 = Drillinge) : ");
		int numberOfChildren = scanner.nextInt();
		long amountOfBirths = this.birthRecordService.countMultipleBirths(timeRange.startDate(), timeRange.endDate(), numberOfChildren);

		System.out.println("\n======================================");
		System.out.println("     Ergebnisse für deine Angaben:");
		System.out.println("======================================\n");
		if (numberOfChildren == 2) {
			System.out.println("Anzahl an Zwillingen: " + amountOfBirths);
		} else if (numberOfChildren == 3) {
			System.out.println("Anzahl an Drillingen: " + amountOfBirths);
		} else {
			System.out.println("Ungültige Eingabe!!!");
		}
		System.out.println("===============================\n");
	}

	private void functionFour() {
		TimeRange timeRange = getTimeRange();
		Map<String, Double> birthByNationality = this.birthRecordService.getAverageChildrenByNationality(timeRange.startDate(), timeRange.endDate());

		System.out.println("\n======================================");
		System.out.println("     Ergebnisse für den Zeitraum:");
		System.out.println("======================================\n");
		birthByNationality.forEach((nationality, count) -> System.out.println(nationality + ": " + count));
		System.out.println("===============================\n");
	}

	private void functionFive() {
		Scanner scanner = new Scanner(System.in);

		System.out.println("\n===============================\n");
		System.out.print("Welchen Wochentag wollen Sie anzeigen (Mo, Di, Mi, Do, Fr, Sa, So, Alle) : ");
		String day = scanner.nextLine();

		Map<String, Long> birthsByDay = this.birthRecordService.getBirthsByDay(day);

		System.out.println("\n======================================");
		System.out.println("Ergebnisse für deine/n Wochentag/e:");
		System.out.println("======================================\n");
		birthsByDay.forEach((weekday, amount) -> System.out.println(weekday + ": " + amount));
		System.out.println("===============================\n");
	}

	private TimeRange getTimeRange() {
		Scanner scanner = new Scanner(System.in);

		System.out.println("\n===============================\n");
		System.out.println("Bitte geben Sie den Zeitraum für die Geburtenstatistik an:");
		System.out.print("Startdatum (Format: yyyy-MM-dd): ");
		String startDate = scanner.nextLine();
		System.out.print("Enddatum (Format: yyyy-MM-dd): ");
		String endDate = scanner.nextLine();

		return new TimeRange(startDate, endDate);
	}
}
