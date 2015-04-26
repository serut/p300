package de.guruz.p300;

import com.apple.eawt.Application;
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

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

import de.guruz.guruzsplash.interfaces.GuruzsplashManager;
import de.guruz.guruztray.interfaces.GuruztrayManager;
import de.guruz.p300.logging.D;

import java.util.logging.Level;
import java.util.logging.Logger;

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
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            Logger.getLogger(MainInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
        initComponents();
        if (OsUtils.isOSX()) {
            System.setProperty("apple.awt.fileDialogForDirectories", "true");
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "p300");
        }

        // set JFrame to appear centered
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);


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

        setTitle("Goat Share");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel4 = new javax.swing.JPanel();
        jButtonOpenWebInterface = new javax.swing.JButton();
        networkPeerTreePanel1 = new de.guruz.p300.windowui.panels.NetworkPeerTreePanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        shareFolderScrollPane = new javax.swing.JScrollPane();
        shareDirectoryConfigurationPanel1 = new de.guruz.p300.windowui.panels.ShareDirectoryConfigurationPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        passwordConfigurationPanel21 = new de.guruz.p300.windowui.panels.PasswordConfigurationPanel();
        jLabel3 = new javax.swing.JLabel();
        downloadDirectoryConfigurationPanel2 = new de.guruz.p300.windowui.panels.DownloadDirectoryConfigurationPanel();
        jLabel4 = new javax.swing.JLabel();
        soundChatConfigurationPanel1 = new de.guruz.p300.windowui.panels.SoundChatConfigurationPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        consolePanel1 = new de.guruz.p300.windowui.panels.ConsolePanel();
        uploadsPanel1 = new de.guruz.p300.windowui.panels.UploadsPanel();
        downloadScrollPane = new javax.swing.JScrollPane();
        downloadsPanel1 = new de.guruz.p300.windowui.panels.DownloadsPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        quitMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        githubProjectMenuItem = new javax.swing.JMenuItem();
        suggestFeatureMenuItem = new javax.swing.JMenuItem();

        jSplitPane1.setBorder(null);
        jSplitPane1.setDividerLocation(240);

        jButtonOpenWebInterface.setText("Open Web Interface");
        jButtonOpenWebInterface.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonOpenWebInterfaceMouseClicked(evt);
            }
        });
        jButtonOpenWebInterface.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOpenWebInterfaceActionPerformed(evt);
            }
        });
        jPanel4.add(jButtonOpenWebInterface);
        jPanel4.add(networkPeerTreePanel1);

        jSplitPane1.setLeftComponent(jPanel4);

        shareFolderScrollPane.setBorder(null);
        shareFolderScrollPane.setPreferredSize(new java.awt.Dimension(350, 300));
        shareFolderScrollPane.setViewportView(shareDirectoryConfigurationPanel1);

        jTabbedPane1.addTab("Share", shareFolderScrollPane);

        jPanel1.setLayout(new java.awt.GridLayout(6, 0));

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel2.setText("Password settings");
        jPanel1.add(jLabel2);
        jPanel1.add(passwordConfigurationPanel21);

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel3.setText("Download folder settings");
        jPanel1.add(jLabel3);
        jPanel1.add(downloadDirectoryConfigurationPanel2);

        jLabel4.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel4.setText("Chat settings");
        jPanel1.add(jLabel4);
        jPanel1.add(soundChatConfigurationPanel1);

        jTabbedPane1.addTab("Settings", jPanel1);

        jScrollPane1.setBorder(null);

        jTabbedPane2.addTab("Console", consolePanel1);
        jTabbedPane2.addTab("Upload", uploadsPanel1);

        jScrollPane1.setViewportView(jTabbedPane2);

        jTabbedPane1.addTab("Logs", jScrollPane1);

        downloadScrollPane.setBorder(null);
        downloadScrollPane.setViewportView(downloadsPanel1);

        jTabbedPane1.addTab("Download", downloadScrollPane);

        jSplitPane1.setRightComponent(jTabbedPane1);

        fileMenu.setText("File");

        quitMenuItem.setText("Quit");
        quitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(quitMenuItem);

        jMenuBar1.add(fileMenu);

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
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 773, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void suggestFeatureMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suggestFeatureMenuItemActionPerformed
        String URL = "http://p300.uservoice.com/forums/1270-general";
        BareBonesBrowserLaunch.openURL(URL);
    }//GEN-LAST:event_suggestFeatureMenuItemActionPerformed

    private void githubProjectMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_githubProjectMenuItemActionPerformed
        String URL = "https://github.com/serut/p300";
        BareBonesBrowserLaunch.openURL(URL);
    }//GEN-LAST:event_githubProjectMenuItemActionPerformed

    private void quitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_quitMenuItemActionPerformed

    private void jButtonOpenWebInterfaceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonOpenWebInterfaceMouseClicked
        new WebinterfaceAction().actionPerformed(null);
    }//GEN-LAST:event_jButtonOpenWebInterfaceMouseClicked

    private void jButtonOpenWebInterfaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOpenWebInterfaceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonOpenWebInterfaceActionPerformed


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
        if (c == null)
            return;

        final Window f;
        if (frames.containsKey(c))
            f = frames.get(c);
        else {
            f = new JFrame();
            ((JFrame) f).setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            f.add(c);
            f.setSize(new Dimension(800, 600));
            f.setLocationByPlatform(true);
            frames.put(c, f);
        }

        Runnable r = new Runnable() {
            public void run() {
                ((JFrame) f).setTitle(title);
                if (toForeground || !f.isVisible())
                    f.setVisible(true);
            }
        };

        if (SwingUtilities.isEventDispatchThread()) {
            r.run();
        } else {
            try {
                SwingUtilities.invokeAndWait(r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @author guruz
     */
    public void loadOSXInterface() {
        if (!OsUtils.isOSX()) {
            return;
        }

        try {
            Application application = Application.getApplication();
            Image image = Toolkit.getDefaultToolkit().getImage("icon.png");
            application.setDockIconImage(image);
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.guruz.p300.windowui.panels.ConsolePanel consolePanel1;
    private de.guruz.p300.windowui.panels.DownloadDirectoryConfigurationPanel downloadDirectoryConfigurationPanel2;
    private javax.swing.JScrollPane downloadScrollPane;
    private de.guruz.p300.windowui.panels.DownloadsPanel downloadsPanel1;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem githubProjectMenuItem;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JButton jButtonOpenWebInterface;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private de.guruz.p300.windowui.panels.NetworkPeerTreePanel networkPeerTreePanel1;
    private de.guruz.p300.windowui.panels.PasswordConfigurationPanel passwordConfigurationPanel21;
    private javax.swing.JMenuItem quitMenuItem;
    private de.guruz.p300.windowui.panels.ShareDirectoryConfigurationPanel shareDirectoryConfigurationPanel1;
    private javax.swing.JScrollPane shareFolderScrollPane;
    private de.guruz.p300.windowui.panels.SoundChatConfigurationPanel soundChatConfigurationPanel1;
    private javax.swing.JMenuItem suggestFeatureMenuItem;
    private de.guruz.p300.windowui.panels.UploadsPanel uploadsPanel1;
    // End of variables declaration//GEN-END:variables
}
