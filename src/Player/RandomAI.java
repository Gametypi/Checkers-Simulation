
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import checkers.Figure.FigureColor;
import datastructs.List;
import checkers.GameLogic;
import checkers.Move;
import checkers.Player;
import gui.Console;
import utilities.FileUtilities;

public class RandomAI implements Player {
	String name;
	List<Move> moveList;
	GameLogic gmlc;
	Console console;
	FigureColor aiFigureColor;
	Random rand;

	public RandomAI(GameLogic pGmlc, Console pConsole) {
		name = "Random Ai";
		rand = new Random();
		moveList = new List<Move>();
		gmlc = pGmlc;
		console = pConsole;
	}
	@Override
	public void prepare(FigureColor color) {
		aiFigureColor = color;
	}

	@Override
	public void requestMove() {
		moveList = Move.getPossibleMoves(aiFigureColor,gmlc.getPlayfield());
		int randomNumber = rand.nextInt(moveList.length);
		moveList.toFirst();
		for(int i = 0;i < randomNumber; i++) {
			moveList.next();
		}
		gmlc.makeMove(moveList.get());
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean acceptDraw() {
		return false;
	}
	@Override
	public void saveInformation(String directory) {
		File file;
		String fileName = "NNPlayer Information.txt";
		if(FileUtilities.searchForEqualFiles(directory, fileName)){
			file = new File(directory + "/" + "(1)" + fileName);
		}
		else {
			file = new File(directory + "/" + fileName) ;
		}

		try {
			file.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		PrintWriter writer ;
		try {
			writer = new PrintWriter(file);
			writer.write("No information for this ai");
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
	}

}
