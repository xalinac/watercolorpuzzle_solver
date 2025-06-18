package com.example.puzzle_solver;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, представляющий состояние головоломки.
 * Хранит список пробирок и умеет генерировать следующие состояния.
 */
public class State {
    private final List<Tube> tubes;

    /**
     * Создаёт состояние на основе списка пробирок,
     * копируя каждую пробирку.
     * 
     * @param tubes список пробирок
     */
    public State(List<Tube> tubes) {
        this.tubes = new ArrayList<>();
        for (Tube tube : tubes) {
            this.tubes.add(new Tube(tube));
        }
    }

    /**
     * Возвращает копию списка пробирок состояния.
     * 
     * @return копия списка пробирок
     */
    public List<Tube> getTubes() {
        List<Tube> copy = new ArrayList<>();
        for (Tube tube : tubes) {
            copy.add(new Tube(tube));
        }
        return copy;
    }

    /**
     * Проверяет, достигнута ли целевая конфигурация.
     * Цель — все пробирки либо пусты, либо полностью заполнены каплями одного
     * цвета.
     * 
     * @return true, если состояние является целевым
     */
    public boolean isGoal() {
        for (Tube tube : tubes) {
            if (!tube.isEmpty() && (!tube.isUniform() || tube.getDrops().size() != tube.getCapacity())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Генерирует все возможные состояния, получающиеся после одного допустимого
     * хода (переливания).
     * 
     * @return список следующих состояний
     */
    public List<State> getNextStates() {
        List<State> nextStates = new ArrayList<>();

        for (int i = 0; i < tubes.size(); i++) {
            for (int j = 0; j < tubes.size(); j++) {
                if (i == j)
                    continue;

                Tube from = tubes.get(i);
                Tube to = tubes.get(j);

                if (from.canPourInto(to)) {
                    List<Tube> newTubes = new ArrayList<>();
                    for (Tube tube : tubes) {
                        newTubes.add(new Tube(tube));
                    }

                    int poured = newTubes.get(i).pourInto(newTubes.get(j));
                    if (poured > 0) {
                        nextStates.add(new State(newTubes));
                    }
                }
            }
        }

        return nextStates;
    }

    /**
     * Применяет ход к текущему состоянию и возвращает новое состояние.
     * 
     * @param move ход для применения
     * @return новое состояние после хода
     */
    public State applyMove(Move move) {
        List<Tube> newTubes = new ArrayList<>();
        for (Tube tube : tubes) {
            newTubes.add(new Tube(tube));
        }

        Tube from = newTubes.get(move.getFromIndex());
        Tube to = newTubes.get(move.getToIndex());

        int poured = from.pourInto(to);
        if (poured != move.getAmount()) {
            throw new IllegalStateException("Unexpected poured amount");
        }

        return new State(newTubes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof State))
            return false;

        State other = (State) o;
        return tubes.equals(other.tubes);
    }

    @Override
    public int hashCode() {
        return tubes.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tubes.size(); i++) {
            sb.append("Tube ").append(i).append(": ").append(tubes.get(i).toString()).append("\n");
        }
        return sb.toString();
    }
}