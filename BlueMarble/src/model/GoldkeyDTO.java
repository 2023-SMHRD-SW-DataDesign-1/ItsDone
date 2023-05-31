package model;

public class GoldkeyDTO {

	// 황금열쇠
	private int goldNum;
	private String goldname; //찬스이름
	private String goldeffect; //찬스효과
	
	
	public GoldkeyDTO(String goldname, String goldeffect) {
		super();
		this.goldname = goldname;
		this.goldeffect = goldeffect;
	}


	public String getGoldname() {
		return goldname;
	}


	public void setGoldname(String goldname) {
		this.goldname = goldname;
	}


	public String getGoldeffect() {
		return goldeffect;
	}


	public void setGoldeffect(String goldeffect) {
		this.goldeffect = goldeffect;
	}

}
