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

		// 4. select a random cell to start
		Random r1 = new Random(width);
		Random r2 = new Random(height);
		int one = r1.nextInt();
		int two = r2.nextInt();
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
			Random r1 = new Random(width);
			Random r2 = new Random(height);
			int one = r1.nextInt();
			int two = r2.nextInt();
			// C2. push it to the stack
			uncheckedCells.push(maze.cell[one][two]);
			// C3. remove the wall between the two cells
			removeWalls(maze.cell[one][two], currentCell);
			// C4. make the new cell the current cell and mark it as visited
			currentCell = maze.cell[one][two];
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
		} else if (c1.getX() + 1 == c2.getX()) {
			c1.setEastWall(false);
		} else if (c1.getY() - 1 == c2.getY()) {
			c1.setNorthWall(false);
		} else if (c1.getY() + 1 == c2.getY()) {
			c1.setSouthWall(false);
		}
	}

	// 8. Complete the getUnvisitedNeighbors method
	// Any unvisited neighbor of the passed in cell gets added
	// to the ArrayList
	private static ArrayList<Cell> getUnvisitedNeighbors(Cell c) {
		ArrayList<Cell> cell = new ArrayList<Cell>();
		if (!maze.getCell(c.getX() + 1, c.getY()).hasBeenVisited()) {
			cell.add(maze.getCell(c.getX() + 1, c.getY()));
		}
		if (!maze.getCell(c.getX() -1 , c.getY()).hasBeenVisited()) {
			cell.add(maze.getCell(c.getX() - 1, c.getY()));
		}
		if (!maze.getCell(c.getX(), c.getY()+1).hasBeenVisited()) {
			cell.add(maze.getCell(c.getX(), c.getY()+1));
		}
		if (!maze.getCell(c.getX(), c.getY()-1).hasBeenVisited()) {
			cell.add(maze.getCell(c.getX() , c.getY()-1));
		}

		return cell;
	}
}
