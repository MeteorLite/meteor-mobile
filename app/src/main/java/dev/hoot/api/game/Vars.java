package dev.hoot.api.game;

import net.runelite.api.VarClientInt;
import net.runelite.api.VarClientStr;
import net.runelite.api.Varbits;

import meteor.Main;

public class Vars
{
	public static int getBit(int id)
	{
		return GameThread.invokeLater(() -> Main.client.getVarbitValue(Main.client.getVarps(), id));
	}

	public static int getBit(Varbits varbits)
	{
		return getBit(varbits);
	}

	public static int getVarp(int id)
	{
		return Main.client.getVarpValue(Main.client.getVarps(), id);
	}

	public static int getVarcInt(VarClientInt varClientInt)
	{
		return Main.client.getVarcIntValue(varClientInt.index);
	}

	public static String getVarcStr(VarClientStr varClientStr)
	{
		return Main.client.getVarcStrValue(varClientStr.index);
	}
}
