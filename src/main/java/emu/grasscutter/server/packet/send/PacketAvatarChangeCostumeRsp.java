package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BaseTypedPacket;
import emu.grasscutter.net.proto.RetcodeOuterClass;
import messages.team.avatar.AvatarChangeCostumeRsp;

public class PacketAvatarChangeCostumeRsp extends BaseTypedPacket<AvatarChangeCostumeRsp> {

	public PacketAvatarChangeCostumeRsp(long avatarGuid, int costumeId) {
		super(new AvatarChangeCostumeRsp(costumeId, avatarGuid));
	}

	public PacketAvatarChangeCostumeRsp() {
		super(new AvatarChangeCostumeRsp());
        proto.setRetcode(RetcodeOuterClass.Retcode.RET_SVR_ERROR_VALUE);

	}
}
