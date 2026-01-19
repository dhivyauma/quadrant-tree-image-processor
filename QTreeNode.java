public class QTreeNode {
    private int x, y; 
    private int size; 
    private int color; 
    private QTreeNode parent;
    private QTreeNode[] children;

    public QTreeNode() {
        // This initializes the parent, children, x, y, size, and color attributes
        this.parent = null;
        this.children = new QTreeNode[4];
        this.x = 0;
        this.y = 0;
        this.size = 0;
        this.color = 0;
    }

    public QTreeNode(QTreeNode[] theChildren, int xcoord, int ycoord, int theSize, int theColor) {
        // This initializes the children, x, y, size, and color attributes using the provided parameters
        this.children = theChildren;
        this.x = xcoord;
        this.y = ycoord;
        this.size = theSize;
        this.color = theColor;
    }

    // This checks if a point is contained in the quadrant
    public boolean contains(int xcoord, int ycoord) {
        // This checks if the provided coordinates are within the bounds of the quadrant
        return xcoord >= x && xcoord < x + size && ycoord >= y && ycoord < y + size;
    }

    public int getx() {
        return x; // This returns the x-coordinate of the upper left corner
    }

    public int gety() {
        return y; // This returns the y-coordinate of the upper left corner
    }

    public int getSize() {
        return size; // This returns the size of the quadrant
    }

    public int getColor() {
        return color; // This returns the average color of the pixels in the quadrant
    }

    public QTreeNode getParent() {
        return parent; // This returns the parent node
    }

    public QTreeNode getChild(int index) throws QTreeException {
        // This checks if the children array is null or if the index is out of bounds
        if (children == null || index < 0 || index > 3) {
            throw new QTreeException("Invalid child index or null children array");
        }
        return children[index]; // This returns the child node at the specified index
    }

    // Setters
    public void setx(int newx) {
        this.x = newx; // This updates the x-coordinate of the upper left corner
    }

    public void sety(int newy) {
        this.y = newy; // This updates the y-coordinate of the upper left corner
    }

    public void setSize(int newSize) {
        this.size = newSize; // This updates the size of the quadrant
    }

    public void setColor(int newColor) {
        this.color = newColor; // This updates the average color of the pixels in the quadrant
    }

    public void setParent(QTreeNode newParent) {
        this.parent = newParent; // This updates the parent node
    }

    public void setChild(QTreeNode newChild, int index) throws QTreeException {
        // This checks if the children array is null or if the index is out of bounds
        if (children == null || index < 0 || index > 3) {
            throw new QTreeException("Invalid child index or null children array");
        }
        children[index] = newChild;
    }

    public boolean isLeaf() {
        // This checks if all child nodes are null, indicating that the node is a leaf
        if (children == null) {
            return true;
        }
        for (QTreeNode child : children) {
            if (child != null) {
                return false;
            }
        }
        return true;
    }
}
