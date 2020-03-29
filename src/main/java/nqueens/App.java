package nqueens;

public class App {
  public static void main(String[] args) {
    int n = Integer.parseInt(args[0]);
    Solver solver = new Solver(n);
    String result = Solver.print(solver.solve());
    System.out.println(result);
  }
}
