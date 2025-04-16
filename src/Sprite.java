public class Sprite {
    int y, x, tile, attr;
    boolean xFlip, yFlip, priority, palette;

    Sprite(int[] oam, int index) {
        int base = index * 4;
        y = oam[base] - 16;
        x = oam[base + 1] - 8;
        tile = oam[base + 2];
        attr = oam[base + 3];

        yFlip = (attr & 0x40) != 0;
        xFlip = (attr & 0x20) != 0;
        priority = (attr & 0x80) == 0;
        palette = (attr & 0x10) != 0; // palette selection (0 or 1)
    }
}
