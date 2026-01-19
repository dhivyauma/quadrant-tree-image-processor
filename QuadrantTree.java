public class QuadrantTree {
    private QTreeNode root; 

    // The constructor
    public QuadrantTree(int[][] thePixels) {
        root = buildTree(thePixels, 0, 0, thePixels.length); // Calling buildTree to construct the tree
    }

    // The recursive method implemented to build the quadrant tree
    private QTreeNode buildTree(int[][] pixels, int x, int y, int size) {
        QTreeNode node = new QTreeNode(); // Creating a new node and setting the coordinates, colour and size of the code
        node.setx(x); 
        node.sety(y); 
        node.setSize(size); 
        node.setColor(Gui.averageColor(pixels, x, y, size)); 
        // If the size is 1, return a leaf node
        if (size == 1) {
            return node;
        } else { // This is the recursive case that splits the current node into four quadrants
            int newSize = size / 2;
            // Recursively build children nodes for each quadrant
            node.setChild(buildTree(pixels, x, y, newSize), 0); 
            node.setChild(buildTree(pixels, x + newSize, y, newSize), 1); 
            node.setChild(buildTree(pixels, x, y + newSize, newSize), 2); 
            node.setChild(buildTree(pixels, x + newSize, y + newSize, newSize), 3); 
            return node;
        }
    }
    public QTreeNode getRoot() {
        return root; // Return the root of the tree
    }

    public ListNode<QTreeNode> getPixels(QTreeNode r, int theLevel) {
        if (r == null || theLevel < 0) {
            return new ListNode<>(null); // This returns an empty list dependent on if node is null or theLevel is invalid
        }
        return buildPixelList(r, theLevel); // Calling the helper method to build the pixel list
    }

    private ListNode<QTreeNode> buildPixelList(QTreeNode node, int theLevel) {
        if (node == null || theLevel == 0) {
            return new ListNode<>(node); // This returns a list containing only the current node if at target level or leaf
        }

        ListNode<QTreeNode> result = null; // Initializing the result list

        // This iterates through the children of the current node
        for (int i = 0; i < 4; i++) {
            ListNode<QTreeNode> childList = buildPixelList(node.getChild(i), theLevel - 1); // This is a recursive call to build lists for child nodes
            result = concatLists(result, childList); // Links the lists together
        }

        return result; // Returns the concatenated list
    }
    private ListNode<QTreeNode> concatLists(ListNode<QTreeNode> list1, ListNode<QTreeNode> list2) {
        // If the first list is empty then the second list is returned 
        if (list1 == null) {
            return list2;
        }
        
        // Traversing the first list in order to find its last node
        ListNode<QTreeNode> temp = list1;
        while (temp.getNext() != null) {
            temp = temp.getNext();
        }
        
        // The second list is appended to the end of the first list
        temp.setNext(list2);
        
        // Return the concatenated list
        return list1;
    }

    public Duple findMatching(QTreeNode r, int theColor, int theLevel) {
        //If node is null or theLevel is invalid and empty Duple is returned 
        if (r == null || theLevel < 0) {
            return new Duple();
        }
        
        ListNode<QTreeNode> resultList = null; // The result list is initialized
        int totalCount = 0; // The total count is Initialized
        
        // This checks if the current node matches the criteria
        if (r.isLeaf() || theLevel == 0) {
            if (Gui.similarColor(r.getColor(), theColor)) {
                resultList = new ListNode<>(r); // Current node is added to the list
                totalCount = 1;
            }
        } else {
            // Matching nodes is recursively searched for in the children of the current node
            for (int i = 0; i < 4; i++) {
                try {
                    QTreeNode child = r.getChild(i);
                    Duple childResult = findMatching(child, theColor, theLevel - 1); // This is a recursive call
                    ListNode<QTreeNode> childList = childResult.getFront();
                    
                    // The lists obtained from recursive calls and concatenated 
                    if (childList != null) {
                        if (resultList == null) {
                            resultList = childList;
                        } else {
                            ListNode<QTreeNode> temp = resultList;
                            while (temp.getNext() != null) {
                                temp = temp.getNext();
                            }
                            temp.setNext(childList);
                        }
                        totalCount += childResult.getCount();
                    }
                } catch (QTreeException e) {
                    // Handle QTreeException if thrown
                }
            }
        }
        
        return new Duple(resultList, totalCount); // This returns the final Duple object
    }

    public QTreeNode findNode(QTreeNode r, int theLevel, int x, int y) {
        // If node is null or theLevel is invalid then it is returned null
        if (r == null || theLevel < 0) {
            return null;
        } 
        
        // If theLevel is 0, the current node is checked to contain the point (x, y)
        if (theLevel == 0) {
            if (r.contains(x, y)) {
                return r; // This returns the current node if it contains the point
            } else {
                return null; // This returns null if the current node does not contain the point
            }
        } 
        
        // Size of quadrant is calculated 
        int newSize = r.getSize() / 2;
        QTreeNode child = null;
        
        // The quadrant containing the point (x, y) is determined and recursively search in that quadrant
        if (x < r.getx() + newSize && y < r.gety() + newSize) {
            child = findNode(r.getChild(0), theLevel - 1, x, y); 
        } else if (x >= r.getx() + newSize && y < r.gety() + newSize) {
            child = findNode(r.getChild(1), theLevel - 1, x, y); 
        } else if (x < r.getx() + newSize && y >= r.gety() + newSize) {
            child = findNode(r.getChild(2), theLevel - 1, x, y); 
        } else {
            child = findNode(r.getChild(3), theLevel - 1, x, y); 
        }
        
        return child; // This returns the node containing the point or null if not found
    }
}