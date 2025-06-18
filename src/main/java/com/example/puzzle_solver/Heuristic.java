package com.example.puzzle_solver;

import java.util.List;

/**
 * Класс эвристики для оценки состояния головоломки.
 * Эвристика складывает для каждой пробирки сумму:
 * - количество капель,
 * - количество разных цветов,
 * - количество пустых мест.
 * Цель — приблизительно оценить "сложность" данного состояния.
 */
public class Heuristic {

    /**
     * Оценивает заданное состояние и возвращает числовую оценку.
     *
     * @param state текущее состояние головоломки
     * @return оценка стоимости состояния (чем выше, тем хуже)
     */
    public int estimate(State state) {
        int totalCost = 0;

        List<Tube> tubes = state.getTubes();

        for (Tube tube : tubes) {
            if (tube.isEmpty() || tube.isUniform()) {
                // Пустые или однородные пробирки считаем "идеальными" — без стоимости
                continue;
            }

            int dropsCount = tube.getDrops().size();
            int differentColors = tube.countDifferentColors();
            int capacity = tube.getCapacity();
            int emptySpaces = capacity - dropsCount;

            // Стоимость оцениваем как сумму: занятые + разные цвета + пустые
            int tubeCost = dropsCount + differentColors + emptySpaces;

            totalCost += tubeCost;
        }

        return totalCost;
    }
}