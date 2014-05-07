public class Edge {

	/* Attributes */
	private String label;
	private String type;
	private Node u;
	private Node v;

	/**
	 * The constructor for the class. The first two parameters are the end points
	 * of the edge. The last parameter is the type of the edge, which for this
	 * project can be either “hall” or “wall”. Each edge will also have a String
	 * label. When an edge is created this label is initially set to the empty
	 * String.
	 * 
	 * @param u
	 * @param v
	 * @param type
	 */
	public Edge(Node u, Node v, String type) {
		this.u = u;
		this.v = v;
		this.type = type;
		label = "";
	}

	/**
	 * returns the first end point of the edge
	 * 
	 * @return Returns the first end point of the edge
	 */
	public Node firstEndpoint() {
		return u;
	}

	/**
	 * returns the second end point of the edge
	 * 
	 * @return Returns the second end point of the edge
	 */
	public Node secondEndpoint() {
		return v;
	}

	/**
	 * returns the type of the edge. As mentioned above, the type of an edge is
	 * one of these 2 possible strings: “hall” or “wall”
	 * 
	 * @return returns the type of the edge.The type of an edge is one of these
	 *         2 possible strings: “hall” or “wall”
	 */
	public String getType() {
		return type;
	}

	/**
	 * sets the label of the edge to the specified value
	 * 
	 * @param label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * gets the specified value of the edge label
	 * 
	 * @return Returns the label of the edge
	 */
	public String getLabel() {
		return label;
	}
}
