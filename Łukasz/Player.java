class Player {
	private String name;
	//private Interfejs interfejs;
	private static int id = 0;
	
	public Player() {
		this.name = "Undefined";
	}
	
	public Player(String name) {
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
}