package com.ben.javacalculator;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by benra_000 on 3/6/2015.
 */
public class TextFileWindow extends JFrame {
	public TextFileWindow(String file) {
		try {
			JPanel panel = new JPanel();
			Scanner reader = new Scanner(new File(file + ".txt"));

			while(reader.hasNext()) {
				JLabel temp = new JLabel("	" + reader.nextLine());
				temp.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
				panel.add(temp);
			}

			reader.close();

			panel.setLayout(new GridLayout(panel.getComponentCount(), panel.getComponentCount()));
			this.add(new JScrollPane(panel));
			this.setVisible(true);
			this.setSize(650,700);
			this.setTitle(file + ".txt");
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Couldn't find " + file);
		}
	}
}
