package controller;

import java.util.Random;

import model.CityDTO;
import model.DiceDTO;
import model.PlayerDTO;

public class GameController {
	
	
	public void buyCity(PlayerDTO player, CityDTO city) {
		
		player.buyCity(city);
		
	}
	
	public void sellCity(PlayerDTO player, CityDTO city) {
		
		player.sellCity(city);
		
	}

	public DiceDTO rollDice() {
		
		Random random = new Random();
		
		int dice1 = random.nextInt(5) + 1;
		int dice2 = random.nextInt(5) + 1;
		int diceSum = dice1 + dice2;
		
		return new DiceDTO(dice1, dice2, diceSum);
	}

}
