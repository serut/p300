/*
 Copyright 2006, 2007, 2008 Markus Goetz, Sebastian Breier
 Webpage on http://p300.eu/
 */
/*
 This file is part of p300.


 p300 is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 p300 is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with p300.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 This file is part of p300.


 p300 is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 p300 is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with p300.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 This file is part of p300.


 p300 is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 p300 is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with p300.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.guruz.p300;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;

import de.guruz.guruzsplash.interfaces.GuruzsplashManager;
import de.guruz.guruztray.interfaces.GuruztrayManager;
import de.guruz.p300.downloader.DownloadManager;
import de.guruz.p300.hosts.Host;
import de.guruz.p300.hosts.HostMap;
import de.guruz.p300.hosts.HostWatchThread;
import de.guruz.p300.hosts.allowing.HostAllowanceManager;
import de.guruz.p300.hosts.httpmulticast.HostFinderThread;
import de.guruz.p300.internet.InternetListenThread;
import de.guruz.p300.logging.D;
import de.guruz.p300.onetoonechat.LanMessageRemoteOutbox;
import de.guruz.p300.onetoonechat.LanMessageRouter;
import de.guruz.p300.onetoonechat.ui.ChatWindowMap;
import de.guruz.p300.onetoonechat.ui.UiMessageRouter;
import de.guruz.p300.osx.OSXCallbackInterface;
import de.guruz.p300.osx.OSXInterface;
import de.guruz.p300.search.IndexerThread;
import de.guruz.p300.search.ui.SearchResultsWindow;
import de.guruz.p300.threads.BandwidthThread;
import de.guruz.p300.threads.ListenThread;
import de.guruz.p300.threads.NewVersionNotificationThread;
import de.guruz.p300.threads.ShutdownThread;
import de.guruz.p300.threads.UdpListenThread;
import de.guruz.p300.threads.UpdaterThread;
import de.guruz.p300.utils.DirectoryUtils;
import de.guruz.p300.utils.IconChooser;
import de.guruz.p300.utils.LockFile;
import de.guruz.p300.utils.OsUtils;
import de.guruz.p300.utils.launchers.BareBonesBrowserLaunch;
import de.guruz.p300.windowui.MainButtonPopupMenu;
import de.guruz.p300.windowui.MainDialogTreeCellEditor;
import de.guruz.p300.windowui.MainDialogTreeCellRenderer;
import de.guruz.p300.windowui.actions.AddDowloadAction;
import de.guruz.p300.windowui.actions.AddHostAction;
import de.guruz.p300.windowui.actions.AddShareAction;
import de.guruz.p300.windowui.actions.BrowseHostAction;
import de.guruz.p300.windowui.actions.ChatWithOtherUsersAction;
import de.guruz.p300.windowui.actions.ConfigurationAction;
import de.guruz.p300.windowui.actions.ConsoleAction;
import de.guruz.p300.windowui.actions.DonationsAction;
import de.guruz.p300.windowui.actions.EarnAction;
import de.guruz.p300.windowui.actions.GetHelpAction;
import de.guruz.p300.windowui.actions.QuitAction;
import de.guruz.p300.windowui.actions.SendFeatureRequestAction;
import de.guruz.p300.windowui.actions.SendFeedbackAction;
import de.guruz.p300.windowui.actions.ShowDownloadsAction;
import de.guruz.p300.windowui.actions.UploadsAction;
import de.guruz.p300.windowui.actions.WebinterfaceAction;
import de.guruz.p300.windowui.maintree.InternetHostsTreeItem;
import de.guruz.p300.windowui.maintree.LANHostTreeItem;
import de.guruz.p300.windowui.maintree.LANHostsTreeItem;
import de.guruz.p300.windowui.maintree.MainDialogTreeModel;
import de.guruz.p300.windowui.maintree.MainTree;
import de.guruz.p300.windowui.panels.ConfigurationPanel;
import de.guruz.p300.windowui.panels.ConsolePanel;
import de.guruz.p300.windowui.panels.DownloadsPanel;
import de.guruz.p300.windowui.panels.InfoPanel;
import de.guruz.p300.windowui.panels.NotImplementedYetPanel;
import de.guruz.p300.windowui.panels.UploadsPanel;

/**
 * This is the "real" main class which creates everything :)
 * 
 * @author guruz
 * 
 */
public class MainDialog {

	private static final String ACTION_SEARCH = "search";

	public static final String BROWSE_LAN_HOST_HERE = "browseLanHostHere";

	public static final String ACTION_GLOBAL_OPEN_WEBINTERFACE = "OpenWebinterface";

	public static final String ACTION_ADD_SHARE = "addshare";

	public static final String ACTION_RESET_ADMIN_PASSWORD = "resetadminpassword";

	public static final String ACTION_SHOW_HIDE_ADMINPASSWORD = "getadminpassword";

	public static final String ACTION_MODIFY_SHARE = "modifyshare";

	public static final String ACTION_DELETE_SHARE = "deleteshare";

	public static final String ACTION_ABOUT = "About";

	public static final String ACTION_QUIT = "Quit";

	public static final String ACTION_OPEN_MY_WEBINTERFACE = "OpenMyWebinterface";

	public static final String ACTION_HIDE = "Hide";

	public static final String ACTION_SHOW = "Show";

	public static final String ACTION_OPEN_CHAT_FOR_SELECTED_HOST = "OpenChatForSelectedHost";

	public static final String ACTION_BROWSE_SELECTED_HOST = "BrowseSelectedHost";

	public static final String ACTION_OPEN_WEBINTERFACE_FOR_SELECTED_HOST = "OpenWebinterfaceForSelectedHost";

	public static BandwidthThread bandwidthThread;

	public static boolean graphicConsoleEnabled;

	public static HostFinderThread hostFinderThread;

	public static HostMap hostMap;

	public static HostWatchThread hostWatchThread;

	public static MainDialog instance;

	public static IndexerThread indexerThread;

	static LaunchTypeType launchType = LaunchTypeType.Undetermined;

	public static LockFile lockFile = null;

	public static ListenThread listenThread;

	public static UdpListenThread multicastListenThread;

	public static InternetListenThread internetListenThread;

	public static UpdaterThread updaterThread;

	/**
	 * will be overwritten
	 */
	private static String myURL = "http://127.0.0.1:4337";

	public static NewVersionNotificationThread newVersionNotificationThread;

	public static boolean requestedShutdown;

	/**
	 * Unnecessary but fixes compiler warning
	 * 
	 * @author tomcat
	 */
	private static final long serialVersionUID = 0;

	/**
	 * Start time in millis since epoch
	 * 
	 * @author guruz
	 */
	public static long startedOn = System.currentTimeMillis();;

	/**
	 * 
	 * 
	 * @author guruz
	 */
	static Timer timer;

	public static DownloadManager downloadManager;

	public static HostAllowanceManager m_hostAllowanceManager;

	public static String launchedByRevision = "";

	/**
	 * 
	 * 
	 * @author guruz
	 */
	private static void createLockFile() {
		File lockDir = new File(Configuration.configDirFileName("locks"));
		DirectoryUtils.makeSureDirectoryExists(lockDir);

		String fn = Configuration.configDirFileName("locks/p300."
				+ MainDialog.getCurrentHTTPPort());
		MainDialog.lockFile = new LockFile(fn);
		MainDialog.lockFile.lock();
	}

	/**
	 * Create all important threads/objects
	 * 
	 * @author guruz
	 */
	private static void createThreadsAndObjects() {
		P300ProxySelector.useIt();

		m_hostAllowanceManager = new HostAllowanceManager();

		// Where we store the discovered multicast hosts
		MainDialog.hostMap = new HostMap();

		// The thread responsible for bandwidth quota
		MainDialog.bandwidthThread = new BandwidthThread();

		// The thread responsible for incoming UDP multicast packets
		MainDialog.multicastListenThread = new UdpListenThread();

		// the thread responsible for multicast over http
		MainDialog.hostFinderThread = new HostFinderThread();

		// how watch thread
		MainDialog.hostWatchThread = new HostWatchThread();
		MainDialog.hostWatchThread.setHostMap(MainDialog.hostMap);

		MainDialog.indexerThread = new IndexerThread();

		MainDialog.updaterThread = new UpdaterThread();
		MainDialog.newVersionNotificationThread = new NewVersionNotificationThread();

		MainDialog.internetListenThread = new InternetListenThread();

		MainDialog.downloadManager = new DownloadManager();

	}

	/**
	 * Return the current HTTP port from listener thread or config
	 * 
	 * @return Integer, usually 4337
	 * @author guruz
	 */
	public static int getCurrentHTTPPort() {
		if (MainDialog.listenThread == null) {
			return Configuration.instance().getDefaultHTTPPort();
		}

		return MainDialog.listenThread.getPort();
	}

	/**
	 * Return the currently running p300 instance
	 * 
	 * @return The current MainDialog instance
	 * @author guruz
	 */
	public static MainDialog getInstance() {
		return MainDialog.instance;
	}

	/**
	 * Return the URL of the current/local p300 instance
	 * 
	 * @return A String of the form "http://192.168.42.10:4337/"
	 * @author guruz
	 */
	public static String getMyURL() {
		return MainDialog.myURL;
	}


	/**
	 * Read paramteres and do stuff
	 * 
	 * @param args
	 *            Arguments from command line
	 * @author guruz
	 * @see #help()
	 */
	public static void handleParameters(String[] args) {
		if (args[0].equals("--altID")) {
			Configuration.useAltUniqueInstanceHash = true;
			System.err.println("Using alternative Instance Hash/ID");
		} else if (args[0].equals("--headless") || args[0].equals("--console")) {
			MainDialog.launchType = LaunchTypeType.Console;
		} else if (args[0].equals("--head") || args[0].equals("--graphical")) {
			MainDialog.launchType = LaunchTypeType.GUI;
		} else if (args[0].equals("--setadminpass")
				|| args[0].equals("--setadminpw")
				|| args[0].equals("--setadminpassword")) {
			// Generate a new password
			MainDialog.resetAdminPassword();

			System.out.println(Configuration.instance().getAdminPassword());
			System.exit(1);
		} else if (args[0].equals("--getadminpass")
				|| args[0].equals("--getadminpw")
				|| args[0].equals("--getadminpassword")) {
			// Output the password
			System.out.println(Configuration.instance().getAdminPassword());
			System.exit(1);
		} else if (args[0].equals("--help") || args[0].equals("-h")) {
			MainDialog.help();
		} else if (args[0].equals("--allow")) {
			if (args.length == 2) {
				System.out.print("Allowing ");
				System.out.println(args[1]);
				Configuration.instance().setIpExplicitlyAllowed(args[1], true);
				System.exit(0);
			} else {
				MainDialog.help();
			}
		} else {
			System.err.println("Unknown option, try --help");
			System.exit(1);
		}
		;
	}

	/**
	 * Prints out the possible parameters to stdout
	 * 
	 * @author guruz
	 * @see #handleParameters(String[])
	 */
	public static void help() {
		System.err.println("Usage (use one of the parameters):");
		System.err.println(" --console             console only");
		System.err.println(" --graphical           with graphical environment");
		System.err
				.println(" --setadminpassword    sets a random admin password");
		System.err.println(" --getadminpassword    gets the admin password");
		System.err
				.println(" --allow HOST          enables HOST for the webinterface");
		System.err.println("");
		System.err.println("Current configuration:");

		System.err.println(" Listening on");
		System.err.print("  http://127.0.0.1:");
		System.err.println(MainDialog.getCurrentHTTPPort());

		System.err.println("  The username for the configuration is: admin");
		System.err.println("  You can set the remaining settings there");
		System.exit(0);
	}



	/**
	 * Check if this is running in console
	 * 
	 * @return True: Running headlessly; False: Running with GUI
	 * @author guruz
	 */
	public static boolean isHeadless() {
		return (MainDialog.launchType == LaunchTypeType.Console);
	}

	/**
	 * Start method
	 * 
	 * @param args
	 *            Command line arguments
	 * @author guruz
	 */
	public static void main(String[] args) {
		Thread
				.setDefaultUncaughtExceptionHandler(new P300UncaughtExceptionHandler());

		if (OsUtils.isOSX()) {
			System.setProperty("apple.awt.fileDialogForDirectories", "true");
			System.setProperty(
					"com.apple.mrj.application.apple.menu.about.name", "p300");
		}

		System.setProperty("java.net.preferIPv4Stack", "true");

		System.setProperty("java.net.useSystemProxies", "true");

		// init our default settings
		if (java.awt.GraphicsEnvironment.isHeadless()) {
			MainDialog.launchType = LaunchTypeType.Console;
		} else {
			MainDialog.launchType = LaunchTypeType.GUI;
		}

		// handle paraeters
		if (args.length >= 1) {
			MainDialog.handleParameters(args);
		}

		// the user may have requested a GUI mode although we do not support it
		if (java.awt.GraphicsEnvironment.isHeadless()) {
			MainDialog.launchType = LaunchTypeType.Console;
		}

		// see if we have java 1.6 and a splash screen
        MainInterface.getInstance().initSplashScreen();
        
		try {
			launchedByRevision = System.getProperty("launchedByP300Revision");
		} catch (Exception e) {
		}
		;

		if (launchedByRevision != null && launchedByRevision.length() > 0)
			D.out("This is p300, revision " + Configuration.getSVNRevision()
					+ " launched by " + launchedByRevision);
		else
			D.out("This is p300, revision " + Configuration.getSVNRevision());

		Configuration.createDotP300();

		// The thread responsible for incoming (HTTP) connections
		try {
			MainDialog.listenThread = new ListenThread();
		} catch (Exception e) {
			String msg = "Could not bind to any TCP port. Please properly configure your firewall. Exiting now.";
			if (MainDialog.isHeadless()) {
				D.out(msg);
			} else {
				JOptionPane.showMessageDialog(null, msg, "alert",
						JOptionPane.ERROR_MESSAGE);
			}

			System.exit(1);
		}
		MainDialog.myURL = "http://127.0.0.1:"
				+ MainDialog.getCurrentHTTPPort() + '/';

		// well :)
		MainDialog.createLockFile();

		if (!MainDialog.isHeadless()) {
			// show the main dialog
			try {
                MainInterface.getInstance().createGUI();
				MainDialog.graphicConsoleEnabled = true;
			} catch (Throwable t) {
				D
						.out("Failure while initializing main window! Try --console as option to run headless");
				t.printStackTrace();
				MainDialog.instance = null;
				MainDialog.graphicConsoleEnabled = false;
				System.exit(1);
			}
		}

		if (MainDialog.graphicConsoleEnabled != false) {
			D
					.out("Graphic mode. CTRL+C or close window to exit. Restart with parameter --help for help");
		} else {
			D
					.out("Headless mode. CTRL+C to exit. Restart with parameter --help for help");
		}

		try {
			MainDialog.createThreadsAndObjects();
			MainDialog.startThreadsAndObjects();
		} catch (Exception e) {
			// FIXME
			e.printStackTrace();
			D.out("Exiting because of exception");
			return;
		}

		MainDialog.handleFirstStart();

		if (!MainDialog.isHeadless() && MainDialog.instance != null) {
			MainInterface.getInstance().setGUIEnabled(true);

			if ((MainInterface.getInstance().guruztrayManager != null)
					&& !Configuration.instance().isFirstStart()) {
				// if we have a tray icon and this is NOT the first start then
				// hide oursevles
				// this is for using p300 in autorun
                MainInterface.getInstance().hideP300();
			} else {
				// show us :)
                MainInterface.getInstance().showP300();
			}

            MainInterface.getInstance().loadOSXInterface();

		}

		// have this get called when the VM exits
		Runtime.getRuntime().addShutdownHook(new ShutdownThread());
		// updaterThread.go(401);

	}

	/**
	 * Reset the administrator password (for web GUI) to a random String of 8
	 * characters
	 * 
	 * @author guruz
	 */
	public static void resetAdminPassword() {
		String newPass = de.guruz.p300.utils.RandomGenerator.string()
				.substring(0, 8);
		Configuration.instance().setAdminPassword(newPass);
	}

	/**
	 * Do stuff on first ever start of p300 - Init host ranges - Show welcome
	 * message
	 * 
	 * @author guruz
	 */
	public static void handleFirstStart() {
		Configuration conf = Configuration.instance();
		boolean firstStart = conf.isFirstStart();

		if (!firstStart) {
			return;
		}

		// allow some host ranges here :)
		// http://en.wikipedia.org/wiki/Private_network
		conf.setIpExplicitlyAllowed("192.168.", true);
		conf.setIpExplicitlyAllowed("10.", true);
		conf.setIpExplicitlyAllowed("169.254.", true); // zeroconf stuff
		conf.setIpExplicitlyAllowed("5.", true); // hamachi
		// enable the automatic detection of local network hosts
		conf.setLocalNetworkIpsImplicitlyAllowed(true);
		

		boolean headless = MainDialog.isHeadless();

		String msg = "This is your first start of p300. Welcome!\n\n"
				+ "You can now add a share. Other hosts should be found automatically, but at the \n"
				+ "first start this can take some time. You can also manually add a host.\n";

		if (Configuration.isJava15()) {
			msg = msg
					+ "\n\nNote that you should update to java 1.6 for tray icon support.";
		}
		

		if (MainInterface.getInstance().guruztrayManager != null
				&& MainInterface.getInstance().guruztrayManager.isSupported()) {
			msg = msg
					+ "\n\nIf you close the main window, it will be minimized in the system tray. On your next start\n"
					+ "of p300 it will automatically be started in the system tray.";
		}

		if (headless) {
			D.out(msg);
		} else {
			JOptionPane.showMessageDialog(null, msg, "information",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * Start a scheduler that send multicast messages and prints upload
	 * connections
	 * 
	 * Start all important threads
	 */
	private static void startThreadsAndObjects() {
		// every 60 seconds (after 30 seconds)
		MainDialog.timer = new Timer();
		// MainDialog.timer.schedule(new TimerTask() {
		// public void run() {
		// D.out ("Start!!!!");
		// MainDialog.multicastListenThread.sendIAmHere();
		// MainDialog.listenThread.printUploadConnections();
		// D.out ("End!!!!");
		// }
		// }, 30 * 1000, Constants.DISCOVERY_MULTICAST_IAMHERE_INTERVAL_MSEC);

		MainDialog.bandwidthThread.start();
		MainDialog.listenThread.start();
		MainDialog.multicastListenThread.start();
		MainDialog.hostFinderThread.start();
		MainDialog.indexerThread.start();
		MainDialog.updaterThread.start();
		MainDialog.newVersionNotificationThread.start();

		MainDialog.hostWatchThread.addObserver(MainDialog.hostMap);

		MainDialog.hostWatchThread.addObserver(LANHostsTreeItem.instance());

		MainDialog.hostWatchThread.start();

		MainDialog.internetListenThread.start();

		MainDialog.downloadManager.start();
	}

	/**
	 * Adds a string to our console text area
	 * 
	 * @param s
	 * @author guruz
	 */
	public void consolePrint(String s) {
        MainInterface.getInstance().consoleField.append(s);
	}

	/**
	 * Adds a line to our console text area
	 * 
	 * @param line
	 * @author guruz
	 */
	public void consolePrintln(String line) {
		MainInterface.getInstance().consoleField.append(line);
        MainInterface.getInstance().consoleField.append("\n");
	}


	public static HostAllowanceManager getHostAllowanceManager() {
		return m_hostAllowanceManager;
	}

}
