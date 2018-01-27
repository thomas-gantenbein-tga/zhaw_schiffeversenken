package ch.zhaw.schiffeversenken.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import ch.zhaw.schiffeversenken.model.Game;


/**
 * Loads and saves the state of the game.
 * 
 *
 */
public class LoaderSaver {
	private FileNameExtensionFilter filter;

	public LoaderSaver() {
		filter = new FileNameExtensionFilter("Battleship files (*.bsv)", "bsv");
	}

	protected Game load() {
		File file;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(filter);

		int returnValue = fileChooser.showOpenDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			return loadGame(file);
		}
		return null;
	}

	private Game loadGame(File file) {
		try (FileInputStream fs = new FileInputStream(file); ObjectInputStream os = new ObjectInputStream(fs);) {
			return (Game) os.readObject();
		} catch (ClassCastException | StreamCorruptedException ex) {
			JOptionPane.showMessageDialog(null, "No valid Battleship Save-File selected", "Wrong file format",
					JOptionPane.ERROR_MESSAGE);
		} catch (ClassNotFoundException ex) {
			JOptionPane.showMessageDialog(null,
					"Contact the developer of this game. Saving will never work with this application.",
					"ClassNotFoundException", JOptionPane.ERROR_MESSAGE);
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "Could not read from disk. Try another file.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}

	protected void save(Game game) {
		File file;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(filter);
		int returnValue = fileChooser.showSaveDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			writeToFile(file, game);
		}
	}

	private void writeToFile(File file, Game game) {
		String fileName = file.getName();
		File fileToWrite;
		if (fileName.contains(".bsv")) {
			fileToWrite = file;
		} else {
			String path = file.getPath();
			path = path + ".bsv";
			fileToWrite = new File(path);
		}
		try (FileOutputStream fs = new FileOutputStream(fileToWrite);
				ObjectOutputStream os = new ObjectOutputStream(fs);) {
			os.writeObject(game);
			JOptionPane.showMessageDialog(null,
					"Successfully saved.", "Saved", JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Could not write on the disk." + " Please try a different target location.",
					"Write failure", JOptionPane.ERROR_MESSAGE);
			;
		}
	}
}
