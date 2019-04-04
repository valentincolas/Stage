package metier;

public class Substrat {
	private String reference;
	private String nom;
	private double ms;
	private double mv;
	private double ratioMsMv;
	private double phosophore;
	private double potassium;
	private double azoteK;
	private double azoteA;
	private double bmpMV;
	private double bmpMF;
	private double pourcentageMethane;
	private int detail;
	
	
	
	public Substrat(String reference, String nom, double ms, double mv, double ratioMsMv, double phosophore,
			double potassium, double azoteK, double azoteA, double bmpMV, double bmpMF, double pctageMethane) {
		super();
		this.reference = reference;
		this.nom = nom;
		this.ms = ms;
		this.mv = mv;
		this.ratioMsMv = ratioMsMv;
		this.phosophore = phosophore;
		this.potassium = potassium;
		this.azoteK = azoteK;
		this.azoteA = azoteA;
		this.bmpMV = bmpMV;
		this.bmpMF = bmpMF;
		this.setPourcentageMethane(pctageMethane);
		this.detail = -1;
	}
	
	
	public Substrat() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Substrat reference =" + reference + ", nom=" + nom + ", ms=" + ms + ", mv=" + mv + ", ratioMsMv="
				+ ratioMsMv + ", phosophore=" + phosophore + ", potassium=" + potassium + ", azoteK=" + azoteK
				+ ", azoteA=" + azoteA + ", bmpMV=" + bmpMV + ", bmpMF=" + bmpMF + ", pourcentageMethane="
				+ pourcentageMethane + ", detail=" + detail;
	}

	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public double getMs() {
		return ms;
	}
	public void setMs(double ms) {
		this.ms = ms;
	}
	public double getMv() {
		return mv;
	}
	public void setMv(double mv) {
		this.mv = mv;
	}
	public double getRatioMsMv() {
		return ratioMsMv;
	}
	public void setRatioMsMv(double ratioMsMv) {
		this.ratioMsMv = ratioMsMv;
	}
	public double getPhosophore() {
		return phosophore;
	}
	public void setPhosophore(double phosophore) {
		this.phosophore = phosophore;
	}
	public double getPotassium() {
		return potassium;
	}
	public void setPotassium(double potassium) {
		this.potassium = potassium;
	}
	public double getAzoteK() {
		return azoteK;
	}
	public void setAzoteK(double azoteK) {
		this.azoteK = azoteK;
	}
	public double getBmpMV() {
		return bmpMV;
	}
	public void setBmpMV(double bmpMV) {
		this.bmpMV = bmpMV;
	}
	public double getBmpMF() {
		return bmpMF;
	}
	public void setBmpMF(double bmpMF) {
		this.bmpMF = bmpMF;
	}
	public int getDetail() {
		return detail;
	}
	public void setDetail(int detail) {
		this.detail = detail;
	}


	public double getAzoteA() {
		return azoteA;
	}


	public void setAzoteA(double azoteA) {
		this.azoteA = azoteA;
	}


	public double getPourcentageMethane() {
		return pourcentageMethane;
	}


	public void setPourcentageMethane(double pourcentageMethane) {
		this.pourcentageMethane = pourcentageMethane;
	}
	
	
}
