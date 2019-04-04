package metier;

public class Courbe {
	private String reference;
	private String blob;
	
	
	
	public Courbe() {
		this.reference = null;
		this.blob = null;
	}
	
	public Courbe(String nom, String binaire) {
		this.reference = nom;
		this.blob = binaire;
	}
	
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getBlob() {
		return blob;
	}
	public void setBlob(String blob) {
		this.blob = blob;
	}
	
	@Override
	public String toString() {
		return "Courbes [reference=" + reference + ", blob=" + blob + "]";
	}
	
	
}
