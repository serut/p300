/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.guruz.p300.windowui.panels;

import de.guruz.p300.MainInterface;
import de.guruz.p300.search.ui.SearchResultsWindow;
import static de.guruz.p300.utils.IconChooser.iconImageFromResource;
import de.guruz.p300.windowui.MainDialogTreeCellEditor;
import de.guruz.p300.windowui.MainDialogTreeCellRenderer;
import de.guruz.p300.windowui.actions.AddHostAction;
import de.guruz.p300.windowui.maintree.InternetHostsTreeItem;
import de.guruz.p300.windowui.maintree.LANHostTreeItem;
import de.guruz.p300.windowui.maintree.LANHostsTreeItem;
import de.guruz.p300.windowui.maintree.MainDialogTreeModel;
import de.guruz.p300.windowui.maintree.MainTree;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;

/**
 *
 * @author Leo
 */
public class NetworkPeerTreePanel extends javax.swing.JPanel {

    JPanel notImplementedYetPanel;

    /**
     * Creates new form NetworkPeerTreePanel
     */
    public NetworkPeerTreePanel() {
        initComponents();
        this.notImplementedYetPanel = new NotImplementedYetPanel();
        searchButton.setEnabled(false);
        jTextField1.getDocument().addDocumentListener(
                new DocumentListener() {

                    public void changedUpdate(DocumentEvent e) {
                        searchButton.setEnabled(jTextField1.getText().length() > 0);
                    }

                    public void insertUpdate(DocumentEvent e) {
                        changedUpdate(e);
                    }

                    public void removeUpdate(DocumentEvent e) {
                        changedUpdate(e);

                    }
                });
        jTextField1.addMouseMotionListener(new MouseMotionListener() {

            public void mouseDragged(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            public void mouseMoved(MouseEvent e) {
                jTextField1.requestFocusInWindow();

            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        searchButton = new javax.swing.JToggleButton();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();

        searchButton.setIcon(getSearchIcon());
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });
        
        MainDialogTreeModel mdtm = MainDialogTreeModel.instance();
        this.jTree1 = new MainTree(mdtm);
        this.jTree1.setRootVisible(false);
        this.jTree1.setRowHeight(0);
        this.jTree1.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));

        this.jTree1.setShowsRootHandles(false);

        jTree1.setEditable(true);
        MainDialogTreeCellRenderer treeCellRender = new MainDialogTreeCellRenderer(
                jTree1);
        MainDialogTreeCellEditor treeCellEditor = new MainDialogTreeCellEditor(
                treeCellRender, jTree1);

        this.jTree1.setCellEditor(treeCellEditor);
        this.jTree1.setCellRenderer(treeCellRender);

        this.jTree1.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {

                TreePath p = jTree1.getClosestPathForLocation(e.getX(), e
                        .getY());

                if (p != null)
                    jTree1.setSelectionPath(p);
                
                if (jTree1.isExpanded(p))
                    jTree1.collapsePath(p);
                else
                    jTree1.expandPath(p);
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent arg0) {

            }

            public void mousePressed(MouseEvent me) {
                if (me.getClickCount() > 2 && me.getButton() == me.BUTTON1) {
                    me.consume();
                    if (jTree1.getSelectionPath() != null
                        && jTree1.getSelectionPath().getLastPathComponent() == InternetHostsTreeItem
                        .instance()) {
                        showSubWindow(null, "Not implemented yet",
                            notImplementedYetPanel);
                    } else if (jTree1.getSelectionPath() != null
                        && jTree1.getSelectionPath().getLastPathComponent() == LANHostsTreeItem
                        .instance()) {
                        new AddHostAction().actionPerformed(null);
                    }
                }

            }

            public void mouseReleased(MouseEvent arg0) {
                // TODO Auto-generated method stub

            }
        });

//        this.jTree1.addTreeWillExpandListener(new TreeWillExpandListener() {
//
//            public void treeWillCollapse(TreeExpansionEvent event)
//                    throws ExpandVetoException {
//                throw new ExpandVetoException(event);
//            }
//
//            public void treeWillExpand(TreeExpansionEvent event)
//                    throws ExpandVetoException {
//            }
//        });
        jScrollPane1.setViewportView(jTree1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTextField1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        String s = jTextField1.getText();
        SearchResultsWindow srw = new SearchResultsWindow(s);

        // JFrame f = new JFrame ();
        // f.setTitle(srw.getTitle ());
        // f.setLocationByPlatform(true);
        // f.setVisible(true);
        showSubWindow(null, srw.getTitle(), srw);

        srw.asyncSearchStartAllHosts();
    }//GEN-LAST:event_searchButtonActionPerformed

    private static ImageIcon searchImageIcon = iconImageFromResource("16x16/system-search.png");

    public static Icon getSearchIcon() {
        return searchImageIcon;
    }

    public void showSubWindow(final Image icon, final String title,
            final JComponent c) {
        showSubWindow(icon, title, c, true);
    }
    /**
     * This call changes the content of the info panel at the right
     *
     * @param c
     */
    private Map<JComponent, Window> frames = new HashMap<JComponent, Window>();

    public void showSubWindow(final Image icon, final String title,
            final JComponent c, final boolean toForeground) {
        if (c == null) {
            return;
        }

        final Window f;
        if (frames.containsKey(c)) {
            f = frames.get(c);
        } else {
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
                if (toForeground || !f.isVisible()) {
                    f.setVisible(true);
                }
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
    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTree jTree1;
    private javax.swing.JToggleButton searchButton;
    // End of variables declaration//GEN-END:variables
}
