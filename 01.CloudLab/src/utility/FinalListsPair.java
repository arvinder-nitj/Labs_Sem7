package utility;

import java.util.ArrayList;
import org.cloudbus.cloudsim.*;
import utility.FinalListsPair;

public class FinalListsPair {
	public ArrayList<Vm>finalvmList;
	public ArrayList<Cloudlet> finalcloudletList;
	public FinalListsPair() {
		finalcloudletList = new ArrayList<Cloudlet>();
		finalvmList       = new ArrayList<Vm>();		
	}
	
	public FinalListsPair(ArrayList<Vm>finalvmList,ArrayList<Cloudlet> finalcloudletList) {
		this.finalcloudletList = finalcloudletList;
		this.finalvmList       = finalvmList;		
	}
}