package de.guruz.p300.windowui.panels;

import de.guruz.p300.Configuration;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Leo
 */
public class SoundChatConfigurationPanel extends JPanel {

    public static final long serialVersionUID = 0l;
    private final JCheckBox m_chatSoundCheckbox;

    public SoundChatConfigurationPanel() {
        m_chatSoundCheckbox = new JCheckBox("Play sound when chat message is sent or received", Configuration.instance().isPlayChatSound());
        m_chatSoundCheckbox.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                Configuration.instance().setPlayChatSound(m_chatSoundCheckbox.isSelected());

            }
        });

        this.add(m_chatSoundCheckbox);

    }
}
