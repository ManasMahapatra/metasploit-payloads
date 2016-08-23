package com.metasploit.meterpreter;

import java.io.InputStream;
import java.io.OutputStream;

//import com.sun.media.sound.WaveFileWriter;
//
//import javax.sound.sampled.*;
//import java.io.*;
//
//import javax.sound.sampled.AudioFormat;
//import javax.sound.sampled.AudioSystem;
//import javax.sound.sampled.Line;
//import javax.sound.sampled.Mixer;
//
//import com.metasploit.meterpreter.TLVPacket;

public class AudioSocketChannel extends Channel {

	public AudioSocketChannel(Meterpreter meterpreter, InputStream in, OutputStream out) {
		super(meterpreter, in, out);
		// TODO Auto-generated constructor stub
	}

//    private TargetDataLine line;
//    private final AudioFormat af;
//
//    /**
//     * Create a new audio channel.
//     *
//     * @param meterpreter The meterpreter this channel should be assigned to.
//     */
//    public AudioSocketChannel(Meterpreter meterpreter, InputStream input, OutputStream output) throws IOException, LineUnavailableException {
//        super(meterpreter, new ByteArrayInputStream(new byte[0]), new ByteArrayOutputStream());
//
//        //configure line
//        line = null;
//        af = new AudioFormat(11000, 8, 1, true, false);
//
//        Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
//        for (int i = 0; i < mixerInfo.length; i++) {
//            Mixer mixer = AudioSystem.getMixer(mixerInfo[i]);
//            Line.Info[] targetLineInfo = mixer.getTargetLineInfo();
//            if (targetLineInfo.length > 0) {
//                line = (TargetDataLine) mixer.getLine(targetLineInfo[0]);
//                break;
//            }
//        }
//        if (line == null)
//            throw new UnsupportedOperationException("No recording device found");
//
//        line.open(af);
//        line.start();
//
//        new AudioBroadcastThread(line).start();
//    }
//
//    public void close() throws IOException {
//        super.close();
//        line.stop();
//        line.close();
//    }
//
//    public synchronized void handleBroadcast(byte[] data, int offset, int len) throws IOException {
//        TLVPacket tlv = new TLVPacket();
//        tlv.add(TLVType.TLV_TYPE_CHANNEL_ID, getID());
//        AudioInputStream ais = new AudioInputStream(new ByteArrayInputStream(data), af, data.length);
//        ByteArrayOutputStream wbaos = new ByteArrayOutputStream();
//        new WaveFileWriter().write(ais, AudioFileFormat.Type.WAVE, wbaos);
//        byte[] wbaosBytes = wbaos.toByteArray();
//        tlv.add(TLVType.TLV_TYPE_CHANNEL_DATA, wbaosBytes);
//        tlv.add(TLVType.TLV_TYPE_LENGTH, wbaosBytes.length);
//        //tlv.add(TLV_TYPE_AUDIO_DATA, wbaosBytes);
//        write(wbaosBytes, len, tlv);
//
//    }
//
//    /**
//     * A thread that polls the channel to provide information when interacting with this channel.
//     */
//    protected class AudioBroadcastThread extends Thread{
//        private final TargetDataLine line;
//
//        public AudioBroadcastThread(TargetDataLine line) {
//            this.line = line;
//        }
//
//        public void run() {
//            try {
//                byte[] buffer = new byte[(int) af.getSampleRate() * af.getFrameSize()];
//                long end = System.currentTimeMillis() + 1000 * af.getFrameSize();
//                int len;
//                while (System.currentTimeMillis() < end && ((len = line.read(buffer, 0, buffer.length)) != -1)) {
//                    handleBroadcast(buffer, 0, len);
//                }
//            } catch (Throwable t) {
//                t.printStackTrace(meterpreter.getErrorStream());
//            }
//        }
//    }

}
