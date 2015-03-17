package com.ben.javacalculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Window that adjusts scale and bounds
 * for the current graph.
 * @author Ben Rasmussen
 */
public class GraphSettingsWindow extends JFrame {
	private JTextField xMin, xMax, xScale, yMin, yMax, yScale;

	public GraphSettingsWindow() {
		xMin = new JTextField("" + Main.graph.getLeftBound());
		xMax = new JTextField("" + Main.graph.getRightBound());
		xScale = new JTextField("" + Main.graph.getxScale());
		yMin = new JTextField("" + Main.graph.getBottomBound());
		yMax = new JTextField("" + Main.graph.getTopBound());
		yScale = new JTextField("" + Main.graph.getyScale());

		JPanel panel = new JPanel(new GridLayout(6,6,5,5));
		panel.add(new JLabel("x Min")); panel.add(xMin);
		panel.add(new JLabel("x Max")); panel.add(xMax);
		panel.add(new JLabel("x Scale")); panel.add(xScale);
		panel.add(new JLabel("y Min")); panel.add(yMin);
		panel.add(new JLabel("y Max")); panel.add(yMax);
		panel.add(new JLabel("y Scale")); panel.add(yScale);

		JButton set = new JButton("Set");
		set.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				packWindowSettings();
			}
		});

		Container container = this.getContentPane();
		JPanel south = new JPanel(); south.add(set);
		container.add(south, BorderLayout.SOUTH);
		container.add(panel, BorderLayout.CENTER);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
	}

	private void packWindowSettings() {
		double[] settings = new double[6];
		settings[0] = Double.parseDouble(xMin.getText());
		settings[1] = Double.parseDouble(xMax.getText());
		settings[2] = Double.parseDouble(xScale.getText());
		settings[3] = Double.parseDouble(yMin.getText());
		settings[4] = Double.parseDouble(yMax.getText());
		settings[5] = Double.parseDouble(yScale.getText());

		Main.graph.setSettings(settings);
	}
}
