package net.runelite.rs.api;

import net.runelite.api.KeyFocusListener;
import net.runelite.mapping.Import;

public interface RSKeyHandler extends KeyFocusListener {
  @Import("KeyHandler_idleCycles")
  int getIdleCycles$api();

  @Import("KeyHandler_idleCycles")
  void setIdleCycles(int i);
}
