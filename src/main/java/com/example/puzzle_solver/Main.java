package com.example.puzzle_solver;

import java.util.ArrayList;
import java.util.List;

/**
 * Основной класс для запуска решения головоломки Colored Water.
 */
public class Main {

    private static final int CAPACITY = 4;

    public static void main(String[] args) {
        List<Tube> tubes = createTubes();

        State startState = new State(tubes);
        Heuristic heuristic = new Heuristic();
        IDAStarSolver solver = new IDAStarSolver(startState, heuristic);

        List<Move> solution = solver.solve();

        if (solution == null) {
            System.out.println("No solution found.");
        } else {
            System.out.println("Solved within " + solution.size() + " steps:");
            printSolution(solution);
        }
    }

    private static List<Tube> createTubes() {
        int[][] rawData = {
                { 4, 4, 10, 2 },
                { 8, 12, 8, 1 },
                { 9, 5, 7, 10 },
                { 5, 2, 3, 5 },
                { 7, 8, 11, 6 },
                { 2, 1, 12, 12 },
                { 11, 8, 7, 4 },
                { 1, 3, 11, 10 },
                { 9, 9, 7, 10 },
                { 11, 6, 2, 6 },
                { 3, 9, 6, 4 },
                { 1, 12, 3, 5 },
                { 0, 0, 0, 0 },
                { 0, 0, 0, 0 }
        };

        List<Tube> tubes = new ArrayList<>();
        for (int[] dropsArr : rawData) {
            List<Integer> drops = new ArrayList<>();
            for (int drop : dropsArr) {
                if (drop != 0) {
                    drops.add(drop);
                }
            }
            tubes.add(new Tube(CAPACITY, drops));
        }
        return tubes;
    }

    private static void printSolution(List<Move> solution) {
        int count = 0;
        for (Move move : solution) {
            System.out.printf("(%2d -> %2d) ", move.getFromIndex(), move.getToIndex());
            count++;
            if (count % 8 == 0) {
                System.out.println();
            }
        }
        if (count % 8 != 0) {
            System.out.println();
        }
    }
}