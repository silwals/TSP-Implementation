
import java.util.Random;

public class TravelingSalesman {

	public final static int MAP_SIZE = 200;
		
	private double[][] coordinates;
	
	private double[][] costs;
	
	public int n;

	boolean[] visited;

	private static int[][] createRandomCoordinates(int n, Random random) {
		int[][] coordinates = new int[n][2];
		
		for (int i = 0; i < coordinates.length; i++) {

			//we ignore the case that two cities have the same coordinates - it should work anyway?
			
			coordinates[i][0] = Math.abs(random.nextInt()%MAP_SIZE); 
			coordinates[i][1] = Math.abs(random.nextInt()%MAP_SIZE); 
		}
		
		return coordinates;
	}
	
	public TravelingSalesman(double[][] coordinates){
		this(coordinates, coordinates.length);
	}

	public TravelingSalesman(int n, Random random){
	//	this(createRandomCoordinates(n, random), n);
	}
	
	public TravelingSalesman(double[][] coordinates, int n){
		this.n = n;
		
		visited = new boolean[n];
		costs = new double[n][n];
		
		this.coordinates = coordinates;

		initCostsByCoordinates();
	}
	
	/*	
	 * create costs matrix by creating coordinates for cities and using flight distance as cost
	 * however, the algorithm is more general than that, arbitrary cost matrices should work, even unsymmetric ones
	 */
	private void initCostsByCoordinates() {
		for(int i = 0;i<coordinates.length;i++){
			for(int j = 0;j<coordinates.length;j++){
				costs[i][j] = calculateTravelCostsBetweenCities(i,j);
			}
		}
	}

	private double calculateTravelCostsBetweenCities(int i, int j){
		double dx = coordinates[i][0]-coordinates[j][0];
		double dy = coordinates[i][1]-coordinates[j][1];
		
		return Math.sqrt(dx*dx+dy*dy);//pythagoras
	}

	public double calculateCosts(int[] route){
		return calculateCosts(route, false);
	}
	
	public double calculateCosts(int[] route, boolean isVerbose){
		
		double travelCosts = 0;
		for (int i = 1; i < route.length; i++) {
			travelCosts += costs[route[i-1]][route[i]]; 
			
			if(isVerbose){
				System.out.println("costs from "+route[i-1]+" to "+route[i]+": "+costs[route[i-1]][route[i]]);
			}
			
		}

		//return to starting city
		travelCosts += costs[route[n-1]][route[0]];
		if(isVerbose){
			System.out.println("costs from "+route[n-1]+" to "+route[0]+": "+costs[route[n-1]][route[0]]);
		}
		return travelCosts;
	}
	
	public void printRoute(int[] route){
		for(int i = 0;i<route.length;i++){
			System.out.print(route[i]+" ");
		}
	}
	
	public void printCosts(){
		System.out.println("costs matrix for the traveling salesman problem:");
		for(int i = 0;i<costs.length;i++){
			for(int j = 0;j<costs[i].length;j++){
				System.out.print(costs[i][j]+" ");
			}
			System.out.print("\n");
		}
	}
	
}