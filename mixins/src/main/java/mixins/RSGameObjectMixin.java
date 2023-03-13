/*
 * Copyright (c) 2016-2017, Adam <Adam@sigterm.info>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package mixins;

import java.awt.Shape;
import net.runelite.api.ObjectComposition;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.coords.Angle;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.mixins.Inject;
import net.runelite.api.mixins.Mixin;
import net.runelite.api.mixins.Shadow;
import net.runelite.rs.api.RSClient;
import net.runelite.rs.api.RSGameObject;
import net.runelite.rs.api.RSModel;
import net.runelite.rs.api.RSObjectComposition;
import net.runelite.rs.api.RSRenderable;

@Mixin(RSGameObject.class)
public abstract class RSGameObjectMixin implements RSGameObject
{
	@Shadow("client")
	private static RSClient client;

	@Inject
	private int gameObjectPlane;

	@Inject
	@Override
	public int getPlane()
	{
		return gameObjectPlane;
	}

	@Inject
	@Override
	public void setPlane(int plane)
	{
		this.gameObjectPlane = plane;
	}

	@Inject
	@Override
	public Point getSceneMinLocation()
	{
		return new Point(getStartX(), getStartY());
	}

	@Inject
	@Override
	public Point getSceneMaxLocation()
	{
		return new Point(getEndX(), getEndY());
	}

	@Inject
	public RSModel getModel()
	{
		RSRenderable renderable = getRenderable();
		if (renderable == null)
		{
			return null;
		}

		if (renderable instanceof RSModel)
		{
			return (RSModel) renderable;
		}
		else
		{
			return renderable.getModel();
		}
	}

	@Inject
	@Override
	public Shape getClickbox()
	{
		return Perspective.getClickbox(client, getModel(), getModelOrientation(), getX(), getY(), getZ());
	}

	@Inject
	@Override
	public Shape getConvexHull()
	{
		RSModel model = getModel();

		if (model == null)
		{
			return null;
		}

		int tileHeight = Perspective.getTileHeight(client, new LocalPoint(getX(), getY()), client.getPlane());

		return model.getConvexHull(getX(), getY(), getModelOrientation(), tileHeight);
	}

	@Override
	@Inject
	public Angle getOrientation()
	{
		int orientation = getModelOrientation();
		int rotation = (getConfig() >> 6) & 3;
		return new Angle(rotation * 512 + orientation);
	}

	@Override
	@Inject
	public int sizeX()
	{
		return getEndX() - getStartX() + 1;
	}

	@Override
	@Inject
	public int sizeY()
	{
		return getEndY() - getStartY() + 1;
	}

	@Inject
	private RSObjectComposition transformedComposition = null;

	@Inject
	@Override
	public WorldArea getWorldArea()
	{
		if (!getLocalLocation().isInScene())
		{
			return null;
		}

		LocalPoint localSWTile = new LocalPoint(
				getLocalLocation().getX() - sizeX() * Perspective.LOCAL_TILE_SIZE / 2,
				getLocalLocation().getY() - sizeY() * Perspective.LOCAL_TILE_SIZE / 2
		);

		LocalPoint localNETile = new LocalPoint(
				getLocalLocation().getX() + sizeX() * Perspective.LOCAL_TILE_SIZE / 2,
				getLocalLocation().getY() + sizeY() * Perspective.LOCAL_TILE_SIZE / 2
		);

		return new WorldArea(
				WorldPoint.fromLocal(client, localSWTile),
				WorldPoint.fromLocal(client, localNETile)
		);
	}

	@Inject
	public RSObjectComposition getTransformedComposition()
	{
		return transformedComposition;
	}

	@Inject
	public void setTransformedComposition(ObjectComposition composition)
	{
		transformedComposition = (RSObjectComposition) composition;
	}
}
