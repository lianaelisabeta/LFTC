package com.company.sintactic;

public class Productie {
    private String left;
    private String right;

    public Productie() {
    }

    public Productie(String left, String right) {
        this.left = left.trim();
        this.right = right.trim();
    }

    public String getLeft() {
        return left.trim();
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getRight() {
        return right.trim();
    }

    public void setRight(String right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return left + "->" + right;
    }
}
