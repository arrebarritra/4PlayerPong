package io.github.arrebarritra._4pp;

import io.github.arrebarritra._4pp.tools.Settings;
import io.github.arrebarritra._4pp.enums.GameState;
import io.github.arrebarritra._4pp.enums.ID;
import io.github.arrebarritra._4pp.enums.PlayState;
import io.github.arrebarritra._4pp.enums.PlayerNumber;
import io.github.arrebarritra._4pp.gui.EndScreen;
import io.github.arrebarritra._4pp.gui.HUD;
import io.github.arrebarritra._4pp.gui.Menu;
import io.github.arrebarritra._4pp.gui.Pause;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import io.github.arrebarritra._4pp.tools.DynamicArray;

/**
 *
 * Main game class. This class updates and renders all game objects
 */
public class Game extends Canvas implements Runnable {

    public static int WIDTH = 640, HEIGHT = WIDTH;
    public static Window window;

    private int fps;
    private long serveTime = 2000, serveTimeStart, serveTimeLeft;
    private Thread thread;
    public boolean running = false;

    private Handler handler;
    private Menu menu;

    private HUD hud;
    private GameTracker tracker;
    private EndScreen endScreen;
    private Score score;
    private Pause pause;

    private Settings settings;

    private GameState gameState;
    private PlayState playState;

    private boolean serving;

    private RenderingHints renderingHints = new RenderingHints(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    public Game() {
        settings = new Settings();
        handler = new Handler();
        menu = new Menu(this, handler, settings);
        pause = new Pause();

        this.addMouseListener(menu);
        this.setGameState(gameState.MENU);
        this.addKeyListener(new KeyInput(handler, this, menu, settings));
        window = new Window(WIDTH, HEIGHT, "4PlayerPong", this);
    }

    /**
     * Instantiates a new game
     */
    public void newGame() {
        spawn();
        score = new Score(this);
        tracker = new GameTracker(handler, settings);
        hud = new HUD(this, tracker, score);
        hud.getStats();
        endScreen = new EndScreen(this, handler, score, menu, settings);

        this.addMouseListener(endScreen);

        this.setGameState(GameState.GAME);
        this.setPlayState(PlayState.PLAYING);
        this.setServing(true);
    }

    /**
     * Resets all variables that are changed in the game
     */
    public void resetGame() {
        score = null;
        tracker = null;
        endScreen = null;
        handler.object = new DynamicArray<GameObject>();
        this.setServing(false);
    }

    /**
     * Spawns all essential starting game objects
     */
    public void spawn() {
        new Player(36, HEIGHT / 2 - 32, ID.Player, handler, PlayerNumber.Player1);
        new Player(WIDTH - 52, HEIGHT / 2 - 32, ID.Player, handler, PlayerNumber.Player2);
        new Player(WIDTH / 2 - 32, 36, ID.Player, handler, PlayerNumber.Player3);
        new Player(WIDTH / 2 - 32, HEIGHT - 52, ID.Player, handler, PlayerNumber.Player4);
        new Block(-52, -52, ID.Block, handler);
        new Block(WIDTH - 52, -52, ID.Block, handler);
        new Block(-52, HEIGHT - 52, ID.Block, handler);
        new Block(WIDTH - 52, HEIGHT - 52, ID.Block, handler);
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
        } catch (InterruptedException ex) {
        }
    }

    /**
     * Game loop. Calls tick() and render methods 60 times a second
     */
    @Override
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            if (delta >= 1) {
                tick();
                delta--;
            }

            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                fps = frames;
                frames = 0;
            }
        }
        stop();
    }

    /**
     * Updates all properties
     */
    private void tick() {
        if (gameState == GameState.GAME) {
            if (playState == PlayState.PLAYING) {
                handler.tick();
                hud.tick();
            } else if (gameState == GameState.MENU) {
                menu.tick();
            }
        }
    }

    /**
     * Renders all objects
     */
    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics2D g = (Graphics2D) bs.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        if (Boolean.valueOf(Settings.getProp("AA"))) {
            g.setRenderingHints(renderingHints);
        }

        if (gameState == GameState.GAME) {
            if (playState == PlayState.PLAYING || playState == PlayState.PAUSED) {
                handler.render(g);
                hud.render(g);

                if (playState == PlayState.PAUSED) {
                    pause.render(g);
                }
            } else if (playState == PlayState.END) {
                endScreen.render(g);
            }
        } else if (gameState == GameState.MENU) {
            menu.render(g);
        }

        g.dispose();
        bs.show();
    }

    /**
     * Starts the serve timer
     */
    public void startServeTimer() {
        this.serveTimeStart = System.currentTimeMillis();
    }

    /**
     * Returns the time left before the ball is served
     *
     * @return Amount of time left before the ball is served
     */
    public long checkServeTimer() {
        long now = System.currentTimeMillis();

        if (playState == playState.PAUSED) {
            this.startServeTimer();
        }
        serveTimeLeft = serveTime - (now - serveTimeStart);

        if (serveTimeLeft > 0) {
            return serveTimeLeft;
        } else {
            this.setServing(false);
            new Ball(Game.WIDTH / 2 - 8, Game.HEIGHT / 2 - 8, ID.Ball, handler, this, tracker, score);
            return 0;
        }
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public PlayState getPlayState() {
        return playState;
    }

    public void setPlayState(PlayState playState) {
        if (playState == PlayState.END) {
            endScreen.getStats();
        }
        this.playState = playState;
    }

    public boolean isServing() {
        return serving;
    }

    public void setServing(boolean serving) {
        if (serving) {
            this.startServeTimer();
            hud.getStats();

            for (int index = 0; index < handler.object.size(); index++) {
                GameObject tempObject = handler.object.get(index);
                if (tempObject.id == ID.Player) {
                    Player tempPlayer = (Player) tempObject;
                    if (tempPlayer.playerNumber.isAi()) {
                        tempPlayer.generateNewRandomPoint();
                    }
                }
            }
        }
        this.serving = serving;
    }

    public int getFps() {
        return fps;
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static void main(String[] args) {
        new Game();
    }
}
