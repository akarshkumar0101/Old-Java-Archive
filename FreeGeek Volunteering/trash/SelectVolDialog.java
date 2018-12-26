package ui.dialog;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import data.Volunteer;
import ui.FGFrame;
import util.Util;

@SuppressWarnings("serial")
public class SelectVolDialog extends JDialog {

	public static final String title = "Select Volunteer";

	private static SelectVolDialog currentDialog = null;

	private Volunteer selection = null;

	private final JScrollPane scrollPane;
	private final JPanel content;
	private final ArrayList<VolButton> volbuttons;

	private final WindowListener closeListener = new WindowListener() {
		@Override
		public void windowOpened(WindowEvent e) {
		}

		@Override
		public void windowClosing(WindowEvent e) {
			currentDialog.triggerReturn();
		}

		@Override
		public void windowClosed(WindowEvent e) {
		}

		@Override
		public void windowIconified(WindowEvent e) {
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
		}

		@Override
		public void windowActivated(WindowEvent e) {
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
		}
	};

	private class VolButton extends JButton {
		private final Volunteer vol;

		private final ActionListener onClick = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selection = vol;
				currentDialog.triggerReturn();
			}
		};

		private VolButton(Volunteer representVol) {
			super();
			vol = representVol;

			setFont(Util.standoutFont);
			setText(Util.formatName(representVol));

			Util.setActionListener(this, onClick);
			this.add(new JLabel("hi"));
		}

	}

	/**
	 * @param owner
	 * @param titleExtension,
	 *            ex. "to Remove" would show up as "Select Volunteer to Remove"
	 */
	private SelectVolDialog(FGFrame owner, String titleExtension) {
		super(owner, title + " " + titleExtension, true);

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(closeListener);

		scrollPane = new JScrollPane();

		content = new JPanel();
		content.setLayout(new GridLayout(1, 1));// Volunteer.volunteers.size(),
												// 1));
		volbuttons = new ArrayList<VolButton>();

		for (Volunteer vol : Volunteer.volunteers) {
			VolButton vb = new VolButton(vol);
			volbuttons.add(vb);
			vb.setMaximumSize(new Dimension(50, 50));
			content.add(vb);
			break;
		}

		scrollPane.setViewportView(content);

		this.add(scrollPane);

		scrollPane.getVerticalScrollBar().setUnitIncrement(10);

		// run on different thread that will get blocked(don't want EDT to get
		// blocked
		new Thread(new Runnable() {
			@Override
			public void run() {
				SelectVolDialog.this.setSize(500, 500);
				SelectVolDialog.this.setVisible(true);

			}
		}).start();
	}

	/**
	 * CALL ON A NEW THREAD TO KEEP EVERYTHING FROM FREEZING<br>
	 * "to Remove" would show up as "Select Volunteer to Remove"
	 * 
	 * @param titleExtension,ex.
	 * 
	 * @param runOnClose
	 * @return
	 */
	public static synchronized Volunteer selectVolunteer(String titleExtension) {
		if (SwingUtilities.isEventDispatchThread())
			throw new RuntimeException("Will freeze application if SelectVolDialog runs on EDT");
		currentDialog = new SelectVolDialog(FGFrame.frame, titleExtension);

		Volunteer vol = currentDialog.getSelectedVolunteer();

		currentDialog.dispose();
		currentDialog = null;
		System.gc();

		return vol;
	}

	private synchronized Volunteer getSelectedVolunteer() {
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return selection;
	}

	private synchronized void triggerReturn() {
		notify();
	}
}
