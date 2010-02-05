package overFlow.Atom;

import java.util.Vector;

public class AtomFloatArray extends Atom{
	
	float[] floatArray;
	Vector<AtomFloat> atomFloatArray = new Vector<AtomFloat>();
	
		public AtomFloatArray(float[] array){
			type = 3;
			floatArray = array;
			setList(true);	//set boolean that this is a list type Atom

			for(int i = 0; i < floatArray.length - 1; i++){
				atomFloatArray.insertElementAt(new AtomFloat(floatArray[i]),0);
			}
		}

		public float getFloat(){
			return floatArray[0];
		}
		
		public float[] getFloatArray() {
			return floatArray;
		}
		public Vector<AtomFloat> getAtomFloatArray() {
			return atomFloatArray;
		}
		
		public AtomFloat getElementAt(int i) {
			return atomFloatArray.get(i);
		}
		
		public int getInt(){
			return (int)floatArray[0];
		}
		
		public int getSize() {
			return floatArray.length;
		}
}
