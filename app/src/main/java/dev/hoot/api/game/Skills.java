package dev.hoot.api.game;

import net.runelite.api.Skill;

import meteor.Main;

public class Skills
{

	public static int getBoostedLevel(Skill skill)
	{
		return Main.client.getBoostedSkillLevel(skill);
	}

	public static int getLevel(Skill skill)
	{
		return Main.client.getRealSkillLevel(skill);
	}

	public static int getExperience(Skill skill)
	{
		return Main.client.getSkillExperience(skill);
	}
}
