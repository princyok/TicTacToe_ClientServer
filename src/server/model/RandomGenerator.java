package server.model;
import java.util.Random;

class RandomGenerator {

/**
 * Creates a random number ranging between low and high,  
 * @param low
 * @param high
 * @return
 */
	int generateRandNumber(int low, int high)
	{
		if(low >= high)
		{
			System.out.println("Error discrete, low >= high");
			System.exit(0);
		}
		
		Random r = new Random();
		int d = r.nextInt(high - low + 1) + low;
		return d;
	}
	
}
