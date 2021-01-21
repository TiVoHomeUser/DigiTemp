package vicw.utils;

import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.TimeZone;


public class TimeZoneSelection extends Frame implements
		SelectTimeZoneInterface, ActionListener, WindowListener, Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 788265586865230115L;
	public static final String VERSION = "TimeZoneSelection V1.1.2.4  2016 (c) Victor Wheeler myapps@vicw.us CVS";

	// private static boolean debug = false; // Trace progress through program
	private TimeZone oldTimeZone = null;
	private TimeZone newTimeZone = null;

	public TimeZoneSelection() {
		this(TimeZone.getDefault());
		// if(debug)
		// System.out.println("Hello from and Done with TimeZoneSelection()");
	}

	public TimeZoneSelection(TimeZone tz) {
		// if(debug)
		// System.out.println("Hello from TimeZoneSelection("+tz.getID()+")");
		newTimeZone = oldTimeZone = tz;
		// if(debug)
		// System.out.println("Selected is "+tz.getID()+" "+newTimeZone.getID()+" "+oldTimeZone.getID());
		init();
		start();
		run();
		// if(debug)
		// System.out.println("Done with TimeZoneSelection("+tz.getID()+")");
	}

	/*
	 * 
	 * setNewTimeZone(TimeZone) Method should be over-ridden in calling class
	 */
	@Override
	public void setNewTimeZone(TimeZone tz) {
		// if(debug)
		// System.out.println("Hello from setNewTimeZone("+tz.getID()+")");
		System.err.println("NOTICE: SelectTimeZone.setNewTimeZone("
				+ tz.getID() + "should be over-ridden");
		// if(debug) System.out.println("Done with setNewTimeZone()");
		// System.exit(0);
	}

	public static void main(String[] args) {
		// if(debug) System.out.println("Hello from TimeZoneSelection.main()");
		// TimeZoneSelection applet = new
		// TimeZoneSelection(TimeZone.getTimeZone("US/Michigan"));
		new TimeZoneSelection(TimeZone.getTimeZone("US/Michigan"));
		// if(debug) System.out.println("TimeZoneSelection = " +
		// applet.toString());

	}

	private void init() {
		// if(debug) System.out.println("Hello from TimeZoneSelection.init()");
		this.setTitle(VERSION);
		if (newTimeZone == null)
			newTimeZone = TimeZone.getDefault(); // JIC 4 WTGBIN
		
		loadList();		// Load up the list with all the valid TZ IDs
		initFrame();	// Add buttons, Layout and panels for display
		

		initListeners();

		update(); // do here otherwise display name does not get updated.
		setVisible(true);
		getTZList().makeVisible(getTZList().getSelectedIndex()); // scroll to display selected item
		// MakeVisible needs to be after setVisible(true) for the listbox to be updated

		// if(debug) System.out.println("Done with TimeZoneSelection.init()");
	}
	

	Thread runner;

	public void start() {
		// if(debug) System.out.println("Hello from TimeZoneSelection.start()");
		runner = new Thread(this);
		runner.start();
		// if(debug) System.out.println("Done with TimeZoneSelection.start()");
	}

	public void run() {
		// if(debug) System.out.println("Hello from TimeZoneSelection.run()");
		Thread thisThread = Thread.currentThread();

		while (runner == thisThread) {
			if (!isValid()) {
				repaint();
				this.validate();
			}
			try {
				Thread.sleep(300);
			} // 1000 = 1 second
			catch (InterruptedException e) {
			}
		}
		// if(debug) System.out.println("Done with TimeZoneSelection.run()");
	}

	//
	private Button applyButton = null;
	private Button selectButton = null;
	private Button cancelButton = null;

	private Button getApplyButton() {
		// if(debug)
		// System.out.println("Hello from TimeZoneSelection.getApplyButton()");
		if (applyButton == null) {
			applyButton = new Button("Apply");
		}
		// if(debug)
		// System.out.println("Done with TimeZoneSelection.getApplyButton()");
		return applyButton;
	}

	//
	private Button getSelectButton() {
		// if(debug)
		// System.out.println("Hello from TimeZoneSelection.getSelectButton()");
		if (selectButton == null) {
			selectButton = new Button("Select");
		}
		// if(debug)
		// System.out.println("Done with TimeZoneSelection.getSelectButton()");
		return selectButton;
	}

	//
	private Button getCancelButton() {
		// if(debug)
		// System.out.println("Hello from TimeZoneSelection.getCancelButton()");
		if (cancelButton == null) {
			cancelButton = new Button("Cancel");
		}
		// if(debug)
		// System.out.println("Done with TimeZoneSelection.getCancelButton()");
		return cancelButton;
	}

	private Panel tzifp = null;
	private Panel getInfoPanel() {
		if (tzifp == null) {
			tzifp = new Panel();
			tzifp.setBackground(Color.red);
			tzifp.add(getSelectBox());
			tzifp.add(getSelectedDisplayName());
			tzifp.add(getHrGmt());
		}
		return tzifp;
	}

	private Panel buttons = null;
	private Panel tzlist = null;
	private Panel getButtonPanel() {
		// if(debug)
		// System.out.println("Hello from TimeZoneSelection.getButtons()");
		if (buttons == null) {
			buttons = new Panel();
			buttons.setBackground(Color.yellow);
			buttons.add(getApplyButton());
			buttons.add(getSelectButton());
			buttons.add(getCancelButton());
		}
		// if(debug)
		// System.out.println("Done with TimeZoneSelection.getButtons()");
		return buttons;
	}

	private Panel getTzList() {
		if (tzlist == null) {
			tzlist = new Panel();
			tzlist.setBackground(Color.blue);
			tzlist.add(getTZList());
		}
		return tzlist;
	}

	private List lb = null;
	private List getTZList() {
		// if(debug)
		// System.out.println("Hello from SelectTimeZone.getTZList()");
		if (lb == null) {
			lb = new List(15, false); // rows, multipleMode
			lb.setBackground(Color.green);

			String[] tzs = TimeZone.getAvailableIDs();
			for (int i = 0; i < tzs.length; i++) {
				lb.add(tzs[i++]);
			}

			lb.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					newTimeZone = TimeZone.getTimeZone(lb.getSelectedItem());
					getTZList().makeVisible(getTZList().getSelectedIndex()); // scroll to display selected item
					update();
				}
			});
			
			lb.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseClicked(MouseEvent e) {
					// System.out.println("Mouse Clicked"+e);
					//newTimeZone = TimeZone.getTimeZone(lb.getSelectedItem());
					//getTZList().makeVisible(getTZList().getSelectedIndex()); // scroll to display selected item
					update();
				}

				@Override
				public void mousePressed(MouseEvent e) {
					// System.out.println("Mouse Pressed"+e);
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					if (e.getClickCount() > 1) { // Same as Apply button
						newTimeZone = TimeZone.getTimeZone(lb.getSelectedItem());
						setNewTimeZone(newTimeZone);
						close();
					}
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// System.out.println("Mouse Entered");
				}

				@Override
				public void mouseExited(MouseEvent e) {
					// System.out.println("Mouse Exited");
				}
			});
			lb.addKeyListener(new java.awt.event.KeyListener() {

				@Override
				public void keyTyped(KeyEvent e) {
					// System.out.println("Key Typed");

				}

				@Override
				public void keyPressed(KeyEvent e) {
					// System.out.println("Key Pressed");

				}

				@Override
				public void keyReleased(KeyEvent e) {
					// System.out.println("Key Released");
					//getTZList().makeVisible(getTZList().getSelectedIndex()); // scroll to display selected item
					update();
					if (e.getKeyChar() == '\n') { // Same as "Apply" button
						newTimeZone = TimeZone.getTimeZone(lb.getSelectedItem());
						setNewTimeZone(newTimeZone);
						close();
					}

				}

			});
		}

		// if(debug) System.out.println("Done with getTZlist()");
		return lb;
	}

	private void update() {	// The 3 info boxes ID, Display name and hrs +/- GMT
		// if(debug) System.out.println("Hello from SelectTimeZone.update()");
		// getTZList().makeVisible(getTZList().getSelectedIndex()); // scroll to display selected item
		getSelectBox().setText(lb.getSelectedItem());
		getSelectedDisplayName().setText(
				TimeZone.getTimeZone(lb.getSelectedItem()).getDisplayName());
		getHrGmt();
		// if(debug) System.out.println("Done with SelectTimeZone.update()");
	}

	private TextField hrsgmt = null;

	private TextField getHrGmt() {
		// if(debug) System.out.println("Hello from SelectTimeZone.getHrGMT()");
		if (hrsgmt == null) {
			hrsgmt = new TextField("GMT "
					+ TimeZone.getTimeZone(getTZList().getSelectedItem())
							.getRawOffset() % (60 * 60), 10);
			hrsgmt.setEnabled(false);
		}
		hrsgmt.setText("GMT "
				+ TimeZone.getTimeZone(getTZList().getSelectedItem())
						.getRawOffset() / 3600000);
		// if(debug) System.out.println("Done with getHrGmt()");
		return hrsgmt;
	}

	private TextField tf = null;
	private TextField getSelectBox() {
		// if(debug)
		// System.out.println("Hello from SelectTimeZone.getSelectBox()");
		if (tf == null) {
			tf = new TextField(getTZList().getItem(
					getTZList().getSelectedIndex()), 25);
			tf.setEnabled(false);
		}
		// if(debug)
		// System.out.println("Done with SelectTimeZone.getSelectBox()");
		return tf;
	}

	private TextField gsdn = null; // Selected Display Name
	private TextField getSelectedDisplayName() {
		// if(debug)
		// System.out.println("Hello from SelectTimeZone.getSelectedDisplay");
		if (gsdn == null) {
			gsdn = new TextField(getTZList().getItem(
					getTZList().getSelectedIndex()), 25);
			gsdn.setEnabled(false);
		}
		// if(debug)
		// System.out.println("Done with SelectTimeZone.getSelectedDisplay");
		return gsdn;
	}

	/*
	 * 		Display stuff 
	 * 
	 */
	private void initFrame(){				// Moved here to clean up init()
		
	setSize(600, 400); // width, height

	GridBagLayout gbl = new GridBagLayout();
	setLayout(gbl);
	GridBagConstraints gbc = new GridBagConstraints();

	gbc.gridwidth = GridBagConstraints.REMAINDER; // end row
	gbc.weightx = 0.0;
	gbc.weighty = 0.0;

	add(getInfoPanel(), gbc);

	gbc.weightx = 0.0;
	gbc.weighty = 0.1;
	add(getTzList(), gbc);

	gbc.weightx = 0.0;
	gbc.weighty = 0.0;
	add(getButtonPanel(), gbc);
	}

	/*
	 * 		Load up the list with all the valid TimeZone ID's
	 * 		find and select the defult in the List 
	 * 
	 */
	private void loadList(){			// Moved here to clean up init()
		int count = getTZList().getItemCount();	// Load up the list with valid TZ IDs
		// System.out.println("count="+count);
		boolean found = false;			// Search for the active one for display
		while (!found && (count > 0)) { // find requested default time zone
			count--;
			if (getTZList().getItem(count).equalsIgnoreCase(
					newTimeZone.getDisplayName()))
				found = true;
			if (getTZList().getItem(count)
					.equalsIgnoreCase(newTimeZone.getID()))
				found = true;
		}
		if (found) { // Highlight it in the List
			// System.out.println("Found "+ newTimeZone.getID() + " at " +
			// count);
			getTZList().select(count);
		} else { // Use default for this location
			// System.err.println("NOT Found "+ newTimeZone.getID() + " at " +
			// count);
			// System.out.println("searched for "+newTimeZone);
			getTZList().select(getTZList().getItemCount() / 2); // Start in the middle
		}
}

	private void initListeners() {
		// if(debug)
		// System.out.println("Hello from TimeZoneSelection.initListeners()");

		addWindowListener(this);
		getApplyButton().addActionListener(this);
		getSelectButton().addActionListener(this);
		getCancelButton().addActionListener(this);

		// if(debug)
		// System.out.println("Done with TimeZoneSelection.initListeners()");
	}

	private void removeListeners() {
		// if(debug)
		// System.out.println("Hello from TimeZoneSelection.removeListeners()");

		getCancelButton().removeActionListener(this);
		getSelectButton().removeActionListener(this);
		getApplyButton().removeActionListener(this);
		removeWindowListener(this);

		// if(debug)
		// System.out.println("Done with TimeZoneSelection.removeListeners()");
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		// if(debug)
		// System.out.println("Hello from TimeZoneSelection.actionPerformed("+e.getID()+")");
		if (e.getSource() == getCancelButton()) {
			newTimeZone = oldTimeZone;
			setNewTimeZone(newTimeZone);	// Restore the old TZ
			close();
		} else {
			newTimeZone = TimeZone.getTimeZone(lb.getSelectedItem());  // For both Apply and Select
			if (newTimeZone == null) {  // Should never be... however some TZ get partially removed from list like "America.Detroit"
				System.err.println("TimeZoneSelect.actionPreformed("
						+ e.getID() + ") newTimeZone is null please report to Oracle");
			} else { // Don't do if newTimeZone is null. 
						// If overridden method does not check an eception will be thrown
				if (e.getSource() == getSelectButton()) {
					setNewTimeZone(newTimeZone);
				}
				if (e.getSource() == getApplyButton()) {
					setNewTimeZone(newTimeZone);
					close();
				}
			}
		}
		// if(debug)
		// System.out.println("Done with TimeZoneSelection.actionPerformed("+e.getID()+")");
		getTZList().makeVisible(getTZList().getSelectedIndex()); // scroll to display selected item
		update();
	}

	private void close() {
		// if(debug) System.out.println("Hello from TimeZoneSelection.close()");
		removeListeners();
		stop();
		// if(debug) System.out.println("Done with TimeZoneSelection.close()");
	}

	public void stop() {
		// if(debug)
		// System.out.println("Hello from TimeZoneSelection.stop()()");
		if (runner != null) {
			runner = null;
			setVisible(false);
			dispose();
		}
		// if(debug) System.out.println("Done with TimeZoneSelection.stop()()");
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// Not used needed for WindowListener
	}

	@Override
	public void windowClosing(WindowEvent e) {
		close();
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// Not used needed for WindowListener
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// Not used needed for WindowListener
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// Not used needed for WindowListener
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// Not used needed for WindowListener
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// Not used needed for WindowListener
	}

}
