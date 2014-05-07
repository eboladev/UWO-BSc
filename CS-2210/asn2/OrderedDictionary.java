public class OrderedDictionary implements OrderedDictionaryADT {

	private TreeNode root, node, current, findNodes, foundNode, successorNode;

	/**
	 * Constructor method which initializes an empty dictionary
	 * 
	 */
	public OrderedDictionary() {
		root = new TreeNode(null);
		current = root;
	}

	/**
	 * Returns the definition of the given word, or it returns an empty string
	 * if the word is not in the dictionary.
	 * 
	 * @param word
	 * @return Returns the definition of the given word, or it returns an empty
	 *         string if the word is not in the dictionary.
	 */
	public String findWord(String word) {
		if (root.getElement() == null) //nothing in root
		{
			return "";
		}
		else if (current == null) //not in tree
		{
			return "";
		}
		else {
			
				if (word.compareTo(current.getElement().word()) == 0)
				{
					return current.getElement().definition();
				}
				else if (word.compareTo(current.getElement().word()) > 0) // if greater
				{
						current = current.getRight(); //move to the right
						String x = findWord(word);
						current = root;
						return x;
						
				}
				else //therefore less than
				{
						current = current.getLeft(); //move to the left
						String x = findWord(word);
						current = root;
						return x;
				}
			}
		
		}

	/**
	 * Returns the type of the given word, or it returns -1 if the word is not
	 * in the dictionary.
	 * 
	 * @param word
	 * @return Returns the type of the given word, or it returns -1 if the word
	 *         is not in the dictionary.
	 */
	public int findType(String word)  {
		if (root.getElement() == null)
		{
			return 0;
		}
		else if (current == null) 
		{
			return 0;
		}
		else {
			
				if (word.compareTo(current.getElement().word()) == 0)
				{
					return current.getElement().type();
				}
				else if (word.compareTo(current.getElement().word()) > 0) // if greater
				{
						current = current.getRight(); //move to right
						int x = findType(word);
						current = root;
						return x;
						
				}
				else //if less than
				{
						current = current.getLeft(); //move to left
						int x = findType(word);
						current = root;
						return x;
				}
			}
		
		}

	/**
	 * Adds the given word, its definition and type into the dictionary. It
	 * throws an OrderedDictionaryException if the word is already in the
	 * dictionary.
	 * 
	 * @param word
	 * @param definition
	 * @param type
	 */
	public void insert(String word, String definition, int type)
			throws DictionaryException {

		if (root.getElement() == null) //If empty tree insert as root
		{
			root.setElement(new DictEntry(word, definition, type));
			root.setLeft(null);
			root.setRight(null);
			current = root;
		}

		else if (word.compareTo(current.getElement().word()) == 0) //if word = root.element throw exception
			{
				current = root;
				throw new DictionaryException("Already in Dictionary");
			}
			
		else if (word.compareTo(current.getElement().word()) > 0) // if greater
				{
					if (current.getRight() == null) { //if spot to the right is empty
						current.setRight(new TreeNode(null));
						current.getRight().setElement(new DictEntry(word, definition,type));
						current.getRight().setRight(null);
						current.getRight().setLeft(null);
						current.getRight().setParent(current);
						current = root;
					} else { //spot to the right isn't empty
						current.getRight().setParent(current); //traverse tree to the right
						current = current.getRight();	
						insert(word, definition, type); //recursive call
						current = root;
					}

				}
		else if (word.compareTo(current.getElement().word()) < 0) // if less than
				{
					if (current.getLeft() == null) { //if spot to the left is empty
						current.setLeft(new TreeNode(null));
						current.getLeft().setElement(new DictEntry(word, definition,
								type));
						current.getLeft().setRight(null);
						current.getLeft().setLeft(null);
						current.getLeft().setParent(current);
					} else { //spot to the left isn't empty
						current.getLeft().setParent(current); //traverse tree to the left
						current = current.getLeft();
						insert(word, definition, type); //recursive call
						current = root;
					}
				}
			}
		
	
/*Warning, My remove code is very thick, definitely an easier way to do this, but this way works, just took absolutely forever*/
	/**
	 * Removes the entry with the given word from the dictionary. It throws an
	 * OrderedDictionaryException if the word is not in the dictionary.
	 * 
	 * @param word
	 */
	public void remove(String word) throws DictionaryException {
		
		foundNode = findNode(word);
		
		if(findNode(word) == null) //Word is not in Ordered Dictionary
		{
			throw new DictionaryException(word + " is not in the Dictionary");
		}
		else //word is in Ordered Dictionary
		{
			if(findNode(word).getRight() == null && findNode(word).getLeft() == null) //if findNode has no children
			{
				if(foundNode.getParent() == null) //if root is the only item
				{
					root = new TreeNode(null); 
				}
				//setting pointer to foundNode to null
				else if(foundNode.getParent().getRight() == foundNode)
				{
					foundNode.getParent().setRight(null);
				}
				else if(foundNode.getParent().getLeft() == foundNode)
				{
					foundNode.getParent().setLeft(null);
				}
				foundNode = null;
			}
			else if((findNode(word).getRight() != null || findNode(word).getLeft() != null) && ((findNode(word).getRight() != null && findNode(word).getLeft() != null))==false)
			//if findNode has exactly one child, left or right but not both children 
			{
				String s = successor(word);
				successorNode = findNode(s);
				if(successorNode == null && foundNode.getLeft() != null) 
					//if there is no successor (bottom right of tree) but has left children
				{
					successorNode = foundNode.getLeft();
					successorNode.setParent(foundNode.getParent());
					foundNode.getParent().setRight(successorNode);
					foundNode = null;
				}
				
				else{ //there is a successor located to nodes right
				successorNode.setParent(foundNode.getParent());
				if(foundNode.getParent().getRight() ==foundNode) //setting parent nodes pointer
				foundNode.getParent().setRight(successorNode);
				else if(foundNode.getParent().getLeft() == foundNode) //setting parent nodes pointer
				foundNode.getParent().setLeft(successorNode);
				foundNode = null;
				}
				}
			else if(findNode(word).getRight() != null && findNode(word).getLeft() != null) //2 children
			{
				//here is where things get a bit messy
				String s = successor(word);
				successorNode = findNode(s);
				//if successor node has no children
				if(successorNode.getLeft()==null && successorNode.getRight() ==null)
				{	
					//if successor node is not to the left of foundNode then
					//set successors left node to foundNode's left
					if(successorNode != foundNode.getLeft())
					{
					successorNode.setLeft(foundNode.getLeft());
					foundNode.getLeft().setParent(successorNode);

					}
					//if successor node is not to the right of foundNode then
					//set successors right node to foundNode's right
					if(successorNode!=foundNode.getRight())
					{
					successorNode.setRight(foundNode.getRight());
					foundNode.getRight().setParent(successorNode);
					}
					//set the parent of successorNode to point to null
					if(successorNode != foundNode.getLeft())
						successorNode.getParent().setRight(null);
					if(successorNode!=foundNode.getRight())
						successorNode.getParent().setLeft(null);
						
					//setting the new parent of successor node
					if(foundNode.getParent().getRight() ==foundNode)
						foundNode.getParent().setRight(successorNode);
					else if(foundNode.getParent().getLeft() == foundNode)
						foundNode.getParent().setLeft(successorNode);
					
					
					//set successorNodes parent
					successorNode.setParent(foundNode.getParent());
					foundNode = null;
				}
				else if(successorNode.getRight() != null) //if successor node has right children
				{	
					successorNode.getParent().setLeft(successorNode.getRight());
					successorNode.getRight().setParent(successorNode.getParent());
					
					//if successor node is not to the left of foundNode then
					//set successors left node to foundNode's left
					if(successorNode != foundNode.getLeft())
					{
					successorNode.setLeft(foundNode.getLeft());
					foundNode.getLeft().setParent(successorNode);
					}
					//if successor node is not to the right of foundNode then
					//set successors right node to foundNode's right
					if(successorNode!=foundNode.getRight())
					{
					successorNode.setRight(foundNode.getRight());
					foundNode.getRight().setParent(successorNode);
					}
					//setting the new parent of successor node
					if(foundNode.getParent().getRight() ==foundNode)
						foundNode.getParent().setRight(successorNode);
					else if(foundNode.getParent().getLeft() == foundNode)
						foundNode.getParent().setLeft(successorNode);
					
					//set successorNodes parent
					successorNode.setParent(foundNode.getParent());
					foundNode = null;
				}
			}
		}
	}

	/**
	 * Returns the successor of the given word (the word from the dictionary
	 * that lexicographically follows the given one); it returns an empty string
	 * if the given word has no successor.
	 * 
	 * @param word
	 * @return Returns the successor of the given word (the word from the
	 *         dictionary that lexicographically follows the given one); it
	 *         returns an empty string if the given word has no successor.
	 */
	public String successor(String word) {
		
		current = findNode(word);
		
		if(current == null) //if word not found
			return "";
		if(current.getRight()!=null) //if has successor
		{
			current = current.getRight();//move to right child
			while(current.getLeft()!=null) // then left most child from right child
			{
				current = current.getLeft();
			}
			String a = current.getElement().word();
			current = root; //reseting current
			return a;
		}
		else { //does not have right child
			node = current.getParent();
			while(node!=null && current == node.getRight()) //transverse up the tree
			{ //until current is no longer its parents right child
				current = node;
				node = node.getParent();
			}
			current=root; //reset current
			if(node == null)
				return ""; 
			else
			return node.getElement().word();
		}
	}

	/**
	 * Returns the predecessor of the given word (the word from the dictionary
	 * that lexicographically precedes the given one); it returns an empty
	 * string if the given word has no predecessor.
	 * 
	 * @param word
	 * @return Returns the predecessor of the given word (the word from the
	 *         dictionary that lexicographically precedes the given one); it
	 *         returns an empty string if the given word has no predecessor.
	 */
	public String predecessor(String word) {
		
		current = findNode(word);
		
		if(current == null) //if word not found
			return "";
		if(current.getLeft()!=null) //if has predecessor
		{
			current = current.getLeft(); //move to left child
			while(current.getRight()!=null) // then right most child from left child
			{
				current = current.getRight();
			}
			String a = current.getElement().word();
			current = root; //reseting current
			return a;
		}
		else {
			node = current.getParent();
			while(node!=null && current == node.getLeft()) //transverse up the tree
			{//until current is no longer its parents left child
				
				current = node;
				node = node.getParent();
			}
			current=root; //reset current
			if(node == null)
				return "";
			else
			return node.getElement().word();
		}
	}
	
	/**
	 * Additional Method exactly the same algorithm as findWord except
	 * it returns the node.
	 * 
	 * @param word
	 * @return Returns the TreeNode of the given word, or it returns null
	 * if the word is not in the dictionary.
	 */
	private TreeNode findNode(String word) {
		if (root.getElement() == null)
		{
			return null;
		}
		else if (current == null) 
		{
			return null;
		}
		else {
			
				if (word.compareTo(current.getElement().word()) == 0)
				{
					return current;
				}
				else if (word.compareTo(current.getElement().word()) > 0) 
				// lexographically greater
				{
						current = current.getRight();
						findNodes = findNode(word); //recursive call
						current = root;
						return findNodes;
						
				}
				else //lexographically less than
				{
						current = current.getLeft();
						findNodes = findNode(word); //recursive call
						current = root;
						return findNodes;
				}
			}
		
		}
} // end
