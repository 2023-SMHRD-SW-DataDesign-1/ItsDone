package model;

public class RankingDTO {

	// 랭킹
	private String id; //플레이어아이디
	private int score; //점수
	private int playtime; //플레이타임
	private int ranking; //랭킹순위

	public RankingDTO(String id, int score, int playtime, int ranking) {
		super();
		this.id = id;
		this.score = score;
		this.playtime = playtime;
		this.ranking = ranking;
		
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getPlaytime() {
		return playtime;
	}

	public void setPlaytime(int playtime) {
		this.playtime = playtime;
	}

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

}
