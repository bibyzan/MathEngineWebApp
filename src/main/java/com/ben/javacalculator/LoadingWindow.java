package com.ben.javacalculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by benra on 3/18/2015.
 */
public class LoadingWindow extends JFrame {
	private JLabel status;
	private int incrementer;

	public LoadingWindow(final int max, final String topic, int i) {
		super("Generating Plot");
		JPanel panel = new JPanel(new GridLayout(2,2));
		status = new JLabel();
		this.incrementer = i;
		panel.add(new JLabel("Loading: "));

		Timer timer = new Timer(1000, e -> status.setText(incrementer + " " + topic + " Loaded of: " + max));
		timer.start();

		setSize(250,150);
		add(panel);
		setVisible(true);
	}
}
