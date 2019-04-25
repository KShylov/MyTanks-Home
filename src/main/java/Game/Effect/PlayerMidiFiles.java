package Game.Effect;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import java.io.File;
import java.io.IOException;

public class PlayerMidiFiles extends SoundEffect implements Runnable {

    private String nameTreck ;

    public String getNameTreck() {
        return nameTreck;
    }

    public void setNameTreck(String nameTreck) {

        this.nameTreck = nameTreck;
    }

    public PlayerMidiFiles(String nameTreck) {

        this.nameTreck = nameTreck;
    }

    public void play(){
            if(!nameTreck.endsWith(".mid")) {
                helpAndExit();
            }
            File midiFile = new File(nameTreck);
            if(!midiFile.exists() || midiFile.isDirectory() || !midiFile.canRead()) {
                helpAndExit();
            }
            // Play once
            try {
                Sequencer sequencer = MidiSystem.getSequencer();
                sequencer.setSequence(MidiSystem.getSequence(midiFile));
                sequencer.open();
                sequencer.start();
                while(true) {
                    if(sequencer.isRunning()) {
                        /*try {
                            Thread.sleep(1000); // Check every second
                        } catch(InterruptedException ignore) {
                            break;
                        }*/
                    } else {
                        break;
                    }
                }
                // Close the MidiDevice & free resources
                sequencer.stop();
                sequencer.close();
            } catch(MidiUnavailableException mue) {
                System.out.println("Midi device unavailable!");
            } catch(InvalidMidiDataException imde) {
                System.out.println("Invalid Midi data!");
            } catch(IOException ioe) {
                System.out.println("I/O Error!");
            }
        }

        /** Provides help message and exits the program */
        private static void helpAndExit() {
            System.out.println("Usage: java MidiPlayer midifile.mid");
            System.exit(1);
        }

    @Override
    public void run() {
        play();
        System.out.println("stop run");
    }
}

