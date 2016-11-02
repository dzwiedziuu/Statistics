package pl.dzwiedziuu.statistics.container.percentile;

import org.junit.Assert;
import org.junit.Test;

public class PercentileListTest
{
	@Test
	public void testMinMaxMid()
	{
		PercentileList percentileList = new PercentileList(0.0, 0.5, 1.0);
		percentileList.add(2.0, 1);
		System.out.println(percentileList);
		percentileList.add(10.0, 5);
		System.out.println(percentileList);
		percentileList.add(1.0, 4);
		System.out.println(percentileList);
		percentileList.add(3.0, 10);
		System.out.println(percentileList);
		Assert.assertEquals(1.0, percentileList.getPercentileValue(0.0), 0.0);
		Assert.assertEquals(3.0, percentileList.getPercentileValue(0.5), 0.0);
		Assert.assertEquals(10.0, percentileList.getPercentileValue(1.0), 0.0);
	}

	@Test
	public void testOneValue()
	{
		PercentileList percentileList = new PercentileList(0.0, 0.5, 1.0);
		percentileList.add(2.0, 1);
		Assert.assertEquals(2.0, percentileList.getPercentileValue(0.0), 0.0);
		Assert.assertEquals(2.0, percentileList.getPercentileValue(0.5), 0.0);
		Assert.assertEquals(2.0, percentileList.getPercentileValue(1.0), 0.0);
	}
}
