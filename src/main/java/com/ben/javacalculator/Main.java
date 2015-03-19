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
	public static Graph graph;
	public static InputOutputWindow window;

	public static void main(String[] args) throws FileNotFoundException{
		graph = new Graph(400,400);

		try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
		catch(Exception e) { e.printStackTrace(); }



		window = new InputOutputWindow();
		//GraphCalculationsWindow graphCalculationsWindow = new GraphCalculationsWindow();
		//Scanner reader = new Scanner(System.in);
		//System.out.println(new Function(reader.nextLine()));
		ProgressMonitor monitor = new ProgressMonitor(window, "progress", "progress", 0, 100);
		monitor.setProgress(0);
		for (int i = 1; i <= 100; i++)
			monitor.setProgress(i);
	}
}
