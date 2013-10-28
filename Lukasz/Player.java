class Player {
	private String name;
	//private Interfejs interfejs;
	private static int id;
	private Boolean ready = false;
	
	public Player() {
		this.name = "Undefined";
		//this.ready = false;
	}
	
	public Player(String name, int id) {
		this.name = name;
		this.id = id;
		//this.ready = false;
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
}