package testJBehave;

import java.util.ArrayList;
import java.util.List;

public class Power {
  
	public List<Integer> powers(int left,int right){
		int i;
		List<Integer> result = new ArrayList<Integer>();
		for(i=left; i<=right; i++){
			if (i == 0){
				result.add(1);
			}
			else{
				result.add(2<<(i-1));
			}
		}
		return result;
	}
	
}
