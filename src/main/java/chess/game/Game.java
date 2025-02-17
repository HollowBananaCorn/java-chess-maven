package chess.game;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import chess.Colour;
import chess.Position;
import chess.move.*;
import chess.piece.Horse;
import chess.piece.Piece;

public abstract class Game
{
    protected String whitePlayer; // may store Addr of playent if i ever make it a web-application
    protected String blackPlayer;
    protected List<Move> moves;
    protected Position startingPosition;
    
    public Game(String p1, String p2, Position startingPosition)
    {
        this.whitePlayer = p1;
        this.blackPlayer = p2;
        this.moves = new ArrayList<>();
        this.startingPosition = startingPosition;
    }

    // Overloaded constructor for games that generate their starting position
    public Game(String p1, String p2)
    {
        this(p1, p2, null);
        initializePosition();
    }

    protected abstract void initializePosition();

    public Position getStartingPosition()
    {
        return startingPosition;
    }

    public Position getCurrentPosition()
    {
        // Cheap to get to current position from starting position, 
        // dont need to store all the positions each turn
        Position position = startingPosition;
        for(Move move:moves)
        {
            position.doMove(move);
        }
        return position;
    }

    public String getWhitePlayer()
    {
        return whitePlayer;
    }


    public String getBlackPlayer()
    {
        return blackPlayer;
    }


    public Colour getTurn()
    { 
        // Calculate the current player's move depending on how many moves where made and who moved first
        if(startingPosition.getTurn() == Colour.WHITE){
            return moves.size() % 2 == 0 ? Colour.WHITE : Colour.BLACK;
        }else{return moves.size() % 2 == 0 ? Colour.BLACK : Colour.WHITE;}
    }

    public void addMove(Move move)
    {
        moves.add(move);
    }

    


    public static void main(String[] args)
    {
        Game game = new StandardGame("w", "b");

        System.out.println("White Player: " + game.getWhitePlayer());
        System.out.println("Black Player: " + game.getBlackPlayer());

        Position startingPosition = game.getCurrentPosition();
        System.out.println("Starting Position:");
        System.out.println(game.getCurrentPosition());
        game.getCurrentPosition().printBoard();


       // Create a custom empty board position
        Piece[][] emptyBoard = new Piece[8][8]; // Create an empty board
        emptyBoard[0][1] = new Horse(Colour.WHITE);
        Position customPosition = new Position(emptyBoard, Colour.BLACK); // Initialize custom position
        System.out.println("Custom Position:");
        customPosition.printBoard();

        // Create a Puzzle game with the custom position
        Game puzzle = new Puzzle("White", "Black", customPosition);
        System.out.println("Puzzle Starting Position:");
        System.out.println(puzzle.startingPosition);
        puzzle.getStartingPosition().printBoard();
    }
}
