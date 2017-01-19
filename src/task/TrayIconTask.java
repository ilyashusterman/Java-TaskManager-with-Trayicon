package task;


import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.logging.Level;

import javax.swing.*;



public class TrayIconTask {
	private static TrayIcon trayIcon;

    private static TrayIconTask tray;
    private TrayIconTask(){
        super();
    }
    public static TrayIconTask getInstance(){
        if(tray==null){
            synchronized (TrayIconTask.class){
                if(tray==null){
                tray= new TrayIconTask();
                }
            }
        }
        return tray;
    }
//		if(trayIcon!=null&&image!=null){
//			trayIcon.setImage(image);return;
//		}
//		java.util.logging.Logger.getLogger(ServerSystemTray.class.getName()).log(Level.WARNING,"not initialize") ;
//	}

    public void execute(Process process, String systemName) {
        /* Use an appropriate Look and Feel */
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            //UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        //Schedule a job for the event-dispatching thread:
        //adding TrayIcon.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI( process,systemName);
            }
        });
    }

    private void createAndShowGUI(Process process, String systemName) {
        //Check the SystemTray support
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        final PopupMenu popup = new PopupMenu();
        	trayIcon =
        			new TrayIcon(createImage("resources/images/trayImage.gif", "tray icon"));

        final SystemTray tray = SystemTray.getSystemTray();
        trayIcon.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ServerSystemTray.execute(process);

			}
		});
        trayIcon.setToolTip(systemName);
        // Create a popup menu components
        MenuItem aboutItem = new MenuItem("About");
        CheckboxMenuItem cb1 = new CheckboxMenuItem("Set auto size");
        CheckboxMenuItem cb2 = new CheckboxMenuItem("Set tooltip");
        Menu displayMenu = new Menu("Display");
        MenuItem errorItem = new MenuItem("Error");
        MenuItem warningItem = new MenuItem("Warning");
        MenuItem infoItem = new MenuItem("Info");
        MenuItem noneItem = new MenuItem("None");
        MenuItem exitItem = new MenuItem("Exit");

        //Add components to popup menu

        popup.add(aboutItem);
        popup.addSeparator();
        popup.add(cb1);
        popup.add(cb2);
        popup.addSeparator();
        popup.add(displayMenu);
        displayMenu.add(errorItem);
        displayMenu.add(warningItem);
        displayMenu.add(infoItem);
        displayMenu.add(noneItem);
        popup.add(exitItem);

        trayIcon.setPopupMenu(popup);

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
            return;
        }

        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "Ilya shusterman CopyRight of ServerSystemTray");
            }
        });

        cb1.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                int cb1Id = e.getStateChange();
                if (cb1Id == ItemEvent.SELECTED){
                    trayIcon.setImageAutoSize(true);
                } else {
                    trayIcon.setImageAutoSize(false);
                }
            }
        });

        cb2.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                int cb2Id = e.getStateChange();
                if (cb2Id == ItemEvent.SELECTED){
                    trayIcon.setToolTip("ServerSystem");
                } else {
                    trayIcon.setToolTip(null);
                }
            }
        });

        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MenuItem item = (MenuItem)e.getSource();
                //TrayIcon.MessageType type = null;
                System.out.println(item.getLabel());
                if ("Error".equals(item.getLabel())) {
                    //type = TrayIcon.MessageType.ERROR;
                    trayIcon.displayMessage("Sun TrayIcon Demo",
                            "This is an error message", TrayIcon.MessageType.ERROR);

                } else if ("Warning".equals(item.getLabel())) {
                    //type = TrayIcon.MessageType.WARNING;
                    trayIcon.displayMessage("Sun TrayIcon Demo",
                            "This is a warning message", TrayIcon.MessageType.WARNING);

                } else if ("Info".equals(item.getLabel())) {
                    //type = TrayIcon.MessageType.INFO;
                    trayIcon.displayMessage("Sun TrayIcon Demo",
                            "This is an info message", TrayIcon.MessageType.INFO);

                } else if ("None".equals(item.getLabel())) {
                    //type = TrayIcon.MessageType.NONE;
                    trayIcon.displayMessage("Sun TrayIcon Demo",
                            "This is an ordinary message", TrayIcon.MessageType.NONE);
                }
            }
        };

        errorItem.addActionListener(listener);
        warningItem.addActionListener(listener);
        infoItem.addActionListener(listener);
        noneItem.addActionListener(listener);

        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tray.remove(trayIcon);
                try {

					System.exit(0);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					 java.util.logging.Logger.getLogger(ServerSystemTray.class.getName()).log(Level.WARNING, e1.getMessage());
				}
            }
        });
        trayIcon.displayMessage("Server App System Up and Running! ",
                "App tray manager by ilya shusterman ", TrayIcon.MessageType.INFO);
    }

    //Obtain the image URL
    protected Image createImage(String path, String description) {
        URL imageURL = getClass().getClassLoader().getResource(path);
        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            java.util.logging.Logger.getLogger(ServerSystemTray.class.getName()).log(Level.WARNING, "Resource not found: " + path);;
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }
}
