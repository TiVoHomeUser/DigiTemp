package vicw.utils;

/*
 * Function should be able to caculate
 *    precent = number / reduction
 *    number = reduction * percent
 *    reduction = number / percent 
 *    
 *  number = 200, reduction = 20, percent = 10
 *  10% of 200 = 20
 *  20 = 10% of 200
 *  
 */
public class perccal {

	private Double percentage = null;
	private Double number = null;
	private Double reduction = null;

	public static void main(String[] args) {
		perccal pcal;
		pcal = new perccal();

		System.out.println("percent = " + pcal.getPercentage());
		System.out.println("number = " + pcal.getNumber());
		System.out.println("Reduction = " + pcal.getReduction());
		System.out.println(pcal.toString());

	}

	public perccal() {

	}

	/**
	 * @param percentage
	 * @param number
	 * @param reduction
	 */

	// precent = number / reduction
	public double getPercentage() {
		if (number != null && reduction != null){
			setPercentage(number / reduction);
		}
		if(percentage != null)
			return percentage;
		else
			return Double.NaN;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	// number = reduction * percent
	public double getNumber() {
		if (reduction != null && percentage != null) {
			setNumber(reduction * percentage);
		}
		if (number != null)
			return number;
		else
			return Double.NaN;
	}

	public void setNumber(double number) {
		this.number = number;
	}

	// reduction = number / percent
	public double getReduction() {
		if (number != null && percentage != null) {
			setReduction(number / percentage);
		}
		if (reduction != null)
			return reduction;
		else
			return Double.NaN;
	}

	public void setReduction(double reduction) {
		this.reduction = reduction;
	}

	public String toString() {
		return new String("number = " + number + " reduction = " + reduction
				+ " percent = " + percentage + "%");
	}
}
