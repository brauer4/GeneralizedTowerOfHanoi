package com.company;

//Author: Collin Brauer
//This is a program using recursion to solve the generalize version of Tower of Hanoi game using Frame Stewart algorithm
// Rules: Disks are transferred between pegs one at a time. At no time may a bigger disk be placed on top of a smaller one.
// For efficiency, the number of moves should always (2^n) - 1. n being the number of disks

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

//This is a class that is used to make a game object in order to dynamically cater the board to the player
//This class contains:
// constructor
// initialization
// The game algorithm
class HanoiGame {

    private int start;
    private int end;
    private int towersNum;
    private int diskNum;
    private Stack<Integer> towersStack;

    public HanoiGame(int start, int end, int diskNum ) {
        this.start = start;
        this.end = end;
        this.diskNum = diskNum;

        init ();
    }

    private void init () {
        towersNum = end;
        towersStack = new Stack<> ();

        for(int i=towersNum;i>=1;i--)
            if(i!=start && i!=end)
                towersStack.push(i);
        towersStack.push(end);
        towersStack.push(start);
    }

    //This is the algorithm to solve the game
    //Rules of Hanoi
    // First being(k = disks to be moved - 1)
    // Second being (k = number of towers - the first k)
    // Third being (k = the remaining k gathered from the second)
    // The stack in the parameters is going to be used to clone each step and make a new calculation
    private ArrayList<Point> hanoi(Stack<Integer> towerPegs, int diskMove, int towersUsed) {

        //base case for the recursion
        if(diskMove == 1){
            ArrayList<Point> base= new ArrayList<Point>();
            Point point=new Point();
            int t1 =towerPegs.peek();
            towerPegs.pop();
            int t2 =towerPegs.peek();
            towerPegs.pop();
            point.x=t1;
            point.y=t2;
            base.add(point);
            return base;
        }

        //calculation for number of disks to be moved
        int k;
        if(towersUsed<5)
            k=diskMove-1;
        else
            k= (int)((float)diskMove/2.0);

        int startPeg,targetPeg,tempPeg;
        //First movements
        //this manipulates the stack(game board) to shift the pegs to desired output
        ArrayList<Point> firstMoves ;
        Stack <Integer>first =  (Stack <Integer>) towerPegs.clone();
        startPeg = first.peek();
        first.pop();
        targetPeg= first.peek();
        first.pop();
        tempPeg = first.peek();
        first.pop();
        first.push(targetPeg);first.push(tempPeg);first.push(startPeg);
        firstMoves = (ArrayList<Point>) (hanoi(first,k,towersUsed)).clone();


        //Second movements
        ArrayList<Point> secondMoves ;
        Stack <Integer>second =  (Stack <Integer>) towerPegs.clone();
        startPeg = second.peek();
        second.pop();
        targetPeg = second.peek();
        second.pop();second.pop();
        second.push(targetPeg);second.push(startPeg);
        secondMoves = (ArrayList<Point>) (hanoi(second,diskMove-k,towersUsed-1)).clone();

        //Third set of moves
        ArrayList<Point> thirdMoves ;
        Stack <Integer>third = (Stack <Integer>) towerPegs.clone();
        startPeg = third.peek();
        third.pop();
        targetPeg = third.peek();
        third.pop();
        tempPeg  = third.peek();
        third.pop();
        third.push(startPeg);third.push(targetPeg);third.push(tempPeg);
        thirdMoves = (ArrayList<Point>) (hanoi(third,k,towersUsed)).clone();

        //This part of the program combines the 3 separate steps to combine into one solution for easier printing
        for (Point p : secondMoves) {
            firstMoves.add(p);
        }
        for(Point p2 : thirdMoves) {
            firstMoves.add(p2);
        }
        return firstMoves;
    }

    public void solve (int disk, int tower) {
        //recursive function that gets the arrayList of solutions from the algorithm
        ArrayList<Point> hanoiSolution = hanoi(towersStack,disk,tower);

        //display the movements for the game
        for(Point solp : hanoiSolution)
            System.out.println(solp.x + "->"+solp.y);
    }
}


public class TowerHanoi {
    public static int disk;
    public static int tower;

    //simple welcome function to accept the input from the console
    public static void welcome(){

        Scanner input = new Scanner(System.in);
        System.out.printf("Welcome to Tower of Hanoi!\n");
        System.out.println("To get started please input the number of disks and number of peg 'towers'.");
        System.out.println("Please enter number of disks: ");
        disk = input.nextInt();
        if(disk < 3 || disk > 40 ){
            System.out.println("Number of disk has to be in the range of [3,40]. Try again.");
            System.out.println("Please enter number of disks: ");
            disk = input.nextInt();
        }
        System.out.println("Please enter number of towers: ");
        tower = input.nextInt();
        if(tower < 5 || tower > 10){
            System.out.println("Number of towers has to be in the range of [5,10]. Try again.");
            System.out.println("Please enter number of towers: ");
            tower = input.nextInt();
        }
        System.out.println("Disks: " + disk + " Towers: " + tower);
        input.close();
    }

    public static void main ( String[] args ) {

        welcome();
        HanoiGame obj = new HanoiGame( 1, tower, disk ); //start, end, diskNum
        obj.solve (disk,tower);

    }
}
