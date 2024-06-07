package com.example.huffmanencoding.Encoding;

import jakarta.websocket.OnError;

public class Node {
    private int val;
    private String character;
    private Node left;
    private Node right;

    public Node(int val, String c, Node left, Node right){
        this.val = val;
        this.character = c;
        this.left = left;
        this.right = right;
    }

    public int getVal() {
        return val;
    }

    public String getCharacter() {
        return character;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public String toString(){
        return "{ value: " + this.val + ", character: " + this.character + ", left child: " + (this.left == null ? "null" : this.left.toString()) + ", right child: " + (this.right == null ? "null" : this.right.toString()) + " }";
    }
}
