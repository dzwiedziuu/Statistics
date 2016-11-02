package pl.dzwiedziuu.statistics.container.percentile;

import pl.dzwiedziuu.statistics.container.utils.lists.SortedUnit;

public class Percentile
{
	private final double value;
	/*
	 * node that holds highest value of totalFloorWeight less than this.totalFloorWeight
	 */ SortedUnit floorSortedUnit;
	/*
	 * value that holds total weight before and exactly in this percentile
	 */
	private long totalFloorWeight;

	public Percentile(double value)
	{
		this.value = value;
	}

	public boolean greaterOrEquals(SortedUnit sortedUnit)
	{
		return floorSortedUnit.compareTo(sortedUnit) <= 0;
	}

	public double getValue()
	{
		return value;
	}

	/*
	 * returns false if no further percentile should be updated
	 */
	public boolean update(SortedUnit removedSortedUnit, SortedUnit addedSortedUnit, long totalWeightBeforeOperation)
	{
		if(removedSortedUnit != null)
		{
			totalWeightBeforeOperation -= removedSortedUnit.getWeight();
			if(this.floorSortedUnit.getValue() >= removedSortedUnit.getValue())
				moveBackward(value * totalWeightBeforeOperation);
		}
		totalWeightBeforeOperation += addedSortedUnit.getWeight();
		moveForward(value * totalWeightBeforeOperation);
		return this.floorSortedUnit.getNext().getValue() <= addedSortedUnit.getValue();
	}

	private void moveBackward(double newActualValue)
	{
		if(floorSortedUnit.getPrev() == null)
			return;
		// if current value is smaller than actualValue - no need to change
		if(floorSortedUnit.getValue() < newActualValue)
			return;
		long newTotalWeight = totalFloorWeight;
		while(floorSortedUnit.getPrev() != null)
		{
			floorSortedUnit = floorSortedUnit.getPrev();
			newTotalWeight -= floorSortedUnit.getWeight();
			if(newTotalWeight < newActualValue)
				break;
		}
		totalFloorWeight = newTotalWeight;
	}

	private void moveForward(double newActualValue)
	{
		if(floorSortedUnit.getNext() == null)
			return;
		long newTotalWeight = totalFloorWeight;
		while(floorSortedUnit.getNext() != null)
		{
			newTotalWeight += floorSortedUnit.getNext().getWeight();
			if(newTotalWeight >= newActualValue)
				break;
			totalFloorWeight = newTotalWeight;
			floorSortedUnit = floorSortedUnit.getNext();
		}
	}

	public Double getPercentile()
	{
		return floorSortedUnit.getNext().getValue();
	}

	@Override
	public String toString()
	{
		return "[" + value + ": tWeight=" + totalFloorWeight + ", value=" + getPercentile() + ", floorId=" + floorSortedUnit.getId() + "]";
	}
}
