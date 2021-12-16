package com.pga.utils;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class TrayIcon {

	public static void create() throws IOException, AWTException {
		
		java.awt.TrayIcon trayIcon = null;
		if (SystemTray.isSupported()) {
		    // get the SystemTray instance
		    SystemTray tray = SystemTray.getSystemTray();
		    // load an image
		    Image image = Toolkit.getDefaultToolkit().getImage(ReadFiles.getURL("others/img/mini-logo.png"));
		    // create a action listener to listen for default action executed on the tray icon
		    ActionListener listnerExit = new ActionListener() {
		        public void actionPerformed(ActionEvent e) {
		        	System.exit(0);
		        }
			};
		    ActionListener listnerOpenInNav = new ActionListener() {
		        public void actionPerformed(ActionEvent e) {
		        	try {
						Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + "http://127.0.0.1:8090");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
		        }
			};
			PopupMenu popup = new PopupMenu();
			
		    MenuItem defaultItem = new MenuItem("Abrir no Navegador");
		    defaultItem.addActionListener(listnerOpenInNav);
		    popup.add(defaultItem);
					
		    defaultItem = new MenuItem("Exit");
		    defaultItem.addActionListener(listnerExit);
		    popup.add(defaultItem);
		    
		    popup.addSeparator();
		    
		    defaultItem = new MenuItem("Close");
		    popup.add(defaultItem);
		    
			trayIcon = new java.awt.TrayIcon(image, "PGA", popup);

		    tray.add(trayIcon);
		} else {
		    // disable tray option in your application or
		    // perform other actions
		}
		// ...
		// some time later
		// the application state has changed - update the image
		if (trayIcon != null) {
//		    trayIcon.setImage(i);
		}
	}
	
}
