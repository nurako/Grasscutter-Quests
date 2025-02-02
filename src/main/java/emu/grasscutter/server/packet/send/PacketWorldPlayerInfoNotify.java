package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.world.World;
import emu.grasscutter.net.packet.BaseTypedPacket;
import lombok.val;
import messages.scene.WorldPlayerInfoNotify;
import messages.scene.entity.OnlinePlayerInfo;

import java.util.ArrayList;

public class PacketWorldPlayerInfoNotify extends BaseTypedPacket<WorldPlayerInfoNotify> {

	public PacketWorldPlayerInfoNotify(World world) {
		super(new WorldPlayerInfoNotify());

        val playerInfoList = new ArrayList<OnlinePlayerInfo>();
        val playerUidList = new ArrayList<Integer>();
        world.getPlayers().forEach(p -> {
            playerInfoList.add(p.getOnlinePlayerInfo());
            playerUidList.add(p.getUid());
        });
	}
}
