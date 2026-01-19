# quadrant-tree-image-processor

A Java implementation of a quadrant tree (quadtree) for representing images as hierarchical regions. Each tree node corresponds to a square region of the image and stores the region’s average color, enabling resolution changes by rendering the image using nodes from different tree levels.

## What it does
- Builds a quadtree from a square pixel matrix (size is a power of 2).
- Stores, per node:
  - Region coordinates (upper-left corner)
  - Region size
  - Average color of the region
  - Parent/children links
- Supports tree traversals to:
  - Retrieve all nodes at a target level (or the deepest level if the target exceeds tree height)
  - Find regions with colors similar to a target color
  - Locate the region at a given level that contains a specific (x, y) point

## Core classes
- `QTreeNode`
  - Represents a quadtree node (region metadata + parent/children).
  - Includes helpers like containment checks and leaf detection.
- `QuadrantTree`
  - Builds the tree recursively from pixel data.
  - Provides recursive queries for level retrieval, color matching, and point-to-region lookup.

## How resolution works
Rendering at a lower resolution corresponds to using nodes from higher (shallower) levels of the tree. Rendering at full resolution corresponds to using leaf-level nodes (single-pixel regions).

## Notes
This project focuses on the data structure and recursive algorithms for building and querying a quadtree representation of an image. A GUI (if included) uses the tree to display the image at different effective resolutions and to highlight pixels/regions with similar colors.

## Tech
- Language: Java
- Key concepts: recursion, trees, linked-list based result aggregation, spatial partitioning (quadtrees)
