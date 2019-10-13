package cn.milai.ib.client.game;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class AudioPlayer {

	private byte[] bytes;

	public AudioPlayer(InputStream in) {
		try {
			bytes = new byte[in.available()];
			in.read(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public class AudioController {
		private boolean closed = false;

		public void close() {
			closed = true;
		}
	}

	public AudioController play() {
		AudioController controller = new AudioController();
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Player player = new Player(new ByteArrayInputStream(bytes));
					while (!controller.closed && !player.isComplete())
						player.play(16);
				} catch (JavaLayerException e) {
					e.printStackTrace();
				}
			}
		});
		thread.setDaemon(true);
		thread.start();
		return controller;
	}

}
