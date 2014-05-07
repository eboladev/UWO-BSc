import java.util.Scanner;

public class Translate {

  public static void main (String[] args) {
    String expression, again;
    String result;
    
    try {
      Scanner in = new Scanner(System.in); 
      do {
        PostedInfixToPostfix translator = new PostedInfixToPostfix();
        System.out.println ("Enter a valid infix expression: ");
        expression = in.nextLine();

        result = translator.translate(expression);
        System.out.println();
        System.out.println ("This expression in postfix: " + result);

        System.out.print ("Translate another expression [Y/N]? ");
        again = in.nextLine();
        System.out.println();
      }
      while (again.equalsIgnoreCase("y"));
    }
    catch (Exception IOException) {
	  System.out.println("Input exception reported");
    }
  }
}
