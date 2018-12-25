package com.company.sintactic;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Productie productie = (Productie) o;
        return left.equals(productie.left) && right.equals(productie.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }
}
