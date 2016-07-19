package com.metasploit.meterpreter.stdapi;

import java.awt.List;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;

import com.metasploit.meterpreter.Meterpreter;
import com.metasploit.meterpreter.TLVPacket;
import com.metasploit.meterpreter.TLVType;
import com.metasploit.meterpreter.command.Command;

public class stdapi_sys_audio_get_interfaces implements Command {
    public int execute(Meterpreter meterpreter, TLVPacket request, TLVPacket response) throws Exception {
    	Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
    	Line.Info[][] lineInfos = new Line.Info[mixerInfo.length][];
        for (int i = 0; i < mixerInfo.length; i++) {
            Mixer mixer = AudioSystem.getMixer(mixerInfo[i]);
            lineInfos[i] = mixer.getTargetLineInfo();
        }
        response.add(TLVType.TLV_TYPE_AUDIO_INTERFACE_NAME, lineInfos);
        return ERROR_SUCCESS;
    }
}
