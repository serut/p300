/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.guruz.p300.windowui.panels;

import de.guruz.p300.Configuration;
import de.guruz.p300.MainDialog;
import de.guruz.p300.MainInterface;
import de.guruz.p300.utils.DirectoryPicker;
import de.guruz.p300.utils.DirectoryUtils;
import de.guruz.p300.utils.FileNameUtils;
import de.guruz.p300.utils.IconChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Leo
 */
public class DownloadDirectoryConfigurationPanel extends javax.swing.JPanel {

    /**
     * Creates new form DownloadDirectoryConfigurationPanel
     */
    public DownloadDirectoryConfigurationPanel() {
        initComponents();
        currentPathTextField.setText(getDownloadDirectory());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        currentPathTextField = new javax.swing.JTextField();
        setDownloadDirectoryButton = new javax.swing.JButton();

        jLabel1.setText("Current path :");

        setDownloadDirectoryButton.setText("Edit");
        setDownloadDirectoryButton.setIcon(IconChooser.iconImageFromResource("22x22/system-file-manager.png"));
        setDownloadDirectoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setDownloadDirectoryButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(currentPathTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(setDownloadDirectoryButton)
                .addContainerGap(28, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(setDownloadDirectoryButton, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(currentPathTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void setDownloadDirectoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setDownloadDirectoryButtonActionPerformed
        String dir = getDownloadDirectory();
        DirectoryUtils.makeSureDirectoryExists(dir);
        dir = DirectoryPicker.pick(MainInterface.getInstance().getWindow(), "Set Download Directory", dir, "Set");

        if (dir != null) {
            Configuration.instance().setFinishedDownloadDir(dir);
            currentPathTextField.setText(dir);
            JOptionPane.showMessageDialog(null, "Download directory is now" + dir + "\n\nYou need to restart this application.");
        }
    }//GEN-LAST:event_setDownloadDirectoryButtonActionPerformed

    private static String getDownloadDirectory() {
        return FileNameUtils.replaceVariables(Configuration.instance().getFinishedDownloadDir());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField currentPathTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton setDownloadDirectoryButton;
    // End of variables declaration//GEN-END:variables
}
