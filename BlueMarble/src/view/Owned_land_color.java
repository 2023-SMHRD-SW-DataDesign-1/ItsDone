package view;

public class Owned_land_color {
	//1P 빨강 2P 파랑 3P 초록 4P 노랑	
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
	
	public static void main(String[] args) {
		//땅 구매했을때는 글자 색만
		System.out.println(ANSI_RED + "1P 땅");
		System.out.println(ANSI_BLUE + "2p 땅" + ANSI_RESET);
		System.out.println(ANSI_GREEN + "3P 땅" + ANSI_RESET);
		System.out.println(ANSI_YELLOW + "4P 땅" + ANSI_RESET);
		//랜드마크 건설시 백그라운드
		System.out.println(ANSI_RED_BACKGROUND + "1P 땅" + ANSI_RESET);
		System.out.println(ANSI_BLUE_BACKGROUND + "2p 땅" + ANSI_RESET);
		System.out.println(ANSI_GREEN_BACKGROUND + "3P 땅" + ANSI_RESET);
		System.out.println(ANSI_YELLOW_BACKGROUND + "4P 땅" + ANSI_RESET);
		
		System.out.println("\u001b[31m test");
		System.out.println(ANSI_GREEN_BACKGROUND + "몬트리올[15]" + ANSI_RESET);
		System.out.println(ANSI_RED + "몬트리올[15]" + ANSI_RESET);
		System.out.println(ANSI_PURPLE + "몬트리올[15]" + ANSI_RESET);
		System.out.println(ANSI_GREEN + "몬트리올[15]" + ANSI_RESET);
		System.out.println(ANSI_RED + "200000" + ANSI_RESET);
		System.out.println(ANSI_BLUE + "2p 플레이어" + ANSI_RESET);
		System.out.println(ANSI_YELLOW + "3p 플레이어" + ANSI_RESET);
		System.out.println(ANSI_CYAN + "4p 플레이어" + ANSI_RESET);
		System.out.println(ANSI_BLACK + "5p 플레이어" + ANSI_RESET);
		System.out.println(ANSI_GREEN_BACKGROUND + ANSI_RED + "7p 플레이어" + ANSI_RESET);
		System.out.println(yellow("8p 플레이어"));
	}

	public static String yellow(String content) {
		return ANSI_YELLOW + content + ANSI_RESET;
	}

}
