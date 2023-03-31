package dev.hoot.api.packets;

import meteor.api.ClientPackets;
import net.runelite.api.packets.PacketBufferNode;
import net.runelite.api.widgets.Widget;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class WidgetPackets
{
	public static void widgetFirstOption(Widget widget)
	{
		queueWidgetAction1Packet(widget.getId(), widget.getItemId(), widget.getIndex());
	}

	public static void widgetSecondOption(Widget widget)
	{
		queueWidgetAction2Packet(widget.getId(), widget.getItemId(), widget.getIndex());
	}

	public static void widgetThirdOption(Widget widget)
	{
		queueWidgetAction3Packet(widget.getId(), widget.getItemId(), widget.getIndex());
	}

	public static void widgetFourthOption(Widget widget)
	{
		queueWidgetAction4Packet(widget.getId(), widget.getItemId(), widget.getIndex());
	}

	public static void widgetFifthOption(Widget widget)
	{
		queueWidgetAction5Packet(widget.getId(), widget.getItemId(), widget.getIndex());
	}

	public static void widgetSixthOption(Widget widget)
	{
		queueWidgetAction6Packet(widget.getId(), widget.getItemId(), widget.getIndex());
	}

	public static void widgetSeventhOption(Widget widget)
	{
		queueWidgetAction7Packet(widget.getId(), widget.getItemId(), widget.getIndex());
	}

	public static void widgetEighthOption(Widget widget)
	{
		queueWidgetAction8Packet(widget.getId(), widget.getItemId(), widget.getIndex());
	}

	public static void widgetNinthOption(Widget widget)
	{
		queueWidgetAction9Packet(widget.getId(), widget.getItemId(), widget.getIndex());
	}

	public static void widgetTenthOption(Widget widget)
	{
		queueWidgetAction10Packet(widget.getId(), widget.getItemId(), widget.getIndex());
	}

	public static void widgetItemAction(net.runelite.api.widgets.WidgetInfo container, net.runelite.api.Item item, String action)
	{
		var actions = item.getActions();
		if (actions == null)
		{
			return;
		}
		var index = actions.indexOf(action);
		widgetItemAction(container, item, index);
	}

	public static void widgetItemAction(net.runelite.api.widgets.WidgetInfo container, net.runelite.api.Item item, int index)
	{
		var widgetPackedId = container.getPackedId();
		var itemId = item.getId();
		var itemSlot = item.getSlot();
		switch (index)
		{
			case 0:
				queueWidgetAction1Packet(widgetPackedId, itemId, itemSlot);
				break;
			case 1:
				queueWidgetAction2Packet(widgetPackedId, itemId, itemSlot);
				break;
			case 2:
				queueWidgetAction3Packet(widgetPackedId, itemId, itemSlot);
				break;
			case 3:
				queueWidgetAction4Packet(widgetPackedId, itemId, itemSlot);
				break;
			case 4:
				queueWidgetAction5Packet(widgetPackedId, itemId, itemSlot);
				break;
			case 5:
				queueWidgetAction6Packet(widgetPackedId, itemId, itemSlot);
				break;
			case 6:
				queueWidgetAction7Packet(widgetPackedId, itemId, itemSlot);
				break;
			case 7:
				queueWidgetAction8Packet(widgetPackedId, itemId, itemSlot);
				break;
			case 8:
				queueWidgetAction9Packet(widgetPackedId, itemId, itemSlot);
				break;
			case 9:
				queueWidgetAction10Packet(widgetPackedId, itemId, itemSlot);
				break;
		}
	}

	public static void queueWidgetAction1Packet(int widgetId, int itemId, int childId)
	{
		createFirstAction(widgetId, itemId, childId).send();
	}

	public static void queueWidgetAction2Packet(int widgetId, int itemId, int childId)
	{
		createSecondAction(widgetId, itemId, childId).send();
	}

	public static void queueWidgetAction3Packet(int widgetId, int itemId, int childId)
	{
		createThirdAction(widgetId, itemId, childId).send();
	}

	public static void queueWidgetAction4Packet(int widgetId, int itemId, int childId)
	{
		createFourthAction(widgetId, itemId, childId).send();
	}

	public static void queueWidgetAction5Packet(int widgetId, int itemId, int childId)
	{
		createFifthAction(widgetId, itemId, childId).send();
	}

	public static void queueWidgetAction6Packet(int widgetId, int itemId, int childId)
	{
		createSixthAction(widgetId, itemId, childId).send();
	}

	public static void queueWidgetAction7Packet(int widgetId, int itemId, int childId)
	{
		createSeventhAction(widgetId, itemId, childId).send();
	}

	public static void queueWidgetAction8Packet(int widgetId, int itemId, int childId)
	{
		createEighthAction(widgetId, itemId, childId).send();
	}

	public static void queueWidgetAction9Packet(int widgetId, int itemId, int childId)
	{
		createNinthAction(widgetId, itemId, childId).send();
	}

	public static void queueWidgetAction10Packet(int widgetId, int itemId, int childId)
	{
		createTenthAction(widgetId, itemId, childId).send();
	}

	public static void queueResumePauseWidgetPacket(int widgetId, int childId)
	{
		createContinuePacket(widgetId, childId).send();
	}

	public static void queueWidgetOnWidgetPacket(int sourceWidgetId, int sourceSlot, int sourceItemId, int destinationWidgetId, int destinationSlot, int destinationItemId)
	{
		createWidgetOnWidget(sourceWidgetId, sourceSlot, sourceItemId, destinationWidgetId, destinationSlot, destinationItemId).send();
	}

	public static PacketBufferNode createType1Action(int widgetId)
	{

		return null; //packet;
	}

	public static PacketBufferNode createDefaultAction(int type, int widgetPackedId, int itemId, int itemSlot)
	{
		switch (type)
		{
			case 1:
				return createFirstAction(widgetPackedId, itemId, itemSlot);
			case 2:
				return createSecondAction(widgetPackedId, itemId, itemSlot);
			case 3:
				return createThirdAction(widgetPackedId, itemId, itemSlot);
			case 4:
				return createFourthAction(widgetPackedId, itemId, itemSlot);
			case 5:
				return createFifthAction(widgetPackedId, itemId, itemSlot);
			case 6:
				return createSixthAction(widgetPackedId, itemId, itemSlot);
			case 7:
				return createSeventhAction(widgetPackedId, itemId, itemSlot);
			case 8:
				return createEighthAction(widgetPackedId, itemId, itemSlot);
			case 9:
				return createNinthAction(widgetPackedId, itemId, itemSlot);
			case 10:
				return createTenthAction(widgetPackedId, itemId, itemSlot);
		}
		throw new IllegalArgumentException("Invalid widget action type: " + type);
	}

	public static PacketBufferNode createFirstAction(int widgetId, int itemId, int childId)
	{
		return ClientPackets.INSTANCE.createWidgetAction1Packet(widgetId, itemId, childId);
	}

	public static PacketBufferNode createSecondAction(int widgetId, int itemId, int childId)
	{
		return ClientPackets.INSTANCE.createWidgetAction2Packet(widgetId, itemId, childId);
	}

	public static PacketBufferNode createThirdAction(int widgetId, int itemId, int childId)
	{
		return ClientPackets.INSTANCE.createWidgetAction3Packet(widgetId, itemId, childId);
	}

	public static PacketBufferNode createFourthAction(int widgetId, int itemId, int childId)
	{
		return ClientPackets.INSTANCE.createWidgetAction4Packet(widgetId, itemId, childId);
	}

	public static PacketBufferNode createFifthAction(int widgetId, int itemId, int childId)
	{
		return ClientPackets.INSTANCE.createWidgetAction5Packet(widgetId, itemId, childId);
	}

	public static PacketBufferNode createSixthAction(int widgetId, int itemId, int childId)
	{
		return ClientPackets.INSTANCE.createWidgetAction6Packet(widgetId, itemId, childId);
	}

	public static PacketBufferNode createSeventhAction(int widgetId, int itemId, int childId)
	{
		return ClientPackets.INSTANCE.createWidgetAction7Packet(widgetId, itemId, childId);
	}

	public static PacketBufferNode createEighthAction(int widgetId, int itemId, int childId)
	{
		return ClientPackets.INSTANCE.createWidgetAction8Packet(widgetId, itemId, childId);
	}

	public static PacketBufferNode createNinthAction(int widgetId, int itemId, int childId)
	{
		return ClientPackets.INSTANCE.createWidgetAction9Packet(widgetId, itemId, childId);
	}

	public static PacketBufferNode createTenthAction(int widgetId, int itemId, int childId)
	{
		return ClientPackets.INSTANCE.createWidgetAction10Packet(widgetId, itemId, childId);
	}

	public static PacketBufferNode createWidgetOnWidget(int sourceWidgetId, int sourceSlot, int sourceItemId, int destinationWidgetId, int destinationSlot, int destinationItemId)
	{
		return ClientPackets.INSTANCE.createItemWidgetOnItemWidgetPacket(sourceWidgetId, sourceSlot, sourceItemId, destinationWidgetId, destinationSlot, destinationItemId);
	}

	public static PacketBufferNode createContinuePacket(int widgetId, int childId)
	{
		return ClientPackets.INSTANCE.createContinuePacket(widgetId, childId);
	}

	public static void widgetAction(Widget widget, String action)
	{
		var actions = widget.getRawActions();
		if (actions == null)
		{
			return;
		}

		var index = List.of(actions).indexOf(action);
		if (index == 0) {
			widgetFirstOption(widget);
		} else if (index == 1) {
			widgetSecondOption(widget);
		} else if (index == 2) {
			widgetThirdOption(widget);
		} else if (index == 3) {
			widgetFourthOption(widget);
		} else if (index == 4) {
			widgetFifthOption(widget);
		} else if (index == 5) {
			widgetSixthOption(widget);
		} else if (index == 6) {
			widgetSeventhOption(widget);
		} else if (index == 7) {
			widgetEighthOption(widget);
		} else if (index == 8) {
			widgetNinthOption(widget);
		} else if (index == 9) {
			widgetTenthOption(widget);
		}
	}
}