package com.simulator.view;

import javafx.geometry.Insets;
import javafx.scene.layout.Pane;

/**
 * Utility methods to centralise all windows style
 * 
 * @author sunquan
 *
 */
public class DisplayUtils {

	private DisplayUtils() {
	}

	public static void applyStyleTo(Pane panel) {
		panel.setStyle("-fx-background-color: #336699;");
		panel.setPadding(new Insets(10));
	}
}
