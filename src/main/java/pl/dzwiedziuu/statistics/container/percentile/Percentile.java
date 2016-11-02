package pl.dzwiedziuu.statistics.container.percentile;

import pl.dzwiedziuu.statistics.container.utils.lists.SortedUnit;

public class Percentile
{
	private final double value;
	/*
	 * node that holds highest value of totalFloorWeight less than this.totalFloorWeight
	 */ SortedUnit floorSortedUnit;
	/*
	 * value that holds total weight before in this percentile
	 */
	private long totalFloorWeight;
	private double actualValue;

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
	public void add(SortedUnit addedSortedUnit)
	{
		moveForward();
	}


	public void prepareForAdd(SortedUnit unitToAdd)
	{
		moveCursorBack(unitToAdd.getWeight());
	}

	void moveCursorBack(long value)
	{
		if(floorSortedUnit.isTechnical())
			return;
		while(value >= 0 && !floorSortedUnit.isTechnical())
		{
			value -= floorSortedUnit.getWeight();
			totalFloorWeight -= floorSortedUnit.getWeight();
			floorSortedUnit = floorSortedUnit.getPrev();
		}
	}

	void moveCursorForth(long value)
	{
		while(value >= 0 && floorSortedUnit.getNext() != null)
		{
			value -= floorSortedUnit.getNext().getWeight();
			totalFloorWeight += floorSortedUnit.getNext().getWeight();
			floorSortedUnit = floorSortedUnit.getNext();
		}
	}

	void moveBackward()
	{
		while(floorSortedUnit.getPrev() != null && totalFloorWeight >= actualValue)
		{
			totalFloorWeight -= floorSortedUnit.getWeight();
			floorSortedUnit = floorSortedUnit.getPrev();
		}
	}

	void moveForward()
	{
		long newTotalWeight = totalFloorWeight;
		while(floorSortedUnit.getNext() != null)
		{
			newTotalWeight += floorSortedUnit.getNext().getWeight();
			if(newTotalWeight >= actualValue)
				break;
			totalFloorWeight = newTotalWeight;
			floorSortedUnit = floorSortedUnit.getNext();
		}
	}

	public void updateActualValue(long totalWeightBeforeOperation)
	{
		this.actualValue = totalWeightBeforeOperation * value;
	}

	/*
			returns percentile value or if percentile is between two values, returns higher
		 */
	public Double getPercentile()
	{
		return (floorSortedUnit.getNext() != null ? floorSortedUnit.getNext() : floorSortedUnit).getValue();
	}

	@Override
	public String toString()
	{
		return "[" + value + ": tWeight=" + totalFloorWeight + ", value=" + getPercentile() + ", floorId=" + floorSortedUnit.getId() + "]";
	}
}
