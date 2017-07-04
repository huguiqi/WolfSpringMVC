package com.wolf.sort;

import com.wolf.model.algorithm.BinaryTree;
import com.wolf.model.algorithm.BinaryTreeMock;
import org.junit.Test;

import java.util.TreeMap;

/**
 * Created by sam on 2017/6/30.
 */
public class SortTest {

    @Test
    public void testQuery(){

        TreeMap map = new TreeMap();
        map.put("A","1");
        map.put("C","2");
        map.put("B","3");
        map.put("E","4");
        map.put("D","5");
        System.out.println(map);

        //调用String的compareTo方法进行排序，用的是底层ASCII码值进行比较
    }

    @Test
    public void testTwoTreeMock(){

        //1. 定义二叉树据结构
        //2. 模拟二叉树遍历方式

        BinaryTreeMock binaryTree = new BinaryTreeMock();
        binaryTree.createBinTree(binaryTree.getRoot());
        //NLR
        System.out.println("*******(前序遍历)[ABDECF]遍历*****************");
        binaryTree.preOrder(binaryTree.getRoot());
        //LNR
        System.out.println("*******(中序遍历)[DBEACF]遍历*****************");
        binaryTree.inOrder(binaryTree.getRoot());
        //RLN
        System.out.println("*******(后序遍历)[DEBFCA]遍历*****************");
        binaryTree.postOrder(binaryTree.getRoot());

        System.out.println("***非递归实现****(前序遍历)[ABDECF]遍历*****************");
        binaryTree.nonRecPreOrder(binaryTree.getRoot());

        System.out.println("***非递归实现****(中序遍历)[DBEACF]遍历*****************");
        binaryTree.nonRecInOrder(binaryTree.getRoot());

        System.out.println("***非递归实现****(后序遍历)[DEBFCA]遍历*****************");
        binaryTree.noRecPostOrder(binaryTree.getRoot());

    }

    @Test
    public void testTwoTreeSort(){

        int  a [ ] = {3,1,2,5,0,7,9,8};
        //todo 使用二叉树方式快速排序

        BinaryTree binaryTree = new BinaryTree();
        for (int i=0;i<a.length;i++){
            binaryTree.add(a[i]);
        }

//        binaryTree.nonRecInOrder(binaryTree.getRoot());
//        binaryTree.deepFirst(binaryTree.getRoot());
        //LNR，从小到大排序
        System.out.println(binaryTree.midNodeInOrder(binaryTree.getRoot()));
        //RNL,从大到小排序
        System.out.println(binaryTree.mid2NodeInOrder(binaryTree.getRoot()));
        //
        System.out.println(binaryTree.searchNode(7));
        System.out.println(binaryTree.preOrder(binaryTree.getRoot()));

        binaryTree.deleteNode(5);

        System.out.println(binaryTree.mid2NodeInOrder(binaryTree.getRoot()));
    }

}
