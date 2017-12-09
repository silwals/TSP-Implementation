import java.io.File;
import java.io.IOException;
import java.util.Scanner;
/*Main program for executing TSP using brute force and Dynamic programming
 * It reads the TSPlib file and passes the file to helper class which returns a 
 * distance matrix between the vertices(cities).
 * The distance matrix is passed to respective algorithms either Brute Force or HeldKarp
*/
public class TSPMain {
	static long startTime = System.currentTimeMillis();

	public static void main(String[] args) throws IOException {
		Scanner in=new Scanner(System.in);
		System.out.println("Press \"0\" for Brute Force  OR  Press \"1\" for Held Karp");
		int response=in.nextInt();
		in.close();
		ReadTSPFile tspFile = new ReadTSPFile(new File("./data/tsp/ulysses16.tsp"));// File names have to be changed
		                                                                          // (ulysses16.tsp,bayg29.tsp,burma14.tsp) 
		if(response==0) {		
		TravelingSalesman salesman = new TravelingSalesman(tspFile.coordinatesMatrix);
		salesman.printCosts();
		System.out.println("*** running brute force algorithm ***");
		TravelingSalesmanBruteForce bruteForce = new TravelingSalesmanBruteForce(salesman);
		bruteForce.run();
		}
		else {
			System.out.println("*** running held karp algorithm ***");	
		TravelingSalesmanHeldKarp tspHKarp = new TravelingSalesmanHeldKarp();
		tspHKarp.getMinCost(tspFile.distMatrix);
		}
		long estimatedTime = System.currentTimeMillis() - startTime;
		System.out.println("Total time taken(secs):" + estimatedTime/1000);
	}

}
