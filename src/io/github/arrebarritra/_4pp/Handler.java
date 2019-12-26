package io.github.arrebarritra._4pp;

import java.awt.Graphics2D;
import io.github.arrebarritra._4pp.tools.DynamicArray;

/**
 *
 * Contains, updates and renders all game objects
 */
public class Handler {

    public DynamicArray<GameObject> object = new DynamicArray<>();

    public void tick() {
        for (int index = 0; index < this.object.size(); index++) {
            GameObject tempObject = this.object.get(index);
            tempObject.tick();
        }
    }

    public void render(Graphics2D g) {
        for (int index = 0; index < this.object.size(); index++) {
            GameObject tempObject = this.object.get(index);
            tempObject.render(g);
        }
    }

    public void addObject(GameObject object) {
        this.object.add(object);
    }

    public void removeObject(GameObject object) {
        this.object.remove(object);
    }
}
