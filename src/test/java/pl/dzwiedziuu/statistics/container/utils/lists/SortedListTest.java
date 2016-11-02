package pl.dzwiedziuu.statistics.container.utils.lists;

import org.junit.Test;

public class SortedListTest
{
	@Test
	public void test()
	{
		SortedList sortedList = new SortedList();
		sortedList.addValue(3.0, 1L);
		sortedList.addValue(4.0, 1L);
		sortedList.addValue(2.0, 1L);
		sortedList.addValue(2.5, 1L);
		sortedList.addValue(3.1, 1L);
		sortedList.addValue(2.5, 1L);
		sortedList.addValue(3.5, 1L);
		System.out.println(sortedList.toStringSorted());
	}

	@Test
	public void test2()
	{
		SortedList sortedList = new SortedList();
		sortedList.addValue(10.0, 2L);
		sortedList.addValue(20.0, 1L);
		sortedList.addValue(20.0, 1L);
		sortedList.addValue(15.0, 2L);
		sortedList.addValue(30.0, 10L);
		sortedList.addValue(5.0, 10L);
		System.out.println(sortedList.toStringSorted());
	}
}
