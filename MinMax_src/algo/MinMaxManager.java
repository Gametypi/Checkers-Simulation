package algo;

import java.util.concurrent.ForkJoinPool;

import checkers.Figure.FigureColor;
import player.MinMaxMTPlayer;

public class MinMaxManager {

	private ForkJoinPool pool;
	
	private MinMaxMTPlayer player;
	
	private final FigureColor playerColor;
	private final FigureColor enemyColor;
	/**
	 * maximun depth the recursion is allowed to go
	 */
	public final int maxDepth;
	
	public MinMaxManager(MinMaxMTPlayer player, int maxDepth, FigureColor playerColor, FigureColor enemyColor) {
		pool = new ForkJoinPool();
		this.player = player;
		this.maxDepth = maxDepth;
		this.playerColor = playerColor;
		this.enemyColor = enemyColor;
	}
	
	public ForkJoinPool getFJPool(){
		return pool;
	}

	public FigureColor getPlayerColor(){
		return playerColor;
	}
	public FigureColor getEnemyColor(){
		return enemyColor;
	}
	
	public MinMaxMTPlayer getPlayer(){
		return player;
	}
	
	public void logError(String msg) {
		//DEBUG: System.out.println(msg);
		player.csl.printError(msg, player.getName());
	}
}
