package nqueens;


import java.util.stream.Stream;

public class V {
  private int x;
  private int y;

  public V(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public V minus(V v) {
    return new V(this.getX() - v.getX(), this.getY() - v.getY());
  }

  public V plus(V v) {
    return new V(this.getX() + v.getX(), this.getY() + v.getY());
  }

  public Stream<V> lineWith(V v, int n) {
    Stream<V> left = this.extend(v, n);
    Stream<V> right = v.extend(this, n);
    return Stream.concat(left, right);
  }

  public Stream<V> extend(V v, int n) {
    if (v.getX() >= 0 && v.getX() < n && v.getY() >= 0 && v.getY() < n) {
      Stream<V> extended = v.extend(v.plus(v.minus(this)), n);
      return Stream.concat(Stream.of(v), extended);
    } else {
      return Stream.empty();
    }
  }

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  public String toString() {
    return "(" + getX() + "," + getY() + ")";
  }

  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (that == null || that.getClass() != this.getClass()) {
      return false;
    }
    V v = (V) that;
    return (v.getX() == this.getX() && v.getY() == this.getY());
  }

  public int hashCode() {
    return 37 * this.getX() + this.getY();
  }

}
