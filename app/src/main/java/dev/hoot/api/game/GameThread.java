package dev.hoot.api.game;

import meteor.Main;
import meteor.rs.ClientThread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class GameThread
{
	private static final long TIMEOUT = 1000;

	public static void invoke(Runnable runnable)
	{
		if (Main.client.isClientThread())
		{
			runnable.run();
		}
		else
		{
			ClientThread.INSTANCE.invokeLater(runnable);
		}
	}

	public static <T> T invokeLater(Callable<T> callable)
	{
		if (Main.client.isClientThread())
		{
			try
			{
				return callable.call();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		try
		{
			FutureTask<T> futureTask = new FutureTask<>(callable);
			ClientThread.INSTANCE.invokeLater(futureTask);
			return futureTask.get(TIMEOUT, TimeUnit.MILLISECONDS);
		}
		catch (ExecutionException | InterruptedException | TimeoutException e)
		{
			e.printStackTrace();
			throw new RuntimeException("Client thread invoke timed out after " + TIMEOUT + " ms");
		}
	}
}

