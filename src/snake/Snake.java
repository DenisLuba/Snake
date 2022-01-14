package snake;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private List<SnakeSection> sections;
    private boolean isAlive;
    private SnakeDirection direction;

    public Snake(int x, int y) {
        sections = new ArrayList<>();
        sections.add(new SnakeSection(x, y));
        isAlive = true;
    }

    void move() {
        if (!isAlive()) return;
        switch (direction) {
            case UP:
                move(0, -1);
                break;
            case RIGHT:
                move(1, 0);
                break;
            case DOWN:
                move(0, 1);
                break;
            case LEFT:
                move(-1, 0);
                break;
        }
    }

    void move(int x, int y) {
        int newX = sections.get(0).getX() + x;
        int newY = sections.get(0).getY() + y;
        SnakeSection snakeSection = new SnakeSection(newX, newY);
        checkBorders(snakeSection); checkBody(snakeSection);
        if (!isAlive) return;
        sections.add(0, new SnakeSection(newX, newY));
        if (Room.game.getMouse().getX() == sections.get(0).getX() &&
        Room.game.getMouse().getY() == sections.get(0).getY())
            Room.game.eatMouse();
        else sections.remove(sections.size() - 1);
    }

    public int getX() {
        return sections.get(0).getX();
    }

    public int getY() {
        return sections.get(0).getY();
    }

    public List<SnakeSection> getSections() {
        return sections;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public SnakeDirection getDirection() {
        return direction;
    }

    public void setDirection(SnakeDirection direction) {
        this.direction = direction;
    }

    public void checkBorders(SnakeSection head) {
        if (head.getX() >= Room.game.getWidth()
                || head.getX() < 0
                || head.getY() >= Room.game.getHeight()
                || head.getY() < 0) {
            isAlive = false;
        }
    }

    public void checkBody(SnakeSection head) {
        if (sections.contains(head)) isAlive = false;
    }
}
