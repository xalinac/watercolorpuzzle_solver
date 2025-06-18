package com.example.puzzle_solver;

import java.util.Objects;

/**
 * Представляет один ход — переливание определённого количества капель
 * из одной пробирки в другую.
 */
public final class Move {
    private final int fromIndex;
    private final int toIndex;
    private final int amount;

    /**
     * Создаёт ход.
     * 
     * @param fromIndex индекс пробирки-источника
     * @param toIndex   индекс пробирки-получателя
     * @param amount    количество переливаемых капель
     */
    public Move(int fromIndex, int toIndex, int amount) {
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
        this.amount = amount;
    }

    /** @return индекс пробирки-источника */
    public int getFromIndex() {
        return fromIndex;
    }

    /** @return индекс пробирки-получателя */
    public int getToIndex() {
        return toIndex;
    }

    /** @return количество переливаемых капель */
    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        // Формат без слова Move для компактного вывода
        return String.format("(%d, %d)", fromIndex, toIndex);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Move))
            return false;
        Move move = (Move) o;
        return fromIndex == move.fromIndex &&
                toIndex == move.toIndex &&
                amount == move.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromIndex, toIndex, amount);
    }
}