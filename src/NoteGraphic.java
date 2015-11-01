import java.awt.Color;
import java.awt.Graphics;

public class NoteGraphic {

	public int first;
	public boolean isClick = false;
	public boolean isBad = false;
	public Note second;
	public int height;
	public int width;
	private int noteHeight = 20;

	public NoteGraphic(Note second, int width) {
		// TODO Auto-generated constructor stub
		this.height = second.length * noteHeight;
		this.width = width;
		this.first = -height;
		this.second = second;
	}

	/**
	 * draw note
	 * @param g
	 * @param color
	 */
	public void draw(Graphics g, Color[] color) {
		int y = this.first;
		Note note = this.second;
		int slot = note.slot;

		if (isBad)
			g.setColor(Variable.colorNoteDisable[slot]);
		else
			g.setColor(color[slot]);
		
		g.fillRoundRect(slot * width, y, width, height, 5, 5);

		g.setColor(Color.white);
		g.drawRoundRect(slot * width, y, width, height, 5, 5);
	}

}
