package com.example.business_server.utils;

import com.example.business_server.model.domain.Post;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class SortUtil {
    static class Node implements Comparator<Node>{
        public Date value;
        public int arrayIdx;
        public int idx;
        public Node(Date value, int arrayIdx, int idx){
            this.value=value;
            this.arrayIdx = arrayIdx;
            this.idx = idx;
        }

        @Override
        public int compare(Node n1, Node n2) {
            return n1.value.compareTo(n2.value);
        }
    }
    static Comparator<Node> nodeComparator = (o1, o2) -> o1.compare(o1,o2);

    public static List<Post> mergeMultipleSortedArray(List<List<Post>> followerPosts){
        PriorityQueue<Node> queue= new PriorityQueue<>(followerPosts.size(), nodeComparator);
        List<Post> ans=new ArrayList<>();
        for (int i = 0; i < followerPosts.size(); i++) {
            if (followerPosts.get(i).size()==0){
                continue;
            }
            queue.add(new Node(followerPosts.get(i).get(0).getCreatedTime(),i,0));
        }

        while (!queue.isEmpty()){
            Node node=queue.poll();

            int arrayIdx = node.arrayIdx;
            int idx = node.idx;

            ans.add(followerPosts.get(arrayIdx).get(idx));

            if (idx != followerPosts.get(arrayIdx).size() - 1) {
                Node newNode =new Node(followerPosts.get(arrayIdx).get(idx+1).getCreatedTime(),arrayIdx,idx+1);
                queue.add(newNode);
            }
        }

        return ans;

    }
}
