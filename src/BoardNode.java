import java.util.ArrayList;

public class BoardNode {
    char character;
    int xPosition;
    int yPosition;
    ArrayList<BoardNode> neighbors;

    public BoardNode(char character, int x, int y) {
        this.character = character;
        this.xPosition = x;
        this.yPosition = y;
    }

    public void addNeighbor(BoardNode node) {
        this.neighbors.add(node);
    }
}
