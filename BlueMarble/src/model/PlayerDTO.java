package model;

public class PlayerDTO {

	//플레이어
	private String id; //플레이어아이디
	private int amount; //재산
	private String palyercolor; //플레이어색상
	private String chancewhether; //찬스유무
	private String documents; //땅문서
	
	
	public PlayerDTO(String id, int amount, String palyercolor, String chancewhether, String documents) {
		super();
		this.id = id;
		this.amount = amount;
		this.palyercolor = palyercolor;
		this.chancewhether = chancewhether;
		this.documents = documents;
	}
}
