import java.util.Iterator;
import java.util.ArrayList;

public class Graph {

	/*Attributes*/
	private Edge[][] graph;
	private Node[] nodeArray;
	private int size=0;
	

	/**
	 * The constructor for the class. Creates an empty graph with n nodes and no
	 * edges. The names of the nodes are 0, 1, . . . , n-1
	 * 
	 * @param n
	 */
	public Graph(int n) { 
			
		graph = new Edge[n][n]; //creates new 2d array
		nodeArray = new Node[n];	//creates new array for nodes
		for(int x = 0;x<n;x++)	// loops and inserts all nodes into the array
		{
		nodeArray[x] = new Node(x);
		}
		size = n;
	}

	/**
	 * adds to the graph an edge connecting u and v. The type for this new edge
	 * is as indicated by the last parameters. The label of the edge is the
	 * empty String. This method throws a GraphException if either node does not
	 * exist or if there is already an edge connecting the given vertices.
	 * 
	 * @param u
	 * @param v
	 * @param edgeType
	 */
	public void insertEdge(Node u, Node v, String edgeType)throws GraphException {
		if(u.getName()>=size) //node u does not exist
		{
			throw new GraphException(u + "does not exist");
		}
		if(v.getName()>=size) //node v does not exist
		{
			throw new GraphException(v + "does not exist");
		}
		if(graph[u.getName()][v.getName()] != null) //spot already has an edge element
		{
			throw new GraphException("already an edge connecting the given verticies");
		}
		else //empty spot in 2d graph array
		{
			graph[u.getName()][v.getName()] = new Edge(u,v,edgeType); //create new edge
			graph[v.getName()][u.getName()] = new Edge(v,u,edgeType); //create new edge
		}
	}

	/**
	 * returns the node with the specified name. If no node with this name
	 * exists, the method should throw a GraphException.
	 * 
	 * @param name
	 * @return Returns the node with the specified name
	 */
	public Node getNode(int name) throws GraphException {

		if(name>=size) //check if node exists
		{
			throw new GraphException("no node with this name exists");
		}
		else
		{
			return nodeArray[name];
		}
	}

	/**
	 * returns a Java Iterator storing all the edges incident on node u. It
	 * returns null if u does not have any edges incident on it. 
	 * Throw a GraphException if u is not a node of the graph.
	 * 
	 * @param u
	 * @return Returns a Java Iterator storing all the edges incident on node u
	 */
	public Iterator<Edge> incidentEdges(Node u) throws GraphException {
	
		if(u.getName()>=size) //node u does not exist
		{
			throw new GraphException(u + "does not exist");
		}
		
	    ArrayList<Edge> arrayList = new ArrayList<Edge>();
	    int count=0;
		for(int x=0;x<size;x++)
		{	    
			if(graph[u.getName()][x]==null) //if no edge
			{
				count++;
			}
			else
			arrayList.add(graph[u.getName()][x]); //add edge to list
		}
		if(count == size) //if no edges
		{
			return null;
		}
		else
		return arrayList.iterator(); 
		
	}

	/**
	 * returns the edge connecting nodes u and v. This method throws a
	 * GraphException if there is no edge between u and v. 
	 * Throw a GraphException if u or v are not nodes of the graph.
	 * 
	 * @param u
	 * @param v
	 * @return Returns the edge connecting nodes u and v
	 */
	public Edge getEdge(Node u, Node v) throws GraphException {
	
		if(u.getName()>=size) //node u does not exist
		{
			throw new GraphException(u + "does not exist");
		}
		if(v.getName()>=size) //node v does not exist
		{
			throw new GraphException(v + "does not exist");
		}
		
		if(graph[u.getName()][v.getName()] != null) //spot already has an edge element
		{
			return graph[u.getName()][v.getName()];
		}
		else //empty spot in 2d graph array
		{
			throw new GraphException("no edge between u and v");
		}
	}

	/**
	 * returns true if and only if nodes u and v are adjacent. 
	 * throw a GraphException if u or v are not nodes of the graph.
	 * 
	 * @param u
	 * @param v
	 * @return Returns true if and only if nodes u and v are adjacent
	 */
	public boolean areAdjacent(Node u, Node v) throws GraphException {
		if(u.getName()>=size) //node u does not exist
		{
			throw new GraphException(u + "does not exist");
		}
		if(v.getName()>=size) //node v does not exist
		{
			throw new GraphException(v + "does not exist");
		}
		if(graph[u.getName()][v.getName()] != null) //spot already has an edge element
		{
			return true;
		}
		else
		return false;
	}
	
}//end
