package de.guruz.p300.windowui.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import de.guruz.p300.MainDialog;
import de.guruz.p300.MainInterface;
import de.guruz.p300.utils.IconChooser;

public class ConfigurationAction extends AbstractAction {

	public ConfigurationAction() {
		super("Configuration", IconChooser.getConfigurationIcon());
	}

	public void actionPerformed(ActionEvent e) {
		MainInterface.getInstance().showSubWindow(null, "Configuration",
				MainInterface.getInstance().getConfigurationPanel());
	}

}
