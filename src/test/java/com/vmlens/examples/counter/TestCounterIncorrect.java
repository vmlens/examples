package com.vmlens.examples.counter;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;

import com.vmlens.annotation.Interleave;

public class TestCounterIncorrect {
	private volatile int i = 0;
	@Interleave
	public void increment() {
	 i++;	
	}
	@Test
	public void testUpdate() throws InterruptedException	{
		Thread first = new Thread( () ->   {increment();} ) ;
		Thread second = new Thread( () ->   {increment();} ) ;
		first.start();
		second.start();
		first.join();
		second.join();
		
	}	
	@After
	public void checkResult() {
		assertEquals( 2 , i );
	}
}
