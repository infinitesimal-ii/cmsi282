package pathfinder.uninformed;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Map;

/**
 * Group Members: Jenna Berlinberg, Donovan Evangelio, Rachel Nguyen
 * 
 * Maze Pathfinding algorithm that implements a basic, uninformed, breadth-first
 * tree search.
 */
public class Pathfinder {

	/**
	 * Given a MazeProblem, which specifies the actions and transitions available in
	 * the search, returns a solution to the problem as a sequence of actions that
	 * leads from the initial to a goal state.
	 * 
	 * @param problem A MazeProblem that specifies the maze, actions, transitions.
	 * @return An ArrayList of Strings representing actions that lead from the
	 *         initial to the goal state, of the format: ["R", "R", "L", ...]
	 */
	public static ArrayList<String> solve(MazeProblem problem) {
		// TODO: Initialize frontier -- what data structure should you use here for
		// breadth-first search? Recall: The frontier holds SearchTreeNodes!
		Queue<SearchTreeNode> frontier = new LinkedList<SearchTreeNode>();
		// TODO: Add new SearchTreeNode representing the problem's initial state to the
		// frontier. Since this is the initial state, the node's action and parent will
		// be null
		frontier.add(new SearchTreeNode(problem.INITIAL_STATE, null, null));
		// TODO: Loop: as long as the frontier is not empty...
		while (!frontier.isEmpty()) {
			// TODO: Get the next node to expand by the ordering of breadth-first search
			SearchTreeNode node = frontier.remove();
			// TODO: If that node's state is the goal (see problem's isGoal method),
			// you're done! Return the solution
			// [Hint] Use a helper method to collect the solution from the current node!
			if (problem.isGoal(node.state)) {
				return getSolution(node);
			}
			// TODO: Otherwise, must generate children to keep searching. So, use the
			// problem's getTransitions method from the currently expanded node's state...
			Map<String, MazeState> actions = problem.getTransitions(node.state);
			// TODO: ...and *for each* of those transition states...
			// [Hint] Look up how to iterate through <key, value> pairs in a Map -- an
			// example of this is already done in the MazeProblem's getTransitions method
			for (Map.Entry<String, MazeState> action : actions.entrySet()) {
				// TODO: ...add a new SearchTreeNode to the frontier with the appropriate
				// action, state, and parent
				frontier.add(new SearchTreeNode(action.getValue(), action.getKey(), node));
			}
		}
		// Should never get here, but just return null to make the compiler happy
		return null;
	}

	private static ArrayList<String> getSolution(SearchTreeNode goalNode) {
		ArrayList<String> sequence = new ArrayList<String>();
		SearchTreeNode current = goalNode;
		while (current.action != null) {
			// Prepending to the sequence gives the correct order at the end
			// since we begin traversal from the goal state.
			sequence.add(0, current.action);
			current = current.parent;
		}
		return sequence;
	}

}

/**
 * SearchTreeNode that is used in the Search algorithm to construct the Search
 * tree.
 */
class SearchTreeNode {

	MazeState state;
	String action;
	SearchTreeNode parent;

	/**
	 * Constructs a new SearchTreeNode to be used in the Search Tree.
	 * 
	 * @param state  The MazeState (col, row) that this node represents.
	 * @param action The action that *led to* this state / node.
	 * @param parent Reference to parent SearchTreeNode in the Search Tree.
	 */
	SearchTreeNode(MazeState state, String action, SearchTreeNode parent) {
		this.state = state;
		this.action = action;
		this.parent = parent;
	}

}