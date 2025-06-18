package com.example.puzzle_solver;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Класс, представляющий пробирку с каплями краски.
 */
public class Tube {

    private final List<Integer> drops;
    private final int capacity;

    /**
     * Создает пустую пробирку с указанной вместимостью.
     *
     * @param capacity вместимость пробирки
     */
    public Tube(final int capacity) {
        this.capacity = capacity;
        this.drops = new ArrayList<>();
    }

    /**
     * Создает пробирку с указанной вместимостью и заданными каплями.
     *
     * @param capacity вместимость пробирки
     * @param drops    список капель (цветов)
     */
    public Tube(final int capacity, final List<Integer> drops) {
        this.capacity = capacity;
        this.drops = new ArrayList<>(drops);
    }

    /**
     * Копирующий конструктор — создаёт глубокую копию другой пробирки.
     *
     * @param other пробирка для копирования
     */
    public Tube(final Tube other) {
        this.capacity = other.capacity;
        this.drops = new ArrayList<>(other.drops);
    }

    /**
     * Проверяет, пустая ли пробирка.
     * 
     * @return true, если пробирка пуста
     */
    public boolean isEmpty() {
        return drops.isEmpty();
    }

    /**
     * Возвращает вместимость пробирки.
     *
     * @return вместимость
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Проверяет, заполнена ли пробирка полностью.
     *
     * @return true, если пробирка полная
     */
    public boolean isFull() {
        return drops.size() == capacity;
    }

    /**
     * Проверяет, однородна ли пробирка — все капли одного цвета.
     * Пустая пробирка считается однородной.
     *
     * @return true, если однородна или пуста
     */
    public boolean isUniform() {
        if (isEmpty()) {
            return true;
        }
        final int color = drops.get(0);
        for (final Integer drop : drops) {
            if (!Objects.equals(drop, color)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Возвращает цвет верхней капли или null, если пробирка пуста.
     *
     * @return цвет верхней капли или null
     */
    public Integer topColor() {
        if (isEmpty()) {
            return null;
        }
        return drops.get(drops.size() - 1);
    }

    /**
     * Считает количество верхних капель одного цвета подряд.
     *
     * @return количество капель сверху одного цвета
     */
    public int countTopSameColor() {
        if (isEmpty()) {
            return 0;
        }
        final Integer color = topColor();
        int count = 0;
        for (int i = drops.size() - 1; i >= 0; i--) {
            if (Objects.equals(drops.get(i), color)) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    /**
     * Считает количество переходов между цветами в пробирке.
     * 
     * Например, для [1,1,2,2,3] возвращает 2 (переходы 1→2 и 2→3).
     *
     * @return число переходов между цветами
     */
    public int countDifferentColors() {
        if (drops.isEmpty()) {
            return 0;
        }
        int changes = 0;
        int prevColor = drops.get(0);
        for (int i = 1; i < drops.size(); i++) {
            if (drops.get(i) != prevColor) {
                changes++;
                prevColor = drops.get(i);
            }
        }
        return changes;
    }

    /**
     * Проверяет возможность переливания в другую пробирку.
     *
     * @param other пробирка-цель
     * @return true, если можно перелить
     */
    public boolean canPourInto(final Tube other) {
        if (isEmpty() || other.isFull()) {
            return false;
        }
        final Integer fromColor = topColor();
        final Integer toColor = other.topColor();

        return other.isEmpty() || Objects.equals(fromColor, toColor);
    }

    /**
     * Переливает капли из этой пробирки в другую.
     *
     * @param other пробирка-цель
     * @return количество перелитых капель
     */
    public int pourInto(final Tube other) {
        if (!canPourInto(other)) {
            return 0;
        }
        final Integer color = topColor();
        final int movable = countTopSameColor();
        final int freeSpace = other.capacity - other.drops.size();
        final int amount = Math.min(movable, freeSpace);

        for (int i = 0; i < amount; i++) {
            this.drops.remove(drops.size() - 1);
            other.drops.add(color);
        }
        return amount;
    }

    /**
     * Возвращает копию списка капель.
     *
     * @return список капель
     */
    public List<Integer> getDrops() {
        return new ArrayList<>(drops);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Tube))
            return false;
        final Tube other = (Tube) obj;
        return capacity == other.capacity && drops.equals(other.drops);
    }

    @Override
    public int hashCode() {
        return Objects.hash(capacity, drops);
    }

    @Override
    public String toString() {
        return drops.toString();
    }
}