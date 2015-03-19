package com.ben.javacalculator;

import com.sun.deploy.panel.SecurityPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Simple GUI to test the
 * Graph, Plot, LineSegment, and Point classes.
 * @author Ben Rasmussen
 */
public class GraphWindow extends JFrame{
	private GraphPanel graphPanel;
	private JPanel functionsPanel;
	private JButton refresh;
	private JTextField textField;
	private JScrollPane scrollPane;
	private Graph graph;
	private int counter;

	public GraphWindow(Graph g) {
		Container container = this.getContentPane();
		graph = g;
		refresh = new JButton("refresh");
		refresh.addActionListener(e -> graphPanel.repaint());
		graphPanel = new GraphPanel();
		functionsPanel = new JPanel();
		graphPanel.add(refresh);
		counter = 1;

		scrollPane = new JScrollPane(functionsPanel);
		container.add(graphPanel, BorderLayout.CENTER);
		//container.add(scrollPane, BorderLayout.EAST);

		this.pack();
		this.setSize(400, 420);
		this.setLocation(300, 10);
		this.setVisible(true);
		this.setTitle("Graph");
		this.addComponentListener(new ResizeWindowListener());
	}

	private class GraphPanel extends JPanel {
		public GraphPanel() {
			this.setSize(400,400);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			graph.drawSwingGraphics(g);
		}
	}

	private class ResizeWindowListener extends ComponentAdapter {
		@Override
		public void componentResized(ComponentEvent e) {
			super.componentResized(e);
			Main.graph.setCurrentScreen(getWidth(),getHeight()-20);
			graphPanel.repaint();
		}
	}
}
