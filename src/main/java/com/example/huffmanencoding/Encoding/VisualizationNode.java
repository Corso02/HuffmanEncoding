package com.example.huffmanencoding.Encoding;

import java.util.ArrayList;

public class VisualizationNode {
    private String name;
    private ArrayList<VisualizationNode> children;

    public VisualizationNode(String name) {
        this.name = name;
        this.children = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<VisualizationNode> getChildren() {
        return children;
    }

    public void addChild(VisualizationNode child){
        this.children.add(child);
    }
    @Override
    public String toString(){
        return "VisualizationNode [name="
                + name
                + ", children=" + children
                + "]";

    }
}
