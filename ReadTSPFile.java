import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/*Helper class to read the tsplib file and convert it into distancematrix
Distancematrix is an adjacency matrix between all the vertices which represent 
the XY location of the cities.*/
public class ReadTSPFile {
	public int dimension;
	public Map<Double, Double> setCities = new HashMap<Double, Double>();
	public ArrayList<Coords> setCities1 = new ArrayList<Coords>();
	public double[][] distMatrix;
	public double[][] coordinatesMatrix;

	class Coords {
		double x;
		double y;

		public boolean equals(Object o) {
			Coords c = (Coords) o;
			return c.x == x && c.y == y;
		}

		public Coords() {
			this.x = 0;
			this.y = 0;
		}

		public Coords(double x, double y) {
			super();
			this.x = x;
			this.y = y;
		}

		public double getX() {
			return x;
		}

		public double getY() {
			return y;
		}

		public int hashCode() {
			return new Integer(x + "0" + y);
		}
	}

	public ReadTSPFile(File file) {
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				StringBuffer stringBuffer = new StringBuffer();
				stringBuffer.append(line);
				line = stringBuffer.toString();
				if (line.startsWith("DIMENSION")) {
					dimension = Integer.parseInt(line.split(" ")[1]);
				}
				if ((line.trim().length() > 0) && Character.isDigit(line.trim().charAt(0))) {
					setCities1.add(new Coords(Double.valueOf(line.split(" ")[1]), Double.valueOf(line.split(" ")[2])));

				}

			}
			fileReader.close();
			coordinatesMatrix = new double[dimension][2];
			int pos = 0;
			for (Coords val : setCities1) {
				coordinatesMatrix[pos][0] = val.getX();
				coordinatesMatrix[pos][1] = val.getY();
			}
			generateDistanceMatrix(setCities1, dimension);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void generateDistanceMatrix(ArrayList<Coords> setCities12, int dimension2) {
		distMatrix = new double[dimension2][dimension2];
		for (int i = 0; i < dimension2; i++) {
			for (int j = 0; j < dimension2; j++) {
				double dist = Math.sqrt(((setCities12.get(j).getX() - setCities12.get(i).getX())
						* (setCities12.get(j).getX() - setCities12.get(i).getX()))
						+ ((setCities12.get(j).getY() - setCities12.get(i).getY())
								* (setCities12.get(j).getY() - setCities12.get(i).getY())));
				DecimalFormat df = new DecimalFormat();
				df.setMaximumFractionDigits(2);
				distMatrix[i][j] = dist;
			}

		}

	}
}
