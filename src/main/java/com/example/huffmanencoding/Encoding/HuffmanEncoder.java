package com.example.huffmanencoding.Encoding;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class HuffmanEncoder {
    private String text;

    private Node rootNode;

    private LinkedHashMap<Character, Integer> sortedFreqTable;

    private HashMap<String, String> encodingTable;

    private ArrayList<TableData> tableData;

    private int encodedMessageBitCount;

    private float savings;

    private int originalBitCount;
    public HuffmanEncoder(String text) {
        this.text = text;
    }

    private Map<Character, Integer> createFreqTable(){
        Map<Character, Integer> map = new HashMap<>();
        for(int i = 0; i < this.text.length(); i++){
            if(!map.containsKey(text.charAt(i))){
                map.put(text.charAt(i), 0);
            }
            map.replace(text.charAt(i), map.get(text.charAt(i))+1);
        }
        return map;
    }

    private LinkedHashMap<Character, Integer> sortFreqTable(Map<Character, Integer> mapToSort){
        LinkedHashMap<Character, Integer> sortedFreqTable = new LinkedHashMap<>();
        ArrayList<Integer> list = new ArrayList<>();

        mapToSort.forEach((character, integer) -> list.add(integer));
        Collections.sort(list, Collections.reverseOrder());
        for(int num: list){
            for(Map.Entry<Character, Integer> entry: mapToSort.entrySet()){
                if(entry.getValue().equals(num)){
                    sortedFreqTable.put(entry.getKey(), num);
                }
            }
        }
        this.sortedFreqTable = sortedFreqTable;
        return sortedFreqTable;
    }

    /**
     * Returns root node of huffman tree
     * */
    private Node createEncodingTree(LinkedHashMap<Character, Integer> freqTable){
        LinkedList<Node> list = new LinkedList<>();
        for(Map.Entry<Character, Integer> entry: freqTable.entrySet()){
            list.add(new Node(entry.getValue(), entry.getKey().toString(), null, null));
        }

        while(list.size() > 1){
            Node last = list.removeLast();
            Node secondToLast = list.removeLast();

            Node newNode = new Node(last.getVal() + secondToLast.getVal(), "", secondToLast, last);
            int insertIdx = list.size() - 1;
            while(insertIdx > -1 && list.get(insertIdx).getVal() < newNode.getVal()){
                insertIdx--;
            }
            list.add(insertIdx+1, newNode);
        }

        return list.get(0);
    }

    private ArrayList<String> traverseHelper(Node node){
        ArrayList<String> list = new ArrayList<>();
        traverse(node,"", list);
        return list;
    }

    private void traverse (Node node, String encodingStr, ArrayList<String> list){
        if(node == null)
            return;

        this.traverse(node.getLeft(), encodingStr.concat("0"), list);
        if(node.getCharacter().length() > 0)
            list.add(node.getCharacter() + " " + encodingStr);
        this.traverse(node.getRight(), encodingStr.concat("1"), list);
    }

    private HashMap<String, String> createEncodingTable(Node node){
        HashMap<String, String> table = new HashMap<>();
        ArrayList<String> traversed = this.traverseHelper(node);
        traversed.forEach(s -> {
            String[] arr = s.split(" ");
            if(s.toString().charAt(0) == ' ')
                table.put(" ", arr[2]);
            else
                table.put(arr[0], arr[1]);
        });
        this.encodingTable = table;
        return table;
    }

    private VisualizationNode prepareForVisualization(Node node){
        if(node.getRight() == null && node.getLeft() == null){
            return new VisualizationNode(node.getCharacter());
        }
        VisualizationNode newNode = new VisualizationNode(String.valueOf(node.getVal()));
        if(node.getLeft() != null){
            newNode.addChild(prepareForVisualization(node.getLeft()));
        }
        if(node.getRight() != null){
            newNode.addChild(prepareForVisualization(node.getRight()));
        }
        return newNode;
    }

    private void createTableForVisualization(){
        ArrayList<TableData> tableData = new ArrayList<>();
        this.sortedFreqTable.forEach((character, integer) -> {
            String strToAdd = character == ' ' ? "space" : character.toString();
            TableData tData = new TableData(strToAdd, integer, this.encodingTable.get(character.toString()));
            tableData.add(tData);
        });
        this.tableData = tableData;
    }

    private void calculateSavings(){
        int original = this.text.length() * 8; // 8 bits per character
        AtomicInteger huffmanEncoded = new AtomicInteger();
        this.tableData.forEach(tData -> {
            huffmanEncoded.addAndGet((tData.getEncoding().length() * tData.getCount()));
        });
        this.encodedMessageBitCount = huffmanEncoded.get();
        this.savings = 100 - (((float)this.encodedMessageBitCount * 100) / original);
        this.originalBitCount = original;
    }

    public String getVisualization(){
        VisualizationNode rootVisNode = this.prepareForVisualization(this.rootNode);
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String json = null;
        try {
            json = mapper.writeValueAsString(rootVisNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    public void encode(){
        this.rootNode = this.createEncodingTree(this.sortFreqTable(this.createFreqTable()));
        this.createEncodingTable(this.rootNode);
        this.createTableForVisualization();
        this.calculateSavings();
    }

    public LinkedHashMap<Character, Integer> getSortedFreqTable() {
        return sortedFreqTable;
    }

    public ArrayList<TableData> getTableData() {
        return tableData;
    }

    public int getEncodedMessageBitCount() {
        return encodedMessageBitCount;
    }

    public float getSavings() {
        return savings;
    }

    public int getOriginalBitCount() {
        return originalBitCount;
    }
}
