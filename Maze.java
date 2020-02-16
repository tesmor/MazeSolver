import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Stack;


/** 
Maze --- program to print and solve a maze, by creating an ArrayList of ArrayLists of MazeSqaure objects, and printing those MazeSqaures. 
@author Tessa Newman-Heggie
Last Modified 4/23/2018
 
*/

public class Maze { 
int numberOfColumns=0;
int numberOfRows= 0;
ArrayList<ArrayList<MazeSquare>> rowList = new ArrayList<ArrayList<MazeSquare>>();
    
/** 
The file is inputed as a text file that describes the maze dimensions, start location, 
finish location, and bottom and left walls of each row.
@param args A String array 
@exception exits program if no String inputed. 
@return no return value
*/
public static void main(String[] args) { 
    if (args.length < 1) { 
        System.err.println("Usage: java Maze mazeFile"); 
        System.exit(1); 
    } 
    Maze maze = new Maze(); 
    if (args.length==1){
        maze.load(args[0]); //loads and reads the file to create the Maze
        maze.printMaze();
    }
    else if((args.length>=2)&&(args[1].equals("--showsolution"))){
        maze.load(args[0]); //loads and reads the file to create the Maze
        maze.getSolution();
    }
     
    
    }

/**
empty constructor for Maze class
*/
 public Maze() { } 
    
/**creates a File object connected to the file inputed by the user, scans each line, then creates MazeSqaure objects, contained in mazeSqaureLists, with that information. 
@param fileName  the name of the text file to be scanned
*/
    
public void load(String fileName) { 
    String inputFilePath = fileName;
    File inputFile = new File(inputFilePath);

    Scanner scanner = null;
        try {
            scanner = new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            System.err.println(e);
            System.exit(1);
            }
        String dimesions = scanner.nextLine();
        setMazeDimensions(dimesions);
        String startLoc = scanner.nextLine();
        String finishLoc = scanner.nextLine();
       
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            ArrayList<MazeSquare> mazeSquareList = new ArrayList<MazeSquare>();
            
            //loops through a line of the file
            for (int n= 0; n<=numberOfColumns-1;n++){   
                boolean hasLeftWall = false;
                boolean hasBottomWall = false;
                
                char char1 = line.charAt(n);
                String c = Character.toString(char1);
                if (c.equals("L")){
                    hasLeftWall= true;
                    hasBottomWall=true;
                }
                else if (c.equals("|")){
                    hasLeftWall=true;
                }
                else if (c.equals("_")){
                    hasBottomWall=true;
                }
                /**creates a new MazeSqaure object with booleans based on the char 
                which describes that square, as read in the inputed file.
                */
                MazeSquare square = new MazeSquare(hasLeftWall, hasBottomWall);
               
                //adds newly created MazeSqaure to ArrayList MazeSquareList 
                mazeSquareList.add(square);
            }
            //adds new ArrayList of MazeSquares to ArrayList of ArrayLists rowList. 
            rowList.add(mazeSquareList);
        } 
            setStartLocation(startLoc);
            setFinishLocation(finishLoc);
}
/**
sets the # of columns and # of rows of the Maze. 
@param line  string of text from the inputed file that indicates the    
dimensions of the maze 
*/
public void setMazeDimensions(String line){
    String str = line;
    String[] splitStr = str.split("\\s+");
    numberOfColumns= Integer.parseInt(splitStr[0]);
    numberOfRows= Integer.parseInt(splitStr[1]);
    }
 /**
sets the start location of the maze, and sends that information to the MazeSqaure
so that it contains the start symbol. 
@param line  string of text from the inputed file that indicates the start 
coordinates of the maze 
*/   
public void setStartLocation(String line){
    String str = line;
    String[] splitStr = str.split("\\s+");
    int startColumn = Integer.parseInt(splitStr[0]);
    int startRow = Integer.parseInt(splitStr[1]);
    ArrayList<MazeSquare> firstThing = rowList.get(startRow);
    MazeSquare second= firstThing.get(startColumn);
    second.makeStart();                        
}
/**
sets the finish location of the maze, and sends that information to the MazeSqaure
so that it contains the finish symbol. 
@param line  string of text from the inputed file that indicates the finish 
coordinates of the maze 
*/  
public void setFinishLocation(String line){
    String str = line;
    String[] splitStr = str.split("\\s+");
    int finishColumn = Integer.parseInt(splitStr[0]);
    int finishRow = Integer.parseInt(splitStr[1]);
    rowList.get(finishRow).get(finishColumn).makeFinish();          
}
/**
prints the MazeSquares contained in the ArrayList of ArrayLists rowList. 
Also prints the top wall and right wall of the maze, because only left 
and bottom wall information is stored in MazeSqaure objects. 
*/
public void printMaze(){ 
    for(int c=0;c<numberOfColumns;c++){
        System.out.print("+-----");}
    System.out.println("+");
    for(int i = 0; i<numberOfRows;i++){ 
        /**
        each temp String will contain an entire line in a row of a maze.
        each row of the Maze should be 4 rows of strings high, and the Maze also
        has a top wall printed separately.
        */
        String temp1="";
        String temp2="";
        String temp3="";
        String temp4="";
        //adds a MazeSqaure to the four rows of strings that represent it
        for(int j= 0; j<numberOfColumns;j++){ 
            MazeSquare currCell = rowList.get(i).get(j);
            temp1= temp1+ currCell.printSquareLineOne();
            temp2= temp2+ currCell.printSquareLineTwo();
            temp3= temp3+ currCell.printSquareLineThree();
            temp4= temp4+ currCell.printSquareLineFour();
        }
        /**
        after all MazeSqaures in a row are added to temps, they 
        are printed, with the right wall of the maze 
        */
        System.out.println(temp1+ "|");
        System.out.println(temp2+"|");
        System.out.println(temp3+"|");
        System.out.println(temp4+"+");
    }
}
/** Computes and returns a solution to this maze. If there are multiple solutions, 
* only one is returned, and getSolution() makes no guarantees about which one. However, 
* the returned solution will not include visits to dead ends or any backtracks, even if 
* backtracking occurs during the solution process. 
* 
* @return a stack of MazeSquare objects containing the sequence of squares visited 
* to go from the start square (bottom of the stack) to the finish square (top of the stack). 
*/ 
    
public Stack<MazeSquare> getSolution(){
    Stack<MazeSquare> solutionSquares = new Stack<>();
    removeVisits();
    boolean solved = false;
    MazeSquare startSquare = findStart();
    MazeSquare currentSquare = startSquare;
   
    
   
    while(!solved){
        int[] indexes = getIndex(currentSquare);
        int i = indexes[0];
        int j = indexes[1];
        
        if (checkNorth(i,j)){
            currentSquare= rowList.get(i-1).get(j);
            solutionSquares.push(currentSquare);
            currentSquare.visit();
        }
        else if(checkSouth(i,j)){
            currentSquare= rowList.get(i+1).get(j);
            solutionSquares.push(currentSquare);
            currentSquare.visit();
        }
        else if (checkEast(i,j)){
            currentSquare= rowList.get(i).get(j+1);
            solutionSquares.push(currentSquare);
            currentSquare.visit();
        }
        else if (checkWest(i,j)){
            currentSquare= rowList.get(i).get(j-1);
            solutionSquares.push(currentSquare);
            currentSquare.visit();
        }
        else {
            
            solutionSquares.pop();
            currentSquare = solutionSquares.peek();
            System.out.println("this worked");
        }
        if (currentSquare.hasFinish()){
            solved= true;
            
        }
    }
    markSolution(solutionSquares);
    printMaze(); 
    return solutionSquares;
}  
/**
Finds the index of a MazeSquare in the format (rows, columns). 
@param MazeSquare object to find the index of
@returns int[] of the coordinates of that MazeSquare in the 2-d arrayList rowList
@returns null if MazeSquare is not in rowList
*/  
public int[] getIndex(MazeSquare currentSquare){
    for(int i = 0; i<numberOfRows;i++){ 
        for(int j= 0; j<numberOfColumns;j++){
            if ( rowList.get(i).get(j) == currentSquare)
            {int[] indexes = new int[]{i, j};
             return (indexes);
            }
        }
    }
    return null;
}
/** 
Finds the start in the maze by looping through the MazeSquares and searching for
the one that has a start.
*/
public MazeSquare findStart(){
    for(int i = 0; i<numberOfRows;i++){ 
        for(int j= 0; j<numberOfColumns;j++){
            MazeSquare currCell = rowList.get(i).get(j);
            if(currCell.hasStart()){
                return currCell;
            }
        }
    }
    return null;
}
/**
Unmarks the MazeSquares as having been visited by the solution finder.
*/  
public void removeVisits(){
    for(int i = 0; i<numberOfRows;i++){ 
        for(int j= 0; j<numberOfColumns;j++){
            MazeSquare currCell = rowList.get(i).get(j);
            currCell.unvisit();
            }
    }
}
/**
checks if the MazeSquare to the north of the given coordinates (i,j) exists, has an open bottom and is unvisited.
@param i integer that represents the row number of the current MazeSquare
@param j integer that represents the column number of the current MazeSquare
@returns true if square above the current sqaure can be travelled to
@returns false if square above current cannot be travelled to
*/
public boolean checkNorth(int i, int j){
    if (i>0){
        MazeSquare northCell = rowList.get(i-1).get(j);
        if (!northCell.hasBottomWall()&&!northCell.haveVisited()){
            return true;
        }
    }
    return false;
}
/**
checks if the MazeSquare to the south of the given coordinates (i,j) exists, has an open top and is unvisited.
@param i integer that represents the row number of the current MazeSquare
@param j integer that represents the column number of the current MazeSquare
@returns true if square below the current sqaure can be travelled to
@returns false if square below current cannot be travelled to
*/
public boolean checkSouth(int i, int j){
    if (i<numberOfRows-1){
        MazeSquare southCell = rowList.get(i+1).get(j);
        MazeSquare currentCell = rowList.get(i).get(j);
        if (!southCell.haveVisited()&&!currentCell.hasBottomWall()){
            return true;
        }
    }
    return false;
    }
/**
checks if the MazeSquare to the east of the given coordinates (i,j) exists, has an open left wall and is unvisited.
@param i integer that represents the row number of the current MazeSquare
@param j integer that represents the column number of the current MazeSquare
@returns true if square to the right the current sqaure can be travelled to
@returns false if square to the right current cannot be travelled to
*/
public boolean checkEast(int i, int j){
    if (j<numberOfColumns-1){
        MazeSquare eastCell = rowList.get(i).get(j+1);
        MazeSquare currentCell = rowList.get(i).get(j);
        if (!eastCell.haveVisited()&&!eastCell.hasLeftWall()){
            return true;
        }
    }
    return false;
}
/**
checks if the MazeSquare to the west of the given coordinates (i,j) exists, has an open right wall and is unvisited.
@param i integer that represents the row number of the current MazeSquare
@param j integer that represents the column number of the current MazeSquare
@returns true if square to the left of the current sqaure can be travelled to
@returns false if square to the left of current cannot be travelled to
*/
public boolean checkWest(int i, int j){
    if (j>0){
        MazeSquare currentCell = rowList.get(i).get(j);
        MazeSquare westCell = rowList.get(i).get(j-1);
        if (!currentCell.hasLeftWall()&&!westCell.haveVisited()){
            return true;}
    }
    return false;
}
/** 
Marks MazeSquares in solutionSqaures stack as part of the maze solution. 
*/
public void markSolution(Stack <MazeSquare> solutionSquares){
   // for (int i=0; i<solutionSquares.size();i++){
    while(!solutionSquares.empty()){
        MazeSquare currentSquare = solutionSquares.peek();
            currentSquare.makePartOfSolution();
            solutionSquares.pop();
    }
}
/** 
Unmarks MazeSquares in solutionSqaures stack as part of the maze solution. 
*/
public void removeSolution(){
    for(int i = 0; i<numberOfRows;i++){ 
        for(int j= 0; j<numberOfColumns;j++){
            MazeSquare currCell = rowList.get(i).get(j);
            currCell.removeSolution();
            }
        }
    }

}





