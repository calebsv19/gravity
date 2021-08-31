import java.awt.*;
import java.util.*;

public class Handler {
    private Random r;
    private final Ball[] balls;
    private final Block[] blocks;

    private static final Color[] COLORS = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.MAGENTA};
    public static final int BALL_NUMBER = 5;
    public static final int BLOCK_NUMBER = 10;
    public static final double SPEED = .8;
    public static final double GRAVITY = .08;

    public Handler(int width, int height){
        r = new Random();
        balls = new Ball[BALL_NUMBER];
        for (int i = 0; i < BALL_NUMBER; i++){
            balls[i] = new Ball(0, 30.0 * i, SPEED + (i * SPEED) / 30, 0);
        }
        blocks = new Block[BLOCK_NUMBER];
        int prev = 0;
        blocks[0] = new Block(height - 400, height, prev, prev + width / BLOCK_NUMBER);
        prev += width / BLOCK_NUMBER;
        blocks[1] = new Block(height - 400, height, prev, prev + width / BLOCK_NUMBER);
        for (int i = 2; i < BLOCK_NUMBER; i++){
            prev += width / BLOCK_NUMBER;
            blocks[i] = new Block(height - r.nextInt(400), height, prev, prev + width / BLOCK_NUMBER);
        }

    }

    public void tick(int height, int width){
        for (int i = 0; i < BALL_NUMBER; i++){
            balls[i].setX(balls[i].getX() + balls[i].getXVel());
            balls[i].setY(balls[i].getY() + balls[i].getYVel());
            clamp(balls[i], height, width);
            balls[i].setYVel(balls[i].getYVel() + GRAVITY);
        }
    }
    // if ((blocks[i].getLeft() < ball.getX() + 20) && (ball.getX() < blocks[i].getLeft()) && (blocks[i].getBottom() > ball.getY()) && (ball.getY() + 20 < blocks[i].getTop()))

    private void clamp(Ball ball, int height, int width){
        int num = (int) (ball.getXVel() / Math.abs(ball.getXVel()));

        int i = (int) Math.floor((ball.getX() + (20 * num)) / width * BLOCK_NUMBER);
        if (i < 0) i = 0;
        else if (i >= BLOCK_NUMBER){
            i = BLOCK_NUMBER - 1;
        }

        if (ball.getY() + 20 >= blocks[i].getTop() && ball.getY() <= blocks[i].getTop() &&
                (ball.getX() < blocks[i].getRight() || ball.getX() + 20 >= blocks[i].getLeft())){
            ball.setY(blocks[i].getTop() - 20);
            ball.setYVel(ball.getYVel() * -0.8446);
        } else if (ball.getY() >= blocks[i].getTop()){
            if (ball.getX() < blocks[i].getLeft()){
                ball.setX(blocks[i].getLeft() - 20);
                ball.setXVel(ball.getXVel() * -1);
            } else if (ball.getX() < blocks[i].getRight()){
                ball.setX(blocks[i].getRight());
                ball.setXVel(ball.getXVel() * -1);
            }
        }

    }

    public void render(Graphics g, int x, int y) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, x, y);
        g.setColor(Color.WHITE);
        for (int i = 0; i < BLOCK_NUMBER; i++){
            g.fillRect(blocks[i].getLeft(), blocks[i].getTop(), blocks[i].getWidth(), y - blocks[i].getTop());
        }
        for (int i = 0; i < BALL_NUMBER; i++){
            g.setColor(COLORS[i % COLORS.length]);
            g.fillOval((int) balls[i].getX(), (int) balls[i].getY(), 20, 20);
        }
    }
}