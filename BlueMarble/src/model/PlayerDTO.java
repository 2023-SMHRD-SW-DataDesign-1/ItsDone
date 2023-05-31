package model;

import java.util.ArrayList;

public class PlayerDTO {

	private String name;
	private int money;
	private int location;
	private int islandCount;
	private boolean isLive;
	private ArrayList<CityDTO> cityList = new ArrayList<>();; // 소유하고 있는 도시

	public PlayerDTO(String name) {
		this.name = name;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}
	
	public int addMoney(int money) {
		this.money += money;
		
		return this.money;
	}
	
	public int decreaseMoney(int money) {
		this.money -= money;
		
		return this.money;
	}

	public ArrayList<CityDTO> getCityList() {
		return cityList;
	}

	public void setCityList(ArrayList<CityDTO> cityList) {
		this.cityList = cityList;
	}
	
	public boolean isLive() {
		return isLive;
	}

	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}
	
	public void buyCity(CityDTO city) {
		this.cityList.add(city);
	}
	
	public void sellCity(CityDTO city) {
		this.cityList.remove(city);
	}
	
	public int getIslandCount() {
		return islandCount;
	}

	public void setIslandCount(int islandCount) {
		this.islandCount = islandCount;
	}
	
	public void decreaseIslandCount() {
		this.islandCount --;
	}

}
