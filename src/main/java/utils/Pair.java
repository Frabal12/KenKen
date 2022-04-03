package utils;

import java.io.Serial;
import java.io.Serializable;

public class Pair<T, E> implements Serializable {
	@Serial
	private static final long serialVersionUID = -413053001478103703L;
	T t;
	E e;

	public Pair(T t, E e) {
		this.t = t;
		this.e = e;
	}

	public String toString() {
		return "(" + t.toString() + "," + e.toString() + ")";
	}


	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Pair || o == null))
			return false;
		if(this==o) return  true;
		@SuppressWarnings("unchecked")
		Pair<T, E> tuple = (Pair<T, E>) o;
		return tuple.e.equals(this.e) && tuple.t.equals(this.t);

	}


	public int hashCode() {
		return t.hashCode() + e.hashCode();
	}

	public T getX() {
		return t;
	}

	public E getY() {
		return e;
	}

	public void setX(T t) {
		this.t = t;
	}

	public void setY(E e) {
		this.e = e;
	}

	public void setAll(T t, E e) {
		this.t = t;
		this.e = e;
	}
}
