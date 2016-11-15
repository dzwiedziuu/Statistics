package pl.aniedzwiedz.statistics.container.percentile;

import pl.aniedzwiedz.statistics.container.utils.lists.SortedUnit;
import pl.aniedzwiedz.statistics.container.utils.lists.SortedList;

public class Percentile implements Comparable<Percentile>
{
	private SortedList sortedList;
	private final double value;
	/*
	 * node that holds highest value of totalFloorWeight less than this.totalFloorWeight
	 */
	private SortedUnit pointer;
	/*
	 * value that holds total weight before in this percentile
	 */
	private long totalFloorWeight;

	public Percentile(SortedList sortedList, double value)
	{
		this.sortedList = sortedList;
		this.value = value;
		this.pointer = sortedList.getFirst();
		update();
	}

	public double getValue()
	{
		return value;
	}

	void moveCursorBack(long value)
	{
		if(pointer.isTechnical())
			return;
		while(value >= 0 && !pointer.isTechnical())
		{
			value -= pointer.getWeight();
			totalFloorWeight -= pointer.getWeight();
			pointer = pointer.getPrev();
		}
	}

	void update()
	{
		double actualValue = sortedList.getTotalWeight() * value;
		long newTotalWeight = totalFloorWeight;
		while(pointer.getNext() != null)
		{
			newTotalWeight += pointer.getNext().getWeight();
			if(newTotalWeight >= actualValue)
				break;
			totalFloorWeight = newTotalWeight;
			pointer = pointer.getNext();
		}
	}

	/*
			returns percentile value or if percentile is between two values, returns higher
		 */
	public Double getPercentile()
	{
		return (pointer.getNext() != null ? pointer.getNext() : pointer).getValue();
	}

	@Override
	public String toString()
	{
		return "[" + value + ": pointerId=" + pointer.getId() + ", tWeight=" + totalFloorWeight + ", value=" + getPercentile() + ", floorId=" + pointer.getId() + "]";
	}

	@Override
	public int compareTo(Percentile o) {
		return Double.compare(this.value, o.value);
	}

	public void increaseTotalWeight(long weight) {
        totalFloorWeight += weight;
	}

	public void switchPointerIfRemoved(SortedUnit removedSortedUnit) {
		if(pointer == removedSortedUnit) {
			totalFloorWeight -= removedSortedUnit.getWeight();
			pointer = removedSortedUnit.getPrev();
		}
	}

	public double getPointerValue() {
		return pointer.getValue();
	}
}
