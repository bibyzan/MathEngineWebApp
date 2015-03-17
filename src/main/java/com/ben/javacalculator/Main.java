package com.ben.javacalculator;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Tester class for the  mathematical computation engine
 * called the TI Killer.
 * @author Ben Rasmussen
 */
public class Main {
	public static double[] variables;
	public static Graph graph;

	public static void main(String[] args) throws FileNotFoundException{
		variables = new double[127];
		for (double v: variables) v = 0;
		graph = new Graph(400,400);

		try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
		catch(Exception e) { e.printStackTrace(); }

		System.out.println(new Function("5xy+5z").getDimensions());

		InputOutputWindow window = new InputOutputWindow();
		//GraphCalculationsWindow graphCalculationsWindow = new GraphCalculationsWindow();
		//Scanner reader = new Scanner(System.in);
	}
}
