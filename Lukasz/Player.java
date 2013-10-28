class Player {
	private String name;
	private static int counter = 0;
	private final int id = counter++;
	private boolean ready = false;
	private int points = 0;
	private boolean ifAnswer = false;
	
	public Player() {
		this.name = "Undefined";
	}

	public Player(String name) {
		this.name = name;
	}
	
	public void setCounter(int counter) {
		Player.counter = counter;
	}
	
	public boolean answered() {
		return ifAnswer;
	}
	
	public void answere(boolean a) {
		this.ifAnswer = a;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setReady() {
		this.ready = true;
	}
	
	public Boolean isReady() {
		return ready;
	}
	
	public int getPoints() {
		return points;
	}
	
	public void increasePoints() {
		this.points++;
	}
}