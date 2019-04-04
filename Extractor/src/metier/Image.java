package metier;

public class Image {
	private String reference;
	private String blob;
	
	
	
	public Image() {
		this.reference = null;
		this.blob = null;
	}
	
	public Image(String nom, String binaire) {
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
