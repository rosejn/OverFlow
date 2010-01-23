package overFlow.Tools;

public class Tools {
	
	
	public static float constrain (float value, float lowerConstraint, float upperConstraint){
		float outputValue;
		if(value < lowerConstraint) {
			outputValue = lowerConstraint;
		}
		else if (value > upperConstraint){
			outputValue = upperConstraint;		
		}
		else {
			outputValue = value;	
		}	
		return outputValue;
	}
	


	public static float map(float value, float leftMin, float leftMax, float rightMin, float rightMax){
		// Figure out how 'wide' each range is
		float leftSpan = leftMax - leftMin;
		float rightSpan = rightMax - rightMin;

		// Convert the left range into a 0-1 range (float)
		float valueScaled = value - leftMin / leftSpan;

		// Convert the 0-1 range into a value in the right range.
		return rightMin + (valueScaled * rightSpan);
	}

}