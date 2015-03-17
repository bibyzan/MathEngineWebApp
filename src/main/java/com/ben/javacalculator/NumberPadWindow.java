package com.ben.javacalculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Optional window made for inputting
 * numbers into a JTextField without a keyboard
 * @author Ben Rasmussen
 */
public class NumberPadWindow extends JFrame {
	JTextField modifier;

	public NumberPadWindow(JTextField textField) {
		JPanel panel = new JPanel(new GridLayout(5,5,3,3));
		modifier = textField;
		ArrayListMod<InputButton> inputButtons = new ArrayListMod<InputButton>();
		for (int i = 0; i < 10; i++)
			inputButtons.add(new InputButton("" + i));

		for (String s: new String[]{"+","-","*","/","(",")","^","=",";",","})
			inputButtons.add(new InputButton(s));

		for (InputButton b: inputButtons)
			panel.add(b);

		this.add(panel);
		this.setSize(250,200);
		this.setTitle("Number Pad");
		this.setVisible(true);
	}

	private class InputButton extends JButton {
		public InputButton (String label) {
			super(label);
			this.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
			this.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					modifier.setText(modifier.getText() + e.getActionCommand());
					modifier.requestFocus();
				}
			});
		}
	}
}
