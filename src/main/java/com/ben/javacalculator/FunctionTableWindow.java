package com.ben.javacalculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Window that generates a data table
 * for the specified function on the graph.
 * @author Ben Rasmussen
 */
public class FunctionTableWindow extends JFrame {
	private JPanel top, contents, bottom;
	private Plot currentPlot;
	private JLabel xAnswer;
	private JTextField xTextField;
	private Font font;
	private int counter;

	public FunctionTableWindow() {
		top = new JPanel(new GridLayout(1,2));
		contents = new JPanel();
		contents.setBorder(BorderFactory.createLineBorder(Color.black));
		bottom = new JPanel(new GridLayout(1,4,3,3));
		font = new Font(Font.SANS_SERIF, Font.BOLD, 15);
		counter = 1;

		xTextField = new JTextField(4);
		xTextField.addKeyListener(new EnterKeyListener());
		xAnswer = new JLabel();
		bottom.add(new JLabel("x = ")); bottom.add(xTextField);
		bottom.add(new JLabel("y = ")) ;bottom.add(xAnswer);

		JLabel xLabel = new JLabel("x"); xLabel.setBorder(BorderFactory.createLineBorder(Color.black));
		JLabel yLabel = new JLabel("y"); yLabel.setBorder(BorderFactory.createLineBorder(Color.black));
		top.add(xLabel); top.add(yLabel);

		contents.add(new JLabel("select a function"));

		fillMenuBar();
		Container container = this.getContentPane();
		container.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
		container.add(top, BorderLayout.NORTH);
		container.add(new JScrollPane(contents), BorderLayout.CENTER);
		container.add(bottom, BorderLayout.SOUTH);

		formatText(container, font);

		this.pack();
		this.setVisible(true);
		this.setTitle("Table");
	}

	private void fillContents() {
		contents.removeAll();

		for (Point p: currentPlot.getTablePoints()) {
			JLabel xTemp = new JLabel("" + p.getX());
			xTemp.setBorder(BorderFactory.createLineBorder(Color.black));
			JLabel yTemp = new JLabel("" + p.getY());
			yTemp.setBorder(BorderFactory.createLineBorder(Color.black));

			contents.add(xTemp); contents.add(yTemp);
		}

		int layoutSetting = contents.getComponentCount() / 2;
		contents.setLayout(new GridLayout(layoutSetting, layoutSetting));

		formatText(contents, font);
		this.setTitle(currentPlot.getFunction().toString());
		this.pack();
		this.setSize(getWidth(), 300);
	}

	private void fillMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Functions");

		for (Plot p: Main.graph.getPlots()) {
			menu.add(new FunctionMenuItem(p));
			counter++;
		}

		menuBar.add(menu);
		this.setJMenuBar(menuBar);
	}

	private void formatText(Component component, Font font) {
		component.setFont ( font );
		if ( component instanceof Container ) {
			for ( Component child : ( ( Container ) component ).getComponents () ) {
				formatText(child, font);
			}
		}

		if (component.getClass().equals(JLabel.class)) {
			JLabel center = ((JLabel)component);
			center.setHorizontalAlignment(SwingConstants.CENTER);
			center.setVerticalAlignment(SwingConstants.CENTER);
		}
	}

	private class FunctionMenuItem extends JMenuItem {
		private Plot thisPlot;

		public FunctionMenuItem(Plot p) {
			this.setText("y" + counter + " = " + p.getFunction().toString());
			this.thisPlot = p;

			this.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					currentPlot = thisPlot;
					fillContents();
				}
			});
		}
	}

	private class EnterKeyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			super.keyTyped(e);
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				xAnswer.setText(currentPlot.getFunction().calcValue("x="+xTextField.getText()));
			}
		}
	}
}
