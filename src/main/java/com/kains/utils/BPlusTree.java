package com.kains.utils;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * B+树实现
 * B+树的特点：
 * 1. 所有叶子节点具有相同的深度
 * 2. 所有数据都存储在叶子节点中
 * 3. 叶子节点之间通过指针连接，方便范围查询
 * 4. 非叶子节点只存储索引键值
 *
 * @param <K> 键的类型（必须可比较）
 * @param <V> 值的类型
 */
public class BPlusTree<K extends Comparable<K>, V> {

    private Node root;
    private final int order; // B+树的阶数
    private final int minKeys; // 最小键数
    private LeafNode firstLeaf; // 指向第一个叶子节点，用于范围查询

    public BPlusTree(int order) {
        this.order = order;
        this.minKeys = (order - 1) / 2;
        this.root = new LeafNode();
        this.firstLeaf = (LeafNode) root;
    }

    /**
     * 节点抽象类，作为内部节点和叶子节点的父类
     */
    @Data
    private abstract class Node {
        List<K> keys; // 键列表
        Node parent; // 父节点
        boolean isLeaf; // 是否为叶子节点

        Node() {
            this.keys = new ArrayList<>();
            this.parent = null;
        }

        abstract V find(K key);
        abstract void insertValue(K key, V value);
        abstract K getFirstLeafKey();
        abstract void split();
    }

    /**
     * 内部节点类
     * 内部节点不存储数据，只存储键值和子节点的引用
     */
    private class InternalNode extends Node {
        List<Node> children; // 子节点列表

        InternalNode() {
            super();
            this.isLeaf = false;
            this.children = new ArrayList<>();
        }

        @Override
        V find(K key) {
            // 找到合适的子节点
            return getChild(key).find(key);
        }

        @Override
        void insertValue(K key, V value) {
            // 找到合适的子节点进行插入
            Node child = getChild(key);
            child.insertValue(key, value);

            // 如果子节点分裂了，需要处理新的键和子节点
            if (child.parent != this) {
                int index = getChildIndex(key);
                keys.add(index, key);
                children.add(index + 1, child);
            }

            // 检查是否需要分裂
            if (keys.size() >= order) {
                split();
            }
        }

        @Override
        K getFirstLeafKey() {
            return children.get(0).getFirstLeafKey();
        }

        @Override
        void split() {
            int midIndex = keys.size() / 2;
            K upKey = keys.get(midIndex);

            // 创建新的内部节点
            InternalNode newNode = new InternalNode();

            // 移动键和子节点到新节点
            for (int i = midIndex + 1; i < keys.size(); i++) {
                newNode.keys.add(keys.get(i));
                newNode.children.add(children.get(i));
                children.get(i).parent = newNode;
            }
            newNode.children.add(children.get(children.size() - 1));
            children.get(children.size() - 1).parent = newNode;

            // 清理当前节点的后半部分
            int size = keys.size();
            for (int i = midIndex; i < size; i++) {
                keys.remove(keys.size() - 1);
                children.remove(children.size() - 1);
            }

            // 处理父节点
            if (parent == null) {
                // 创建新的根节点
                InternalNode newRoot = new InternalNode();
                newRoot.keys.add(upKey);
                newRoot.children.add(this);
                newRoot.children.add(newNode);
                this.parent = newRoot;
                newNode.parent = newRoot;
                root = newRoot;
            } else {
                // 将新节点插入到父节点
                newNode.parent = parent;
                ((InternalNode) parent).insertChild(upKey, newNode);
            }
        }

        private void insertChild(K key, Node child) {
            int index = getChildIndex(key);
            keys.add(index, key);
            children.add(index + 1, child);

            if (keys.size() >= order) {
                split();
            }
        }

        private Node getChild(K key) {
            int index = getChildIndex(key);
            return children.get(index);
        }

        private int getChildIndex(K key) {
            int index = 0;
            while (index < keys.size() && keys.get(index).compareTo(key) <= 0) {
                index++;
            }
            return index;
        }
    }

    /**
     * 叶子节点类
     * 叶子节点存储实际的键值对数据
     */
    private class LeafNode extends Node {
        List<V> values; // 值列表
        LeafNode next; // 指向下一个叶子节点的指针

        LeafNode() {
            super();
            this.isLeaf = true;
            this.values = new ArrayList<>();
            this.next = null;
        }

        @Override
        V find(K key) {
            int index = getKeyIndex(key);
            return index < keys.size() && keys.get(index).equals(key) ? values.get(index) : null;
        }

        @Override
        void insertValue(K key, V value) {
            int index = getKeyIndex(key);

            if (index < keys.size() && keys.get(index).equals(key)) {
                values.set(index, value); // 更新已存在的值
            } else {
                keys.add(index, key);
                values.add(index, value);
            }

            if (keys.size() >= order) {
                split();
            }
        }

        @Override
        K getFirstLeafKey() {
            return keys.isEmpty() ? null : keys.get(0);
        }

        @Override
        void split() {
            int midIndex = keys.size() / 2;

            // 创建新的叶子节点
            LeafNode newLeafNode = new LeafNode();

            // 移动后半部分的键值对到新节点
            for (int i = midIndex; i < keys.size(); i++) {
                newLeafNode.keys.add(keys.get(i));
                newLeafNode.values.add(values.get(i));
            }

            // 清理当前节点的后半部分
            int size = keys.size();
            for (int i = midIndex; i < size; i++) {
                keys.remove(keys.size() - 1);
                values.remove(values.size() - 1);
            }

            // 维护叶子节点的链表结构
            newLeafNode.next = this.next;
            this.next = newLeafNode;

            // 处理父节点
            if (parent == null) {
                // 创建新的根节点
                InternalNode newRoot = new InternalNode();
                newRoot.keys.add(newLeafNode.keys.get(0));
                newRoot.children.add(this);
                newRoot.children.add(newLeafNode);
                this.parent = newRoot;
                newLeafNode.parent = newRoot;
                root = newRoot;
            } else {
                // 将新节点插入到父节点
                newLeafNode.parent = parent;
                ((InternalNode) parent).insertChild(newLeafNode.keys.get(0), newLeafNode);
            }
        }

        private int getKeyIndex(K key) {
            int index = 0;
            while (index < keys.size() && keys.get(index).compareTo(key) < 0) {
                index++;
            }
            return index;
        }
    }

    /**
     * 插入键值对
     *
     * @param key 键
     * @param value 值
     */
    public void insert(K key, V value) {
        root.insertValue(key, value);
    }

    /**
     * 查找指定键对应的值
     *
     * @param key 键
     * @return 值，如果键不存在则返回null
     */
    public V find(K key) {
        return root.find(key);
    }

    /**
     * 范围查询
     * 返回指定范围内的所有值
     *
     * @param start 起始键（包含）
     * @param end 结束键（包含）
     * @return 范围内的值列表
     */
    public List<V> rangeSearch(K start, K end) {
        List<V> result = new ArrayList<>();

        // 找到起始叶子节点
        LeafNode current = firstLeaf;
        while (current != null && current.keys.get(0).compareTo(start) < 0) {
            current = current.next;
        }

        // 收集范围内的值
        while (current != null) {
            for (int i = 0; i < current.keys.size(); i++) {
                K key = current.keys.get(i);
                if (key.compareTo(start) >= 0 && key.compareTo(end) <= 0) {
                    result.add(current.values.get(i));
                }
                if (key.compareTo(end) > 0) {
                    return result;
                }
            }
            current = current.next;
        }

        return result;
    }

    /**
     * 打印树结构（用于调试）
     */
    public void printTree() {
        printNode(root, 0);
    }

    private void printNode(Node node, int level) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < level; i++) {
            indent.append("  ");
        }

        System.out.println(indent + "Level " + level + ":");
        System.out.println(indent + "Keys: " + node.keys);

        if (!node.isLeaf) {
            InternalNode internalNode = (InternalNode) node;
            for (Node child : internalNode.children) {
                printNode(child, level + 1);
            }
        }
    }
}
