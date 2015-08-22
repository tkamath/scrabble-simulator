package scrabble;

import scrabble.TileBag.Tile;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Main {
	
	private static Player currPlayer;
	private static TileBag bag = new TileBag();
	private static ArrayList<BoardButton> buttonList;
	private static PriorityQueue<Integer> occupied;
	
	public static Player getCurrPlayer() {
		return currPlayer;
	}
	
	static void setCurrPlayer(Player player) {
		currPlayer = player;
		if (currPlayer != null) {
			currPlayer.fillPlayerTiles(bag);
			System.out.println(currPlayer.getList());
		}
	}
	
//	Board board;
	ArrayList<Player> list;
	int numPlayers;
	int currentPlayer;

	Player next() {
		currentPlayer = (currentPlayer + 1) % numPlayers;
		return list.get(currentPlayer);
	}
	
	public static void main (String[] args) {
		JFrame frame = new JFrame("Scrabble");
		currPlayer = null;
		buttonList = new ArrayList<BoardButton>();
		occupied = new PriorityQueue<Integer>();

		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(720, 720);
		frame.setLayout(new GridLayout(17, 15));
		
		int currIndex;
		int currRow;
		int currCol;
		BoardButton button;
		
		for (int i = 0; i < 225; i += 1) {
			
			currIndex = i;
			currRow = row(i);
			currCol = col(i);
			button = new BoardButton(currIndex);
			
			
			if (currIndex % 7 == 0 && currRow % 7 == 0) {
				if (currRow == 7 && currCol == 7) {
					button.setDoubleWord(true);
				} else {
					button.setTripleWord(true);
				}
			} else if (currRow % 14 == 0 && currCol % 8 == 3) {
				button.setDoubleLetter(true);
			} else if (currCol % 14 == 0 && currRow % 8 == 3) {
				button.setDoubleLetter(true);
			} else if (currRow == currCol || 14 - currCol == currRow) {
				if (currRow < 5 || currRow > 9) {
					button.setDoubleWord(true);
				} else if (currRow == 5 || currRow == 9) {
					button.setTripleLetter(true);
				} else {
					button.setDoubleLetter(true);
				}
			} else if (currRow % 12 == 1 && (currCol == 5 || currCol == 9)) {
				button.setTripleLetter(true);
			} else if (currCol % 12 == 1 && (currRow == 5 || currRow == 9)) {
				button.setTripleLetter(true);
			} else if (Math.abs(7 - currCol) == 1 && currRow % 10 == 2) {
				button.setDoubleLetter(true);
			} else if (Math.abs(7 - currRow) == 1 && currCol % 10 == 2) {
				button.setDoubleLetter(true);
			} else if (currCol == 7 && currRow % 8 == 3) {
				button.setDoubleLetter(true);
			} else if (currRow == 7 && currCol % 8 == 3) {
				button.setDoubleLetter(true);
			 } else {
				button.setBackground(new Color(244, 225, 160));
			 }
			buttonList.add(button);
			frame.add(button);
		}
		Player P1 = new Player("P1");
		Player P2 = new Player("P2");
		
		for (int j = 0; j < 30; j += 1) {
			Font smallFont = new Font(Font.SANS_SERIF, Font.BOLD, 10);
			JButton extra;
			if (j == 0) {
				extra = new PlayerButton(P1, P2, "P1 Start");
			} else if (j == 14) {
				extra = new PlayerButton(P1, P2, "P1 End");
			} else if (j == 15) {
				extra = new PlayerButton(P2, P1, "P2 Start");
			} else if (j == 29) {
				extra = new PlayerButton(P2, P1, "P2 End");
			} else if (j == 7) {
				extra = new JButton("P1");
			} else if (j == 22) {
				extra = new JButton("P2");
			} else {
				extra = new JButton();
			}
			extra.setFont(smallFont);
			extra.setBorder(null);
			frame.add(extra);
		}
		frame.setVisible(true);
	//	ScrabbleGame game = new ScrabbleGame(P1, P2);
	}
	
	static void lockButton(int index) {
		BoardButton button = buttonList.get(index);
		button.disableTextField();
		if (button.isSpecial()) {
			button.makeOrdinary();
		}
	}
	
	static void updatePlayerScore(Player player) {		
		TreeSet<Integer> indices = player.getCurrButtonIndices();
		int wordScore = 0;
		int firstInd = indices.first();
		int lastInd = indices.last();
//		boolean horiz = row(lastInd) == row(firstInd);
		boolean vert = col(lastInd) == col(firstInd);

		if (vert) {
			if (occupied.contains(firstInd - 15) || !occupied.contains(lastInd + 15)) {
				wordScore = goUpFromInd(lastInd, indices);
			} else if (occupied.contains(firstInd + 15) || !occupied.contains(lastInd - 15)) {
				wordScore = goDownFromInd(firstInd, indices);
			}
		} else {
			if (col(lastInd) == 14 || !occupied.contains(lastInd + 1) || 
					(col(firstInd) != 0 && occupied.contains(firstInd - 1))) {
				wordScore = goLeftFromInd(lastInd, indices);

			} else if (col(firstInd) == 0 || !occupied.contains(firstInd - 1) || 
					(col(lastInd) != 14 && occupied.contains(lastInd + 1))) {
				wordScore = goRightFromInd(firstInd, indices);
			}
		}
		player.setScore(player.getScore() + wordScore);
	}

	void setTileOnButton(int index, Tile tile) {
		buttonList.get(index).setTileOnButton(tile);
	}
	
	static void setIndexAsOccupied(int index) {
		occupied.add(index);
	}

	static PriorityQueue<Integer> getOccupiedIndices() {
		return occupied;
	}
 
	static int goUpFromInd(int currInd, TreeSet<Integer> turnIndices) {
//		System.out.println("going up now");
		int wordScore = 0;
		boolean hasDoubleWord = false;
		boolean hasTripleWord = false;
		int lowest = currInd;
		
		while (occupied.contains(lowest + 15)) {
			lowest += 15;
		}
		
		for (int prev = lowest; occupied.contains(prev); prev -= 15) {	
			if (turnIndices.contains(prev)) {
				int leftmostInd = prev;
				while (row(leftmostInd - 1) == row(leftmostInd) && occupied.contains(leftmostInd - 1)) {
					leftmostInd -= 1;
				}
				for (int k = leftmostInd; row(k) == row(leftmostInd) && occupied.contains(k); k += 1) {
					wordScore += tempScore(k);
				}
			} else {
				wordScore += tempScore(prev);
			}
//			
			BoardButton prevButton = buttonList.get(prev);
			hasDoubleWord = checkDoubleWord(prevButton, hasDoubleWord);
			hasTripleWord = checkTripleWord(prevButton, hasTripleWord);
		}

		wordScore = applySpecialWeightHelper(wordScore, hasDoubleWord, hasTripleWord);
		return wordScore;
	}
	
	static int goDownFromInd(int currInd, TreeSet<Integer> turnIndices) {
//		System.out.println("going down now");
		int wordScore = 0;
		boolean hasDoubleWord = false;
		boolean hasTripleWord = false;	
		int highest = currInd;

		while (occupied.contains(highest - 15)) {
			highest -= 15;
		}

		for (int next = highest; occupied.contains(next); next += 15) {
			if (turnIndices.contains(next)) {
				int leftmostInd = next;
				while (row(leftmostInd - 1) == row(leftmostInd) && occupied.contains(leftmostInd - 1)) {
					leftmostInd -= 1;
				}
				for (int k = leftmostInd; row(k) == row(leftmostInd) && occupied.contains(k); k += 1) {
					wordScore += tempScore(k);
				}
			} else {
				wordScore += tempScore(next);
			}
//			
			BoardButton prevButton = buttonList.get(next);
			hasDoubleWord = checkDoubleWord(prevButton, hasDoubleWord);
			hasTripleWord = checkTripleWord(prevButton, hasTripleWord);
		}
		
		wordScore = applySpecialWeightHelper(wordScore, hasDoubleWord, hasTripleWord);
		return wordScore;
	}
	
	
	static int goLeftFromInd(int currInd, TreeSet<Integer> turnIndices) {
		System.out.println("going left now");
		int currRow = currInd / 15;
		int wordScore = 0;
		boolean hasDoubleWord = false;
		boolean hasTripleWord = false;
		
		int rightmost = currInd;
		while (occupied.contains(rightmost + 1) && row(rightmost + 1) == row(currInd)) {
			rightmost += 1;
		}
		
		for (int prev = rightmost; row(prev) == currRow && occupied.contains(prev); prev -= 1) { 
			if (turnIndices.contains(prev)) {
				int highestInd = prev;
				while (occupied.contains(highestInd + 15)) {
					highestInd += 15;
				}
				for (int k = highestInd; occupied.contains(k); k += 15) {
					wordScore += tempScore(k);
				}
			} else {
				wordScore += tempScore(prev);
			}
			BoardButton prevButton = buttonList.get(prev);
			hasDoubleWord = checkDoubleWord(prevButton, hasDoubleWord);
			hasTripleWord = checkTripleWord(prevButton, hasTripleWord);
		}
		wordScore = applySpecialWeightHelper(wordScore, hasDoubleWord, hasTripleWord);
		return wordScore;
	}
	
	static int goRightFromInd(int currInd, TreeSet<Integer> turnIndices) {
		System.out.println("going right now");
		int wordScore = 0;
		boolean hasDoubleWord = false;
		boolean hasTripleWord = false;
		int currRow = currInd / 15;
		
		int leftmost = currInd;
		while (occupied.contains(leftmost - 1) && row(leftmost - 1) == row(currInd)) {
			leftmost -= 1;
		}

		for (int next = leftmost; row(next) == currRow && occupied.contains(next); next += 1) {
			
			if (turnIndices.contains(next)) {
				int highestInd = next;
				while (occupied.contains(highestInd + 15)) {
					highestInd += 15;
				}
				for (int k = highestInd; occupied.contains(k); k += 15) {
					wordScore += tempScore(k);
				}
			} else {
				wordScore += tempScore(next);
			}
			BoardButton prevButton = buttonList.get(next);
			hasDoubleWord = checkDoubleWord(prevButton, hasDoubleWord);
			hasTripleWord = checkTripleWord(prevButton, hasTripleWord);
		}
		
		wordScore = applySpecialWeightHelper(wordScore, hasDoubleWord, hasTripleWord);
		return wordScore;
	}
	
	private static boolean checkDoubleWord(BoardButton button, boolean hasDoubleWord) {
		if (hasDoubleWord || button.isDoubleWord()) {
			return true;
		}
		return false;
	}
	
	private static boolean checkTripleWord(BoardButton button, boolean hasTripleWord) {
		if (hasTripleWord || button.isTripleWord()) {
			return true;
		}
		return false;
	}
	
	private static int tempScore(int index) {
		return tempScore(buttonList.get(index));
	}
	
	private static int tempScore(BoardButton button) {
		int temp = button.getButtonTile().getVal();
		temp = applySpecialWeightHelper(temp, button.isDoubleLetter(), button.isTripleLetter());
		System.out.println(button.getButtonTile() + " " + temp);
		return temp;
	}

	static int applySpecialWeightHelper(int score, boolean isDoubleVal, boolean isTripleVal) {
		if (isDoubleVal) {
			score *= 2;
		}
		if (isTripleVal) {
			score *= 3;
		}
		return score;
	}
	
	static int row(int index) {
		return index / 15;
	}
	
	static int col(int index) {
		return index % 15;
	}
}
