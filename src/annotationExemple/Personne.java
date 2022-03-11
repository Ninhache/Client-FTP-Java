package annotationExemple;

@UserActions(Actions.GET)
public class Personne {
	
	String name;
	
	public Personne(String name) {
		this.name = name;
	}

	@UserActions(Actions.LIST)
	public void listFiles() {
		System.out.println("List files");
	}
	
	@UserActions(Actions.QUIT)
	public void quitStream() {
		System.out.println("Client leaving");
	}
	
	@UserActions(Actions.GET)
	public void getFile(String name) {
		System.out.println("User looking for " + name);
	}
	
}
