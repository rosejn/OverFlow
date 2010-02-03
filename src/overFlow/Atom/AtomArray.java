package overFlow.Atom;

public class AtomArray extends Atom{

	Atom[] atomArray;
	
		AtomArray(Atom[] aArray){
			atomArray = aArray;
		}
		
		Atom[] getAtomArray(){
			return atomArray;
		}
}
