package practice_problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Pizza implements Cloneable {

	private static final int MAX_RETRIES = 20;
	
	public enum Ingredient{
		TOMATO,
		MUSHROOM
	}
	public enum ExpandPolicy{
		BOTTOM,
		RIGHT
	}
	
	private final int rows;
	private final int cols;
	private final int minIngredients;
	private final int maxCells;
	private final Ingredient[][] ingredients;
	private final PizzaScanner pizzaScanner;
	private int totalSliced;
	
	public Pizza(final int rows, final int cols, final int minIngredients, final int maxCells, final PizzaScanner pizzaScanner) {
		this.rows = rows;
		this.cols = cols;
		this.minIngredients = minIngredients;
		this.maxCells = maxCells;
		this.ingredients = new Ingredient[rows][cols];
		this.pizzaScanner = pizzaScanner;
		this.totalSliced = 0;
	}
	
	public int getTotalSliced() {
		return totalSliced;
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getCols() {
		return cols;
	}
	
	public int getMaxCells() {
		return maxCells;
	}
	
	public Ingredient[][] getIngredients() {
		return ingredients;
	}
	
	public void setIngredient(final int i, final int j, final char ingredient) {
		setIngredient(i, j, ingredient == 'T' ? Ingredient.TOMATO : Ingredient.MUSHROOM);
	}
	
	public void setIngredient(final int i, final int j, final Ingredient ingredient) {
		ingredients[i][j] = ingredient;
	}
	
	public boolean isAlreadyUsed(final int r, final int c) {
		return pizzaScanner.isAlreadyUsed(r, c);
	}
	
	public Set<Slice> computeBestSlices() {
		final Set<Slice> slices = new HashSet<Slice>();
		
		int retryCount = 0;
		int[] initPos;
		Iterator<Integer> randomExpandPolicyGenerator;
		while ((initPos = pizzaScanner.nextUnusedPos()) != null && retryCount < MAX_RETRIES) {
			final List<ExpandPolicy> expandPolicies = new ArrayList<ExpandPolicy>(Arrays.<ExpandPolicy>asList(ExpandPolicy.values()));
			randomExpandPolicyGenerator = new Random().ints(0, expandPolicies.size()).iterator();
			final Slice slice = new Slice(initPos[0], initPos[1], this);
			
			boolean keepExploring = true;
			while (keepExploring) {
				final ExpandPolicy currExpandPolicy = expandPolicies.get(randomExpandPolicyGenerator.next());
				Slice.ExpansionTryResult res;
				switch (currExpandPolicy) {
				case BOTTOM:
					res = slice.tryExpandToTheBottom();
					break;
				case RIGHT:
				default:
					res = slice.tryExpandToTheRight();
				}
				
				switch(res) {
				case OUT_OF_BOUNDS:
				case ALREADY_USED:
					expandPolicies.remove(currExpandPolicy);
					if (expandPolicies.isEmpty()) {
						keepExploring = false;
					} else {
						randomExpandPolicyGenerator = new Random().ints(0, expandPolicies.size()).iterator();
					}
					break;
				case TOO_BIG:
					keepExploring = false;
					break;
				default:
				}
			}
			
			if (slice.hasEnoughIngredients(minIngredients)) {
				final int[][] sliceCoords = slice.getCoordinates();
				pizzaScanner.markAsUsed(sliceCoords[0][0], sliceCoords[0][1], sliceCoords[1][0], sliceCoords[1][1]);
				slices.add(slice);
				totalSliced += slice.getSize();
				retryCount = 0;
			} else {
				retryCount++;
			}
		}
		
		return slices;
	}
	
	public Pizza clone() {
		final Pizza newPizza = new Pizza(rows, cols, minIngredients, maxCells, pizzaScanner);
		
		for (int i = 0; i < ingredients.length; i++ ) {
			for (int j = 0; j < ingredients[i].length; j++) {
				newPizza.setIngredient(i, j, ingredients[i][j]);
			}
		}
		
		return newPizza;
	}
}
