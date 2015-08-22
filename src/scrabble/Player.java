package scrabble;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.TreeSet;

import scrabble.TileBag;
import scrabble.TileBag.Tile;

public class Player {
	private boolean mode;
	private boolean blocked;
	private String name;
	private int score;	
	
	private ArrayList<Tile> tileList;
	private ArrayList<Tile> tilesUsedInTurn;
	private TreeSet<Integer> currButtonIndices;

	
	public Player(String playerName) {
		name = playerName;
		mode = false;
		blocked = false;
		tileList = new ArrayList<Tile>();
		tilesUsedInTurn = new ArrayList<Tile>();
		score = 0;
		currButtonIndices = new TreeSet<Integer>();
	}

	TreeSet<Integer> getCurrButtonIndices() {
		return currButtonIndices;
	}
	
//	void clearTurnTiles() {
//		tilesUsedInTurn.clear();
//	}
	
	void clearTurn() {
		tilesUsedInTurn.clear();
		currButtonIndices.clear();
	}
	
	void addIndexToTurn(int index) {
		currButtonIndices.add(index);
	}
	
	void removeIndexFromTurn(int index) {
		currButtonIndices.remove(index);
	}
	
	void addToTurn(BoardButton button, String letter) {
		for (Tile tile: tileList) {
			if (tile.toString().equals(letter)) {
				useTileInTurn(button, tile);
				break;
			}
		}
	}
	
	void useTileInTurn(BoardButton button, Tile tile) {
		tileList.remove(tile);
		tilesUsedInTurn.add(tile);
		button.setTileOnButton(tile);
	}
	
	void removeFromTurn(BoardButton button, String letter) {
		for (Tile tile: tilesUsedInTurn) {
			if (tile.toString().equals(letter)) {
				retractTileInTurn(button, tile);
				break;
			}
		}
	}

	void retractTileInTurn(BoardButton button, Tile tile) {
		tilesUsedInTurn.remove(tile);
		tileList.add(tile);
		button.setTileOnButton(null);
	}
	
	
//	void addToTurn(String letter) {
//		for (Tile tile: tileList) {
//			if (tile.toString().equals(letter)) {
//				useTileInTurn(tile);
//				break;
//			}
//		}
//	}
	
//	void useTileInTurn(Tile tile) {
//		tileList.remove(tile);
//		tilesUsedInTurn.add(tile);
//	}
	
//	void removeFromTurn(String letter) {
//		for (Tile tile: tilesUsedInTurn) {
//			if (tile.toString().equals(letter)) {
//				retractTileInTurn(tile);
//				break;
//			}
//		}
//	}

//	void retractTileInTurn(Tile tile) {
//		tilesUsedInTurn.remove(tile);
//		tileList.add(tile);
//	}
	
	String getName() {
		return name;
	}
	
	void setName(String str) {
		name = str;
	}
	
	boolean getMode() {
		return mode;
	}
	
	void setMode(boolean bool) {
	//	if (bool) {
	//		blocked = false;
	//	}
		mode = bool;
	}
	
	boolean getBlock() {
		return blocked;
	}
	
	void setBlock(boolean bool) {
	//	if (bool) {
	//		mode = false;
	//	}
		blocked = bool;
	}
	
	int getScore() {
		return score;
	}
	
	void setScore(int i) {
		score = i;
	}
	
//	void updateScore() {
////	boolean doubleWordScore = false;
////	boolean tripleWordScore = false;
//		for (Tile tile: tilesUsedInTurn) {
//			score += tile.getVal();
//		}
//		for (int index: currButtonIndices) {
		//	score += Main.
//		}
//	}
//

	void setTileOnButton(BoardButton button, Tile tile) {
		button.setTileOnButton(tile);
	}
	
	ArrayList<Tile> getList() {
		return tileList;
	}
	
	ArrayList<Tile> getTilesLeft() {
		return tileList;
	}
	
//	TileBag bag;
//	ArrayList<Tile> tileList;
//	int score;
//	String name;
	
//	public Player() {
	//	this.bag = bag;
	//	fillPlayerTiles();
//		tileList = new ArrayList<Tile>();
//		score = 0;
//		name = null;
//	}

	
	void fillPlayerTiles(TileBag bag) {
		while (tileList.size() < 7 && !bag.emptyStack()) {
			Tile tile = bag.removeTile();
			tileList.add(tile);
		}
		if (bag.emptyStack()) {
			System.out.println("Empty bag");
		}
	}
	
//	void addTileToBoard(char letter, Board board, int row, int col, TileBag bag) {
//		for (Tile tile: tileList) {
//			if (new Character(tile.getLetter()).compareTo(letter) == 0) {
//				board.addTile(tile, row, col);
//				int index = 15 * row + col;
//				HashMap<Integer, String> bonuses = new HashMap<Integer, String>();
//				if (bonuses.containsKey(index)) {
//					if (bonuses.get(index).equals("2L")) {
//						score += 2 * tile.getVal();
//					} else if (bonuses.get(index).equals("3L")) {
//						score += 3 * tile.getVal();
//					}
//				} else {
//					score += tile.getVal();
//				}
//				tileList.remove(tile);
//				fillPlayerTiles(bag);
//				break;
//			}
//		}
//	}

//	boolean inList(char letter) {
//		for (Tile tile: tileList) {
//			if (new Character(tile.getLetter()).compareTo(letter) == 0) {
//				return true;
//			}
//		}
//		return false;
//	}
	
	public boolean inList(String letter) {
		return tileList.toString().contains(letter.toUpperCase());
	}
}