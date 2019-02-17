package practice_problem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class Main {

	private static final String INPUT_FILE_PATH = "practice_problem/datasets/a_example";
	private static final String INPUT_EXTENSION = "in";
	private static final String OUTPUT_FILE_PATH = "a_example";
	private static final String OUTPUT_EXTENSION = "out";
	
	public static void main(String[] args) throws FileNotFoundException {
		final Scanner sc = new Scanner(new File(INPUT_FILE_PATH + "." + INPUT_EXTENSION));
		
		final int rows = sc.nextInt(), cols = sc.nextInt(), minIngredients = sc.nextInt(), maxCells = sc.nextInt();
		sc.nextLine();
		PizzaScanner pizzaScanner = new PizzaScanner(rows, cols), auxPizzaScanner = pizzaScanner.clone();
		Pizza pizza = new Pizza(rows, cols, minIngredients, maxCells, pizzaScanner), auxPizza;
		for (int i = 0; i < rows; i++) {
			final String[] row = sc.nextLine().split("");
			for (int j = 0; j < cols; j++) {
				pizza.setIngredient(i, j, row[j].charAt(0));
			}
		}
		sc.close();
		auxPizza = pizza.clone();
		
		int maxReached = 0, k = 0;
		while (true) {
			final Set<Slice> solution = pizza.computeBestSlices();
			if (maxReached < pizza.getTotalSliced()) {
				maxReached = pizza.getTotalSliced();
				final Iterator<Slice> solutionIt = solution.iterator();
				final int numberOfSlices = solution.size();
				
				final PrintStream ps = new PrintStream(new File(OUTPUT_FILE_PATH + k + "." + OUTPUT_EXTENSION));
				ps.println(numberOfSlices);
				int[][] sliceCoords;
				for (int i = 0; i < numberOfSlices; i++) {
					sliceCoords = solutionIt.next().getCoordinates();
					ps.println(sliceCoords[0][0] + " " + sliceCoords[0][1] + " " + sliceCoords[1][0] + " " + sliceCoords[1][1]);
				}
				ps.close();
				
				k++;
			}
			pizzaScanner = auxPizzaScanner;
			pizza = auxPizza;
			auxPizzaScanner = pizzaScanner.clone();
			auxPizza = pizza.clone();
		}
	}
}
