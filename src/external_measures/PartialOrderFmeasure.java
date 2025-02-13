package external_measures;

import interfaces.Measure;

import java.util.LinkedList;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.interfaces.Instance;

public class PartialOrderFmeasure implements Measure {
	public int TP = 0;
	public int FP = 0;
	public int TN = 0;
	public int FN = 0;
	
	@Override
	public double getMeasure(Hierarchy h) {
		TP = 0;
		FP = 0;
		TN = 0;
		FN = 0;
		LinkedList<Instance> allInstances = h.getRoot().getSubtreeInstances();
		Instance[] allInstancesArr = allInstances.toArray(new Instance[allInstances.size()]);
		
		for(int i = 0; i < allInstancesArr.length; i++)
		{
			String firstTrueClass = allInstancesArr[i].getTrueClass();
			String firstAssignClass = allInstancesArr[i].getNodeId();
			for(int j = 0; j < allInstancesArr.length; j++)
			{
				if(i != j)
				{
					String secondTrueClass = allInstancesArr[j].getTrueClass();
					String secondAssignClass = allInstancesArr[j].getNodeId();
					
					if((firstTrueClass+basic_hierarchy.common.Constants.HIERARCHY_BRANCH_SEPARATOR)
							.startsWith(secondTrueClass+basic_hierarchy.common.Constants.HIERARCHY_BRANCH_SEPARATOR))
					{
						if((firstAssignClass+basic_hierarchy.common.Constants.HIERARCHY_BRANCH_SEPARATOR)
								.startsWith(secondAssignClass+basic_hierarchy.common.Constants.HIERARCHY_BRANCH_SEPARATOR))
						{
							TP++;
						}
						else
						{
							FN++;
						}
					}
					else
					{
						if((firstAssignClass+basic_hierarchy.common.Constants.HIERARCHY_BRANCH_SEPARATOR)
								.startsWith(secondAssignClass+basic_hierarchy.common.Constants.HIERARCHY_BRANCH_SEPARATOR))
						{
							FP++;
						}
						else
						{
							TN++;
						}
					}
				}
			}
		}
		double numerator = 2*TP;
		double denominator = 2*TP + FP + FN;
		return numerator/denominator;
	}

	@Override
	public double desiredValue() {
		return 1;
	}

	@Override
	public double notDesiredValue() {
		return 0;
	}

}
