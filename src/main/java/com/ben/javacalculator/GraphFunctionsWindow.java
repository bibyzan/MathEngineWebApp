package com.ben.javacalculator;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Window Class that adds and
 * removes functions to the graph
 * @author Ben Rasmussen
 */
public class GraphFunctionsWindow extends JFrame {
	private Font font;
	private JTextField textField;
	private JPanel midPanel, inputPanel, bottomPanel;
	private int counter;

	public GraphFunctionsWindow() {
		font = new Font(Font.SANS_SERIF, Font.PLAIN, 15);
		textField = new JTextField(12);
		textField.setFont(font);
		textField.addKeyListener(new EnterListener());
		counter = 1;
		midPanel = new JPanel();
		inputPanel = new JPanel();
		bottomPanel = new JPanel();

		JButton removePlots = new JButton("remove unchecked");
		removePlots.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < Main.graph.getPlots().size(); i++) {
					if (!Main.graph.getPlots().get(i).isEnabled()) {
						Main.graph.getPlots().remove(i);
						i--;
					}
				}
				midPanel.removeAll();
				counter = 1;
				for (Plot p: Main.graph.getPlots()) addFunction(p);
				pack();
			}
		});

		for (Plot p: Main.graph.getPlots())
			addFunction(p);

		inputPanel.add(textField);
		bottomPanel.add(removePlots);
		Container container = this.getContentPane();
		container.add(inputPanel, BorderLayout.NORTH);
		container.add(midPanel, BorderLayout.CENTER);
		container.add(bottomPanel, BorderLayout.SOUTH);
		this.pack();
		this.setTitle("y = ");
		this.setVisible(true);
	}

	private void addFunction(Plot p) {
		FunctionCheckbox temp = new FunctionCheckbox(p);
		midPanel.setLayout(new GridLayout(counter, counter, 3, 3));
		midPanel.add(temp);
		counter++;
		this.pack();
	}

	private class FunctionCheckbox extends JCheckBox {
		private Plot thisPlot;

		public FunctionCheckbox(Plot p) {
			super("y" + counter + "= " + p.getFunction().toString());
			this.thisPlot = p;
			this.setFont(font);
			this.setSelected(true);

			this.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					thisPlot.setEnabled(isSelected());
				}
			});
		}
	}

	private class EnterListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			super.keyPressed(e);
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				try {
					Function f = new Function(textField.getText());
					Main.graph.addPlot(f);
					addFunction(Main.graph.getPlots().getLastElement());
					textField.setText("");
				} catch (Exception x) {
					JOptionPane.showMessageDialog(null, "Sorry, try a different way of writing your equation");
					textField.setSelectionStart(0);
					textField.setSelectionEnd(textField.getText().length());
				}
			}
		}
	}
}
