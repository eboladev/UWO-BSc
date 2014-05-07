/**
 * @author Matthew Stokes
 *
 *  Represents the situation in which a collection is empty.
 */

public class SearchException extends Exception
{
  /**
   * Sets up this exception with an appropriate message.
   * @param collection String representing the name of the collection
   */
  public SearchException (String collection)
  {
    super ("The " + collection + " is empty.");
  }
}
