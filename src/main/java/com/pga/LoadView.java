package com.pga;

import java.awt.Color;
import java.awt.Window.Type;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.pga.utils.ReadFiles;

public class LoadView {
	
	private static JFrame frameLoad;

	public static void main(String[] args) throws IOException {
		initialize();
	}
	
	public LoadView() throws IOException {
		
		if(frameLoad == null) {
			initialize();
		}
	}

	
	public static void initialize() throws IOException {
		frameLoad = new JFrame();
		frameLoad.setType(Type.UTILITY);
		frameLoad.setUndecorated(true);
		frameLoad.setResizable(false);
		frameLoad.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameLoad.setBounds(480, 230, 400, 230);
		JPanel contentPane;
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new LineBorder(Color.LIGHT_GRAY));
		frameLoad.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblImgLoading = new JLabel("");
		lblImgLoading.setIcon(new ImageIcon(ReadFiles.getURL("others/img/logo-loading.png")));
		lblImgLoading.setBounds(1, 1, 390, 220);
		contentPane.add(lblImgLoading);
		frameLoad.setVisible(true);
	}
	
	public static JFrame getLoadFrame() {
		return frameLoad;
	}

	public static void setLoadFrame(JFrame frameLoad) {
		LoadView.frameLoad = frameLoad;
	}
}
