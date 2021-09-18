package pl.rafalb.rsockettest.domain;

import lombok.Data;

@Data
public class Graph {
    private Node[][] grade;
    private Node root;
}
