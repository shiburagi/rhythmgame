public class ThreadGroup {

	private MainPanel mp;
	private Thread noteThread;
	private Thread gameThread;

	private int fps = 25;
	private long spf = 1000 / fps;

	private boolean hasNext = true;

	public ThreadGroup(MainPanel mp) {
		this.mp = mp;

		noteThread();
		gameThread();
	}

	/**
	 * thread for add new note in note list
	 */
	private void noteThread() {
		noteThread = new Thread() {

			public void run() {

				try {
					for (int i = 0; i < Map.notes.length; i++) {
						hasNext = true;
						int timer = (int) (Map.notes[i].delay * 1000);
						if (timer > 0)
							sleep(timer);
						mp.addNote(Map.notes[i]);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				hasNext = false;
			}

		};

		noteThread.start();
	}

	/**
	 * Thread for update note position
	 */
	private void gameThread() {
		gameThread = new Thread() {

			public void run() {
				try {
					while (!mp.isFinish() || hasNext) {

						sleep(spf);
						mp.updateNotes();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				mp.finish();
			}

		};

		gameThread.start();
	}

}
