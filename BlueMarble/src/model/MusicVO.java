package model;

public class MusicVO {

	// Music 플레이리스트 설계도 구성
	// VO(Value Object)

	// 필드(제목, 가수, 음악길이, 파일경로)
	private String name;
	private String singer;
	private int playTime;
	private String musicPath;

	// 생성자 만들기
	// 생성자 : 객체를 생성하는 순간 자동으로 호출되는 메소드
	// 주요역할 : 객체를 생성함과 동시에 필드에 값 적용
	// 생성자의 특징
	// 1. 리턴타입이 없다. (void 키워드 사용x)
	// 2. 생성자 메소드의 이름은 클래스의 이름과 동일하다.
	// 3. new 키워드를 통해서만 호출되는 메소드
	public MusicVO(String name, String singer, int playTime, String musicPath) {
		this.name = name;
		this.singer = singer;
		this.playTime = playTime;
		this.musicPath = musicPath;
	}

	// getter -> 필드에 저장된 값을 불러올 때
	// setter -> 필드에 값을 저장할 때
	// 단축키 Alt + Shift + S
	public String getName() {
		return name;
	}

	public String getSinger() {
		return singer;
	}

	public int getPlayTime() {
		return playTime;
	}

	public String getMusicPath() {
		return musicPath;
	}

}
