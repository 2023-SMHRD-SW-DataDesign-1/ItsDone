package model;

public class CityDTO {
	
	private String name;
	private int owner = -1;
	private int price;
	private int house_price;
	private int building_price;
	private int hotel_price;
	private boolean house;
	private boolean building;
	private boolean hotel;
	
	public int getHouse_price() {
		return house_price;
	}


	public void setHouse_price(int house_price) {
		this.house_price = house_price;
	}


	public int getBuilding_price() {
		return building_price;
	}


	public void setBuilding_price(int building_price) {
		this.building_price = building_price;
	}


	public int getHotel_price() {
		return hotel_price;
	}


	public void setHotel_price(int hotel_price) {
		this.hotel_price = hotel_price;
	}
	
	
	public CityDTO(String name, int price) {
		super();
		this.name = name;
		this.price = price;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getOwner() {
		return owner;
	}


	public int setOwner(int owner) {
		this.owner = owner;
		
		return this.owner;
	}


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	public boolean isHotel() {
		return hotel;
	}


	public void setHotel(boolean hotel) {
		this.hotel = hotel;
	}


	public boolean isBuilding() {
		return building;
	}


	public void setBuilding(boolean building) {
		this.building = building;
	}


	public boolean isHouse() {
		return house;
	}


	public void setHouse(boolean house) {
		this.house = house;
	}
	

}
