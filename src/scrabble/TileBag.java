package scrabble;

import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

public class TileBag {
//	private List<Tile> list = new ArrayList<Tile>();
	private Stack<Tile> tileStack = new Stack<Tile>();
//	private HashMap<Character, Integer> distribution = 
//			new HashMap<Character, Integer>();
	
	private final HashMap<Character, Integer> letterVals =
			new HashMap<Character, Integer>();
	
	private final HashMap<Character, Integer> origDistr =
			new HashMap<Character, Integer>();
	
	TileBag() {
		
		setLetterVal(0, '-');
		setOrigDistr(2, '-');

		setLetterVal(1, 'E', 'A', 'I', 'O', 'N', 'R', 'T', 'L', 'S', 'U');
		setOrigDistr(12, 'E');
		setOrigDistr(9, 'A', 'I');
		setOrigDistr(8, 'O');
		setOrigDistr(6, 'N', 'R', 'T');
		setOrigDistr(4, 'L', 'S', 'U');

		setLetterVal(2, 'D', 'G');
		setOrigDistr(4, 'D');
		setOrigDistr(3, 'G');

		setLetterVal(3, 'B', 'C', 'M', 'P');
		setOrigDistr(2, 'B', 'C', 'M', 'P');

		setLetterVal(4, 'F', 'H', 'V', 'W', 'Y');
		setOrigDistr(2, 'F', 'H', 'V', 'W', 'Y');

		setLetterVal(5, 'K');
		setOrigDistr(1, 'K');

		setLetterVal(8, 'J', 'X');
		setOrigDistr(1, 'J', 'X');

		setLetterVal(10, 'Q', 'Z');
		setOrigDistr(1, 'Q', 'Z');

	}
	
	private void setLetterVal(int val, char... letters) {
		for (char letter: letters) {
			letterVals.put(letter, val);
		}
	}
	
	private void setOrigDistr(int distr, char... letters) {
		for (char letter: letters) {
			origDistr.put(letter, distr);
			for (int i = 0; i < distr; i += 1) {
				tileStack.add(new Tile(letter));
			}
		}
	}
	
//	void addToList(char letter, int val, int num) {
////		int curr = 0;
////		if (distribution.containsKey(letter)) {
////			curr = distribution.get(letter); 
////		}
////		for (int j = 0; j < num; j += 1) {
////			curr += 1;
////			list.add(new Tile(letter, val, num));
////			distribution.put(letter, curr);
////		}
//		for (int j = 0; j < num; j += 1) {
//			tileStack.add(new Tile(letter, val, num));
//		}
//		distribution.put(letter, num);
//	}
	
	public Tile removeTile() {
		if (tileStack.isEmpty()) {
			System.out.println("Empty bag");
			return null;
		} else {
			Collections.shuffle(tileStack);
			return tileStack.pop();
		}

//		try {
//		//	char letter = tileStack.peek().getLetter();
//		//	distribution.put(letter, distribution.get(letter) - 1);
//			Collections.shuffle(tileStack);
//		//	Tile tile = tileStack.pop();
//		//	char letter = tile.getLetter();
//		//	distribution.put(letter, distribution.get(letter) - 1);
//		//	return tile;
//			return tileStack.pop();
//		} catch (EmptyStackException e) {
//			System.out.println(e.getMessage());
//			return null;
//		}
	}
	
//	 int getStackSize() {
//		 return tileStack.size();
//	 }
	
	boolean emptyStack() {
		return tileStack.empty();
	}
	

	public class Tile {
		private char letter;
		private int val;
		private int num;
		
		
		Tile(char l) {
			letter = l;
			val = letterVals.get(l);
			num = origDistr.get(l);
		}

		char getLetter() {
			return letter;
		}
		
		int getNum() {
			return num;
		}
		
		int getVal() {
			return val;
		}
		
		@Override
		public String toString() {
			return String.valueOf(letter);
		}
	}
}
