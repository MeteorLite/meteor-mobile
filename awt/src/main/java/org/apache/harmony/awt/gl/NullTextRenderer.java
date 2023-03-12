package org.apache.harmony.awt.gl;

import java.awt.Graphics2D;
import java.awt.font.GlyphVector;

public class NullTextRenderer extends TextRenderer {

	@Override
	public void drawString(Graphics2D g, String str, float x, float y) {
		
	}

	@Override
	public void drawGlyphVector(Graphics2D g, GlyphVector glyphVector, float x,
			float y) {
		
	}

}
