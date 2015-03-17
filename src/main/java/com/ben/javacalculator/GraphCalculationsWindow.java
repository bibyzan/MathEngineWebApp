package com.ben.javacalculator;

import javax.swing.*;
import java.awt.*;

/**
 * Class that performs
 * calculations as requested by the user
 * on one or several functions currently graphed.
 * @author Ben Rasmussen
 */
public class GraphCalculationsWindow extends JFrame {
	ArrayListMod<GraphPanel> graphPanels;

	public GraphCalculationsWindow() {
		graphPanels = new ArrayListMod<GraphPanel>();

		JScrollPane jScrollPane = new JScrollPane();
		jScrollPane.add(new GraphPanel());
		jScrollPane.add(new GraphPanel());

		this.add(jScrollPane);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private class GraphPanel extends JPanel {
		private Graph graph;

		public GraphPanel() {
			this.setSize(400,400);
			graph = new Graph(400,400);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Main.graph.drawSwingGraphics(g);
		}
	}
}
