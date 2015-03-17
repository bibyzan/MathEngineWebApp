package com.ben.javacalculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

/**
 * Simple GUI made to test the Equation class
 * and FunctionPart subclasses.
 * @author Ben Rasmussen
 */
public class InputOutputWindow extends JFrame {
	private JTextField textBox;
	private Equation equation;
	private ArrayListMod<JLabel> outputs;
	private JLabel currentAnswer;
	private JPanel top, center, bottom, right;
	private Container container;

	public InputOutputWindow() {
		container = this.getContentPane();
		container.setLayout(new BorderLayout());
		top = new JPanel();
		bottom = new JPanel(new GridLayout(4,4,5,5));
		right = new JPanel(new GridLayout(7,7,3,3));
		center = new JPanel();
		textBox = new JTextField(14);
		textBox.setText("press enter for solutions");
		textBox.setSelectionStart(0);
		textBox.setSelectionEnd(textBox.getText().length());
		textBox.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
		textBox.addKeyListener(new EnterListener());

		String[] rightPanelButtonStrings = {"Graph", "Table", "Window", "y = ", "NumPad", "Clear-All", "Help/About"};
		for (String s: rightPanelButtonStrings)
			right.add(new RightPanelButton(s));

		outputs = new ArrayListMod<JLabel>();
		equation = new Equation();
		currentAnswer = new JLabel("");
		currentAnswer.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
		JScrollPane centerPane = new JScrollPane(center);

		ArrayListMod<FunctionButton> functionButtons = new ArrayListMod<FunctionButton>();
		String[] functions = {"sin","cos","tan","csc","sec","cot","sqrt","abs","log",
				"arcsin", "arccos", "arctan", "ln", "d/dx", "∫", "π"};
		for (String s: functions) functionButtons.add(new FunctionButton(s));
		for (FunctionButton b: functionButtons) bottom.add(b);

		disableNonFunctional();
		//retrievePreviousOutputs();
		top.add(textBox);
		container.add(top, BorderLayout.NORTH);
		container.add(centerPane, BorderLayout.CENTER);
		container.add(bottom, BorderLayout.SOUTH);
		container.add(right, BorderLayout.EAST);
		this.pack();
		this.setSize(375, 450);
		this.setLocation(300, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setTitle("Java Calculator");
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				//saveOutputs();
			}

			@Override
			public void windowActivated(WindowEvent e) {
				super.windowActivated(e);
				textBox.setSelectionStart(0);
				textBox.setSelectionEnd(textBox.getText().length());
			}
		});
	}

	private void saveOutputs() {
		try {
			FileWriter fw = new FileWriter("OutputLog.txt");
			PrintWriter pw = new PrintWriter(fw);
			for (Component c : center.getComponents()) {
				String output = ((JLabel) c).getText();
				pw.println(output);
			}
			pw.close();

			fw = new FileWriter("FunctionArchive.txt");
			pw = new PrintWriter(fw);

			for (Plot p: Main.graph.getPlots())
				pw.println(p.getFunction().toString());

			pw.close();
		} catch (Exception e) {/*gives up*/}
	}

	private void retrievePreviousOutputs() {
		try {
			Scanner reader = new Scanner(new File("OutputLog.txt"));

			while (reader.hasNext()) {
				JLabel temp = new JLabel(reader.nextLine());
				temp.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));
				if (!temp.getText().equals("empty history"))
					center.add(temp);
			}

			int amount = center.getComponentCount();
			if (amount > 0)
				center.setLayout(new GridLayout(amount, amount, 5, 5));

			reader = new Scanner(new File("FunctionArchive.txt"));

			while (reader.hasNext())
				Main.graph.addPlot(new Function(reader.nextLine()));
		} catch (FileNotFoundException e) {
			center.add(new JLabel("empty history"));
		}
	}

	private void addEquation() {
		JLabel left = new JLabel(equation.toString());

		left.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));

		center.add(left);
		int amount = center.getComponentCount();
		center.setLayout(new GridLayout(amount, amount, 5, 5));
		this.pack();
		this.setSize(400,450);
	}

	private void disableNonFunctional() {
		String[] nonFunctional = {"d/dx", "∫", "π"};

		for (Component component: bottom.getComponents())
			for (String s: nonFunctional)
				if (((FunctionButton)component).getText().equals(s))
					component.setEnabled(false);
	}

	private class FunctionButton extends JButton {
		public FunctionButton(String text) {
			super(text);
			this.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 18));

			this.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getActionCommand().equals("π")) {
						textBox.setText(textBox.getText() + "π");
					} else {
						textBox.setText(textBox.getText() + e.getActionCommand() + "(");
					}
					textBox.requestFocus();
				}
			});
		}
	}

	private class EnterListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			super.keyTyped(e);
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				try {
					equation = new Equation(textBox.getText());
					textBox.setText("");
					currentAnswer.setText(equation.toString());
					addEquation();
				} catch (Exception x) {
					JOptionPane.showMessageDialog(null, "Sorry, try a different way of writing your equation");
					textBox.setSelectionStart(0);
					textBox.setSelectionEnd(textBox.getText().length());
				}
			}

			if (textBox.getText().length() > 14) {
				textBox.setColumns(textBox.getColumns() + 1);
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			super.keyReleased(e);
			if (!checkLegal(e.getKeyChar())) {
				try {
					String temp = textBox.getText();
					if (temp.length() == 1)
						temp = "";
					else
						temp = temp.substring(0, temp.length() - 1);
					textBox.setText(temp);
				} catch (StringIndexOutOfBoundsException s) {/*save*/};
			}
		}

		private boolean checkLegal(char c) {
			int[] notAllowed = {115,105,110,99,111,115,116,97,101,114,108,113,93,103};
			boolean result = true;

			for (int l: notAllowed)
				if (l == (int)c)
					result = false;

			return result;
		}
	}

	private class RightPanelButton extends JButton{
		public RightPanelButton(String name) {
			super(name);
			this.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
			this.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getActionCommand().equals("NumPad")) {
						NumberPadWindow numPad = new NumberPadWindow(textBox);
					} else if (e.getActionCommand().equals("y = ")) {
						GraphFunctionsWindow graphFunctionsWindow = new GraphFunctionsWindow();
					} else if (e.getActionCommand().equals("Graph")) {
						GraphWindow graphWindow = new GraphWindow();
					} else if (e.getActionCommand().equals("Window")) {
						GraphSettingsWindow graphSettingsWindow = new GraphSettingsWindow();
					} else if (e.getActionCommand().equals("Table")) {
						FunctionTableWindow functionTableWindow = new FunctionTableWindow();
					} else if (e.getActionCommand().equals("Clear-All")) {
						for (Frame frame: getFrames())
							frame.setVisible(false);
						center.removeAll();
						Main.graph.getPlots().clear();
						reopen();
					} else if (e.getActionCommand().equals("Help/About")) {
						Desktop dt = Desktop.getDesktop();
						try {
							dt.open( new File("ReadMe.txt"));
						} catch (IOException i) {
							JOptionPane.showMessageDialog(null, "Could not find ReadMe.txt");
						}
					}
				}
			});
		}
	}

	private void reopen() {
		this.setVisible(true);
		this.pack();
		this.setSize(375,450);
	}

	private class LabelListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			for (Component c: center.getComponents()) {
				if (e.getSource() == c) {
					textBox.setText(((JLabel)c).getText());
				}
			}
		}
	}
}
