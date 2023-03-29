package net.runelite.rs.api;


import net.runelite.mapping.Import;

import java.awt.event.MouseWheelEvent;

public interface RSMouseWheelHandler {

    @Import("mouseWheelMoved")
    static void mouseWheelMoved$api(MouseWheelEvent event) {

    }
}
