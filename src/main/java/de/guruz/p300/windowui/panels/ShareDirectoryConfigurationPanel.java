/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.guruz.p300.windowui.panels;

import de.guruz.p300.MainDialog;
import de.guruz.p300.MainInterface;
import de.guruz.p300.search.IndexerThread;
import de.guruz.p300.shares.ShareManager;
import de.guruz.p300.utils.DirectoryPicker;
import de.guruz.p300.utils.IconChooser;
import de.guruz.p300.windowui.ShareTableModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Leo
 */
public class ShareDirectoryConfigurationPanel extends JPanel implements ActionListener {

    public static final long serialVersionUID = 0l;

    /**
     *
     * @author guruz
     */
    private JTextField passwordField;

    /**
     * Button to add a new share to the shares list Opens windows to enter name
     * and directory
     *
     * @author guruz
     */
    private JButton addshareButton;

    /**
     * Button used to modify a share directory
     *
     * @author guruz
     */
    private JButton modifyshareButton;

    /**
     * Button used to delete a share
     *
     * @author guruz
     */
    private JButton deleteShareButton;

    /**
     * Table showing the list of shares
     *
     * @author guruz
     */
    private JTable shareTable;

    public ShareDirectoryConfigurationPanel() {
        this.addshareButton = new JButton();
        this.addshareButton.setToolTipText("Add a share");
        this.addshareButton.setIcon(IconChooser
                .iconImageFromResource("22x22/list-add.png"));
        this.addshareButton.setActionCommand(MainDialog.ACTION_ADD_SHARE);
        this.addshareButton.addActionListener(this);

        this.modifyshareButton = new JButton();
        this.modifyshareButton.setToolTipText("Modify share");
        this.modifyshareButton.setIcon(IconChooser
                .iconImageFromResource("22x22/preferences-system.png"));
        this.modifyshareButton
                .setActionCommand(MainDialog.ACTION_MODIFY_SHARE);
        this.modifyshareButton.addActionListener(this);

        this.deleteShareButton = new JButton();
        this.deleteShareButton.setToolTipText("Delete share");
        this.deleteShareButton.setIcon(IconChooser
                .iconImageFromResource("22x22/list-remove.png"));
        this.deleteShareButton
                .setActionCommand(MainDialog.ACTION_DELETE_SHARE);
        this.deleteShareButton.addActionListener(this);

        this.shareTable = new JTable(ShareTableModel.instance());
        this.shareTable.setShowGrid(false);
        JScrollPane shareScrollPane = new JScrollPane(this.shareTable);
			// shareScrollPane.setPreferredSize(shareTable.getMinimumSize());

        // FIXME: spalten gescheite groesse machen
        this.shareTable
                .setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.shareTable.setPreferredScrollableViewportSize(new Dimension(
                300, 70));
        this.shareTable
                .setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);

        this.setLayout(new BorderLayout());
        this.add(new JLabel(
                "You offer files in these directories:"),
                BorderLayout.NORTH);

        this.add(shareScrollPane, BorderLayout.CENTER);
        JPanel sharesSubPanel = new JPanel();
        this.add(sharesSubPanel, BorderLayout.SOUTH);

        sharesSubPanel.add(this.addshareButton);
        sharesSubPanel.add(this.modifyshareButton);
        sharesSubPanel.add(this.deleteShareButton);

    }

    public void actionPerformed(ActionEvent ae) {
        String acs = ae.getActionCommand();
        if (acs.equals(MainDialog.ACTION_ADD_SHARE)) {

            String dir = DirectoryPicker.pick(MainInterface.getInstance().getWindow(), "Add",
                    null, "Add");

            if (dir != null) {
                File d = new File(dir);

                String s = (String) JOptionPane
                        .showInputDialog(
                                MainInterface.getInstance().getWindow(),
                                "Pick a name for a share (only normal letters and numbers)",
                                "Pick share name", JOptionPane.PLAIN_MESSAGE,
                                null, null, d.getName());

                if ((s != null) && (s.length() > 0)) {

                    boolean succ = ShareManager.instance().addShare(s, dir);

                    if (succ) {
                        // tell indexer to restart
                        if (MainDialog.indexerThread != null) {
                            IndexerThread.restartIndexer();
                        }

                        this.shareTable.revalidate();

                        JOptionPane.showMessageDialog(this, "Share added. It will take a while until it can be searched");
                    } else {
                        JOptionPane.showMessageDialog(this, "Error: Share was not added. Check the console");
                    }
                }
            }

        } else if (acs.equals(MainDialog.ACTION_MODIFY_SHARE)) {
            int row = this.shareTable.getSelectedRow();

            if (row == -1) {
                return;
            }

            Object s_ = this.shareTable.getModel().getValueAt(row, 0);

            if (!(s_ instanceof String)) {
                return;
            }

            String s = (String) s_;

            if ((s != null) && (s.length() > 0)) {
                String old_dir = ShareManager.instance().getShare(s)
                        .getFileLocation();
                String dir = DirectoryPicker.pick(MainInterface.getInstance().getWindow(),
                        "Modify", old_dir, "Modify");

                if (dir != null) {
                    // ShareManager.instance().addShare(s, dir);

                    ShareManager.instance().getShare(s).setLocation(dir);
                    ShareManager.instance().notifyObservers();

                    this.shareTable.revalidate();
                }
            }
        } else if (acs.equals(MainDialog.ACTION_DELETE_SHARE)) {
            System.out.println("Demande de suppresio ed share");
            int row = this.shareTable.getSelectedRow();

            if (row == -1) {
                return;
            }

            if (row >= this.shareTable.getModel().getRowCount()) {
                return;
            }

            Object s_ = this.shareTable.getModel().getValueAt(row, 0);

            if (!(s_ instanceof String)) {
                return;
            }

            String s = (String) s_;

            if (s != null) {
                ShareManager.instance().removeShare(s);

                this.shareTable.revalidate();
            }
        }
    }

    public void addShare() {
        actionPerformed(new ActionEvent(this, 0, MainDialog.ACTION_ADD_SHARE));
    }

}
