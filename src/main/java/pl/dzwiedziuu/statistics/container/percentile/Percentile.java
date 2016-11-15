package pl.dzwiedziuu.statistics.container.percentile;

import pl.dzwiedziuu.statistics.container.utils.lists.SortedList;
import pl.dzwiedziuu.statistics.container.utils.lists.SortedUnit;

public class Percentile implements Comparable<Percentile>
{
	private SortedList sortedList;
	private final double value;
	/*
	 * node that holds highest value of totalFloorWeight less than this.totalFloorWeight
	 */
	private SortedUnit floorSortedUnit;
	/*
	 * value that holds total weight before in this percentile
	 */
	private long totalFloorWeight;

	public Percentile(SortedList sortedList, double value)
	{
		this.sortedList = sortedList;
		this.value = value;
		this.floorSortedUnit = sortedList.getFirst();
		update();
	}

	public double getValue()
	{
		return value;
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

	void update()
	{
		double actualValue = sortedList.getTotalWeight() * value;
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

	@Override
	public int compareTo(Percentile o) {
		return Double.compare(this.value, o.value);
	}

	public void increaseTotalWeightIfNecessary(SortedUnit unitToAdd) {
		if(unitToAdd.getValue() < floorSortedUnit.getValue())
			totalFloorWeight += unitToAdd.getWeight();
	}
}
