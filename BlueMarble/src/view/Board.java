package view;

import java.util.ArrayList;
import java.util.Scanner;

import controller.GameController;
import model.CityDTO;
import model.DiceDTO;
import model.PlayerDTO;

public class Board {

	private static ArrayList<PlayerDTO> playerList = new ArrayList<>();
	private static ArrayList<CityDTO> cityList = new ArrayList<>();
	private static GameController gc = new GameController();

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);

		boolean isRun = true;

		while (isRun) {

			showMenu();

			switch (scan.nextInt()) {

			case 1:
				playerList.clear();
				cityList.clear();
				System.out.println("부루마블 게임을 시작합니다.");
				System.out.print("게임을 진행할 인원을 입력하세요 (2~4) >> ");
				int playerCount = scan.nextInt();

				if (playerCount > 1 && playerCount < 5) {
					for (int i = 0; i < playerCount; i++) {
						System.out.print((i + 1) + "번째 플레이어의 이름을 입력하세요 >> ");
						playerList.add(new PlayerDTO(scan.next()));
						playerList.get(i).setMoney(1500000);
						playerList.get(i).setLive(true);
					}

					int playerNum = 0;
					ArrayList<Integer> alivePlayer = new ArrayList<>();
					

					cityAdd(); // 도시 세팅

					// 게임 시작
					while (true) {
						//================================게임 종료 조건 확인============================================
						alivePlayer.clear();

						for (int i = 0; i < playerList.size(); i++) {
							if(playerList.get(i).isLive()) {
								alivePlayer.add(i);
							}
						}
						
						//System.out.println("alivePlayer.size() : " + alivePlayer.size());
						
						if(alivePlayer.size() == 1) {
							// 랭킹 등록
							System.out.println(playerList.get(alivePlayer.get(0)).getName()  + "님께서 승리하셨습니다.");
							System.out.println(" 소지금 : " + playerList.get(alivePlayer.get(0)).getMoney() + "원");
							break;
						}

						if (playerNum > playerCount - 1) {
							playerNum = 0;
						}
						
						// 플레이어가 기권했다면 다음 플레이어로 넘어감
						if(!playerList.get(playerNum).isLive()) {
							playerNum++;
							continue;
						}
						
						//================================게임 진행============================================
						System.out.println(playerList.get(playerNum).getName() + "님 차례입니다.");
						System.out.print("[1]주사위를 굴린다 [2]건물을 구입한다 [0]기권한다 >> ");
						int select = scan.nextInt();
						
						//================================주사위 굴리기============================================
						if (select == 1) {

							DiceDTO dice = gc.rollDice();

							int dice1 = dice.getDice1();
							int dice2 = dice.getDice2();
							int diceSum = dice.getDiceSum();

							int location = playerList.get(playerNum).getLocation() + diceSum;

							if (location > (cityList.size() - 1)) {
								location -= cityList.size();
								System.out.println("출발 지점을 경유하여 월급을 수령합니다.(+300,000원)");
								playerList.get(playerNum).addMoney(300000);
							}

							System.out.println("첫 번째 주사위 : " + dice1 + " 두 번째 주사위 : " + dice2);
							System.out.println(diceSum + "칸 앞으로 이동 : " + cityList.get(location).getName());

							playerList.get(playerNum).setLocation(location);
							// 통행료 정산 및 도시 구매 로직
							// 도시 소유자가 -1이 아닌경우 체크
							int cityOwner = cityList.get(location).getOwner();
							int cityPrice = cityList.get(location).getPrice();
							int playerMoney = playerList.get(playerNum).getMoney();
							String playerName = playerList.get(playerNum).getName();
							String cityName = cityList.get(location).getName();

							if (cityOwner == -1 && cityPrice < playerMoney) {

								System.out.print(cityName + "("+cityPrice+"원)도시를 구매하시겠습니까? [1]구매 [0]취소 >> ");
								if (scan.nextInt() == 1) {
									// gc.buyCity(playerList.get(playerNum), cityList.get(location));
									playerList.get(playerNum).buyCity(cityList.get(location));
									cityOwner = cityList.get(location).setOwner(playerNum); // city의 owner 변경
									playerMoney = playerList.get(playerNum).decreaseMoney(cityPrice); // player 돈 차감

									System.out.println("구매를 완료했습니다.");
									System.out.println(
											"구매자 : " + playerList.get(cityOwner).getName() + "님, 소지금 : " + playerMoney);

								} else {
									System.out.println("구매를 취소하셨습니다.");
								}

							} else {
								System.out.println("통행요금을 징수합니다 : -" + cityPrice);
								playerList.get(cityOwner).addMoney(cityPrice);
								playerMoney = playerList.get(playerNum).decreaseMoney(cityPrice);
								System.out.println("현재 소지금 : " + playerMoney);

								if (playerMoney < 0) {
									playerList.get(playerNum).setLive(false);
									System.out.println(playerName + "님께서 파산하셨습니다.");
									playerNum++;
									continue;
								}
							}
							
						//================================건물 구매============================================
						} else if (select == 2) {
							if (playerList.get(playerNum).getCityList() != null) {
								// 건물 구매
							} else {
								System.out.println("소유중인 도시가 없습니다.");
								continue;
							}
							
						//================================기권============================================
						} else if (select == 0) {
							System.out.println(playerList.get(playerNum).getName() + "님께서 기권하셨습니다.");
							playerList.get(playerNum).setLive(false);
						}
						playerNum++;
					}

				} else {
					System.out.println("올바른 숫자를 입력하세요.");
					break;
				}

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
		cityList.add(new CityDTO("출발", 0));
		cityList.add(new CityDTO("타이티", 50000));
		cityList.add(new CityDTO("마닐라", 80000));
		cityList.add(new CityDTO("싱가포르", 100000));
		cityList.add(new CityDTO("황금열쇠", 0));
		cityList.add(new CityDTO("카이로", 100000));
		cityList.add(new CityDTO("이스탄불", 120000));
		cityList.add(new CityDTO("무인도", 0));
		cityList.add(new CityDTO("아테네", 140000));
		cityList.add(new CityDTO("황금열쇠", 0));
		cityList.add(new CityDTO("독도", 160000));
		cityList.add(new CityDTO("스톡홀름", 160000));
		cityList.add(new CityDTO("아테네", 180000));
		cityList.add(new CityDTO("황금열쇠", 0));
		cityList.add(new CityDTO("몬트리올", 200000));
		cityList.add(new CityDTO("황금열쇠", 0));
		cityList.add(new CityDTO("빌리", 240000));
		cityList.add(new CityDTO("시드니", 240000));
		cityList.add(new CityDTO("하와이", 260000));
		cityList.add(new CityDTO("리스본", 260000));
		cityList.add(new CityDTO("마드리드", 260000));
		cityList.add(new CityDTO("우주여행", 0));
		cityList.add(new CityDTO("도쿄", 300000));
		cityList.add(new CityDTO("파리", 320000));
		cityList.add(new CityDTO("황금열쇠", 0));
		cityList.add(new CityDTO("푸켓", 350000));
		cityList.add(new CityDTO("뉴욕", 350000));
		cityList.add(new CityDTO("서울", 500000));
	}

}