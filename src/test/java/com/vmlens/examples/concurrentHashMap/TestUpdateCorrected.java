package com.vmlens.examples.concurrentHashMap;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Test;

import com.vmlens.annotation.Interleave;

public class TestUpdateCorrected {
	private final ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<Integer, Integer>();
	@Interleave(group = TestUpdateCorrected.class, threadCount = 2)
	public void update() {
			map.compute(1, (key, value) -> {
				if (value == null) {
					return 1;
				} else {
					return value + 1;
				}
			});
	}
	@Test
	public void testUpdate() throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.execute(() -> {  update(); });
		executor.execute(() -> {  update(); });
		executor.shutdown();
		executor.awaitTermination(10, TimeUnit.MINUTES);
	}
	@After
	public void checkResult() {
		int sum = 0;
		for (Integer value : map.values()) {
			sum += value;
		}
		assertEquals(2, sum);
	}
}
