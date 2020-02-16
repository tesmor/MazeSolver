/**
MazeSqaure -----  Creates MazeSqaure objects 
intended for use with class Maze. 
@author Tessa Newman-Heggie
Last Modified 4/23/2018

*/


public class MazeSquare {  
    
    private boolean hasStart;
    private boolean hasFinish;
    private boolean hasLeft;
    private boolean hasBottom;
    private String leftWall = "|     ";
    private String noWall = "      ";
    private String finishLeftWall = "|  F  ";
    private String finishNoWall = "   F  ";
    private String startLeftWall = "|  S  ";
    private String startNoWall = "   S  ";
    private String solutionLeftWall = "|  *  ";
    private String solutionNoWall = "   *  ";
    private boolean visited = false;
    private boolean partOfSolution = false;

/**creates a MazeSqaure object
@param leftWall   boolean indicating if MazeSqaure has a left wall
@param bottomWall boolean indicating if MazeSqaure has bottom wall
*/
 public MazeSquare(boolean leftWall, boolean bottomWall){
        hasLeft= leftWall;
        hasBottom= bottomWall;
        hasStart =  false; 
        hasFinish= false;
}
/**
@returns a boolean indicating if the MazeSqaure has a bottom wall
*/
public boolean hasBottomWall() { 
            return hasBottom;
}
/**
@returns a boolean indicating if the MazeSqaure has a left wall
*/
public boolean hasLeftWall() { 
           return hasLeft; 
}
/**
sets a left wall in a MazeSqaure
*/
public void makeLeft(){
        hasLeft = true;
}
/**
sets the bottom wall in a MazeSqaure
*/
public void makeBottom(){
        hasBottom = true;
}
/**
@returns a boolean indicating if the MazeSqaure contains the start of the maze
*/
public boolean hasStart() { 
        return hasStart;  
}
/**
@returns a boolean indicating if the MazeSqaure contains the finish of the maze
*/
public boolean hasFinish() { 
        return hasFinish;  
}
/**
sets the start location of the maze in a MazeSqaure
*/
public void makeStart(){
        hasStart = true;
}
/**
sets the finish location of the maze in a MazeSqaure
*/
public void makeFinish(){
        hasFinish = true;
}
/**
marks a MazeSqaure as visited by the solution finder in Maze
*/
public void visit(){
    visited = true;
}
/**
marks a MazeSqaure as unvisited by the solution finder in Maze
*/
public void unvisit(){
    visited = false;
}
/**
@returns a boolean indicating if this MazeSqaure has been visited 
by the solution finder
*/
public boolean haveVisited(){
    return visited;
}

/**
marks a MazeSwaure as part of the solution path
*/
public void makePartOfSolution(){
    partOfSolution = true;
}
/**
unmarks a MazeSwaure as part of the solution path
*/
public void removeSolution(){
    partOfSolution = false;
}

/**
@returns a boolean indicating if this MazeSqaure is part of the solution
*/
public boolean partOfSolution(){
    return partOfSolution;
}
/**
@returns the first line of text in a MazeSquare. Does not include top walls. 
*/
public String printSquareLineOne(){
        if(hasLeftWall()){return(leftWall);}
        else{return(noWall);}
}

/**
@returns the second line of text in a MazeSquare, that contains the start and finish 
if hasStart() or hasFinish() is true for that MazeSqaure, and the solution 
marking if that MazeSquare is part of the solution.
*/
public String printSquareLineTwo(){
       if (hasStart){
            if(hasLeftWall()){return(startLeftWall);}
            else{return(startNoWall);}
       }
        else if (hasFinish){
            if(hasLeftWall()){return(finishLeftWall);}
            else{return(finishNoWall); }
        }
        else if (partOfSolution){
            if(hasLeftWall()){return(solutionLeftWall);}
            else{return(solutionNoWall);}
        }
        else {
            if(hasLeftWall()){return(leftWall);}
            else{return(noWall);}
        }
}
/**
@returns the third line of text in a MazeSquare. 
*/
public String printSquareLineThree(){   
        if(hasLeftWall()){return(leftWall);}
        else{return(noWall);}
}
/**
@returns the final line of text in a MazeSquare, which contains a bottom wall
if hasBottomWall() returns true for that MazeSquare 
*/
public String printSquareLineFour(){
        if (hasBottomWall()){return("+-----");}
        else{return("+     ");}
    }
}
    
    
