package scrabble;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class PlayerButton extends JButton {

	public PlayerButton(final Player player, final Player opponent, final String label) {
		
		this.setText(label);
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				if (player.getBlock()) {
					JOptionPane.showMessageDialog(null, "Other player's turn", "Error", JOptionPane.ERROR_MESSAGE);
				} else if (label.endsWith("Start")) {
					if (!player.getMode()) {
						player.setMode(true);
						player.setBlock(false);
						
						opponent.setMode(false);
						opponent.setBlock(true);
						Main.setCurrPlayer(player);
					}
					
				} else if (label.endsWith("End")) {
				//	player.updateScore();
					boolean disconnected = false;
				//	HashSet<Integer> occ = Main.getOccupiedIndices();
					PriorityQueue<Integer> occ = Main.getOccupiedIndices();
					TreeSet<Integer> turnIndices = player.getCurrButtonIndices();
					
					if (!turnIndices.isEmpty() || !occ.isEmpty()) {
						disconnected = true;
						int row;
						int col;
						for (int index : turnIndices) {
							row = Main.row(index);
							col = Main.col(index);
							if (row > 0 && occ.contains(index - 15)) {
								disconnected = false;
								break;
							} else if (row < 14 && occ.contains(index + 15)) {
								disconnected = false;
								break;
							} else if (col > 0 && occ.contains(index - 1)) {
								disconnected = false;
								break;
							} else if (col < 14 && occ.contains(index + 1)) {
								disconnected = false;
								break;
							}
						}
					} 
					if (disconnected && !turnIndices.isEmpty() && !occ.isEmpty()) {
						String str = "Tiles added must be connected to existing tiles";
						JOptionPane.showMessageDialog(null, str, "Error", JOptionPane.ERROR_MESSAGE);
					} else {
						int rowFirstInd = Main.row(turnIndices.first());
						int colFirstInd = Main.col(turnIndices.first());
						boolean allSameRow = true;
						boolean allSameCol = true;
						
						for (int index: turnIndices) {
							if (Main.row(index) != rowFirstInd) {
								allSameRow = false;
							}
							if (Main.col(index) != colFirstInd) {
								allSameCol = false;
							}
						}
						if (!allSameRow && !allSameCol) {
							JOptionPane.showMessageDialog(null, "Tiles must be collinear", "Error", JOptionPane.ERROR_MESSAGE);
						} else {
							for (int index: turnIndices) {
								Main.setIndexAsOccupied(index);
							}

							Main.updatePlayerScore(player);
							System.out.println(player.getName() + ": " + player.getScore());
							
							for (int index: turnIndices) {
								Main.lockButton(index);
							}
							
							player.clearTurn();
							player.setMode(false);
							player.setBlock(true);
							
							opponent.setBlock(false);
							Main.setCurrPlayer(null);
					}
						
//					}
//					if (disconnected) {
//						String str = "Tiles added must be connected to existing tiles";
//						JOptionPane.showMessageDialog(null, str, "Error", JOptionPane.ERROR_MESSAGE);
//					} else if (!turnIndices.isEmpty()) {
//						int rowFirstInd = Main.row(turnIndices.first());
//						int colFirstInd = Main.col(turnIndices.first());
//						boolean allSameRow = true;
//						boolean allSameCol = true;
//						
//						for (int index: turnIndices) {
//							if (Main.row(index) != rowFirstInd) {
//								allSameRow = false;
//							}
//							if (Main.col(index) != colFirstInd) {
//								allSameCol = false;
//							}
//						}
//						if (!allSameRow && !allSameCol) {
//							JOptionPane.showMessageDialog(null, "Tiles must be collinear", "Error", JOptionPane.ERROR_MESSAGE);
//						} else {
//							for (int index: turnIndices) {
//								Main.setIndexAsOccupied(index);
//							}
//
//							Main.updatePlayerScore(player);
//							System.out.println(player.getScore());
//							
//							for (int index: turnIndices) {
//								Main.lockButton(index);
//							}
//							
//							player.clearTurn();
//							player.setMode(false);
//							player.setBlock(true);
//							
//							opponent.setBlock(false);
//							Main.setCurrPlayer(null);
//						}
					}
					
				}
			}
		});
	}

}
