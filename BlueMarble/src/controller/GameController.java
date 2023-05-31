package controller;

import java.util.ArrayList;
import java.util.Random;

import model.CityDTO;
import model.DAO;
import model.DiceDTO;
import model.GoldkeyDTO;
import model.PlayerDTO;

public class GameController {
	
	DAO dao = new DAO();
	
	public GoldkeyDTO getGoldkeyList(){
		
		Random random = new Random();
		
		ArrayList<GoldkeyDTO> goldkeyList = dao.selectGoldkey();
		
		int index = random.nextInt(3);
		
		return goldkeyList.get(index);
	}
	
	public int upRank(String name, int money) {
		int cnt = dao.insertRanking(name, money);
		
		return cnt;
	}
	
	public ArrayList<CityDTO> getCityList() {
		
		ArrayList<CityDTO> cityList = dao.selectCity();
		
		return cityList;
	}
	
	public ArrayList<PlayerDTO> getRankList(){
		
		ArrayList<PlayerDTO> rankList = dao.selectRanking();
		
		return rankList;
	}
	
	
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
