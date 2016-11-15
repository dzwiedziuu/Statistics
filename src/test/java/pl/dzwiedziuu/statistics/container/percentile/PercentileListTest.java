package pl.dzwiedziuu.statistics.container.percentile;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

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

	@Test
	public void test1a()
	{
		double[] percentiles = new double[]{0.0, 1.0};
		PercentileList pList = new PercentileList(percentiles);
		pList.add(10.0, 2L);
		checkPercentiles(pList, percentiles, new Double[]{10.0, 10.0});
		pList.add(20.0, 1L);
		checkPercentiles(pList, percentiles, new Double[]{10.0, 20.0});
		pList.add(20.0, 1L);
		checkPercentiles(pList, percentiles, new Double[]{10.0, 20.0});
		pList.add(15.0, 2L);
		checkPercentiles(pList, percentiles, new Double[]{10.0, 20.0});
		pList.add(30.0, 10L);
		checkPercentiles(pList, percentiles, new Double[]{10.0, 30.0});
		pList.add(5.0, 10L);
		checkPercentiles(pList, percentiles, new Double[]{5.0, 30.0});
	}

	@Test
	public void test1()
	{
		PercentileList pList = new PercentileList(0.5);
		Assert.assertEquals(new Double(-Double.MAX_VALUE), pList.getPercentileValue(0.5));
		pList.add(10.0, 2L);
		Assert.assertEquals(new Double(10), pList.getPercentileValue(0.5));
		pList.add(20.0, 1L);
		Assert.assertEquals(new Double(10), pList.getPercentileValue(0.5));
		pList.add(20.0, 1L);
		Assert.assertEquals(new Double(10), pList.getPercentileValue(0.5));
		pList.add(15.0, 2L);
		Assert.assertEquals(new Double(15), pList.getPercentileValue(0.5));
		pList.add(30.0, 10L);
		Assert.assertEquals(new Double(30), pList.getPercentileValue(0.5));
	}

	@Test
	public void test2()
	{
		PercentileList pList = new PercentileList(0.1, 0.9);
		for(int i = 0; i < 100; i++)
			pList.add(new Double(i), 1L);
		Assert.assertEquals(new Double(9), pList.getPercentileValue(0.1));
		Assert.assertEquals(new Double(89), pList.getPercentileValue(0.9));
	}

	@Test
	public void test2a()
	{
		PercentileList pList = new PercentileList(0.5);
		for(int i = 99; i >= 0; i--)
		{
			pList.add(new Double(i), 1L);
			// double d = pList.getPercentileValue(0.5);
			// System.out.println(d);
		}
		Assert.assertEquals(new Double(49.0), pList.getPercentileValue(0.5));
	}

	@Test
	public void test3()
	{
		PercentileList pList = new PercentileList(0.1, 0.9);
		for(int i = 0; i < 100; i++)
			pList.add(new Double(i), i + 1);
		Assert.assertEquals(new Double(31.0), pList.getPercentileValue(0.1));
		Assert.assertEquals(new Double(94.0), pList.getPercentileValue(0.9));
	}

	@Test
	public void test3b()
	{
		double[] percentiles = new double[]{0.1, 0.3, 0.5, 0.7, 0.9};
		PercentileList pList = new PercentileList(percentiles);
		pList.add(0.0, 300);
		checkPercentiles(pList, percentiles, new Double[]{0.0, 0.0, 0.0, 0.0, 0.0});
		pList.add(1.0, 200);
		checkPercentiles(pList, percentiles, new Double[]{0.0, 0.0, 0.0, 1.0, 1.0});
		pList.add(1.0, 400);
		checkPercentiles(pList, percentiles, new Double[]{0.0, 0.0, 1.0, 1.0, 1.0});
		pList.add(1.0, 400);
		checkPercentiles(pList, percentiles, new Double[]{0.0, 1.0, 1.0, 1.0, 1.0});
		pList.add(1.0, 0);
		checkPercentiles(pList, percentiles, new Double[]{0.0, 1.0, 1.0, 1.0, 1.0});
		pList.add(0.0, 1000);
		checkPercentiles(pList, percentiles, new Double[]{0.0, 0.0, 0.0, 1.0, 1.0});
		pList.add(1.0, 400);
		checkPercentiles(pList, percentiles, new Double[]{0.0, 0.0, 1.0, 1.0, 1.0});
		pList.add(0.0, 1000);
		checkPercentiles(pList, percentiles, new Double[]{0.0, 0.0, 0.0, 1.0, 1.0});
	}

	@Test
	public void test4()
	{
		double[] percentiles = new double[]{0.1, 0.3, 0.5, 0.7, 0.9};
		PercentileList pList = new PercentileList(percentiles);
		for(int i = 100; i > 0; i--)
		{
			int j = 2 * i + 50 * (i % 2 == 0 ? 1 : 2) * (i % 3 == 0 ? 1 : -1);
			if(j <= 0)
				break;
			pList.add((double) j, i / 10);
		}
		checkPercentiles(pList, percentiles, new Double[]{42.0, 78.0, 114.0, 182.0, 250.0});
	}

	@Test
	public void testFile1() throws FileNotFoundException
	{
		double[] percentiles = new double[]{0.0, 0.95, 1.0};
		PercentileList pList = loadFromFile("src/test/resources/testCase1.txt", percentiles);
		checkPercentiles(pList, percentiles, new Double[]{1.0001918756563748, 1.0002006290750285, 1.0002006290750285});
	}

	private PercentileList loadFromFile(String filePath, double[] percentiles) throws FileNotFoundException
	{
		PercentileList pList = new PercentileList(percentiles);
		File file = new File(filePath);

		ArrayList<Integer> idxArrayList = new ArrayList<>();
		ArrayList<Double> valueArrayList = new ArrayList<>();
		ArrayList<Long> weightArrayList = new ArrayList<>();

		try(Scanner scanner = new Scanner(file))
		{
			scanner.useDelimiter("\\[\\[|,\\[|;|\\]|=").useLocale(Locale.ENGLISH);
			while(scanner.hasNext())
			{
				scanner.next();
				int idx = scanner.nextInt();
				idxArrayList.add(idx);
				scanner.next();
				double value = scanner.nextDouble();
				valueArrayList.add(value);
				scanner.next();
				long weight = scanner.nextLong();
				weightArrayList.add(weight);
				scanner.next();
				scanner.next();
			}
		}

		Integer[] indexes = new Integer[idxArrayList.size()];
		for(int i = 0; i < idxArrayList.size(); i++)
			indexes[idxArrayList.get(i)] = i;
		for(Integer i : indexes)
			pList.add(valueArrayList.get(i), weightArrayList.get(i));
		return pList;
	}

	private void checkPercentiles(PercentileList pList, double[] percentiles, Double[] values)
	{
		for(int i = 0; i < percentiles.length; i++)
			Assert.assertEquals("P[" + percentiles[i] + "]", values[i], pList.getPercentileValue(percentiles[i]));
	}
}
