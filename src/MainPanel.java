import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class MainPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 446253257020640150L;

	private Color[] colorNote;

	private final MainFrame mf;

	private boolean startgame = false;

	private ArrayList<NoteGraphic> noteList = new ArrayList<>();

	private Integer noteSpeed = 5;

	private int yBound = 450;

	private String accurateText = "";

	private String comboText = "";

	private int combo;

	private Color textColor = Color.white;

	private int perfectRange = 15;

	private int goodRange = 30;

	private int noteWidth = 50;

	private int rangeClick = 100;

	public MainPanel(MainFrame mf) {
		this.mf = mf;
		setLayout(null);

		// Copy default color to note color.
		colorNote = new Color[Variable.colorNoteDefault.length];
		for (int i = 0; i < colorNote.length; i++) {
			colorNote[i] = Variable.colorNoteDefault[i];
		}

		setBackground(Color.black);
	}

	public void paint(Graphics g) {
		super.paint(g);
		int adjX = 0, adjY = 450;

		int heightKey = 100;

		Font keyNoteFont = new Font("Arial", Font.BOLD, 28);
		FontMetrics fontMetrics = g.getFontMetrics();

		// check if game is started or not
		// if game is start, draw note
		// otherwise, display text to start a game.
		if (startgame) {

			// draw all note
			for (NoteGraphic pair : noteList) {
				pair.draw(g, colorNote);
			}

		} else {

			g.setColor(Color.white);
			g.setFont(keyNoteFont);
			g.drawString("Press F2", 30, 200);
			g.drawString("to start", 30, 230);
			g.drawString("a game...", 30, 260);

		}

		// draw key note.
		for (int i = 0; i < 4; i++) {

			g.setColor(Color.white);
			g.fillRect(adjX + i * noteWidth, adjY, noteWidth, heightKey);

			g.setColor(colorNote[i]);
			g.fillRect(adjX + i * noteWidth + 2, adjY + 2, noteWidth - 4,
					heightKey - 4);

			g.setColor(Color.white);

			g.setFont(keyNoteFont);

			String keyNote = Character.toString((char) Variable.keyNoteCode[i]);

			g.drawString(keyNote,
					adjX + i * noteWidth + fontMetrics.stringWidth(keyNote),
					adjY + heightKey / 2);

		}

		// for accuracy text
		g.setColor(textColor);
		g.drawString(
				accurateText,
				(mf.getWidth() / 2 - fontMetrics.stringWidth(accurateText)) - 15,
				230);

		// for combo text
		g.setColor(Color.white);
		g.drawString(comboText,
				(mf.getWidth() / 2 - fontMetrics.stringWidth(comboText)) - 15,
				260);

	}

	/**
	 * keynote press action
	 * 
	 * @param j
	 */
	public void keyPress(int j) {
		colorNote[j] = Variable.colorNotePress[j];
		for (NoteGraphic note : noteList) {
			if (note.second.slot == j) {
				if (!note.isClick) {
					int y = note.first + note.height;
					int diff = Math.abs(y - yBound);
					note.isClick = true;
					if (diff < perfectRange) { // perfect
						comboText = String.valueOf(++combo);
						accurateText = "Perfect";
						textColor = Color.cyan;
					} else if (diff < goodRange) { // good
						comboText = String.valueOf(++combo);
						accurateText = "Good";
						textColor = Color.green;
					} else if (diff < rangeClick) { //bad
						combo = 0;
						comboText = "";
						accurateText = "Bad";
						textColor = Color.red;

						note.isBad = true;

					} else {
						note.isClick = false;
					}

				}
				break;
			}
		}
		repaint();
	}

	/**
	 * keynote release action
	 * 
	 * @param j
	 */
	public void keyRelease(int j) {
		colorNote[j] = Variable.colorNoteDefault[j];
		for (NoteGraphic note : noteList) {
			if (note.second.slot == j) {
				if (note.second.length > 1) {
					int y = note.first;
					int diff = Math.abs(y - yBound);
					if (diff < perfectRange) { // perfect
						comboText = String.valueOf(++combo);
						accurateText = "Perfect";
						textColor = Color.cyan;
					} else if (diff < goodRange) { // good
						comboText = String.valueOf(++combo);
						accurateText = "Good";
						textColor = Color.green;
					} else if (diff < rangeClick) { // bad
						combo = 0;
						comboText = "";
						accurateText = "Bad";
						textColor = Color.red;

						note.isBad = true;
					}
				}
				break;
			}
		}
		repaint();
	}

	/**
	 * Start a game
	 */
	public void startGame() {
		if (!startgame) {
			startgame = true;
			noteList.clear();

			combo = 0;
			comboText = "";
			accurateText = "";
			new ThreadGroup(this);
			repaint();
		}
	}

	/**
	 * add new note into notelist. for display in game canvas.
	 * 
	 * @param note
	 */
	public void addNote(Note note) {
		// TODO Auto-generated method stub

		noteList.add(new NoteGraphic(note, noteWidth));
	}

	/**
	 * update note new y location. remove if the note out of bounds.
	 */
	public void updateNotes() {
		// TODO Auto-generated method stub
		try {
			int i = 0;
			for (NoteGraphic note : noteList) {
				note.first += noteSpeed;
				if (note.first - note.height > yBound) {
					if (!note.isClick) { // miss
						combo = 0;
						comboText = "";
						accurateText = "Miss";
						textColor = Color.white;
					}
					noteList.remove(i);

				}

				i++;

			}
			if (!noteList.isEmpty()) {

			}

			repaint();
		} catch (Exception e) {

		}

	}

	/**
	 * get game status
	 * 
	 * @return
	 */
	public boolean isFinish() {
		// TODO Auto-generated method stub

		return noteList.isEmpty();
	}

	/**
	 * excute game finish action
	 */
	public void finish() {
		startgame = false;
		comboText = "";
		accurateText = "";

		repaint();
	}
}
