package gui;

import javax.swing.*;
import java.awt.*;

/**
 * An abstract base class for section panels in the GUI.
 * Provides a common background color and layout setup for all derived panels.
 * This class should be extended by specific section panels like TopSectionPanel, BottomSectionPanel, etc.
 * @returns A JPanel with a predefined background color.
 */
public abstract class BaseSectionPanel extends JPanel {
    protected static final Color TABLE_COLOR = new Color(0, 128, 0);

    public BaseSectionPanel() {
        setBackground(TABLE_COLOR);
    }
}
