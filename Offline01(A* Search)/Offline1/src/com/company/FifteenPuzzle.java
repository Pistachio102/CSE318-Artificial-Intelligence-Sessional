package com.company;

import java.io.File;
import java.util.*;

public class FifteenPuzzle {

    private class TilePosition {
        int x;
        int y;

        TilePosition(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }

    private int[][] tiles;
    private TilePosition blankTile;

    private FifteenPuzzle() {
        tiles = new int[4][4];
        int count = 1;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tiles[i][j] = count;
                count++;
            }
        }

        blankTile = new TilePosition(4 - 1, 4 - 1);
        tiles[blankTile.x][blankTile.y] = 0;
    }

    private FifteenPuzzle(int[][] t) {
        tiles = t;
        OuterLoop:
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (tiles[i][j] == 0) {
                    blankTile = new TilePosition(i, j);
                    break OuterLoop;
                }
            }
        }

    }

    private FifteenPuzzle(FifteenPuzzle toClone) {
        this();  // chain to basic init
        for (TilePosition tilePosition : getAllTilePositions()) {
            tiles[tilePosition.x][tilePosition.y] = toClone.getTileValue(tilePosition);
        }
        blankTile = toClone.getBlankTile();
    }

    private final static FifteenPuzzle GOAL_STATE = new FifteenPuzzle();

    private List<TilePosition> getAllTilePositions() {
        ArrayList<TilePosition> tilePositions = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tilePositions.add(new TilePosition(i, j));
            }
        }
        return tilePositions;
    }


    private int getTileValue(TilePosition tilePosition) {
        return tiles[tilePosition.x][tilePosition.y];
    }


    private TilePosition getBlankTile() {
        return blankTile;
    }

    private TilePosition tilePositionForTileValue(int value) {
        for (TilePosition tilePosition : getAllTilePositions()) {
            if (getTileValue(tilePosition) == value) {
                return tilePosition;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof FifteenPuzzle) {
            for (TilePosition tilePosition : getAllTilePositions()) {
                if (this.getTileValue(tilePosition) != ((FifteenPuzzle) o).getTileValue(tilePosition)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }


    @Override
    public int hashCode() {
        int hashCodeResult = 0;
        for (TilePosition tilePosition : getAllTilePositions()) {
            hashCodeResult = (hashCodeResult * 10) + this.getTileValue(tilePosition);
        }
        return hashCodeResult;
    }


    private List<TilePosition> allValidMoves() {
        ArrayList<TilePosition> tilePositions = new ArrayList<>();
        for (int dx = -1; dx < 2; dx++) {
            for (int dy = -1; dy < 2; dy++) {
                TilePosition tilePosition = new TilePosition(blankTile.x + dx, blankTile.y + dy);
                if (isValidMove(tilePosition)) {
                    tilePositions.add(tilePosition);
                }
            }
        }
        return tilePositions;
    }


    private boolean isValidMove(TilePosition p) {
        if ((p.x < 0) || (p.x >= 4)) {
            return false;
        }
        if ((p.y < 0) || (p.y >= 4)) {
            return false;
        }
        int dx = blankTile.x - p.x;
        int dy = blankTile.y - p.y;
        return (Math.abs(dx) + Math.abs(dy) == 1) && (dx * dy == 0);
    }


    private void performMove(TilePosition tilePosition) {
        if (!isValidMove(tilePosition)) {
            throw new RuntimeException("Invalid performMove");
        }
        if (tiles[blankTile.x][blankTile.y] == 0) {
            tiles[blankTile.x][blankTile.y] = tiles[tilePosition.x][tilePosition.y];
            tiles[tilePosition.x][tilePosition.y] = 0;
            blankTile = tilePosition;
        }
    }

    private FifteenPuzzle performMoveAndProduceANewState(TilePosition tilePosition) {
        FifteenPuzzle out = new FifteenPuzzle(this);
        out.performMove(tilePosition);
        return out;
    }

    private int numberOfMisplacedTiles() {
        int result = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if ((tiles[i][j] > 0) && (tiles[i][j] != GOAL_STATE.tiles[i][j])) {
                    result++;
                }
            }
        }
        return result;
    }

    private int manhattanDistance() {
        int result = 0;
        for (TilePosition tilePosition : getAllTilePositions()) {
            int tileValue = getTileValue(tilePosition);
            if (tileValue > 0) {
                TilePosition correct = GOAL_STATE.tilePositionForTileValue(tileValue);
                if (correct != null) {
                    result += Math.abs(correct.x - tilePosition.x);
                    result += Math.abs(correct.y - tilePosition.y);
                }
            }
        }
        return result;
    }

    private boolean isSolved(String heuristicInitials) {
        if ("mt".equalsIgnoreCase(heuristicInitials)) {
            return numberOfMisplacedTiles() == 0;
        } else if ("md".equalsIgnoreCase(heuristicInitials)) {
            return manhattanDistance() == 0;
        }
        return false;
    }


    private List<FifteenPuzzle> allAdjacentPuzzles() {
        ArrayList<FifteenPuzzle> fifteenPuzzles = new ArrayList<>();
        for (TilePosition move : allValidMoves()) {
            fifteenPuzzles.add(performMoveAndProduceANewState(move));
        }
        return fifteenPuzzles;
    }


    private void show() {
        System.out.println("---------------------");
        for (int i = 0; i < 4; i++) {
            System.out.print("| ");
            for (int j = 0; j < 4; j++) {
                int value = tiles[i][j];
                StringBuilder stringBuilder;
                if (value > 0) {
                    if (value < 10) {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("0");
                        stringBuilder.append(value);
                    } else {
                        stringBuilder = new StringBuilder(Integer.toString(value));
                    }
                } else {
                    stringBuilder = new StringBuilder();
        }
        while (stringBuilder.length() < 3) {
            stringBuilder.append(" ");
        }
        stringBuilder.append("| ");
        System.out.print(stringBuilder);
    }
            System.out.println();
        }
        System.out.print("---------------------\n\n");
    }


    private List<FifteenPuzzle> aStarSolve(String heuristicInitials) {
        HashMap<FifteenPuzzle, FifteenPuzzle> predecessorHashMap = new HashMap<>();
        HashMap<FifteenPuzzle, Integer> depthHashMap = new HashMap<>();
        final HashMap<FifteenPuzzle, Integer> costHashMap = new HashMap<>();
        Comparator<FifteenPuzzle> comparator = Comparator.comparingInt(costHashMap::get);
        PriorityQueue<FifteenPuzzle> fifteenPuzzlePriorityQueue = new PriorityQueue<>(10000, comparator);

        predecessorHashMap.put(this, null);
        depthHashMap.put(this, 0);
        if ("mt".equalsIgnoreCase(heuristicInitials)) {
            costHashMap.put(this, this.numberOfMisplacedTiles());
        } else if ("md".equalsIgnoreCase(heuristicInitials)) {
            costHashMap.put(this, this.manhattanDistance());
        }
        fifteenPuzzlePriorityQueue.add(this);
        int count = 0;
        while (fifteenPuzzlePriorityQueue.size() > 0) {
            FifteenPuzzle candidate = fifteenPuzzlePriorityQueue.remove();
            count++;

            if (candidate.isSolved(heuristicInitials)) {
                System.out.println("Cost of calculating solution was " + count + " nodes");
                LinkedList<FifteenPuzzle> solution = new LinkedList<>();
                FifteenPuzzle backtrace = candidate;
                while (backtrace != null) {
                    solution.addFirst(backtrace);
                    backtrace = predecessorHashMap.get(backtrace);
                }
                return solution;
            }
            for (FifteenPuzzle fifteenPuzzle : candidate.allAdjacentPuzzles()) {
                if (!predecessorHashMap.containsKey(fifteenPuzzle)) {
                    predecessorHashMap.put(fifteenPuzzle, candidate);
                    depthHashMap.put(fifteenPuzzle, depthHashMap.get(candidate) + 1);
                    int cost = 0;
                    if ("mt".equalsIgnoreCase(heuristicInitials)) {
                        cost = fifteenPuzzle.numberOfMisplacedTiles();
                    } else if ("md".equalsIgnoreCase(heuristicInitials)) {
                        cost = fifteenPuzzle.manhattanDistance();
                    }
                    costHashMap.put(fifteenPuzzle, depthHashMap.get(candidate) + 1 + cost);
                    fifteenPuzzlePriorityQueue.add(fifteenPuzzle);
                }
            }
        }
        return null;
    }

    private static void showSolution(List<FifteenPuzzle> solution) {

        System.out.println("All " + solution.size() + " steps are shown below:");
        for (FifteenPuzzle fifteenPuzzle : solution) {
            fifteenPuzzle.show();
        }

    }

    private boolean isSolvable(int[] puzzle) {
        int parity = 0;
        int gridWidth = (int) Math.sqrt(puzzle.length);
        int row = 0; // the current row we are on
        int blankRow = 0; // the row with the blankTile getTileValue

        for (int i = 0; i < puzzle.length; i++) {
            if (i % gridWidth == 0) { // advance to next row
                row++;
            }
            if (puzzle[i] == 0) { // the blankTile getTileValue
                blankRow = row; // save the row on which encountered
                continue;
            }
            for (int j = i + 1; j < puzzle.length; j++) {
                if (puzzle[i] > puzzle[j] && puzzle[j] != 0) {
                    parity++;
                }
            }
        }

        if (gridWidth % 2 == 0) { // even grid
            if (blankRow % 2 == 0) { // blankTile on odd row; counting from bottom
                return parity % 2 == 0;
            } else { // blankTile on even row; counting from bottom
                return parity % 2 != 0;
            }
        } else { // odd grid
            return parity % 2 == 0;
        }
    }


    public static void main(String[] args) throws Exception {
        int[][] puzzledTiles = new int[4][4];
        int[] checkPuzzle = new int[16];
        int i = 0, j = 0;

        File file = new File("G:\\BUET Academic\\L3-T2\\AI Sessional\\Offline1\\Input.txt");
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            puzzledTiles[i][j] = Integer.parseInt(sc.nextLine());
            System.out.println(puzzledTiles[i][j]);
            j++;
            if (j > 3) {
                i++;
                j = 0;
            }
        }

        int p = 0;
        for (int a = 0; a < 4; a++) {
            for (int b = 0; b < 4; b++) {
                checkPuzzle[p++] = puzzledTiles[a][b];
            }

        }

        List<FifteenPuzzle> solution;
        FifteenPuzzle puzzle = new FifteenPuzzle(puzzledTiles);

        if (puzzle.isSolvable(checkPuzzle)) {
            System.out.println("The puzzle is solvable.");

            System.out.println("Solution using misplaced tiles as heuristic :");
            solution = puzzle.aStarSolve("mt");
            if (solution != null) {
                showSolution(solution);
            }

            System.out.println("Solution using manhattan distance as heuristic :");
            solution = puzzle.aStarSolve("md");
            if (solution != null) {
                showSolution(solution);
            }
        } else System.out.println("The puzzle can't be solved.");


    }
}
