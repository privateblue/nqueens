object nqueens1 {
  import math._

  case class V(x: Int, y: Int) {
    def -(v: V): V = V(x - v.x, y - v.y)
  }

  def solve(n: Int): List[V] = attempt(n, grid(n), List())

  // given an expected queen count, a list of still available squares, and a
  // list of queens already found, return a list of additional queens, to
  // form a solution together
  def attempt(n: Int, available: List[V], queens: List[V]): List[V] =
    available match {
      // if no squares remain available, return empty list
      case Nil =>
        List()

      // if we only need one more queen, pick an available square, add it,
      // and return queens
      case c :: rest if queens.size + 1 == n =>
        c :: queens

      // pick an available square
      case c :: rest =>
        // remove all squares from availables which this candidate makes invalid
        val filtered = rest.filterNot(dropBy(c))
        // add candidate to queens and recurse with filtered availables and
        // new set of queens
        val recurse = attempt(n, filtered, c :: queens)
        // check if the recursion returned any solutions, and if so, then
        // return it, otherwise try again without this candidate as available
        if (!recurse.isEmpty) recurse else attempt(n, rest, queens)
    }

  // check if a square is to be dropped from the availables, in relation to a
  // candidate
  def dropBy(c: V)(v: V): Boolean =
    // the square is in the same row or column or diagonal as the candidate
    (c - v).x == 0 || (c - v).y == 0 || abs((c - v).x) == abs((c - v).y)

  def grid(n: Int): List[V] =
    for {
      y <- (0 until n).toList
      x <- (0 until n).toList
    } yield V(x, y)

  def print(queens: List[V]): String =
    if (queens.isEmpty) "No solutions"
    else
      grid(queens.size)
        .map(s => if (queens.contains(s)) 'Q' else '.')
        .sliding(queens.size, queens.size)
        .map(_.mkString(" "))
        .mkString("\n")
}
