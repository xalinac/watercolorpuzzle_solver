package com.example.puzzle_solver;

import java.util.*;

/**
 * Решатель головоломки методом IDA*.
 */
public class IDAStarSolver {
    private final State startState;
    private final Heuristic heuristic;

    public IDAStarSolver(State startState, Heuristic heuristic) {
        this.startState = startState;
        this.heuristic = heuristic;
    }

    /**
     * Запускает поиск решения.
     * 
     * @return список ходов для решения или null, если решения нет
     */
    public List<Move> solve() {
        int threshold = heuristic.estimate(startState);
        List<Move> path = new ArrayList<>();
        Set<State> visited = new HashSet<>();

        while (true) {
            int temp = search(startState, 0, threshold, path, visited);
            if (temp == -1) {
                return path; // решение найдено
            }
            if (temp == Integer.MAX_VALUE) {
                return null; // решения нет
            }
            threshold = temp; // увеличиваем порог
            visited.clear();
        }
    }

    /**
     * Рекурсивный поиск с ограничением по порогу f(n).
     */
    private int search(State state, int g, int threshold, List<Move> path, Set<State> visited) {
        int f = g + heuristic.estimate(state);
        if (f > threshold) {
            return f;
        }
        if (state.isGoal()) {
            return -1;
        }

        visited.add(state);
        int min = Integer.MAX_VALUE;

        for (State nextState : state.getNextStates()) {
            if (visited.contains(nextState)) {
                continue;
            }
            Move move = findMove(state, nextState);
            if (move == null) {
                continue;
            }
            path.add(move);

            int temp = search(nextState, g + 1, threshold, path, visited);
            if (temp == -1) {
                return -1;
            }
            if (temp < min) {
                min = temp;
            }
            path.remove(path.size() - 1);
        }

        visited.remove(state);
        return min;
    }

    /**
     * Определяет ход, который приводит из состояния from в to.
     */
    private Move findMove(State from, State to) {
        List<Tube> fromTubes = from.getTubes();
        List<Tube> toTubes = to.getTubes();

        // Находим пробирки, которые изменились
        List<Integer> changedIndices = new ArrayList<>();
        for (int i = 0; i < fromTubes.size(); i++) {
            if (!fromTubes.get(i).equals(toTubes.get(i))) {
                changedIndices.add(i);
            }
        }

        if (changedIndices.size() != 2) {
            return null; // Обычно изменение происходит между двумя пробирками
        }

        int i = changedIndices.get(0);
        int j = changedIndices.get(1);

        Tube fromBeforeI = fromTubes.get(i);
        Tube fromAfterI = toTubes.get(i);
        Tube fromBeforeJ = fromTubes.get(j);
        Tube fromAfterJ = toTubes.get(j);

        int pouredIJ = calculatePouredAmount(fromBeforeI, fromAfterI, fromBeforeJ, fromAfterJ);
        if (pouredIJ > 0) {
            return new Move(i, j, pouredIJ);
        }

        int pouredJI = calculatePouredAmount(fromBeforeJ, fromAfterJ, fromBeforeI, fromAfterI);
        if (pouredJI > 0) {
            return new Move(j, i, pouredJI);
        }

        return null;
    }

    /**
     * Вычисляет количество перелитых капель между состояниями двух пробирок.
     */
    private int calculatePouredAmount(Tube fromBefore, Tube fromAfter, Tube toBefore, Tube toAfter) {
        int beforeFromSize = fromBefore.getDrops().size();
        int afterFromSize = fromAfter.getDrops().size();

        int beforeToSize = toBefore.getDrops().size();
        int afterToSize = toAfter.getDrops().size();

        if (beforeFromSize > afterFromSize && beforeToSize < afterToSize) {
            return beforeFromSize - afterFromSize;
        }
        return 0;
    }
}