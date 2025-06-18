# Water Puzzle Solver

Программа решает головоломку "Сортировка жидкостей" (Water Sort Puzzle).

## Описание задачи

Дано N пробирок одинакового объема V, заполненных разноцветными каплями жидкости. Цель — отсортировать жидкости так, чтобы каждая пробирка содержала капли одного цвета или была пустой, переливая жидкости по определённым правилам.

## Правила

- За один ход можно перелить капли из одной пробирки в другую.
- Переливка возможна только если:
  - В пробирке-источнике есть жидкость.
  - В пробирке-приёмнике есть свободное место.
  - Переливаются только верхние капли одного цвета.
  - Приёмник либо пуст, либо верхняя капля того же цвета.
  - Можно перелить все верхние капли одного цвета или столько, сколько поместится.

## Запуск

Проект использует Maven.
### Windows
[Скачать Maven](https://maven.apache.org/download.cgi)
```
choco install maven
```
или
```
scoop install main/maven
```
### macOS / Linux
```
brew install maven
```

<<<<<<< HEAD
### Windows

[Скачать Maven](https://maven.apache.org/download.cgi)

```
choco install maven
```

или

```
scoop install main/maven
```

### macOS / Linux

```
brew install maven
```

1. Скачайте репозиторий:

   ### Windows

   ```bash
   git clone https://github.com/xalinac/watercolorpuzzle_solver &&
   cd watercolorpuzzle_solver
   ```

   ### macOS / Linux

=======

1. Скачайте репозиторий:

   ### Windows
    ```bash
    git clone https://github.com/xalinac/watercolorpuzzle_solver &&
    cd watercolorpuzzle_solver
    ```
   ### macOS / Linux
>>>>>>> 4e6e81c70047f7825a25d706089fdde0cf6397c9
   ```
   git clone https://github.com/xalinac/watercolorpuzzle_solver
   cd watercolorpuzzle_solver
   ```

3. Соберите и запустите проект:
   ```
   mvn -q clean compile exec:java
   ```

## Структура проекта

- `src/main/java/com/example/puzzle_solver` — исходный код.

- `Main.java` — точка входа, пример задачи и вывод решения.

- `Tube.java` — модель пробирки.

- `State.java` — состояние головоломки.

- `Move.java` — ход переливания.

- `IDAStarSolver.java` — алгоритм поиска решения.

- `Heuristic.java` — эвристика для ускорения поиска.
