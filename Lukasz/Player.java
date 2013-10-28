class Player {
	private String name;
	private static int counter = 0;
	private final int id = counter++;
	private Boolean ready = false;
	private int points = 0;
	
	public Player() {
		this.name = "Undefined";
		this.ready = false;
	}
	
	public Player(String name) {
		this.name = name;
		this.ready = false;
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
	
	public void increasePoints(int p) {
		this.points += p;
	}
}