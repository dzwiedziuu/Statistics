package pl.dzwiedziuu.statistics.container.percentile;

import org.junit.Assert;
import org.junit.Test;
import pl.dzwiedziuu.statistics.container.utils.lists.SortedList;
import pl.dzwiedziuu.statistics.container.utils.lists.SortedUnit;

/**
 * Created by Andrzej on 2016-11-02.
 */
public class PercentileTest
{
	@Test
	public void testMoveForward()
	{
		Percentile percentile = new Percentile(0.5);
		SortedList sortedList = new SortedList();
		sortedList.addValue(SortedUnit.create(11, 1.0, 5));
		sortedList.addValue(SortedUnit.create(12, 2.0, 5));
		sortedList.addValue(SortedUnit.create(13, 2.0, 2));
		sortedList.addValue(SortedUnit.create(14, 3.0, 5));
		percentile.floorSortedUnit = sortedList.getFirst();
		percentile.updateActualValue(0);
		percentile.moveForward();
		Assert.assertEquals(SortedList.START_NODE_ID, (long) percentile.floorSortedUnit.getId());
		percentile.updateActualValue(1);
		percentile.moveForward();
		Assert.assertEquals(SortedList.START_NODE_ID, (long) percentile.floorSortedUnit.getId());
		percentile.updateActualValue(10);
		percentile.moveForward();
		Assert.assertEquals(SortedList.START_NODE_ID, (long) percentile.floorSortedUnit.getId());
		percentile.updateActualValue(11);
		percentile.moveForward();
		Assert.assertEquals(11, (long) percentile.floorSortedUnit.getId());
		percentile.updateActualValue(20);
		percentile.moveForward();
		Assert.assertEquals(11, (long) percentile.floorSortedUnit.getId());
		percentile.updateActualValue(22);
		percentile.moveForward();
		Assert.assertEquals(12, (long) percentile.floorSortedUnit.getId());
		percentile.updateActualValue(24);
		percentile.moveForward();
		Assert.assertEquals(12, (long) percentile.floorSortedUnit.getId());
		percentile.updateActualValue(30);
		percentile.moveForward();
		Assert.assertEquals(13, (long) percentile.floorSortedUnit.getId());
		percentile.updateActualValue(36);
		percentile.moveForward();
		Assert.assertEquals(14, (long) percentile.floorSortedUnit.getId());
	}

	@Test
	public void testMoveBackward()
	{
		Percentile percentile = new Percentile(0.5);
		SortedList sortedList = new SortedList();
		sortedList.addValue(SortedUnit.create(11, 1.0, 5));
		sortedList.addValue(SortedUnit.create(12, 2.0, 5));
		sortedList.addValue(SortedUnit.create(13, 2.0, 2));
		sortedList.addValue(SortedUnit.create(14, 3.0, 5));
		percentile.floorSortedUnit = sortedList.getFirst();
		percentile.updateActualValue(36);
		percentile.moveForward();
		percentile.updateActualValue(35);
		percentile.moveBackward();
		Assert.assertEquals(14, (long) percentile.floorSortedUnit.getId());
		percentile.updateActualValue(34);
		percentile.moveBackward();
		Assert.assertEquals(13, (long) percentile.floorSortedUnit.getId());
		percentile.updateActualValue(30);
		percentile.moveBackward();
		Assert.assertEquals(13, (long) percentile.floorSortedUnit.getId());
		percentile.updateActualValue(24);
		percentile.moveBackward();
		Assert.assertEquals(12, (long) percentile.floorSortedUnit.getId());
		percentile.updateActualValue(22);
		percentile.moveBackward();
		Assert.assertEquals(12, (long) percentile.floorSortedUnit.getId());
		percentile.updateActualValue(20);
		percentile.moveBackward();
		Assert.assertEquals(11, (long) percentile.floorSortedUnit.getId());
		percentile.updateActualValue(11);
		percentile.moveBackward();
		Assert.assertEquals(11, (long) percentile.floorSortedUnit.getId());
		percentile.updateActualValue(10);
		percentile.moveBackward();
		Assert.assertEquals(SortedList.START_NODE_ID, (long) percentile.floorSortedUnit.getId());
		percentile.updateActualValue(1);
		percentile.moveBackward();
		Assert.assertEquals(SortedList.START_NODE_ID, (long) percentile.floorSortedUnit.getId());
		percentile.updateActualValue(0);
		percentile.moveBackward();
		Assert.assertEquals(SortedList.START_NODE_ID, (long) percentile.floorSortedUnit.getId());
		percentile.updateActualValue(-1);
		percentile.moveBackward();
		Assert.assertEquals(SortedList.START_NODE_ID, (long) percentile.floorSortedUnit.getId());
	}
}