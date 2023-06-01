package controller;

import java.util.ArrayList;

import javazoom.jl.player.MP3Player;
import model.MusicVO;

public class MusicController {
	
	ArrayList<MusicVO> musicList = new ArrayList<>();

	MP3Player mp3 = new MP3Player();

	int index = 0;
	
	public MusicController() {
		musicList.add(new MusicVO("인트로", "...", 25, "music/모두의 마블 도입.mp3"));
		musicList.add(new MusicVO("마이턴", "...", 1, "music/MyTurn_A01.mp3"));
		musicList.add(new MusicVO("주사위", "...", 1, "music/주사위 굴림 일반.mp3"));
		musicList.add(new MusicVO("도시인수", "...", 1, "music/도시가 인수되었어요.mp3"));
		musicList.add(new MusicVO("시작지점", "...", 1, "music/User_In_A01.mp3"));
		musicList.add(new MusicVO("무인도", "...", 1, "music/무인도.mp3"));
		musicList.add(new MusicVO("찬스카드", "...", 1, "music/찬스카드~.mp3"));
		musicList.add(new MusicVO("파산", "...", 1, "music/파산시.mp3"));
		musicList.add(new MusicVO("우승", "...", 1, "music/우승1.mp3"));
		
	}
	
	// 각 기능 메소드 구현
	// 1. 재생
	public void play(int index) {
		// 현재 재생중인 곡이 있는지 확인하여 중지
		if (mp3.isPlaying()) {
			stop();
		}

		// 음악 재생
		mp3.play(musicList.get(index).getMusicPath());

		// 음악 정보 출력
//		System.out.println(musicList.get(index).getSinger() + " - " + musicList.get(index).getName());
	}

	// 2. 정지
	public void stop() {
		mp3.stop();
//		System.out.println("노래를 정지 합니다.");
	}
}
