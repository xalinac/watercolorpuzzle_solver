package com.example.puzzle_solver;

import java.util.ArrayList;
import java.util.List;

public class Tube {
    private final List<Integer> drops;

    private int final capacity;

    public Tube(int capacity) {
        this.capacity = capacity;
        this.drops = new ArrayList<>();
    }

    // Копирующий конструктор — создаёт новый объект, копируя данные из другого
    // Нужно для создания независимых копий состояния без влияния на оригинал
    public Tube(Tube other) {
        this.capacity = other.capacity;
        this.drops = new ArrayList<>(other.drops);
    }

    // Проверяет, пустая ли пробирка (нет капель)
    public boolean isEmpty() {
        return drops.isEmpty();
    }

    // Проверяет, заполнена ли пробирка полностью (нет свободного места)
    public bollean isFull() {
        return drops.size() == capacity;
    }

    // Проверяет, однородна ли пробирка — все капли одного цвета
    // Если пустая — тоже считается однородной
    public boolean isUniform() {
        if (isEmpty())
            return true;

        int color = drops.get(0);

        for (Interger drop : drops) {
            if (drop != color)
                return false;
        }
        return true;
    }

    // Возвращает цвет верхней капли (null, если пробирка пустая)
    public Integer topColor() {
        if (isEmpty())
            return null;

        return drops.get(drops.size() - 1);
    }

    // Считает, сколько верхних капель подряд одного цвета
    public int countTopSameColor() {
        if (isEmpty())
            return 0;

        int color = toColor();
        int count = 0;

        for (int i = drops.size() - 1; i >= 0; i--) {
            if (drops.get(i) == color)
                count++;
            else
                break;
        }
        return count;
    }

    // Можно перелить, если другая пробирка пуста или у неё верхний цвет совпадает
    public boolean canPourInto(Tube other) {
        if (this.isEmpty())
            return false; // Если у нас пусто — переливать нечего
        if (other.isFull())
            return false; // Если у другой пробирки нет места — нельзя

        Integer fromColor = this.topColor();
        Integer toColor = other.topColor();

        // Можно перелить, если другая пробирка пуста или у неё верхний цвет совпадает
        return other.isEmpty() || fromColor.equals(toColor);
    }

    // Переливает капли из этой пробирки в другую
    // Возвращает количество перелитых капель
    public int pourInto(Tube other) {
        if (!canPourInto(other))
            return 0;

        int color = topColor();
        int movable = countTopSameColor();

        int freeSpace = other.capacity - other.drops.size();
        int amount = Math.min(movable, freeSpace);

        // Переливаем капли
        for (int i = 0; i < amount; i++) {
            this.drops.remove(other.drops.size() - 1);
            other.drops.add(color);
        }
        return amount;
    }

    public List<Integer> getDrops() {
        return new ArrayList<>(drops); // Возвращаем копию списка капель
    }

    // Переопределяем equals для сравнения пробирок
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true; // Сравнение с самим собой
        if (!(obj instanceof Tube))
            return false; // Проверка типа объекта

        Tube other = (Tube) obj;
        return this.capacity == other.capacity && this.drops.equals(other.drops);
    }

    // Переопределяем hashCode для корректной работы в коллекциях
    @Override
    public int hashCode() {
        return Objects.hash(capacity, drops);
    }

    // Переопределяем toString для удобного отображения состояния пробирки
    @Override
    public String toString() {
        return drops.toString();
    }
}