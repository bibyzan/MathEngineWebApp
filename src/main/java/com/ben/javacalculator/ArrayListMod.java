package com.ben.javacalculator;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Class made with the intention of
 * overwriting ArrayList methods as needed for this program
 * @author Ben Rasmussen
 */

public class ArrayListMod<E> extends ArrayList<E> {
	public ArrayListMod() {
		super();
	}
	public ArrayListMod(Collection<? extends E> c) {
		super(c);
	}
	public ArrayListMod(E e) {
		super();
		super.add(e);
	}

	public void add(ArrayList<E> newArray) {
		for (E e: newArray) {
			super.add(e);
		}
	}

	public String calcValue(String input) {
		try {
			String str = "";
			for (Object o: super.toArray()) {
				FunctionPart f = ((FunctionPart)o);
				str += f.calcValue(input);
			}
			return str;
		} catch (ClassCastException c) {
			return "woops!";
		}
	}

	@Override
	public String toString() {
		String str = "";
		for (Object o: super.toArray()) {
			str += o;
		}
		return str;
	}

	public E getLastElement() {
		return super.get(super.size()-1);
	}
}
