import java.io.File;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
//        File file = new File("d-10-01.txt.txt");
//        Grid grid = new Grid(file);
//        grid.loadBoard();
//        grid.printBoard();
//        Player play = new Player(grid);
//        play.fillMap();
//        play.forwardChecking(play.nextCellUsingDomDeg(), 2);
//        grid.printBoard();
//        System.out.println(play.numberOfNodes);
//        System.out.println(play.numberOfBacktracks);

        Scanner sc = new Scanner(System.in);
        boolean isTrue = true;
        while (isTrue)
        {
            File file = new File("d-15-01.txt.txt");
            Grid grid = new Grid(file);
            grid.loadBoard();


            System.out.println("1. Simple Backtracking + SDF");
            System.out.println("2. Simple Backtracking + DomDeg");
            System.out.println("3. Simple Backtracking + Brelaz");
            System.out.println("4. Forward Checking + SDF");
            System.out.println("5. Forward Checking + DomDeg");
            System.out.println("6. Forward Checking + Brelaz");
            System.out.println("7. Exit");
            int choice = sc.nextInt();

            if (choice == 1)
            {
                grid.printBoard();
                Player play = new Player(grid);
                play.fillMap();
                play.backtrack(play.nextCellUsingSDF(),1);
                grid.printBoard();
                System.out.println(play.numberOfNodes);
                System.out.println(play.numberOfBacktracks);
                grid.emptyBoard();
            }
            else if (choice == 2)
            {
                grid.printBoard();
                Player play = new Player(grid);
                play.fillMap();
                play.backtrack(play.nextCellUsingDomDeg(),2);
                grid.printBoard();
                System.out.println(play.numberOfNodes);
                System.out.println(play.numberOfBacktracks);
                grid.emptyBoard();
            }
            else if (choice == 3)
            {
                grid.printBoard();
                Player play = new Player(grid);
                play.fillMap();
                play.backtrack(play.nextCellUsingBrelaz(),3);
                grid.printBoard();
                System.out.println(play.numberOfNodes);
                System.out.println(play.numberOfBacktracks);
                grid.emptyBoard();
            }
            else if (choice == 4)
            {
                grid.printBoard();
                Player play = new Player(grid);
                play.fillMap();
                play.forwardChecking(play.nextCellUsingSDF(),1);
                grid.printBoard();
                System.out.println(play.numberOfNodes);
                System.out.println(play.numberOfBacktracks);
                grid.emptyBoard();
            }
            else if (choice == 5)
            {
                grid.printBoard();
                Player play = new Player(grid);
                play.fillMap();
                play.forwardChecking(play.nextCellUsingDomDeg(),2);
                grid.printBoard();
                System.out.println(play.numberOfNodes);
                System.out.println(play.numberOfBacktracks);
                grid.emptyBoard();
            }
            else if (choice == 6)
            {
                grid.printBoard();
                Player play = new Player(grid);
                play.fillMap();
                play.forwardChecking(play.nextCellUsingBrelaz(),3);
                grid.printBoard();
                System.out.println(play.numberOfNodes);
                System.out.println(play.numberOfBacktracks);
                grid.emptyBoard();
            }
            else if (choice == 7)
            {
                isTrue = false;
            }
            else
            {
                System.out.println("Enter a valid number.");
            }


        }




    }
    }
