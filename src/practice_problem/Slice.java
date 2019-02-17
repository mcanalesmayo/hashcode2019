package practice_problem;

public class Slice {

	public enum ExpansionTryResult{
		SUCCESS,
		OUT_OF_BOUNDS,
		TOO_BIG,
		ALREADY_USED
	}
	
	private int nTomatoes;
	private int nMushrooms;
	private int size;
	
	private int r0;
	private int c0;
	private int r1;
	private int c1;
	
	private Pizza pizza;
	
	public Slice(final int r0, final int c0, final Pizza pizza) {
		if (pizza.getIngredients()[r0][c0] == Pizza.Ingredient.TOMATO) {
			this.nTomatoes = 1;
			this.nMushrooms = 0;
		} else {
			this.nTomatoes = 0;
			this.nMushrooms = 1;
		}
		this.size = 1;
		
		this.r0 = r0;
		this.c0 = c0;
		this.r1 = r0;
		this.c1 = c0;
		this.pizza = pizza;
	}
	
	public int getSize() {
		return size;
	}
	
	public int[][] getCoordinates() {
		return new int[][]{{r0, c0}, {r1, c1}};
	}
	
	public ExpansionTryResult tryExpandToTheRight() {
		if (r1 + 1 == pizza.getRows()) {
			return ExpansionTryResult.OUT_OF_BOUNDS;
		} else if (isTooBig(getSize() + c1 - c0 + 1)) {
			return ExpansionTryResult.TOO_BIG;
		} else if (pizza.isAlreadyUsed(r1 + 1, c1)) {
			return ExpansionTryResult.ALREADY_USED;
		}
		
		r1++;
		final Pizza.Ingredient[][] pizzaIngredients = pizza.getIngredients();
		for (int j = c0; j <= c1; j++) {
			if (pizzaIngredients[r1][j] == Pizza.Ingredient.TOMATO) {
				nTomatoes++;
			} else {
				nMushrooms++;
			}
		}
		size += c1 - c0 + 1;
		
		return ExpansionTryResult.SUCCESS;
	}
	
	public ExpansionTryResult tryExpandToTheBottom() {
		if (c1 + 1 == pizza.getCols()) {
			return ExpansionTryResult.OUT_OF_BOUNDS;
		} else if (isTooBig(getSize() + r1 - r0 + 1)) {
			return ExpansionTryResult.TOO_BIG;
		} else if (pizza.isAlreadyUsed(r1, c1 + 1)) {
			return ExpansionTryResult.ALREADY_USED;
		}
		
		c1++;
		final Pizza.Ingredient[][] pizzaIngredients = pizza.getIngredients();
		for (int i = r0; i <= r1; i++) {
			if (pizzaIngredients[i][c1] == Pizza.Ingredient.TOMATO) {
				nTomatoes++;
			} else {
				nMushrooms++;
			}
		}
		size += c1 - c0 + 1;
		
		return ExpansionTryResult.SUCCESS;
	}
	
	private boolean hasEnoughTomatoes(final int minTomatoes) {
		return nTomatoes >= minTomatoes;
	}
	
	private boolean hasEnoughMushrooms(final int minMushrooms) {
		return nMushrooms >= minMushrooms;
	}
	
	public boolean hasEnoughIngredients(final int minIngredients) {
		return hasEnoughTomatoes(minIngredients) && hasEnoughMushrooms(minIngredients);
	}
	
	public boolean isTooBig(int newSize) {
		return newSize > pizza.getMaxCells();
	}
}
