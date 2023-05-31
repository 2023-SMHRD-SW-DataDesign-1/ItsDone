package view;

import java.util.ArrayList;
import java.util.Scanner;

import controller.GameController;
import model.CityDTO;
import model.DiceDTO;
import model.GoldkeyDTO;
import model.PlayerDTO;

public class Board {

	private static ArrayList<PlayerDTO> playerList = new ArrayList<>();
	private static ArrayList<CityDTO> cityList = new ArrayList<>();
	private static ArrayList<GoldkeyDTO> goldkeyList = new ArrayList<>();
	private static GameController gc = new GameController();
	

	private static String[] playerNameArray = new String[4];
	private static int[] playerMoneyArray = new int[4];
	private static String[] playerLocationArray = new String[4];

	public static void main(String[] args) {

		showOpenning();

//		cityList = gc.getCityList();
//		showBoard();

		Scanner scan = new Scanner(System.in);

		boolean isRun = true;

		while (isRun) {

			showMenu();

			switch (scan.nextInt()) {

			case 1:
				// 화면 표시 정보 초기화
				for (int i = 0; i < playerNameArray.length; i++) {
					playerNameArray[i] = "";
					playerMoneyArray[i] = 0;
					playerLocationArray[i] = "";
				}

				playerList.clear();
				cityList.clear();
				System.out.println("부루마블 게임을 시작합니다.");
				System.out.print("게임을 진행할 인원을 입력하세요 (2~4) >> ");
				int playerCount = scan.nextInt();

				if (playerCount > 1 && playerCount < 5) {
					for (int i = 0; i < playerCount; i++) {
						System.out.print((i + 1) + "번째 플레이어의 이름을 입력하세요 >> ");
						String name = scan.next();
						playerNameArray[i] = name;
						playerMoneyArray[i] = 1500000;
						playerLocationArray[i] = "출발";
						playerList.add(new PlayerDTO(name));
						playerList.get(i).setMoney(1500000);
						playerList.get(i).setLive(true);
					}

					int playerNum = 0;
					ArrayList<Integer> alivePlayer = new ArrayList<>();

					cityList = gc.getCityList();

					// 게임 시작
					while (true) {
						// 게임 종료 조건확인
						alivePlayer.clear();

						for (int i = 0; i < playerList.size(); i++) {
							if (playerList.get(i).isLive()) {
								alivePlayer.add(i);
							}
						}

						if (alivePlayer.size() == 1) {
							// 랭킹 등록
							String winnerName = playerList.get(alivePlayer.get(0)).getName();
							int winnerMoney = playerList.get(alivePlayer.get(0)).getMoney();

							System.out.print(winnerName + "님께서 승리하셨습니다.");
							System.out.println(" 소지금 : " + winnerMoney + "원");

							int cnt = gc.upRank(winnerName, winnerMoney);

							if (cnt > 0) {
								System.out.println("랭킹에 등록되었습니다.");
							}
							break;
						}

						if (playerNum > playerCount - 1) {
							playerNum = 0;
						}

						// 플레이어가 기권했다면 다음 플레이어로 넘어감
						if (!playerList.get(playerNum).isLive()) {
							playerNum++;
							continue;
						}

						// 무인도체크
						if (playerList.get(playerNum).getIslandCount() > 0) {
							System.out.println(playerList.get(playerNum).getName() + "님은 무인도에 갇혔습니다. 탈출까지 "
									+ playerList.get(playerNum).getIslandCount() + "턴 남았습니다.");

							// 무인도 이미지 보여주기

							playerList.get(playerNum).decreaseIslandCount(); // 무인도 count 1씩 감소
							playerNum++;
							continue;
						}

						// 게임진행
						try {
							Thread.sleep(1500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						showBoard(); // 보드판 보여주기

						System.out.println("\n" + playerList.get(playerNum).getName() + "님 차례입니다.");
						System.out.print("[1]주사위를 굴린다 [0]기권한다 >> ");
						int select = scan.nextInt();

						// 주사위 굴리기
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

							System.out.print("첫 번째 주사위 : " + dice1 + ", 두 번째 주사위 : " + dice2 + ", ");
							System.out.println(diceSum + "칸 앞으로 이동");
							playerList.get(playerNum).setLocation(location);
							// 통행료 정산 및 도시 구매 로직
							// 도시 소유자가 -1이 아닌경우 체크
							int cityOwner = cityList.get(location).getOwner();
							int cityPrice = cityList.get(location).getPrice();
							int playerMoney = playerList.get(playerNum).getMoney();
							String playerName = playerList.get(playerNum).getName();
							String cityName = cityList.get(location).getName();
							boolean cityHouse = cityList.get(location).isHouse();
							boolean cityBuilding = cityList.get(location).isBuilding();
							boolean cityHotel = cityList.get(location).isHotel();

							// 특수 지점
							if (cityName.equals("출발")) {
								System.out.println("출발지점 입니다.");
								playerLocationArray[playerNum] = "출발";
							} else if (cityName.equals("무인도")) {
								System.out.println("무인도에 갇혔습니다. 3턴간 움직일 수 없습니다.");
								playerLocationArray[playerNum] = "무인도";
								playerList.get(playerNum).setIslandCount(2);
							} else if (cityName.equals("황금열쇠")) {
								playerLocationArray[playerNum] = "황금열쇠";
								System.out.println("황금열쇠를 뽑습니다.");
								GoldkeyDTO goldkey = gc.getGoldkeyList();
								
								int keynum = goldkey.getGoldNum();
								
								System.out.println("이름 : " + goldkey.getGoldname());
								System.out.println("효과 : " + goldkey.getGoldeffect());
								
								if(keynum == 1) {
									playerMoneyArray[playerNum] = playerList.get(playerNum).decreaseMoney(100000);
								}else if(keynum ==2) {
									playerMoneyArray[playerNum] = playerList.get(playerNum).addMoney(200000);
								}else if(keynum ==3) {
									playerMoneyArray[playerNum] = playerList.get(playerNum).decreaseMoney(100000);
								}else if(keynum ==4) {
									playerMoneyArray[playerNum] = playerList.get(playerNum).addMoney(100000);
								}
			
							} 
							// 도시
							else {
								// 도시 정보 출력
								System.out.println(
										"---------------------------------------------------------------------------");
								System.out.println("도시명\t\t통행료\t\t소유자\t\t별장\t빌딩\t호텔");
								System.out.println(
										"---------------------------------------------------------------------------");
								System.out.print(cityName + "\t\t" + cityPrice + "\t\t");

								if (cityOwner != -1) {
									System.out.print(playerList.get(cityOwner).getName() + "\t\t");
								} else {
									System.out.print("X\t\t");
								}
								if (cityHouse) {
									System.out.print("O\t");
								} else {
									System.out.print("X\t");
								}
								if (cityBuilding) {
									System.out.print("O\t");
								} else {
									System.out.print("X\t");
								}
								if (cityHotel) {
									System.out.print("O\t");
								} else {
									System.out.print("X\t");
								}
								System.out.println(
										"\n---------------------------------------------------------------------------");
								playerLocationArray[playerNum] = cityName;

								if (cityOwner == -1 && cityPrice < playerMoney) {

									System.out.print(cityName + "(" + cityPrice + "원)도시를 구매하시겠습니까? [1]구매 [0]취소 >> ");
									if (scan.nextInt() == 1) {

										playerList.get(playerNum).buyCity(cityList.get(location));
										cityOwner = cityList.get(location).setOwner(playerNum); // city의 owner 변경
										playerMoney = playerList.get(playerNum).decreaseMoney(cityPrice); // player 돈 차감
										playerMoneyArray[playerNum] = playerMoney;

										System.out.println("구매를 완료했습니다.");
										System.out.println("구매자 : " + playerList.get(cityOwner).getName() + "님, 소지금 : "
												+ playerMoney);

									} else {
										System.out.println("구매를 취소하셨습니다.");
									}

								}
								// 통행료 지불
								else {
									if (playerNum != cityOwner) {

										int price;

										if (cityHotel) {
											price = cityPrice * 3;
										} else if (cityBuilding) {
											price = cityPrice * 2;
										} else if (cityHouse) {
											price = cityPrice;
										} else {
											price = cityPrice / 2;
										}

										System.out.println(playerList.get(cityOwner).getName() + "님에게 통행요금 " + price
												+ "원을 지불합니다.");
										playerList.get(cityOwner).addMoney(price);
										playerMoneyArray[cityOwner] += price;
										playerMoney = playerList.get(playerNum).decreaseMoney(price);
										playerMoneyArray[playerNum] = playerMoney;
										System.out.println("현재 소지금 : " + playerMoney);

										if (playerMoney < 0) {
											playerList.get(playerNum).setLive(false);
											playerMoneyArray[playerNum] = 0;
											playerLocationArray[playerNum] = "파산";
											System.out.println(playerName + "님께서 파산하셨습니다.");
											playerNum++;
											continue;
										}
									} else {
										System.out.println("자신이 소유한 도시에 방문하였습니다.");
										if (!cityHouse) {
											System.out.println("별장을 건설합니다.");
											cityList.get(location).setHouse(true);
										} else if (!cityBuilding) {
											System.out.println("빌딩을 건설합니다.");
											cityList.get(location).setBuilding(true);
										} else if (!cityHotel) {
											System.out.println("호텔을 건설합니다.");
											cityList.get(location).setHotel(true);
										} else {
											System.out.println("더 이상 건설할 건물이 없습니다.");
										}
									}
								}
							}

							// 기권
						} else if (select == 0) {
							System.out.println(playerList.get(playerNum).getName() + "님께서 기권하셨습니다.");
							if (playerList.get(playerNum).getCityList().size() > 0) {

								for (int i = 0; i < playerList.get(playerNum).getCityList().size(); i++) {
									
								}

							}
							playerList.get(playerNum).setLive(false);
							playerLocationArray[playerNum] = "기권";
							playerMoneyArray[playerNum] = 0;
							playerNum++;
							continue;
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

				ArrayList<PlayerDTO> rankList = gc.getRankList();

				System.out.println("랭킹\t\t이름\t\t점수");

				for (int i = 0; i < rankList.size(); i++) {
					System.out.println(
							(i + 1) + "위\t\t" + rankList.get(i).getName() + "\t\t" + rankList.get(i).getMoney());
				}

				break;

			case 0:
				showEnding();
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

	public static void showBoard() {
		System.out.printf("%-6s", "+---------");
		System.out.printf("%-6s", "---------");
		System.out.printf("%-6s", "---------");
		System.out.printf("%-6s", "---------");
		System.out.printf("%-6s", "---------");
		System.out.printf("%-6s", "---------");
		System.out.printf("%-6s", "---------");
		System.out.printf("%-6s", "---------");
		System.out.printf("%-6s%n", "----------------+");

		System.out.printf("|%8s|", cityList.get(14).getName() + "[15]");
		System.out.printf("|%8s|", cityList.get(15).getName() + "[16]");
		System.out.printf("|%8s|", cityList.get(16).getName() + "[17]");
		System.out.printf("|%8s|", cityList.get(17).getName() + "[18]");
		System.out.printf("|%8s|", cityList.get(18).getName() + "[19]");
		System.out.printf("|%8s|", cityList.get(19).getName() + "[20]");
		System.out.printf("|%8s|", cityList.get(20).getName() + "[21]");
		System.out.printf("|%8s|%n", cityList.get(21).getName() + "[22]");
		System.out.printf("|%9d|", cityList.get(14).getPrice());
		System.out.printf("|%9d|", cityList.get(15).getPrice());
		System.out.printf("|%9d|", cityList.get(16).getPrice());
		System.out.printf("|%9d|", cityList.get(17).getPrice());
		System.out.printf("|%9d|", cityList.get(18).getPrice());
		System.out.printf("|%9d|", cityList.get(19).getPrice());
		System.out.printf("|%9d|", cityList.get(20).getPrice());
		System.out.printf("|%9d|%n", cityList.get(21).getPrice());
		System.out.printf("%-6s", "|--------");
		System.out.printf("%-6s", "---------");
		System.out.printf("%-6s", "---------");
		System.out.printf("%-6s", "---------");
		System.out.printf("%-6s", "---------");
		System.out.printf("%-6s", "---------");
		System.out.printf("%-6s", "---------");
		System.out.printf("%-6s%n", "-------------------------|");
		System.out.printf("|%-8s|", cityList.get(13).getName() + "[14]");
		System.out.printf("%-67s", " ");
		System.out.printf("|%-8s|%n", cityList.get(22).getName() + "[23]");
		System.out.printf("|%-9s|", cityList.get(13).getPrice());
		System.out.printf("%-5s", " ");
		System.out.print("★플레이어★");
		System.out.printf("%-5s", " ");
		System.out.print("★보유금액★");
		System.out.printf("%-5s", " ");
		System.out.print("★위치★");
		System.out.printf("%-32s", " ");
		System.out.printf("|%-9d|%n", cityList.get(22).getPrice());
		System.out.printf("|%-9s|", "---------");
		System.out.printf("%-4s", " ");
		System.out.printf("|%-8s|", playerNameArray[0]);
		System.out.printf("%-1s", " ");
		System.out.printf("|%-8s|", playerMoneyArray[0]);
		System.out.printf("%-1s", " ");
		System.out.printf("|%-8s|", playerLocationArray[0]);
		System.out.printf("%-29s", " ");
		System.out.printf("|%-8s|%n", "---------");
		System.out.printf("|%-8s|", cityList.get(12).getName() + "[13]");
		System.out.printf("%-4s", " ");
		System.out.printf("|%-8s|", playerNameArray[1]);
		System.out.printf("%-1s", " ");
		System.out.printf("|%-8s|", playerMoneyArray[1]);
		System.out.printf("%-1s", " ");
		System.out.printf("|%-8s|", playerLocationArray[1]);
		System.out.printf("%-29s", " ");
		System.out.printf("|%-8s|%n", cityList.get(23).getName() + "[24]");
		System.out.printf("|%-9d|", cityList.get(12).getPrice());
		System.out.printf("%-4s", " ");
		System.out.printf("|%-8s|", playerNameArray[2]);
		System.out.printf("%-1s", " ");
		System.out.printf("|%-8s|", playerMoneyArray[2]);
		System.out.printf("%-1s", " ");
		System.out.printf("|%-8s|", playerLocationArray[2]);
		System.out.printf("%-29s", " ");
		System.out.printf("|%-9d|%n", cityList.get(23).getPrice());
		System.out.printf("|%-9s|", "---------");
		System.out.printf("%-4s", " ");
		System.out.printf("|%-8s|", playerNameArray[3]);
		System.out.printf("%-1s", " ");
		System.out.printf("|%-8s|", playerMoneyArray[3]);
		System.out.printf("%-1s", " ");
		System.out.printf("|%-8s|", playerLocationArray[3]);
		System.out.printf("%-29s", " ");
		System.out.printf("|%-8s|%n", "---------");
		System.out.printf("|%-8s|", cityList.get(11).getName() + "[12]");
		// System.out.printf("|%-4s|", " ");
		System.out.printf("%-67s", " ");
		System.out.printf("|%-8s|%n", cityList.get(24).getName() + "[25]");
		System.out.printf("|%-9d|", cityList.get(11).getPrice());
		System.out.printf("%-4s", " ");
		System.out.printf("%-14s", "ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
		System.out.printf("%-4s", " ");
		System.out.printf("%-14s", "ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
		System.out.printf("%-23s", " ");
		System.out.printf("|%-9s|%n", cityList.get(24).getPrice());
		System.out.printf("|%-9s|", "---------");
		System.out.printf("%-3s", " ");
		System.out.print(" |               | "); // 15
		System.out.printf("%-3s", " ");
		System.out.print(" |               | "); // 15
		System.out.printf("%-23s", " ");
		System.out.printf("|%-8s|%n", "---------");
		System.out.printf("|%-8s|", cityList.get(10).getName() + "[11]");
		System.out.printf("%-4s", " "); // 주사위1 상반
		System.out.printf("|%-4s", "    ");
		System.out.print("●");
		System.out.print("      "); // 6
		System.out.print("●");
		System.out.print("   |"); // 까지 // 4
		System.out.printf("%-5s", " "); // 주사위2 상반
		System.out.print("|    ");
		System.out.print("●");
		System.out.print("      "); // 6
		System.out.print("●");
		System.out.print("   |"); // 까지 //4
		System.out.printf("%-25s", " ");
		System.out.printf("|%-8s|%n", cityList.get(25).getName() + "[26]");
		System.out.printf("|%-9d|", cityList.get(10).getPrice());
		System.out.printf("%-3s", " ");
		System.out.print(" |               | ");
		System.out.printf("%-3s", " ");
		System.out.print(" |               | ");
		System.out.printf("%-24s", " ");
		System.out.printf("|%-8d|%n", cityList.get(25).getPrice());
		System.out.printf("|%-9s|", "---------");
		System.out.printf("%-4s", " "); // 주사위1
		System.out.printf("|%-4s", "    ");
		System.out.print("●");
		System.out.print("      "); // 6
		System.out.print("●");
		System.out.print("   |"); // 까지 // 4
		System.out.printf("%-5s", " "); // 주사위2
		System.out.print("|    ");
		System.out.print("●");
		System.out.print("      "); // 6
		System.out.print("●");
		System.out.print("   |"); // 까지 //4
		System.out.printf("%-25s", " ");
		System.out.printf("|%-7s|%n", "--------");
		System.out.printf("|%-8s|", cityList.get(9).getName() + "[10]");
		System.out.printf("%-3s", " ");
		System.out.print(" |               | "); // 15
		System.out.printf("%-3s", " ");
		System.out.print(" |               | "); // 15
		System.out.printf("%-23s", " ");
		System.out.printf("|%-8s|%n", cityList.get(26).getName() + "[27]");
		System.out.printf("|%-9s|", cityList.get(9).getPrice());
		System.out.printf("%-4s", " "); // 주사위1 하반
		System.out.print("|    ");
		System.out.print("●");
		System.out.print("      ");
		System.out.print("●");
		System.out.print("   |"); // 까지
		System.out.printf("%-5s", " "); // 주사위2 하반
		System.out.print("|    ");
		System.out.print("●");
		System.out.print("      ");
		System.out.print("●");
		System.out.print("   |"); // 까지
		System.out.printf("%-25s", " ");
		System.out.printf("|%-8d|%n", cityList.get(26).getPrice());
		System.out.printf("|%-9s|", "---------");
		System.out.printf("%-3s", " ");
		System.out.print(" |               | "); // 15
		System.out.printf("%-3s", " ");
		System.out.print(" |               | "); // 15
		System.out.printf("%-24s", " ");
		System.out.printf("|%-7s|%n", "--------");
		System.out.printf("|%-8s|", cityList.get(8).getName() + "[9]");
		System.out.printf("%-4s", " ");
		System.out.printf("%-14s", "ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
		System.out.printf("%-4s", " ");
		System.out.printf("%-14s", "ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
		System.out.printf("%-24s", " ");
		System.out.printf("|%-7s|%n", cityList.get(27).getName() + "[28]");
		System.out.printf("|%-9d|", cityList.get(8).getPrice());
		System.out.printf("%-68s", " ");
		System.out.printf("|%-8d|%n", cityList.get(27).getPrice());
		System.out.printf("%-6s", "|--------");
		System.out.printf("%-6s", "---------");
		System.out.printf("%-6s", "---------");
		System.out.printf("%-6s", "---------");
		System.out.printf("%-6s", "---------");
		System.out.printf("%-6s", "---------");
		System.out.printf("%-6s", "---------");
		System.out.printf("%-6s%n", "-------------------------|");
		System.out.printf("|%-8s|", cityList.get(7).getName() + "[8]");
		System.out.printf("|%-8s|", cityList.get(6).getName() + "[7]");
		System.out.printf("|%-8s|", cityList.get(5).getName() + "[6]");
		System.out.printf("|%-8s|", cityList.get(4).getName() + "[5]");
		System.out.printf("|%-8s|", cityList.get(3).getName() + "[4]");
		System.out.printf("|%-8s|", cityList.get(2).getName() + "[3]");
		System.out.printf("|%-8s|", cityList.get(1).getName() + "[2]");
		System.out.printf("|%-8s|%n", cityList.get(0).getName() + "[1]");
		System.out.printf("|%-9s|", cityList.get(7).getPrice());
		System.out.printf("|%-9d|", cityList.get(6).getPrice());
		System.out.printf("|%-9d|", cityList.get(5).getPrice());
		System.out.printf("|%-10s|", cityList.get(4).getPrice());
		System.out.printf("|%-9d|", cityList.get(3).getPrice());
		System.out.printf("|%-9d|", cityList.get(2).getPrice());
		System.out.printf("|%-9d|", cityList.get(1).getPrice());
		System.out.printf("|%-9s|%n", "<-");
		System.out.printf("%-6s", "+---------");
		System.out.printf("%-6s", "---------");
		System.out.printf("%-6s", "---------");
		System.out.printf("%-6s", "---------");
		System.out.printf("%-6s", "---------");
		System.out.printf("%-6s", "---------");
		System.out.printf("%-6s", "---------");
		System.out.printf("%-6s%n", "------------------------+");
	}

	public static void showOpenning() {
		System.out.println("\r\n"
				+ "                                                ...                                                 \r\n"
				+ "                                            .-::~~~~~~,.                                            \r\n"
				+ "                                          .:;::::::::::;:,.                                         \r\n"
				+ "                                       .-~::::~--~~~::::~~:~.                                       \r\n"
				+ "                                       ~:::~-,,,,,,,,-~:;;::~,                                      \r\n"
				+ "                                     .:~::-.,.,,--:~-:~-:;::::,                                     \r\n"
				+ "                                    .;;:~--...,.-~:~~-~:::!:~;;,                                    \r\n"
				+ "                                   .::~--~- ,:~-~:;;::;;;*=!;:~:-                                   \r\n"
				+ "                                   :::~~::::;!::;;!:-~,-:;:-:::::.                                  \r\n"
				+ "                                  ~:~~-~~:!*!;;;;;~,.,,;!,~~~~::~~.                                 \r\n"
				+ "                                 ,:~~-~:;**;:;!;*;::-,,~~~~-~-~::;,                                 \r\n"
				+ "                                 -:;-::;!!;:~;:;;;;:.-,-,.-,~--~::~.                                \r\n"
				+ "                                ,~:~-:;!!;~~~:~;;;~----,,~:-~-,~:::,                                \r\n"
				+ "                                -::--::~;!:::;;!:,.-,,~--::::~,~:::~                                \r\n"
				+ "                                ~::,::,;!!;!;:,~-,,~,,--~~;::;~-~:::                                \r\n"
				+ "                                :::-,-~:!*;:::.-~~::-,.,-~:::;:~~:::.                               \r\n"
				+ "                                ::~,-,,:;!;;~: ,,~ -~~---~:!;::~-~:~-                               \r\n"
				+ "                               .:::.:-::;!~-:!-,.,~-~~~~~:~;:::--::~~                               \r\n"
				+ "                               -::~-:,~~!;::;!---~--~~~~~~~,-~~--:::~                               \r\n"
				+ "                               -;:~-~~~~:;:;,,---,~~-~~::;!~~-:~-:::-                               \r\n"
				+ "                               .::~,;:.-:;-~--,,, ,~~-~::::;~~:~-::~~                               \r\n"
				+ "          .               ......:::.:~-,.:;~~-,. ..,,,-~:---:~~--::~~-     ,,,         ,,-          \r\n"
				+ "        -:~~;.         -~~~~~~~~:::~~~;:~::::~--,,.    .~~,.-~---:::~:~  ,:~~::-     .~~~::~        \r\n"
				+ "        ;~~~;:        ,::~:~~:~~~~~~~:::::::~::--,~~. .-:,.,~-,,-::::~:~ ~:~~::;     -:~~~::        \r\n"
				+ "       ~:,..~:-  .,   ;~.                  .,~::,~~~::::::::::::::, .-~;-:-..,~~~~::::~...~:-       \r\n"
				+ "      .:-   ,::-:~::,,:,      ...........   .-::~::::::::::::::::~   .~:::.  .~:;;;;;~,   -:~       \r\n"
				+ "      .:-   ,:;::~~::::.  .-:::::::::::;:,   ,::;;;:..         ,:~   ,::~~.               -:~       \r\n"
				+ "      .:-   .:;~,..-~~:,  .~::::::::::::~-   ,:::::-   .---,  .,:~.  .:::~.   ,,,,...,.   -:~       \r\n"
				+ "      .:-    :~    .,~:,   ~::::;;::;;;;;-   ,::::;,  .-:::-  .,:~.   ~:::.  .~:::::::-  .-:~       \r\n"
				+ "      .:-   ,:,      ~:.   ~:::              ,::::~.  ,::;;~   ,:~   .-~:~.   -::::::-,   ,~:       \r\n"
				+ "      .:-   ,~,      ~:,   ~::-.             ,::::-   -::;;~   ,:~    ..-:.  ..      .. ..-:: .     \r\n"
				+ "      .:-   .~,   . .~:,   ~::.    ~~~~~~~~~~~::::.  .~::;;~   ,:~      ,:,............ ..-;:~::    \r\n"
				+ "      .:-   .~~.    ,::,   ~:~.   ,::::::::::::::-  ..:::;;~   ,:~   .~~~~~~~~~~~~~~~~~~~~~~~~::~   \r\n"
				+ "      .:-   .::~,.,-~::,   ~:-    -::::::::::::::,   ~;::;;~   ,:~   .~-................... ..-~:,  \r\n"
				+ "      .:-   .::::::::::,   ~:,   .~:::::::::::::~.  .:;::;;~   ,:~   .~-                      .~;,  \r\n"
				+ "       ;-   .~~~~~~~~~-.  .~~    ,:~::::::::::::,  ..~~~~::-. .-:~   .~:::~~~~~~,.   ~~~~~~:~::~~   \r\n"
				+ "       ;~.                .~-                ,~:,    .        .,:~ ..,~:::,,,,,,.    ,,,,,~:~;;;,   \r\n"
				+ "       ;:~.             ..~:~.               ,~:-.             ,:~.  .:::~.               -~~.,.    \r\n"
				+ "    .:;::::~::::::::::::::;;:::::::::::::::::::::::::::::::::::::::::::::::~~~~::::::~-  .-::       \r\n"
				+ "   ,~::~~::~::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::-,,,,,,,---,.  .-;:       \r\n"
				+ "   :~-....,..................................-~~,..,~:-~~,~:::~,...-::-,:~.               ,~~       \r\n"
				+ "  .:,.      . .,  .....       .. ...  .  ... ,::,...~:.-: -::~ .-:-.,:, ::.  .~:::~~~~~::~~:~       \r\n"
				+ "   ;~--------,,-  ..,,,,,,,,,,-,,,,   .~-,,,,-::::~.~~.-: -::~.~;;:-.~, ::,   -::::::;:::~::~       \r\n"
				+ "   ,;:::::::;::~ . .~:::::::::::::- . ,;:~::;:::::~.~~.-: -::~  ....,:, ::~   .~::;;;:::-.,~-       \r\n"
				+ "    ,;;;;;;;;;:~.  .~;:;;;;;;;;;;:- . ,::;;;!;::::~... -: -::::-,,,-::, :::~.  .-~:~~:-  .::-       \r\n"
				+ "              ::-..-:;          ,:~, .~:: .-,-~~::-.--.-: -::~,,------. ::::~-.   ......-::~.       \r\n"
				+ "              -::~~:;-           ~~~~;;: .~;.:~::~.,~:.-: -::~ ....  .  :::::::~-,..,~~~:;-         \r\n"
				+ "               :;::;~             :::~:,.-;,,:;::-.-::,-:.-::~.~;;;;::-.~::~-;:;;:::::::;,          \r\n"
				+ "                ,~~,               .-,..~;::-~:~~.,~::,-:.-::~.-:::::~,.~::~  ,~~~~~~~-.            \r\n"
				+ "                                    .:,~!,~:.~;:,.~~:~.,~.-~~~........ .~::-                        \r\n"
				+ "                                     .-- ,-~,~;:~~::::~~:-~::::~~--~~~~~::;                         \r\n"
				+ "                                        .~.,,.~~;:::::::::::::::::::::::::,                         \r\n"
				+ "                                         ~     ,------------------------,.                          \r\n"
				+ "");
	}

	public static void showEnding() {
		System.out.println("\r\n" + "\r\n"
				+ "                                                      .                                  \r\n"
				+ "                                    .                      .      .                      \r\n"
				+ "                                                ,..                                      \r\n"
				+ "                                              ,~;:~:-                                    \r\n"
				+ "                                            .::;;;;;:~              .-,                  \r\n"
				+ "                                           .:;;:-,.:::-            ~;:;-                 \r\n"
				+ "                                           ~:;:     ~:-      .,.  :;:;;:                 \r\n"
				+ "                 ,,-,,,                    ::~      -~:   . .-;~ ~;:--;~                 \r\n"
				+ "               .;:;;;!:,                   :;. ,-   ~~: -    ~;,,:;- ::,                 \r\n"
				+ "              ,;;;::::;:-            ,,.   ,.  :;   ~;,~;,  .;:-:;:~-:-                  \r\n"
				+ "        .    ~::;:.   ~;~.         .;~:*.      ;:  ,;:-~:.  ~!:-~;:;:-                   \r\n"
				+ "            -:!:,      ::,  .     ,~;!;;,     ~;~ ,;~--!:  ,:;~,:;;~:  ,~                \r\n"
				+ "           .:;;.  -   .:;,    ,~  :;:,-;-    .::.-:;, ~:: .:;:~~:::.  -::                \r\n"
				+ "           -;;-  :;   .:~,..  ::.-;!- :~,    ,:: -;:, :!- ~:;;.,;;,  -:;~                \r\n"
				+ "           ;;:. ,~:   ,:~~;- .:~-:;:.~:: .   ~!- .~;: :;,-;:!; .;::.-:;-                 \r\n"
				+ "          .;;~  -;~   ::~~;. -:;:~;~,;;.     :;,  .;~.:;;:~:;:  :;;;;:,                  \r\n"
				+ "          ,;;,  ~;~  :;:.;:  ~::-:;;;:.  .~ ,:~.  ~:- ~!;:,~!,   ~-:-,                   \r\n"
				+ "           !:  ,;;,.~:;-:;; -:~:~;:;:.  .:~..;!  .~;, .:~  :;.                           \r\n"
				+ "           .   :;;.:;:-.:;-,~!:~~;:~.  ,;:- ,;;  ::~      ~;:.                           \r\n"
				+ "              .:;~ ~;;--;: :;;;.~;:   ,-:~  .;:.~;:      .:;~                            \r\n"
				+ "       .     .,:;,  ~;~,;:~:;;~ ~:; .,;;~.   ::;;:. ~~-,~:!:                             \r\n"
				+ "              -!;   ::-.;;;;:;- ~:;;~*;~     .:;,   :;:::;:,                             \r\n"
				+ "              ~!:  ,;~  -~--:;.  ~;;;:-              ~;!;~,                              \r\n"
				+ "              ~;~ .;~,   . :::    -~-.                ..                                 \r\n"
				+ "              ~!~-:;-     ~;:-.                                                          \r\n"
				+ "              ,:!;:- .,,,-::~                                                            \r\n"
				+ "               ~:~.  ,~;*:;:                                                             \r\n"
				+ "                     .::::~.                                                             \r\n"
				+ "                               .                              .                          \r\n"
				+ "                  .                                                                      \r\n"
				+ "                                                                                         \r\n"

				+ " ");
	}

	private static int getKorCnt(String kor) {
		int cnt = 0;
		for (int i = 0; i < kor.length(); i++) {
			if (kor.charAt(i) >= '가' && kor.charAt(i) <= '힣') {
				cnt++;
			}
		}
		return cnt;
	}

	public static String convert(String word, int size) {
		String formatter = String.format("%%%ds", size - getKorCnt(word));
		return String.format(formatter, word);
	}
}