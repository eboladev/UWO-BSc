public class TreeNode {

	   private DictEntry element;
	   private TreeNode left, right, parent;

		/**
		 * Constructor Binary TreeNode. Creates a new tree node type DictEntry
		 * @param obj
		 */
	public  TreeNode (DictEntry obj)
	   {
	      element = obj;
	      left = null;
	      right = null;
	      parent = null;
	   }  
	
	/**
	 * Getter Method to get TreeNode's right child
	 * @return Returns right child
	 */
	public  TreeNode getRight()
	   {
		   return right;
	   }
	/**
	 * Setter Method to set TreeNode's right child
	 * @param node
	 */
	public void setRight(TreeNode node)
	   {
		  right = node;
	   }
	/**
	 * Getter Method to get TreeNode's left child
	 * @return Returns left child
	 */
	public  TreeNode getLeft()
	   {
		   return left;
	   }
	/**
	 * Setter Method to set TreeNode's left child
	 * @param node
	 */
	public void setLeft(TreeNode node)
	   {
		  left = node;
	   }
	/**
	 * Getter Method to get TreeNode's parent
	 * @return Returns parent
	 */
	public  TreeNode getParent()
	   {
		   return parent;
	   }
	/**
	 * Setter Method to set TreeNode's parent
	 * @param node
	 */
	public void setParent(TreeNode node)
	   {
		  parent = node;
	   }
	/**
	 * Getter Method to get TreeNode's element
	 * @return Returns element
	 */
	public  DictEntry getElement()
	   {
		   return element;
	   }
	/**
	 * Setter Method to set TreeNode's element
	 * @param node
	 */
	public void setElement(DictEntry node)
	   {
		  element = node;
	   }
}
