package model;

public class CityDTO {

	//도시
	private int atoll; //통행료
	private int price; //건물가격
	
	private int summerhouse; //별장
	private int building; //빌딩
	private int hotel; //호텔
	private int landmark; //랜드마크
	private int tourism; //관광지
	
	public CityDTO(int atoll, int price, int summerhouse, int building, int hotel, int landmark, int tourism) {
		super();
		this.atoll = atoll;
		this.price = price;
		this.summerhouse = summerhouse;
		this.building = building;
		this.hotel = hotel;
		this.landmark = landmark;
		this.tourism = tourism;
	}
	
	
	
	
	
}
