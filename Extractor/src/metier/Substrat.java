package metier;


/**
 * <b>Cette classe permet de stocker les informations relatives � un substrat dans un objet Substrat<b>
 * @author COLAS Valentin
 * @version 1.0
 */
public class Substrat {
	/**
	 * La r�f�rence du substrat
	 */
	private String reference;
	/**
	 * Le nom du substrat
	 */
	private String nom;
	/**
	 * Le pourcentage de mati�re s�che
	 */
	private double ms;
	/**
	 * Le pourcentage de mati�re volatile
	 */
	private double mv;
	/**
	 * Le ratio mati�re s�che / mati�re volatile
	 */
	private double ratioMsMv;
	/**
	 * La contenance en phosphore
	 */
	private String phosphore;
	/**
	 * La contenance en potassium
	 */
	private String potassium;
	/**
	 * La contenance en azote kjeldahl
	 */
	private String azoteK;
	/**
	 * La contenance en azote ammonical
	 */
	private String azoteA;
	/**
	 * Le bmp de la mati�re volatile
	 */
	private double bmpMV;
	/**
	 * Le bmp de la mati�re fra�che
	 */
	private double bmpMF;
	/**
	 * Le pourcentage de m�thane parmi les gaz �mis
	 */
	private String pourcentageMethane;
	/**
	 * Le niveau de d�tail sur la provenance
	 */
	private int detail;
	
	
	/**
	 * 
	 * @param reference
	 * 	La r�f�rence du substrat
	 * @param nom
	 * Le nom du substrat
	 * @param ms
	 * Le pourcentage de mati�re s�che
	 * @param mv
	 * Le pourcentage de mati�re volatile
	 * @param ratioMsMv
	 * La ratio masse s�che / masse vol
	 * @param phosophore
	 * La contenance en phosphore
	 * @param potassium
	 * La contenance en potassium
	 * @param azoteK
	 * La contenance en azote kjeldahl
	 * @param azoteA
	 * La contenance en azote ammoniacal
	 * @param bmpMV
	 * Le bmp de la mati�re volatile
	 * @param bmpMF
	 * Le bmp de la mati�re fra�che
	 * @param pctageMethane
	 * Le pourcentage de m�thane parmis les gaz �mis
	 */
	public Substrat(String reference, String nom, double ms, double mv, double ratioMsMv, String phosophore,
			String potassium, String azoteK, String azoteA, double bmpMV, double bmpMF, String pctageMethane) {
		super();
		this.reference = reference;
		this.nom = nom;
		this.ms = ms;
		this.mv = mv;
		this.ratioMsMv = ratioMsMv;
		this.phosphore = phosophore;
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
				+ ratioMsMv + ", phosophore=" + phosphore + ", potassium=" + potassium + ", azoteK=" + azoteK
				+ ", azoteA=" + azoteA + ", bmpMV=" + bmpMV + ", bmpMF=" + bmpMF + ", pourcentageMethane="
				+ pourcentageMethane + ", detail=" + detail;
	}

	/**
	 * 
	 * @return La r�f�rence du substrat
	 */
	public String getReference() {
		return reference;
	}
	/**
	 * 
	 * @param reference
	 * 	La r�f�rence � attribuer
	 * 
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}
	/**
	 * 
	 * @return Le nom du substrat
	 * 
	 */
	public String getNom() {
		return nom;
	}
	/**
	 * 
	 * @param nom
	 * Le nom � attribuer
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	/**
	 * 
	 * @return Le pourcentage de mat�re s�che
	 */
	public double getMs() {
		return ms;
	}
	/**
	 * 
	 * @param ms
	 * Le pourcentage de mati�re s�che � affecter
	 */
	public void setMs(double ms) {
		this.ms = ms;
	}
	/**
	 * 
	 * @return Le pourcentage de mati�re volatile
	 */
	public double getMv() {
		return mv;
	}
	/**
	 * 
	 * @param mv
	 * Le pourcentage de mati�re volatile � affecter
	 */
	public void setMv(double mv) {
		this.mv = mv;
	}
	/**
	 * 
	 * @return Le ratio de mati�re s�che / mati�re volatile
	 */
	public double getRatioMsMv() {
		return ratioMsMv;
	}
	/**
	 * 
	 * @param ratioMsMv
	 * Le ratio de mati�re s�che / mati�re volatile � affecter
	 *
	 */
	public void setRatioMsMv(double ratioMsMv) {
		this.ratioMsMv = ratioMsMv;
	}
	/**
	 * 
	 * @return La contenance en phosphore du substrat
	 */
	public String getPhosphore() {
		return phosphore;
	}
	/**
	 * 
	 * @param phosophore
	 * La contenance en phosphore � affecter
	 */
	public void setPhosphore(String phosophore) {
		this.phosphore = phosophore;
	}
	/**
	 * 
	 * @return La contenance en potassium du substrat
	 */
	public String getPotassium() {
		return potassium;
	}
	/**
	 * 
	 * @param potassium
	 * La contenance en potassium � affecter
	 */
	public void setPotassium(String potassium) {
		this.potassium = potassium;
	}
	/**
	 * 
	 * @return La contenance en azote kjeldahl du substrat
	 */
	public String getAzoteK() {
		return azoteK;
	}
	/**
	 * 
	 * @param azoteK
	 * La contenance en azote kjeldahl � affecter
	 */
	public void setAzoteK(String azoteK) {
		this.azoteK = azoteK;
	}
	/**
	 * 
	 * @return Le bmp de la mati�re volatile du substrat
	 */
	public double getBmpMV() {
		return bmpMV;
	}
	/**
	 * 
	 * @param bmpMV
	 *  Le bmp de la mati�re volatile � affecter
	 */
	public void setBmpMV(double bmpMV) {
		this.bmpMV = bmpMV;
	}
	/**
	 * 
	 * @return  Le bmp de la mati�re fra�che du substrat
	 */
	public double getBmpMF() {
		return bmpMF;
	}
	/**
	 * 
	 * @param bmpMF
	 *  Le bmp de la mati�re fra�che � affecter
	 */
	public void setBmpMF(double bmpMF) {
		this.bmpMF = bmpMF;
	}
	/**
	 * 
	 * @return Le niveau de d�tail de la provenance du substrat
	 */
	public int getDetail() {
		return detail;
	}
	/**
	 * 
	 * @param detail
	 * Le niveau de d�tail de la provenance � affecter
	 */
	public void setDetail(int detail) {
		this.detail = detail;
	}

	/**
	 * 
	 * @return La contenance en azote ammoniacal du substrat
	 */
	public String getAzoteA() {
		return azoteA;
	}

	/**
	 * 
	 * @param azoteA
	 * La contenance en azote ammoniacal � affecter
	 */
	public void setAzoteA(String azoteA) {
		this.azoteA = azoteA;
	}

	/**
	 * 
	 * @return Le pourcentage de m�thane parmi les gaz �mis par le substart
	 */
	public String getPourcentageMethane() {
		return pourcentageMethane;
	}

	/**
	 * 
	 * @param pourcentage
	 * Le pourcentage de m�thane parmi les gaz �mis � affecter
	 */
	public void setPourcentageMethane(String pourentage) {
		this.pourcentageMethane = pourentage;
	}
	
	
}
