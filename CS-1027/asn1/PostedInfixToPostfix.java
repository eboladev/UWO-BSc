import java.util.StringTokenizer;

public class PostedInfixToPostfix {

  private final String ADD = "+";
  private final String SUBTRACT = "-";
  private final String MULTIPLY = "*";
  private final String DIVIDE = "/";
  
  private final String BRACKETOPEN ="(";
  private final String BRACKETCLOSED = ")";
  
  
  private final int ADD_PREC = 2;
  private final int MUL_PREC = 4;
  
  private ArrayStack<String> stack;

  public PostedInfixToPostfix() {
    stack = new ArrayStack<String>();
  }

  public String translate (String expr) {

    String token, outputString = "" ;
    StringTokenizer tokenizer = new StringTokenizer (expr);

    while (tokenizer.hasMoreTokens()) {
      token = tokenizer.nextToken();
      System.out.println("token is " + token) ;
      
      if (!isOperator(token)) {
        outputString += token + " " ;
      }
      else {
        if (stack.isEmpty()) {
          stack.push(token) ;
        }
        else {
          if (precedence(stack.peek()) > precedence(token)) {
            outputString += stack.pop() + " ";
          }
          stack.push(token) ;
        }
      }
    }
    while (!stack.isEmpty()) {
      outputString += stack.pop() + " " ;
    }
    return outputString ;
  }
  
  private int precedence(String token) {
      
    if (token.equals(ADD) || token.equals(SUBTRACT)) {
      return ADD_PREC ;
    }
    else {
      return MUL_PREC ;
    } 
  }

  private boolean isOperator (String token) {

    return (token.equals(ADD) || token.equals(SUBTRACT) || token.equals(MULTIPLY) || token.equals(DIVIDE));
  }
}
  
