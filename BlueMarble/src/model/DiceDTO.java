package model;

public class DiceDTO {
	
	private int dice1;
	private int dice2;
	private int diceSum;
	
	public DiceDTO(int dice1, int dice2, int diceSum) {
		this.dice1 = dice1;
		this.dice2 = dice2;
		this.diceSum = diceSum;
	}

	public int getDice1() {
		return dice1;
	}

	public int getDice2() {
		return dice2;
	}

	public int getDiceSum() {
		return diceSum;
	}
}
