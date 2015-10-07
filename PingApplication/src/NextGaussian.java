import java.util.Random;

public class NextGaussian {

	double avg;
	double sd;
	double answer;
	
	public NextGaussian(double avg, double sd){
		this.avg = avg;
		this.sd = sd;
	}
	
	public double generateGaussian(){
		return this.getGaussian(avg, (sd*sd));
	}

	private Random fRandom = new Random();
	  
	private double getGaussian(double aMean, double aVariance){
		return aMean + fRandom.nextGaussian() * aVariance;						//Calculating the next gaussian random number.
	}
}
