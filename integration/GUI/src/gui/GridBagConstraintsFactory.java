package gui;

import java.awt.*;

/**
 * Factory class for creating GridBagConstraints instances.
 * This class provides methods to create GridBagConstraints with specific configurations,
 * allowing for easier management of layout constraints in GUI components.
 * * @returns A GridBagConstraints object configured with the specified parameters.
 */
public class GridBagConstraintsFactory {
    public static GridBagConstraints createConstraints(int gridx, int gridy, int anchor, double weightx, double weighty, int fill) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.anchor = anchor;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        gbc.fill = fill;
        return gbc;
    }

    public static GridBagConstraints createDefaultConstraints(int gridx, int gridy) {
        return createConstraints(gridx, gridy, GridBagConstraints.CENTER, 1.0, 1.0, GridBagConstraints.NONE);
    }
}
