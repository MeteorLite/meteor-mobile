package osrs;

import java.awt.Component;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
/*import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;*/
import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ai")
@Implements("MouseWheelHandler")
public final class MouseWheelHandler implements class171/*, MouseWheelListener*/ {
   @ObfuscatedName("aj")
   @Export("rotation")
   static int rotation = 0;

   @ObfuscatedName("aj")
   @ObfuscatedSignature(
      descriptor = "(Ljava/awt/Component;I)V",
      garbageValue = "-1696751646"
   )
   @Export("addTo")
   void addTo(BufferedImage var1) {
      /*var1.addMouseWheelListener(this);*/
   }

   @ObfuscatedName("al")
   @ObfuscatedSignature(
      descriptor = "(Ljava/awt/Component;B)V",
      garbageValue = "-75"
   )
   void method86(BufferedImage var1) {
      /*var1.removeMouseWheelListener(this);*/
   }

   @ObfuscatedName("ac")
   @ObfuscatedSignature(
      descriptor = "(I)I",
      garbageValue = "1061064035"
   )
   @Export("useRotation")
   public synchronized int useRotation() {
      int var1 = this.rotation;
      this.rotation = 0;
      return var1;
   }

   @Export("mouseWheelMoved")
   @ObfuscatedName("mouseWheelMoved")
   public static void mouseWheelMoved(MouseWheelEvent var1) {
      MouseWheelHandler.rotation += var1.getWheelRotation();
   }
}
