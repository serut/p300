package de.guruz.p300.windowui.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import de.guruz.p300.MainDialog;
import de.guruz.p300.MainInterface;
import de.guruz.p300.utils.IconChooser;

public class AddShareAction extends AbstractAction {

	public AddShareAction() {
		super("Add A Shared Folder", IconChooser.getAddIcon());
	}


	public void actionPerformed(ActionEvent arg0) {
        MainInterface md = MainInterface.getInstance();
		md.showSubWindow(null, "Configuration",
				md.getConfigurationPanel());
		md.getConfigurationPanel().addShare ();
	}

}
