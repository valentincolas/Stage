package metier;


/**
 * <b>Cette classe permet de stocker les informations relatives à un substrat dans un objet Substrat<b>
 * @author COLAS Valentin
 * @version 1.0
 */
public class Substrat {
	/**
	 * La référence du substrat
	 */
	private String reference;
	/**
	 * Le nom du substrat
	 */
	private String nom;
	/**
	 * Le pourcentage de matière sèche
	 */
	private double ms;
	/**
	 * Le pourcentage de matière volatile
	 */
	private double mv;
	/**
	 * Le ratio matière sèche / matière volatile
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
	 * Le bmp de la matière volatile
	 */
	private double bmpMV;
	/**
	 * Le bmp de la matière fraîche
	 */
	private double bmpMF;
	/**
	 * Le pourcentage de méthane parmi les gaz émis
	 */
	private String pourcentageMethane;
	/**
	 * Le niveau de détail sur la provenance
	 */
	private int detail;
	
	
	/**
	 * 
	 * @param reference
	 * 	La référence du substrat
	 * @param nom
	 * Le nom du substrat
	 * @param ms
	 * Le pourcentage de matière sèche
	 * @param mv
	 * Le pourcentage de matière volatile
	 * @param ratioMsMv
	 * La ratio masse sèche / masse vol
	 * @param phosophore
	 * La contenance en phosphore
	 * @param potassium
	 * La contenance en potassium
	 * @param azoteK
	 * La contenance en azote kjeldahl
	 * @param azoteA
	 * La contenance en azote ammoniacal
	 * @param bmpMV
	 * Le bmp de la matière volatile
	 * @param bmpMF
	 * Le bmp de la matière fraîche
	 * @param pctageMethane
	 * Le pourcentage de méthane parmis les gaz émis
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
	 * @return La référence du substrat
	 */
	public String getReference() {
		return reference;
	}
	/**
	 * 
	 * @param reference
	 * 	La référence à attribuer
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
	 * Le nom à attribuer
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	/**
	 * 
	 * @return Le pourcentage de matère sèche
	 */
	public double getMs() {
		return ms;
	}
	/**
	 * 
	 * @param ms
	 * Le pourcentage de matière sèche à affecter
	 */
	public void setMs(double ms) {
		this.ms = ms;
	}
	/**
	 * 
	 * @return Le pourcentage de matière volatile
	 */
	public double getMv() {
		return mv;
	}
	/**
	 * 
	 * @param mv
	 * Le pourcentage de matière volatile à affecter
	 */
	public void setMv(double mv) {
		this.mv = mv;
	}
	/**
	 * 
	 * @return Le ratio de matière sèche / matière volatile
	 */
	public double getRatioMsMv() {
		return ratioMsMv;
	}
	/**
	 * 
	 * @param ratioMsMv
	 * Le ratio de matière sèche / matière volatile à affecter
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
	 * La contenance en phosphore à affecter
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
	 * La contenance en potassium à affecter
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
	 * La contenance en azote kjeldahl à affecter
	 */
	public void setAzoteK(String azoteK) {
		this.azoteK = azoteK;
	}
	/**
	 * 
	 * @return Le bmp de la matière volatile du substrat
	 */
	public double getBmpMV() {
		return bmpMV;
	}
	/**
	 * 
	 * @param bmpMV
	 *  Le bmp de la matière volatile à affecter
	 */
	public void setBmpMV(double bmpMV) {
		this.bmpMV = bmpMV;
	}
	/**
	 * 
	 * @return  Le bmp de la matière fraîche du substrat
	 */
	public double getBmpMF() {
		return bmpMF;
	}
	/**
	 * 
	 * @param bmpMF
	 *  Le bmp de la matière fraîche à affecter
	 */
	public void setBmpMF(double bmpMF) {
		this.bmpMF = bmpMF;
	}
	/**
	 * 
	 * @return Le niveau de détail de la provenance du substrat
	 */
	public int getDetail() {
		return detail;
	}
	/**
	 * 
	 * @param detail
	 * Le niveau de détail de la provenance à affecter
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
	 * La contenance en azote ammoniacal à affecter
	 */
	public void setAzoteA(String azoteA) {
		this.azoteA = azoteA;
	}

	/**
	 * 
	 * @return Le pourcentage de méthane parmi les gaz émis par le substart
	 */
	public String getPourcentageMethane() {
		return pourcentageMethane;
	}

	/**
	 * 
	 * @param pourcentage
	 * Le pourcentage de méthane parmi les gaz émis à affecter
	 */
	public void setPourcentageMethane(String pourentage) {
		this.pourcentageMethane = pourentage;
	}
	
	
}
