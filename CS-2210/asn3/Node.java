public class Node {

	/* Attributes */

	private int name;
	private boolean mark;

	/**
	 * The constructor for the class. Creates an unmarked node with the given
	 * name. The name of a node is an integer value between 0 and n-1, where n
	 * is the number of vertices in the graph to which the node belongs.
	 * 
	 * @param name
	 */
	public Node(int name) {
		this.name = name;
	}

	/**
	 * marks the node with the specified value.
	 * 
	 * @param mark
	 */
	public void setMark(boolean mark) {
		this.mark = mark;
	}

	/**
	 * returns the value with which the node has been marked
	 * 
	 * @return Returns the value with which the node has been marked
	 */
	public boolean getMark() {
		return mark;
	}

	/**
	 * returns the name of the vertex
	 * 
	 * @return Returns the name of the vertex
	 */
	public int getName() {
		return name;
	}
}
