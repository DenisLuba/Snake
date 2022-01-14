package snake;

import java.awt.event.KeyEvent;

public class Room {
    private int width, height;
    private Snake snake;
    private Mouse mouse;
    public static Room game;

    public Room(int width, int height, Snake snake) {
        this.width = width;
        this.height = height;
        this.snake = snake;
    }

    public static void main(String[] args) throws InterruptedException {
        Snake snake = new Snake(0, 0);
        game = new Room(20, 10, snake);
        game.setSnake(snake);
        snake.setDirection(SnakeDirection.DOWN);
        game.createMouse();
        game.run();
        game.print();
    }

    void sleep() throws InterruptedException {
        int delay = snake.getSections().size() < 12 ? 500 - ((snake.getSections().size() - 1) * 20) :
                snake.getSections().size() < 16 ? 300 - ((snake.getSections().size() - 11) * 25) : 200;
        Thread.sleep(delay);
    }

    void run() throws InterruptedException {
        //Создаем объект "наблюдатель за клавиатурой" и стартуем его.
        KeyboardObserver keyboardObserver = new KeyboardObserver();
        keyboardObserver.start();

        //пока змея жива
        while (snake.isAlive()) {
            //"наблюдатель" содержит события о нажатии клавиш?
            if (keyboardObserver.hasKeyEvents()) {
                KeyEvent event = keyboardObserver.getEventFromTop();
                //Если равно символу "q" - выйти из игры.
                if (event.getKeyChar() == 'q') return;

                //Если "стрелка влево" - сдвинуть фигурку влево
                if (event.getKeyCode() == KeyEvent.VK_LEFT)
                    snake.setDirection(SnakeDirection.LEFT);
                //Если "стрелка вправо" - сдвинуть фигурку вправо
                else if (event.getKeyCode() == KeyEvent.VK_RIGHT)
                    snake.setDirection(SnakeDirection.RIGHT);
                //Если "стрелка вверх" - сдвинуть фигурку вверх
                else if (event.getKeyCode() == KeyEvent.VK_UP)
                    snake.setDirection(SnakeDirection.UP);
                //Если "стрелка вниз" - сдвинуть фигурку вниз
                else if (event.getKeyCode() == KeyEvent.VK_DOWN)
                    snake.setDirection(SnakeDirection.DOWN);
            }

            snake.move(); //двигаем змею
            print();      //отображаем следующее текущее состояние игры
            sleep();      //пауза между ходами
        }

        //Выводим сообщение "Game Over!"
        System.out.println("Game Over!");
    }

    void print() {
        int[][] window = new int[height][width];
        for(int[] w : window) for (int i : w) i = 0;

        for (SnakeSection section : snake.getSections()) window[section.getY()][section.getX()] = 2;
        window[snake.getY()][snake.getX()] = 1;
        window[mouse.getY()][mouse.getX()] = 3;

        for (int[] w : window) {
            for (int i : w) {
                String symbol = i == 1 ? "X" : i == 2 ? "x" : i == 3 ? "^" : ".";
                System.out.print(symbol);
            }
            System.out.println();
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Snake getSnake() {
        return snake;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }

    public void createMouse() {
        int x = (int) (Math.random() * width);
        int y = (int) (Math.random() * height);
        this.mouse = new Mouse(x, y);
    }

    public void eatMouse() {
        createMouse();
    }
}
