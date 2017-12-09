
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
 /*Program to implement Held-Karp(Dynamic Programming method) 
 for Travelling Saleman Problem*/

public class TravelingSalesmanHeldKarp {

	private static int MAXVAL = Integer.MAX_VALUE;

	public double getMinCost(double[][] distance) {
		Map<Index, Double> minCostDP = new HashMap<>();
		Map<Index, Integer> parent = new HashMap<>();

		List<Set<Integer>> allSets = generateCombination(distance.length - 1);
		for (Set<Integer> set : allSets) {
			for (int currentVertex = 1; currentVertex < distance.length; currentVertex++) {
				if (set.contains(currentVertex)) {
					continue;
				}
				Index index = Index.createNewIndex(currentVertex, set);
				double minCost = MAXVAL;
				int minPrevVertex = 0;
				Set<Integer> copySet = new HashSet<>(set);
				for (int prevVertex : set) {
					double cost = distance[prevVertex][currentVertex] + getCost(copySet, prevVertex, minCostDP);
					if (cost < minCost) {
						minCost = cost;
						minPrevVertex = prevVertex;
					}
				}
				if (set.size() == 0) {
					minCost = distance[0][currentVertex];
				}
				minCostDP.put(index, minCost);
				parent.put(index, minPrevVertex);
			}
		}

		Set<Integer> set = new HashSet<>();
		for (int i = 1; i < distance.length; i++) {
			set.add(i);
		}
		double min = Integer.MAX_VALUE;
		int prevVertex = -1;
		Set<Integer> copySet = new HashSet<>(set);
		for (int i : set) {
			double cost = distance[i][0] + getCost(copySet, i, minCostDP);
			if (cost < min) {
				min = cost;
				prevVertex = i;
			}
		}

		parent.put(Index.createNewIndex(0, set), prevVertex);
		getTour(parent, distance.length);
		return min;
	}

	private void getTour(Map<Index, Integer> parent, int totalVertices) {
		Set<Integer> set = new HashSet<>();
		for (int i = 0; i < totalVertices; i++) {
			set.add(i);
		}
		Integer start = 0;
		Deque<Integer> stack = new LinkedList<>();
		while (true) {
			stack.push(start);
			set.remove(start);
			start = parent.get(Index.createNewIndex(start, set));
			if (start == null) {
				break;
			}
		}
		ArrayList<String> lst = new ArrayList<String>();
		stack.forEach(v -> lst.add(String.valueOf(v)));
		System.out.println("\nTSP tour of size :" + totalVertices);
		System.out.println(lst.toString());
	}

	private double getCost(Set<Integer> set, int prevVertex, Map<Index, Double> minCostDP) {
		set.remove(prevVertex);
		Index index = Index.createNewIndex(prevVertex, set);
		double cost = minCostDP.get(index);
		set.add(prevVertex);
		return cost;
	}

	private List<Set<Integer>> generateCombination(int n) {
		int input[] = new int[n];
		for (int i = 0; i < input.length; i++) {
			input[i] = i + 1;
		}
		List<Set<Integer>> allSets = new ArrayList<>();
		int result[] = new int[input.length];
		generateCombination(input, 0, 0, allSets, result);
		Collections.sort(allSets, new sizeComparator());
		return allSets;
	}

	private void generateCombination(int input[], int start, int pos, List<Set<Integer>> allSets, int result[]) {
		if (pos == input.length) {
			return;
		}
		Set<Integer> set = createSet(result, pos);
		allSets.add(set);
		for (int i = start; i < input.length; i++) {
			result[pos] = input[i];
			generateCombination(input, i + 1, pos + 1, allSets, result);
		}
	}

	private static Set<Integer> createSet(int input[], int pos) {
		if (pos == 0) {
			return new HashSet<>();
		}
		Set<Integer> set = new HashSet<>();
		for (int i = 0; i < pos; i++) {
			set.add(input[i]);
		}
		return set;
	}

	private static class sizeComparator implements Comparator<Set<Integer>> {
		@Override
		public int compare(Set<Integer> o1, Set<Integer> o2) {
			return o1.size() - o2.size();
		}
	}
}
