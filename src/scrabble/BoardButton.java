package scrabble;

import scrabble.TileBag.Tile;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class BoardButton extends JButton {

	private static Color tW = new Color (233, 73, 50);
	private static Color dW = new Color (200, 145, 255);
	private static Color tL = new Color (100, 100, 255);
	private static Color dL = new Color (147, 220, 255);
	
	private int index;
	private int row;
	private int col;
	private JTextField textField;
	private Tile buttonTile;
	private boolean doubleLetter;
	private boolean tripleLetter;
	private boolean doubleWord;
	private boolean tripleWord;
	
	public BoardButton(int ind) {
		index = ind;
		row = index / 15;
		col = index % 15;
		buttonTile = null;
		doubleLetter = false;
		tripleLetter = false;
		doubleWord = false;
		tripleWord = false;
		
		textField = new JTextField();
		textField.setVisible(false);
		addButtonKeyListener(this, textField);
		addFieldKeyListener(this, textField);
		add(textField);
	}
	
	
	private void addButtonKeyListener(final BoardButton boardButton, final JTextField textField) {
		boardButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
			//	System.out.println(textField.isEditable());
				if (Main.getCurrPlayer() != null) {
					if (textField.isEditable()) {
						textField.setVisible(true);
					}
				} else {
					String err = "Player must start turn";
					JOptionPane.showMessageDialog(null, err, "Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		});
	}
	
	private void addFieldKeyListener(final BoardButton boardButton, final JTextField textField) {
		textField.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				String oldText = boardButton.getText();
				if (arg0.getKeyChar() == KeyEvent.VK_ENTER) {
					
					String trimmed = textField.getText().trim().toUpperCase();
					if (trimmed.length() > 1) {
						String str = "Input must not exceed one character";
						JOptionPane.showMessageDialog(null, str, "Error", JOptionPane.ERROR_MESSAGE);
					} else if (!trimmed.isEmpty() && !trimmed.equals("-") && !Character.isAlphabetic(trimmed.charAt(0))) {
						JOptionPane.showMessageDialog(null, "Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
					} else if (!Main.getCurrPlayer().inList(trimmed)) {
						JOptionPane.showMessageDialog(null, "Tile not contained in list", "Error", JOptionPane.ERROR_MESSAGE);
					} else {
						if (trimmed.isEmpty()) {
							if (!oldText.isEmpty()) {
								Main.getCurrPlayer().removeFromTurn(boardButton, oldText);
								Main.getCurrPlayer().removeIndexFromTurn(boardButton.getIndex());
								
							}
						} else {
							if (oldText.isEmpty()) {
								Main.getCurrPlayer().addIndexToTurn(boardButton.getIndex());
							} else if (!trimmed.equals(oldText)) {
								Main.getCurrPlayer().removeFromTurn(boardButton, oldText);
							}
							Main.getCurrPlayer().addToTurn(boardButton, trimmed);
						}
						boardButton.setText(trimmed);
						textField.setVisible(false);
					}

				}
			}
			
		});
	}
	
	int getIndex() {
		return index;
	}
	
	int getRow() {
		return row;
	}
	
	int getCol() {
		return col;
	}

	Tile getButtonTile() {
		return buttonTile;
	}

	void setTileOnButton(Tile buttonTile) {
		this.buttonTile = buttonTile;
	}

	void disableTextField() {
		textField.setEditable(false);
	}
	
	void setDoubleWord(boolean dw) {
		doubleWord = dw;
		if (doubleWord) {
			setBackground(dW);
		}
	}
	
	void setTripleWord(boolean tw) {
		tripleWord = tw;
		if (tripleWord) {
			setBackground(tW);
		}
	}
	
	void setDoubleLetter(boolean dl) {
		doubleLetter = dl;
		if (doubleLetter) {
			setBackground(dL);
		}
	}
	
	void setTripleLetter(boolean tl) {
		tripleLetter = tl;
		if (tripleLetter) {
			setBackground(tL);
		}
	}
	
	void setSpecialLetterVals(boolean dl, boolean tl) {
		doubleLetter = dl;
		tripleLetter = tl;
		if (doubleLetter) {
			setBackground(dL);
		} else if (tripleLetter) {
			setBackground(tL);
		}
	}
	
	void setSpecialWordVals(boolean dw, boolean tw) {
		doubleWord = dw;
		tripleWord = tw;
		if (doubleWord) {
			setBackground(dW);
		} else if (tripleWord) {
			setBackground(tW);
		}
	}
	
	boolean isDoubleLetter() {
		return doubleLetter;
	}
	
	boolean isTripleLetter() {
		return tripleLetter;
	}
	
	boolean isDoubleWord() {
		return doubleWord;
	}
	
	boolean isTripleWord() {
		return tripleWord;
	}

	boolean isSpecial() {
		return doubleLetter || tripleLetter || doubleWord || tripleWord;
	}
	
	void makeOrdinary() {
		doubleLetter = false;
		tripleLetter = false;
		doubleWord = false;
		tripleWord = false;
	}
}