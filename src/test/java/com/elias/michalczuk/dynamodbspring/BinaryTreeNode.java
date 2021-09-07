package com.elias.michalczuk.dynamodbspring;

import lombok.AllArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@Accessors(chain = true)
public class BinaryTreeNode {

    String value;
    public BinaryTreeNode left = null;
    public BinaryTreeNode right = null;

    @Override
    public String toString() {
        return "value: " + value;
    }
}
