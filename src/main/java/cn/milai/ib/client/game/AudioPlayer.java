package cn.milai.ib.client.game;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class AudioPlayer {

	private boolean closed = false;
	
	private byte[] bytes;
	
	public AudioPlayer(InputStream in) {
		try {
			bytes = new byte[in.available()];
			in.read(bytes);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Player player = new Player(new ByteArrayInputStream(bytes));
					while(!closed && !player.isComplete())
						player.play(1);
					player.close();
				} catch (JavaLayerException e) {
					e.printStackTrace();
				}
			}
		});
		thread.setDaemon(true);
		thread.start();
	}
	
	public void close() {
		closed = true;
	}
	
}
