import java.io.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Labyrinth {

	/* Attributes */
	private Graph graph = null;
	private Stack<Node> path = null;
	private Node start;
	private Node end;
	private int bombs;

	/**
	 * Constructor for building a labyrinth from the contents of the input file.
	 * If the input file does not exist, this method should throw a
	 * LabyrinthException. Read below to learn about the format of the input
	 * file.
	 * 
	 * @param inputFile
	 */
	public Labyrinth(String inputFile) throws LabyrinthException {
		path = new Stack<Node>();
		int width;
		int height;
		int rowcount = -1;
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(inputFile)); // read
																				// input												// file
			in.readLine(); // discarding "S" scale factor
			width = Integer.parseInt(in.readLine()); // Width
			height = Integer.parseInt(in.readLine()); // Height
			bombs = Integer.parseInt(in.readLine()); // number of walls can break
			graph = new Graph(width * height);

			for (;;) {
				String line = in.readLine();
				if (line == null) // End of file
				{
					in.close();
					break;
				}
				rowcount++;
				for (int x = 0; x < line.length(); x++) {
					/*
					 * • ’s’: entrance to the labyrinth
					 * • ’e’: exit of the labyrinth
					 * • ’o’: room
					 * • ’h’: horizontal wall 
					 * • ’v’: vertical wall 
					 * • ’-’: horizontal hall
					 * • ’|’: vertical hall
					 * • ’ ’: unbreakable, solid rock
					 */
					switch (line.charAt(x)) {
					case 'h':
						graph.insertEdge(graph.getNode(((rowcount/2)*(width))+((x-1)/2)),graph.getNode(((rowcount/2)*(width))+((x+1)/2)),"wall");
						break;
					case 'v':
						graph.insertEdge(graph.getNode((((rowcount-1)/2)*(width))+((x)/2)),graph.getNode((((rowcount+1)/2)*(width))+((x)/2)),"wall");
						break;
					case 's':
						start = graph.getNode(((rowcount/2)*(width))+((x)/2));
						break;
					case 'e':
						end = graph.getNode(((rowcount/2)*(width))+((x)/2));
						break;
					case ' ':
						break;
					case '-':
						graph.insertEdge(graph.getNode(((rowcount/2)*(width))+((x-1)/2)),graph.getNode(((rowcount/2)*(width))+((x+1)/2)),"hall");
						break;
					case '|':
						graph.insertEdge(graph.getNode((((rowcount-1)/2)*(width))+((x)/2)),graph.getNode((((rowcount+1)/2)*(width))+((x)/2)),"hall");
						break;
					case 'o':
						break;
					}// end of switch
				}// end of for
			} // end infinite for
		} // end try
		catch (FileNotFoundException e) {
			throw new LabyrinthException("Input file does not exist");
		} catch (GraphException e) {
			throw new LabyrinthException("Graph Exception Error");
		} catch (IOException e) {
			throw new LabyrinthException("IO Exception");
		}
	} // end labyrinth

	/**
	 * returns a reference to the graph representing the labyrinth. Throws a
	 * Labyrinth Exception if the graph is not defined.
	 * 
	 * @return Returns a reference to the graph representing the labyrinth
	 */
	public Graph getGraph() throws LabyrinthException
	{
		if (graph == null)
			throw new LabyrinthException("graph is not defined");
		else
			return graph;
	}

	/**
	 * returns a java Iterator containing the nodes along the path from the
	 * entrance to the exit of the labyrinth, if such a path exists. If the path
	 * does not exist, this method returns the value null.
	 * 
	 * @return Returns a java Iterator containing the nodes along the path from
	 *         the entrance to the exit of the labyrinth, if such a path exists.
	 *         If the path does not exist, this method returns the value null.
	 */
	public Iterator<Node> solve()
	{
		path(start, end);
		
		if(path.size() ==0)
			return null;
		else
			return path.iterator();
	}

	private boolean path(Node starting, Node end) {
		starting.setMark(true); // visit start
		path.push(starting);

		try {
			if (starting == end) //if node is end node
			{
				return true;
			}

			Iterator<Edge> edges = graph.incidentEdges(starting); //get all the edges of the starting point
			while (edges.hasNext())
			{
				Edge edge = edges.next(); //get first edge
				if (edge.getLabel() != "DISCOVERY") // if edge isn't discovery
				{
					if (edge.secondEndpoint().getMark() != true) //if second end point isn't marked
					{
						if ((bombs > 0 && edge.getType() == "wall")|| edge.getType() == "hall")
						{
							if (edge.getType() == "wall")
							{
								bombs--; 
							}
							/*Set both edges in the matrix (ex. 0-1 and 1-0)*/
							edge.setLabel("DISCOVERY");
							graph.getEdge(edge.secondEndpoint(),edge.firstEndpoint()).setLabel("DISCOVERY"); 
							if (path(edge.secondEndpoint(), end) == true)
								return true;
						}
					}
				}
			} //end while
			
			path.pop(); //node is, or leads, to a dead end
			Iterator<Edge> edges2 = graph.incidentEdges(starting);
			while (edges2.hasNext())
			{
				Edge edge2 = edges2.next();
				if (edge2.getLabel() == "DISCOVERY")
				{
					if (edge2.getType() == "wall")
					{
						bombs++;
					}
					edge2.setLabel(""); //undo discovery edge
					
					/*Set both edges in the matrix (ex. 0-1 and 1-0)*/
					starting.setMark(false);
					graph.getEdge(edge2.secondEndpoint(), edge2.firstEndpoint()).setLabel("");
					break;
				}
			}
			return false;
		}
		
		catch (Exception e)
		{
			System.out.println("Exception");
			return false;
		}
	}
}