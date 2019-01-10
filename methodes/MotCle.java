package methodes;

import java.text.DecimalFormat;

import Programme.WordFrequencyData;

public class MotCle implements Comparable<MotCle> {

	private String Mot;
	private double NbrTFIDF;
	private double NbrF;

	public MotCle(String Mot, double NbrTFIDF, double NbrF) {
		this.Mot = Mot;
		this.NbrTFIDF = NbrTFIDF;
		this.NbrF = NbrF;
	}

	public int compareTo(MotCle other) {
		return Mot.compareTo(other.Mot);
	}

	public boolean equals(Object other) {
		if (other == null)
			return false;
		else if (other == this) // Self always matches
			return true;
		// Guard against class cast exception
		else if (!(other instanceof MotCle))
			return false;
		return (compareTo((MotCle) other) == 0);
	}

	public MotCle() {
		super();
	}

	public double getNbrTFIDF() {
		return NbrTFIDF;
	}

	public double getNbrF() {
		return NbrF;
	}

	public void setNbrF(double NbrF) {
		this.NbrF = NbrF;
	}

	public void setNbrTFIDF(double NbrTFIDF) {
		this.NbrTFIDF = NbrTFIDF;
	}

	public String getMot() {
		return Mot;
	}

	public void setMot(String mot) {
		this.Mot = mot;
	}

	@Override
	public String toString() {
		return "(" + Mot + "," + NbrTFIDF +")";// DecForm(NbrF) + ")";
	}

	public static String DecForm(Double x) {
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(0); // arrondi à 2 chiffres apres la
										// virgules
		df.setMinimumFractionDigits(0);
		return df.format(x);
	}

	public static String DecForm2(Double x) {
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(4); // arrondi à 2 chiffres apres la
										// virgules
		df.setMinimumFractionDigits(2);
		return df.format(x);
	}

}
