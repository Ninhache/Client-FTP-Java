package annotationExemple;

public enum Actions { 
	GET(1),
	QUIT(2),
	LIST(3),
	NOPE(4);
	
	int index;
	Actions(int i) {
		this.index = i;
	}

}
