package vicw.utils.led;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Test extends Frame implements Runnable, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7873730301121219506L;
	private boolean running = false;
	private Thread thread = null;
	private int sleepValue = 1000;
	private String title_string = "Test";
	private Dy dy = null;
	//private RoundLed rl = null;
	//private RetLed retl = null;
	private SevenSegLed ssl =null;
	public Test() {
		init();
		start();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Starting Test...");
		Test test = new Test();
		do
			try {
				Thread.sleep((long) 5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		while (test.running);
		test = null;
	}

	public void actionPerformed(ActionEvent e) {
		System.out.println("actionPreformed(e)");

	}

	private void init() {
		System.out.println("init()");
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.out.println("windowClosing(WindowEvent)");
				running = false;
			}
		});
		
		// Instantiate 
		//System.out.println("Instantiate a DY object");
		dy = new Dy(10, 30, 120, 60, Color.RED, Color.BLACK, 3);
		// dy = new Dy(10, 30, 120, 60, Color.RED, 3);
		dy.setValue(68.3);
//if(true) return;
		//System.out.println("Instantiate a ssl object");
		ssl = new SevenSegLed(dy.getSize().width+10+10, 30 + dy.getSize().height/2, dy.getSize().width/6, dy.getSize().height/2, Color.GREEN);
		ssl.setValue(15); // 15 = 'F' 12 = 'C' 

		// TEST OVER-RIDES
		//System.out.println("Testing calls to over-ride SetColors");
		ssl.setOffColor(Color.WHITE);
		dy.setOffColor(Color.WHITE);
		ssl.setBackGroundColor(Color.WHITE);
		dy.setBackGroundColor(Color.WHITE);

		dy.enhancedLED(false);
		ssl.enhancedLED(false);
		// END OF OVER-RIDES
		
		//System.out.println("Now testing setsize");
		this.setSize((dy.getSize().width + 100), dy.getSize().height + 40);
		this.setTitle(title_string);
		//dy.setOffColor(offColor);
		//ssl.setOffColor(offColor);
		this.setVisible(true);
		
		//System.out.println("Done with init()");
	}

	private void start() {
		System.out.println("start()");
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
		System.out.println("end of start()");
	}

	public void run() {
		System.out.println("run()");
		if (running)
			return;
		running = true;

		Thread thisThread = Thread.currentThread();
		// int i =0;
		while (running && thread == thisThread) {
			try {
				Thread.sleep(sleepValue); // 1000 = 1 second
			} catch (InterruptedException e) {
				running = false;
			}
			if (running)
				updateDisplay();
		}
		cleanUp();
	}

	public void stop() {
		System.out.println("stop()");

	}

	private void updateDisplay() {
		//retl.setEnable(rl.isOn());
		//rl.setEnable(!rl.isOn());
		repaint();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Window#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		dy.paint(g);
		ssl.paint(g);
		//rl.paint(g);
		//retl.paint(g);
		super.paint(g);
	}

	public void cleanUp() {
		// removeListners();
		dispose();
		System.exit(0);
	}

}
