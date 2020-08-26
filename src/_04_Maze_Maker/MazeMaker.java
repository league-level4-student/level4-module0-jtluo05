package _04_Maze_Maker;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MazeMaker {

	private static int width;
	private static int height;

	private static Maze maze;

	private static Random randGen = new Random();
	private static Stack<Cell> uncheckedCells = new Stack<Cell>();

	public static Maze generateMaze(int w, int h) {
		width = w;
		height = h;
		maze = new Maze(width, height);

		Cell cell1=maze.getCell(randGen.nextInt(width),0);
		cell1.setNorthWall(false);
		Cell cell2=maze.getCell(randGen.nextInt(width),height-1);
		cell2.setSouthWall(false);
		
		// 4. select a random cell to start
		Random r1 = new Random();
		Random r2 = new Random();
		int one = r1.nextInt(width);
		int two = r2.nextInt(height);
		// 5. call selectNextPath method with the randomly selected cell
		selectNextPath(maze.cell[one][two]);

		return maze;
	}

	// 6. Complete the selectNextPathMethod
	private static void selectNextPath(Cell currentCell) {
		
		
		// A. mark cell as visited
		currentCell.hasBeenVisited();
		// B. Get an ArrayList of unvisited neighbors using the current cell and the
		// method below
		ArrayList<Cell> a = getUnvisitedNeighbors(currentCell);
		// C. if has unvisited neighbors,
		if (!a.isEmpty()) {

			// C1. select one at random.
			Random r1 = new Random();
			Random r2 = new Random();
			Cell c1 = a.get(r1.nextInt(a.size()));
		
			// C2. push it to the stack
			uncheckedCells.push(c1);
			// C3. remove the wall between the two cells
			removeWalls(c1, currentCell);
			// C4. make the new cell the current cell and mark it as visited
			currentCell = c1;
			currentCell.setBeenVisited(true);
			// C5. call the selectNextPath method with the current cell
			selectNextPath(currentCell);
		}
		// D. if all neighbors are visited
		if (a.isEmpty()) {
			// D1. if the stack is not empty
			if (!uncheckedCells.isEmpty()) {

				// D1a. pop a cell from the stack
				currentCell = uncheckedCells.pop();
				// D1b. make that the current cell

				// D1c. call the selectNextPath method with the current cell
				selectNextPath(currentCell);
			}
		}

	}

	// 7. Complete the remove walls method.
	// This method will check if c1 and c2 are adjacent.
	// If they are, the walls between them are removed.
	private static void removeWalls(Cell c1, Cell c2) {
		if (c1.getX() - 1 == c2.getX()) {
			c1.setWestWall(false);
			c2.setEastWall(false);
		} else if (c1.getX() + 1 == c2.getX()) {
			c1.setEastWall(false);
			c2.setWestWall(false);
		} else if (c1.getY() - 1 == c2.getY()) {
			c1.setNorthWall(false);
			c2.setSouthWall(false);
		} else if (c1.getY() + 1 == c2.getY()) {
			c1.setSouthWall(false);
			c2.setNorthWall(false);
		}
	}

	// 8. Complete the getUnvisitedNeighbors method
	// Any unvisited neighbor of the passed in cell gets added
	// to the ArrayList
	private static ArrayList<Cell> getUnvisitedNeighbors(Cell c) {
		ArrayList<Cell> cell = new ArrayList<Cell>();
		if (c.getX() < width - 1) {

			if (!maze.getCell(c.getX() + 1, c.getY()).hasBeenVisited()) {
				cell.add(maze.getCell(c.getX() + 1, c.getY()));
			}
		}
		if (c.getX() > 0) {

			if (!maze.getCell(c.getX() - 1, c.getY()).hasBeenVisited()) {
				cell.add(maze.getCell(c.getX() - 1, c.getY()));
			}
		}
		if (c.getY() < height - 1) {
			if (!maze.getCell(c.getX(), c.getY() + 1).hasBeenVisited()) {
				cell.add(maze.getCell(c.getX(), c.getY() + 1));
			}
		}
		if (c.getY() > 0) {
			if (!maze.getCell(c.getX(), c.getY() - 1).hasBeenVisited()) {
				cell.add(maze.getCell(c.getX(), c.getY() - 1));
			}
		}
		return cell;
	}
}
