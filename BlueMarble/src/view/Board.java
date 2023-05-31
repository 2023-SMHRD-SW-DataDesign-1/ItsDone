package view;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import model.CityDTO;
import model.PlayerDTO;

public class Board {

	static ArrayList<PlayerDTO> playerList = new ArrayList<>();
	static ArrayList<CityDTO> cityList = new ArrayList<>();

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);

		boolean isRun = true;

		while (isRun) {

			showMenu();

			switch (scan.nextInt()) {

			case 1:
				System.out.println("부루마블 게임을 시작합니다.");
				System.out.print("게임을 진행할 인원을 입력하세요 (2~4) >> ");
				int playerCount = scan.nextInt();
				Random random = new Random();

				if (playerCount > 1 && playerCount < 5) {
					for (int i = 0; i < playerCount; i++) {
						System.out.print((i + 1) + "번째 플레이어의 이름을 입력하세요 >> ");
						playerList.add(new PlayerDTO(scan.next()));
						playerList.get(i).setMoney(500000);
					}
					
					int playerNum = 0;
					
					cityAdd();	// 도시 세팅
					
					// 게임 시작
					while (true) {
						// 게임 종료 조건 처리
						if (playerList.size() <= 1) {
							System.out.println(playerList.get(0).getName() + "님께서 승리하셨습니다.");
							break;
						}

						if (playerNum > playerCount - 1) {
							playerNum = 0;
						}

						System.out.println(playerList.get(playerNum).getName() + "님 차례입니다.");
						System.out.print("[0]주사위를 굴린다 [1]건물을 구입한다 [2]기권한다 >> ");
						int select = scan.nextInt();
						if (select == 0) {
							int dice1 = random.nextInt(5) + 1;
							int dice2 = random.nextInt(5) + 1;
							int diceSum = dice1 + dice2;
							int location = playerList.get(playerNum).getLocation() + diceSum;

							if (location > (cityList.size() - 1)) {
								location -= cityList.size();
								System.out.println("출발 지점을 경유하여 월급을 수령합니다.(+200,000원)");
								playerList.get(playerNum).setMoney(playerList.get(playerNum).getMoney() + 200000);
							}

							System.out.println("첫 번째 주사위 : " + dice1 + " 두 번째 주사위 : " + dice2);
							System.out.println(diceSum + "칸 앞으로 이동 : " + cityList.get(location).getName());
							playerList.get(playerNum).setLocation(location);
							// 통행료 정산 및 도시 구매 로직
							// 도시 소유자가 -1이 아닌경우 체크

						} else if (select == 1) {
							if (playerList.get(playerNum).getCityList() != null) {
								// 건물 구매
							} else {
								System.out.println("소유중인 도시가 없습니다.");
								continue;
							}

						} else if (select == 2) {
							System.out.println(playerList.get(playerNum).getName() + "님께서 기권하셨습니다.");
							playerList.get(playerNum).setLive(false);
						}
						playerNum++;
					}

				} else {
					System.out.println("올바른 숫자를 입력하세요.");
					break;
				}

				// 랭킹 등록
				System.out.print("우승자 : " + playerList.get(0).getName() + "님");
				System.out.println(" 소지금 : " + playerList.get(0).getMoney() + "원");

				break;

			case 2:
				// DB에 접속하여 랭킹 테이블에서 데이터 가져오기
				System.out.println("랭킹을 확인합니다.");
				break;

			case 0:
				System.out.println("게임을 종료합니다.");
				isRun = false;
				scan.close();
				break;

			default:
				System.out.println("올바른 숫자를 입력하세요.");
				break;

			}
		}

	}

	public static void showMenu() {
		System.out.println("=============부루마블 게임=============");
		System.out.print("[1]게임시작 [2]랭킹확인 [0]종료 >> ");
	}

	public static void cityAdd() {
		cityList.add(new CityDTO("출발", 000));
		cityList.add(new CityDTO("타이베이", 000));
		cityList.add(new CityDTO("황금열쇠", 000));
		cityList.add(new CityDTO("베이징", 000));
		cityList.add(new CityDTO("마닐라", 000));
		cityList.add(new CityDTO("제주도", 000));
		cityList.add(new CityDTO("싱가포르", 000));
		cityList.add(new CityDTO("황금열쇠", 000));
		cityList.add(new CityDTO("카이로", 000));
		cityList.add(new CityDTO("이스탄불", 000));
		cityList.add(new CityDTO("무인도", 000));
		cityList.add(new CityDTO("아테네", 000));
		cityList.add(new CityDTO("황금열쇠", 000));
		cityList.add(new CityDTO("코펜하겐", 000));
		cityList.add(new CityDTO("스톡홀름", 000));
		cityList.add(new CityDTO("콩코드여객기", 000));
		cityList.add(new CityDTO("베른", 000));
		cityList.add(new CityDTO("황금열쇠", 000));
		cityList.add(new CityDTO("베를린", 000));
		cityList.add(new CityDTO("오타와", 000));
		cityList.add(new CityDTO("사회복지기금수령처", 000));
		cityList.add(new CityDTO("부에노스아이레스", 000));
		cityList.add(new CityDTO("황금열쇠", 000));
		cityList.add(new CityDTO("상파울로", 000));
		cityList.add(new CityDTO("시드니", 000));
		cityList.add(new CityDTO("부산", 000));
		cityList.add(new CityDTO("하와이", 000));
		cityList.add(new CityDTO("리스본", 000));
		cityList.add(new CityDTO("황금열쇠", 000));
		cityList.add(new CityDTO("마드리드", 000));
		cityList.add(new CityDTO("우주여행", 000));
		cityList.add(new CityDTO("도쿄", 000));
		cityList.add(new CityDTO("컬럼비아호", 000));
		cityList.add(new CityDTO("파리", 000));
		cityList.add(new CityDTO("로마", 000));
		cityList.add(new CityDTO("황금열쇠", 000));
		cityList.add(new CityDTO("런던", 000));
		cityList.add(new CityDTO("뉴욕", 000));
		cityList.add(new CityDTO("사회복지기금(접수처)", 000));
		cityList.add(new CityDTO("서울", 000));
	}

}