package nqueens;

import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.Optional;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import static java.lang.Math.abs;

public class Solver {
  private int n;

  public Solver(int n) {
    this.n = n;
  }

  public List<V> solve() {
    return attempt(grid(), Collections.emptyList());
  }

  // given an expected queen count, a list of still available squares, and a
  // list of queens already found, return a list of additional queens, to
  // form a solution together
  public List<V> attempt(List<V> available, List<V> queens) {
    Optional<V> c = available.stream().findFirst();
    // if no squares remain available, return empty list
    if (!c.isPresent()) {
      return Collections.emptyList();

    // if we only need one more queen, pick an available square, add it,
    // and return queens
    } else if (queens.size() + 1 == n) {
      return Stream.concat(Stream.of(c.get()), queens.stream())
        .collect(Collectors.toList());

    // pick an available square
    } else {
      List<V> rest = available.stream().skip(1)
        .collect(Collectors.toList());
      // remove all squares from availables which this candidate makes invalid
      List<V> filtered = rest.stream().filter(v -> !dropBy(c.get(), queens, v))
        .collect(Collectors.toList());
      // add candidate to queens and recurse with filtered availables and
      // new set of queens
      List<V> nqs = Stream.concat(Stream.of(c.get()), queens.stream())
        .collect(Collectors.toList());
      List<V> recurse = attempt(filtered, nqs);
      // check if the recursion returned any solutions, and if so, then
      // return it, otherwise try again without this candidate as available
      if (recurse.size() > 0) {
        return recurse;
      } else {
        return attempt(rest, queens);
      }
    }
  }

  // check if a square is to be dropped from the availables, in relation to a
  // candidate, and a list of queens
  public boolean dropBy(V c, List<V> queens, V v) {
    V d = c.minus(v);
    // the square is in the same row or column or diagonal as the candidate
    boolean isAttacked =
      d.getX() == 0 || d.getY() == 0 || abs(d.getX()) == abs(d.getY());
    // the square is on any of the lines formed by the candidate and each queen
    boolean isLine =
      queens.stream().flatMap(q -> q.lineWith(c, this.n)).anyMatch(q -> q.equals(v));
    return isAttacked || isLine;
  }

  public List<V> grid() {
    List<V> squares = new ArrayList<V>();
    for (int y = 0; y < n; y++) {
      for (int x = 0; x < n; x++) {
        squares.add(new V(x, y));
      }
    }
    return squares;
  }

  public static String print(List<V> queens) {
    int n = (int) queens.size();
    if (n > 0) {
      List<String> squares =
        new Solver(n).grid().stream().map(s -> queens.contains(s) ? "Q" : ".")
          .collect(Collectors.toList());
      String ret = "";
      for (int i = 0; i < n; i++) {
        ret = ret + squares.subList(i * n, i * n + n).stream().collect(Collectors.joining(" ")) + "\n";
      }
      return ret;
    } else {
      return "No solutions";
    }
  }

  public int getN() {
    return this.n;
  }

}
