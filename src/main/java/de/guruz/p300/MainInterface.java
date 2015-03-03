package de.guruz.p300;

import de.guruz.guruzsplash.interfaces.GuruzsplashManager;
import de.guruz.guruztray.interfaces.GuruztrayManager;
import de.guruz.p300.logging.D;
import de.guruz.p300.onetoonechat.LanMessageRemoteOutbox;
import de.guruz.p300.onetoonechat.LanMessageRouter;
import de.guruz.p300.onetoonechat.ui.ChatWindowMap;
import de.guruz.p300.onetoonechat.ui.UiMessageRouter;
import de.guruz.p300.osx.OSXCallbackInterface;
import de.guruz.p300.osx.OSXInterface;
import de.guruz.p300.utils.OsUtils;
import de.guruz.p300.utils.launchers.BareBonesBrowserLaunch;
import de.guruz.p300.windowui.actions.WebinterfaceAction;
import de.guruz.p300.windowui.panels.ConfigurationPanel;
import de.guruz.p300.windowui.panels.ConsolePanel;
import de.guruz.p300.windowui.panels.DownloadsPanel;
import de.guruz.p300.windowui.panels.UploadsPanel;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Window;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import de.guruz.guruzsplash.interfaces.GuruzsplashManager;
import de.guruz.guruztray.interfaces.GuruztrayManager;
import de.guruz.p300.logging.D;

import java.net.URL;

/**
 * Created by Leo on 01/03/15.
 */
public class MainInterface extends javax.swing.JFrame implements OSXCallbackInterface {

    private static MainInterface instance = new MainInterface();
    private static Window currentWindow = null;
    public UploadsPanel uploadsPanel;
    public DownloadsPanel downloadsPanel;
    /**
     * @author guruz
     * @see de.guruz.guruztray.interfaces.GuruztrayManager
     */
    GuruztrayManager guruztrayManager = null;
    ConsolePanel consolePanel;
    ConfigurationPanel configurationPanel;
    JTextArea consoleField;
    private GuruzsplashManager guruzsplashManager;
    private Map<JComponent, Window> frames = new HashMap<JComponent, Window>();
    private LanMessageRouter lanMessageRouter = null;
    private ChatWindowMap chatWindowMap = null;
    // Variables declaration - do not modify                     
    private javax.swing.JMenuItem consoleLogMenuItem;
    private de.guruz.p300.windowui.panels.DownloadDirectoryConfigurationPanel downloadDirectoryConfigurationPanel2;
    private javax.swing.JScrollPane downloadScrollPane;
    private de.guruz.p300.windowui.panels.DownloadsPanel downloadsPanel1;
    private javax.swing.JScrollPane explorePane;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem githubProjectMenuItem;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JMenu logMenu;
    private de.guruz.p300.windowui.panels.NetworkPeerTreePanel networkPeerTreePanel1;
    private de.guruz.p300.windowui.panels.PasswordConfigurationPanel passwordConfigurationPanel21;
    private javax.swing.JMenuItem quitMenuItem;
    private de.guruz.p300.windowui.panels.ShareDirectoryConfigurationPanel shareDirectoryConfigurationPanel1;
    private javax.swing.JScrollPane shareFolderScrollPane;
    private de.guruz.p300.windowui.panels.SoundChatConfigurationPanel soundChatConfigurationPanel1;
    private javax.swing.JMenuItem suggestFeatureMenuItem;
    private javax.swing.JMenuItem uploadLogMenuItem;
    private javax.swing.JMenu webInterfaceMenu;


    //
    // SIGNLETON DESIGN
    //
    private MainInterface() {
    }

    public static MainInterface getInstance() {
        return instance;
    }

    public static Window getWindow() {
        return currentWindow;
    }

    /**
     * Hide the splash screen after one second
     *
     * @author guruz
     * @see #guruzsplashManager
     * @see #initSplashScreen()
     */
    public void hideSplashScreen() {
        if (this.guruzsplashManager != null) {
            try {
                Thread.sleep(1000);
                this.guruzsplashManager.hide();
            } catch (Throwable t) {
                // e.printStackTrace();
                D.out("Splashscreen: " + t.toString());
            }

        }
    }

    /**
     * Initialize and show the splash screen
     *
     * @return True: Splash is visible; False: Splash not visible (might be no
     * GUI)
     * @author guruz
     * @see #guruzsplashManager
     * @see #hideSplashScreen()
     */
    public boolean initSplashScreen() {
        try {
            String className = "de.guruz.guruzsplash.implementation.GuruzsplashManagerImplementation";
            Class<?> c = Class.forName(className);
            Object o = c.newInstance();
            this.guruzsplashManager = (GuruzsplashManager) o;
            if (this.guruzsplashManager.isVisible()) {
                return true;
            } else {
                this.guruztrayManager = null;
                return false;
            }

        } catch (Exception t) {
            D.out("No splash screen supported on this platform or java version" + t.getMessage());
            this.guruzsplashManager = null;
            return false;
        }
    }

    public void setGUIEnabled(boolean b) {
    }

    public ConsolePanel getConsolePanel() {
        return consolePanel;
    }

    public DownloadsPanel getDownloadsPanel() {
        return downloadsPanel;
    }

    public LanMessageRouter getLANMessageRouter() {
        return lanMessageRouter;
    }

    public ChatWindowMap getChatWindowMap() {
        return chatWindowMap;
    }

    public ConfigurationPanel getConfigurationPanel() {
        return configurationPanel;
    }

    /**
     * Creates new form MainDialog
     */
    public void createGUI() {
        this.consolePanel = new ConsolePanel();
        this.uploadsPanel = new UploadsPanel();
        this.downloadsPanel = new DownloadsPanel();
        currentWindow = SwingUtilities.getWindowAncestor(this);
        chatWindowMap = new ChatWindowMap();
        lanMessageRouter = new LanMessageRouter();
        UiMessageRouter uiMessageRouter = new UiMessageRouter();
        LanMessageRemoteOutbox lanMessageRemoteOutbox = new LanMessageRemoteOutbox();

        lanMessageRouter.setUiMessageRouter(uiMessageRouter);
        lanMessageRouter.setLanMessageRemoteOutbox(lanMessageRemoteOutbox);
        lanMessageRemoteOutbox.setUiMessageRouter(uiMessageRouter);
        initComponents();

        // de the splash, no matter if it exists or is visible
        Runnable r = new Runnable() {
            public void run() {
                //MainInterface.getInstance().hideSplashScreen();
                MainInterface.getInstance().showP300();
            }
        };

        new Thread(r).start();
        System.setProperty("com.apple.mrj.application.apple.menu.about.name",
                "p300");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        explorePane = new javax.swing.JScrollPane();
        networkPeerTreePanel1 = new de.guruz.p300.windowui.panels.NetworkPeerTreePanel();
        shareFolderScrollPane = new javax.swing.JScrollPane();
        shareDirectoryConfigurationPanel1 = new de.guruz.p300.windowui.panels.ShareDirectoryConfigurationPanel();
        downloadScrollPane = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        downloadsPanel1 = new de.guruz.p300.windowui.panels.DownloadsPanel();
        jPanel1 = new javax.swing.JPanel();
        soundChatConfigurationPanel1 = new de.guruz.p300.windowui.panels.SoundChatConfigurationPanel();
        passwordConfigurationPanel21 = new de.guruz.p300.windowui.panels.PasswordConfigurationPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        downloadDirectoryConfigurationPanel2 = new de.guruz.p300.windowui.panels.DownloadDirectoryConfigurationPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        quitMenuItem = new javax.swing.JMenuItem();
        webInterfaceMenu = new javax.swing.JMenu();
        logMenu = new javax.swing.JMenu();
        consoleLogMenuItem = new javax.swing.JMenuItem();
        uploadLogMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        githubProjectMenuItem = new javax.swing.JMenuItem();
        suggestFeatureMenuItem = new javax.swing.JMenuItem();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        explorePane.setViewportView(networkPeerTreePanel1);

        jTabbedPane1.addTab("Explore", explorePane);

        shareFolderScrollPane.setViewportView(shareDirectoryConfigurationPanel1);

        jTabbedPane1.addTab("Share folder", shareFolderScrollPane);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(downloadsPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(downloadsPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
                                .addContainerGap())
        );

        downloadScrollPane.setViewportView(jPanel2);

        jTabbedPane1.addTab("Download manager", downloadScrollPane);

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel2.setText("Password settings");

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel3.setText("Download folder settings");

        jLabel4.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel4.setText("Chat settings");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel2)
                                                        .addComponent(jLabel3)
                                                        .addComponent(jLabel4)))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(29, 29, 29)
                                                .addComponent(downloadDirectoryConfigurationPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(36, 36, 36)
                                                .addComponent(passwordConfigurationPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(38, 38, 38)
                                                .addComponent(soundChatConfigurationPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(126, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(downloadDirectoryConfigurationPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(passwordConfigurationPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(soundChatConfigurationPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 128, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Options", jPanel1);

        fileMenu.setText("File");

        quitMenuItem.setText("Quit");
        quitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(quitMenuItem);

        jMenuBar1.add(fileMenu);

        webInterfaceMenu.setText("Open Webinterface");
        webInterfaceMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                webInterfaceMenuMouseClicked(evt);
            }
        });
        jMenuBar1.add(webInterfaceMenu);

        logMenu.setText("Logs");

        consoleLogMenuItem.setText("Console");
        consoleLogMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consoleLogMenuItemActionPerformed(evt);
            }
        });
        logMenu.add(consoleLogMenuItem);

        uploadLogMenuItem.setText("Upload");
        uploadLogMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadLogMenuItemActionPerformed(evt);
            }
        });
        logMenu.add(uploadLogMenuItem);

        jMenuBar1.add(logMenu);

        helpMenu.setText("Help");

        githubProjectMenuItem.setText("Github project");
        githubProjectMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                githubProjectMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(githubProjectMenuItem);

        suggestFeatureMenuItem.setText("Suggest feature");
        suggestFeatureMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suggestFeatureMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(suggestFeatureMenuItem);

        jMenuBar1.add(helpMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>                        

    private void suggestFeatureMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        String URL = "http://p300.uservoice.com/forums/1270-general";
        BareBonesBrowserLaunch.openURL(URL);
    }

    private void githubProjectMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        String URL = "https://github.com/serut/p300";
        BareBonesBrowserLaunch.openURL(URL);
    }

    private void webInterfaceMenuMouseClicked(java.awt.event.MouseEvent evt) {
        new WebinterfaceAction().actionPerformed(null);
    }

    private void uploadLogMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        JFrame f = new JFrame();
        f.setSize(600, 200);
        f.add(this.uploadsPanel);
        f.setVisible(true);
    }

    private void consoleLogMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        JFrame f = new JFrame();
        f.setSize(600, 200);
        f.add(this.consolePanel);
        f.setVisible(true);
    }

    private void quitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(0);
    }

    public void hideP300() {
        this.setVisible(false);
    }

    public void showP300() {
        this.setVisible(true);
        this.requestFocus();
        this.toFront();
    }

    public boolean isDownloadsPanelShown() {
        // System.out.println ("downloads panel showing " +
        // downloadsPanel.isShowing());
        return downloadsPanel.isShowing();
    }

    public void showSubWindow(final Image icon, final String title,
                              final JComponent c) {
        showSubWindow(icon, title, c, true);
    }

    public void showSubWindow(final Image icon, final String title,
                              final JComponent c, final boolean toForeground) {
        MainInterface.getInstance().showSubWindow(icon, title, c, toForeground);
    }

    /**
     * @author guruz
     */
    public void loadOSXInterface() {
        if (!OsUtils.isOSX()) {
            return;
        }

        try {
            OSXInterface i = null;
            String className = "de.guruz.p300.osx.OSX";
            Class<?> c = Class.forName(className);
            Object o = c.newInstance();
            i = (OSXInterface) o;
            i.setCallback(this);

        } catch (Throwable t) {
            // e.printStackTrace();
            return;
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.guruz.p300.osx.OSXCallbackInterface#OSXreOpenApplication()
     */
    public void OSXreOpenApplication() {
        this.showP300();
    }

    public void OSXabout() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
