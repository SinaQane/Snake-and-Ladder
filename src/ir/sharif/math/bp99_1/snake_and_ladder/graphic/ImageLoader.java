package ir.sharif.math.bp99_1.snake_and_ladder.graphic;

import ir.sharif.math.bp99_1.snake_and_ladder.util.Config;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageLoader {
    final static ImageLoader instance = new ImageLoader();
    private final Map<String, BufferedImage> imageMap;
    private final Map<String, Icon> dice;

    public static BufferedImage getImage(String name) {
        return instance.imageMap.get(name);
    }

    public static Icon getIcon(String dice) {
        return instance.dice.get(dice);
    }

    private ImageLoader() {
        imageMap = new HashMap<>();
        dice = new HashMap<>();
        load();
    }

    private void load() {
        Config config = Config.getConfig("images");
        for (Map.Entry<Object, Object> k : config.entrySet()) {
            String key = (String) k.getKey();
            File file = new File((String) k.getValue());
            try {
                BufferedImage image = ImageIO.read(file);
                String name = key.replace('-', ' ');
                imageMap.put(name, image);
            } catch (IOException e) {
                System.err.println(file.toString());
                throw new RuntimeException("image file not exist", e);
            }
        }
        config = Config.getConfig("dice");
        for (Map.Entry<Object, Object> k : config.entrySet()) {
            String key = (String) k.getKey();
            String value = (String) k.getValue();
            Icon icon = new ImageIcon(value);
            dice.put(key, icon);
        }
    }

}
