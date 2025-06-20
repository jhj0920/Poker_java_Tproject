package gui;

import javax.swing.*;
import java.awt.*;

public abstract class BaseSectionPanel extends JPanel {
    protected static final Color TABLE_COLOR = new Color(0, 128, 0);

    public BaseSectionPanel() {
        setBackground(TABLE_COLOR);
    }
}
