import java.util.*;

public class Main {
	// _______________________ MAIN _______________________
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n, x, y, knopen, verbindingen;
		List<Lijn> lijnen;
		Route route;

		// AANTAL TESTGEVALLEN
		n = sc.nextInt();
		for (x = 0; x < n; x++) {
			//aantal knopen
			knopen = sc.nextInt();
			// aantal verbindingen
			verbindingen = sc.nextInt();

			// MAAK LIJST MET LIJNEN AAN
			lijnen = new ArrayList<>();
			for (y = 0; y < verbindingen; y++) {
				//MAAK LIJNEN AAN
				lijnen.add(new Lijn(sc.nextInt() - 1, sc.nextInt() - 1, sc.nextInt()));
			}
			// ZOEK HET KORSTE PAD
			route = new Route(knopen);
			System.out.print(x + 1 + " ");
			route.bellmanFord(lijnen);

		}
		sc.close();
	}
}

//_______________________ EDGE _______________________
class Lijn {
	int s, d, w;

	public Lijn(int s, int d, int w) {
		this.s = s;
		this.d = d;
		this.w = w;
	}
}

//_______________________ PAD _______________________
class Route {
	int max = Integer.MAX_VALUE; // oneindig
	int[] afstand;
	int[] ouder;
	int knopen_temp, source, knopen, aantalLijnen;

	Route(int knopen) {
		this.knopen = knopen;
		source = 0;
		afstand = new int[knopen];
		ouder = new int[knopen];
	}

	void bellmanFord(List<Lijn> lijnen) {
		int s, d, w, i;
		aantalLijnen = lijnen.size();

		Arrays.fill(afstand, max); // alle knooppunten ONEINDIG
		afstand[source] = 0; // geen initieel gewicht
		Arrays.fill(ouder, -1); // nog geen ouder

		// RELAXATIE
		knopen_temp = knopen;
		while (knopen_temp-- > 0) {
			// tempKnopen--;
			for (i = 0; i < aantalLijnen; i++) {
				s = lijnen.get(i).s;
				d = lijnen.get(i).d;
				w = lijnen.get(i).w;

				if (afstand[s] != max && (afstand[s] + w < afstand[d])) {
					afstand[d] = afstand[s] + w;
					ouder[d] = s;
				}
			}
		} 
		// CONTOLE OP NEGATIEVE CYCLI
		for (i = 0; i < aantalLijnen; i++) {
			s = lijnen.get(i).s;
			d = lijnen.get(i).d;
			w = lijnen.get(i).w;

			if (afstand[s] != max && (afstand[s] + w < afstand[d])) {
				System.out.println("min oneindig");
				return;
			}

		}
		// CONTROLEREN OP ONGECONNECTEERDE VERBINDINGEN (MAX)
		if (afstand[knopen - 1] == max) { //laatste knoop in lijst nog niet behandeld
			System.out.println("plus oneindig");
		} else {
			System.out.println(afstand[knopen - 1]);

		}
	}
}
