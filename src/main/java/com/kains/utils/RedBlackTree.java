package com.kains.utils;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 红黑树实现
 * 红黑树的五个性质：
 * 1. 每个节点要么是红色，要么是黑色
 * 2. 根节点必须是黑色
 * 3. 所有叶子节点（NIL）都是黑色
 * 4. 如果一个节点是红色，则它的两个子节点都是黑色
 * 5. 对于每个节点，从该节点到其所有后代叶子节点的简单路径上，均包含相同数目的黑色节点
 *
 * @param <K> 键的类型
 * @param <V> 值的类型
 */
public class RedBlackTree<K extends Comparable<K>, V> {

    @Getter
    private Node root;

    // NIL节点，表示叶子节点，统一使用一个黑色NIL节点
    private final Node NIL;

    public RedBlackTree() {
        NIL = new Node(null, null);
        NIL.color = Color.BLACK;
        root = NIL;
    }

    /**
     * 红黑树节点
     */
    @Data
    private class Node {
        private K key;
        private V value;
        private Node left;
        private Node right;
        private Node parent;
        private Color color;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = NIL;
            this.right = NIL;
            this.parent = NIL;
            this.color = Color.RED; // 新插入的节点默认为红色
        }
    }

    private enum Color {
        RED, BLACK
    }

    /**
     * 左旋操作
     * 假设x是一个节点，y是x的右孩子。左旋以x到y的链为支轴进行。
     * 使y成为该子树新的根节点，x成为y的左孩子，y的左孩子成为x的右孩子。
     *
     *     x                               y
     *    / \     左旋转(Left Rotate)      / \
     *   α   y   ---------------------->  x   γ
     *      / \                         / \
     *     β   γ                       α   β
     *
     * @param x 要进行左旋的节点
     */
    private void leftRotate(Node x) {
        // 获取x的右孩子y
        Node y = x.right;

        // 将y的左孩子β设置为x的右孩子
        x.right = y.left;
        if (y.left != NIL) {
            y.left.parent = x;
        }

        // 将y的父节点设置为x的父节点
        y.parent = x.parent;

        // 根据x是其父节点的左孩子还是右孩子，将y设置为对应的孩子
        if (x.parent == NIL) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }

        // 将x设置为y的左孩子
        y.left = x;
        x.parent = y;
    }

    /**
     * 右旋操作
     * 假设y是一个节点，x是y的左孩子。右旋以y到x的链为支轴进行。
     * 使x成为该子树新的根节点，y成为x的右孩子，x的右孩子成为y的左孩子。
     *
     *       y                            x
     *      / \    右旋转(Right Rotate)   / \
     *     x   γ  --------------------> α   y
     *    / \                              / \
     *   α   β                            β   γ
     *
     * @param y 要进行右旋的节点
     */
    private void rightRotate(Node y) {
        // 获取y的左孩子x
        Node x = y.left;

        // 将x的右孩子β设置为y的左孩子
        y.left = x.right;
        if (x.right != NIL) {
            x.right.parent = y;
        }

        // 将x的父节点设置为y的父节点
        x.parent = y.parent;

        // 根据y是其父节点的左孩子还是右孩子，将x设置为对应的孩子
        if (y.parent == NIL) {
            root = x;
        } else if (y == y.parent.left) {
            y.parent.left = x;
        } else {
            y.parent.right = x;
        }

        // 将y设置为x的右孩子
        x.right = y;
        y.parent = x;
    }

    /**
     * 插入节点后的修复操作
     * 插入可能会违反红黑树的性质4（红色节点的子节点必须是黑色）
     * 通过变色和旋转操作来恢复红黑树的性质
     *
     * @param node 新插入的节点
     */
    private void insertFixup(Node node) {
        // 当父节点是红色时，需要修复
        while (node.parent.color == Color.RED) {
            if (node.parent == node.parent.parent.left) {
                // 父节点是祖父节点的左孩子
                Node uncle = node.parent.parent.right;

                if (uncle.color == Color.RED) {
                    // Case 1: 叔叔节点是红色
                    node.parent.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    node.parent.parent.color = Color.RED;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.right) {
                        // Case 2: 叔叔节点是黑色，且当前节点是右孩子
                        node = node.parent;
                        leftRotate(node);
                    }
                    // Case 3: 叔叔节点是黑色，且当前节点是左孩子
                    node.parent.color = Color.BLACK;
                    node.parent.parent.color = Color.RED;
                    rightRotate(node.parent.parent);
                }
            } else {
                // 父节点是祖父节点的右孩子（对称情况）
                Node uncle = node.parent.parent.left;

                if (uncle.color == Color.RED) {
                    node.parent.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    node.parent.parent.color = Color.RED;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.left) {
                        node = node.parent;
                        rightRotate(node);
                    }
                    node.parent.color = Color.BLACK;
                    node.parent.parent.color = Color.RED;
                    leftRotate(node.parent.parent);
                }
            }

            if (node == root) {
                break;
            }
        }
        // 确保根节点是黑色
        root.color = Color.BLACK;
    }

    /**
     * 插入新节点
     *
     * @param key 键
     * @param value 值
     */
    public void insert(K key, V value) {
        Node node = new Node(key, value);
        Node y = NIL;
        Node x = root;

        // 找到插入位置
        while (x != NIL) {
            y = x;
            int cmp = key.compareTo(x.key);
            if (cmp < 0) {
                x = x.left;
            } else if (cmp > 0) {
                x = x.right;
            } else {
                // 键已存在，更新值
                x.value = value;
                return;
            }
        }

        node.parent = y;
        if (y == NIL) {
            root = node;
        } else {
            int cmp = key.compareTo(y.key);
            if (cmp < 0) {
                y.left = node;
            } else {
                y.right = node;
            }
        }

        // 修复红黑树性质
        insertFixup(node);
    }

    /**
     * 查找节点
     *
     * @param key 键
     * @return 值，如果键不存在则返回null
     */
    public V find(K key) {
        Node node = findNode(key);
        return node != NIL ? node.value : null;
    }

    private Node findNode(K key) {
        Node current = root;
        while (current != NIL) {
            int cmp = key.compareTo(current.key);
            if (cmp < 0) {
                current = current.left;
            } else if (cmp > 0) {
                current = current.right;
            } else {
                return current;
            }
        }
        return NIL;
    }

    /**
     * 中序遍历
     * 用于验证树的结构是否正确
     */
    public void inorderTraversal() {
        inorderTraversal(root);
        System.out.println();
    }

    private void inorderTraversal(Node node) {
        if (node != NIL) {
            inorderTraversal(node.left);
            System.out.print(node.key + "(" + (node.color == Color.RED ? "R" : "B") + ") ");
            inorderTraversal(node.right);
        }
    }
}
