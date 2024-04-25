package textSave_Load_System;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;


public class TextSaveLoad extends JFrame implements ActionListener{
	
	private static final long serialVersionID = 1L;
	JLabel label;
	JTextArea area;
	JButton save_button, load_button;
	
	public TextSaveLoad() {
		this.setSize(200, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//JLabelの組み込み
		label = new JLabel("Not saved.");
		this.add(label, BorderLayout.NORTH);
		
		//JTextAreaの組み込み
		area = new JTextArea();
		JScrollPane scroll = new JScrollPane(area);
		this.add(scroll,BorderLayout.CENTER);
		
		//JButtonの組み込み
		save_button = new JButton("Save");
		save_button.setSize(100, 25);
		save_button.setLocation(20, 80);
		save_button.addActionListener(this);
		load_button = new JButton("Load");
		load_button.setSize(100, 25);
		load_button.setLocation(200, 80);
		load_button.addActionListener(this);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2));
		panel.add(save_button);
		panel.add(load_button);
		this.add(panel, BorderLayout.SOUTH);
	}
	
	public void actionPerformed(ActionEvent ev) {
		
		//Loadボタンの処理
		if(ev.getSource() == load_button) {
			JFileChooser chooser = new JFileChooser();
			int res = chooser.showOpenDialog(this);
			if(res == JFileChooser.APPROVE_OPTION) {
				String fname = chooser.getSelectedFile().getAbsolutePath();
				area.setText(loadFromFile(fname));
			}
		}
		
		//Saveボタンの処理
		if(ev.getSource() == save_button) {
			JFileChooser chooser = new JFileChooser();
			int res = chooser.showSaveDialog(this);
			if(res == JFileChooser.APPROVE_OPTION) {
				String fname = chooser.getSelectedFile().getAbsolutePath();
				saveToFile(fname, area.getText());
			}
		}
	}
	
	//保存用メソッド
	public void saveToFile(String f, String data) {
		FileWriter writer = null;
		BufferedWriter bwriter = null;
		try {
			writer = new FileWriter(f);
			bwriter = new BufferedWriter(writer);
			bwriter.write(data);
		} catch (IOException e) {
			e.printStackTrace();
			label.setText("Error...");
		} finally {
			try {
				bwriter.close();
				label.setText("Save completed.");
			} catch(IOException e) {
				e.printStackTrace();
				label.setText("Save failed...");
			}
		}
	}
	
	//読み込み用メソッド
	public String loadFromFile(String f) {
		FileReader reader = null;
		BufferedReader breader = null;
		String loaded = "";
		try {
			reader = new FileReader(f);
			breader = new BufferedReader(reader);
			
			String s = null;
			while((s = breader.readLine()) != null) {
				loaded += s.trim() + "\r\n";
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
			label.setText("Load data not found...");
		} catch(IOException e) {
			e.printStackTrace();
			label.setText("Error...");
		} finally {
			try {
				breader.close();
				label.setText("Load completed.");
			} catch(IOException e) {
				e.printStackTrace();
				label.setText("Load failed...");
			}
		}
		
		return loaded;
	}
	
	public static void main(String[] args) {
		TextSaveLoad tsl = new TextSaveLoad();
		
		//ウィンドウ自体の題名を設定
		tsl.setTitle("TextSave_Load_System");
		
		//ウィンドウを表示
		tsl.setVisible(true);
	}

}
