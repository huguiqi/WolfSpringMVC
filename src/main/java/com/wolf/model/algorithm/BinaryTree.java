package com.wolf.model.algorithm;



import java.util.*;

/**
 * Created by sam on 2017/7/3.
 */
public class BinaryTree <T extends Comparable>{

    private TreeNode root;


    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }



    private class TreeNode{

        private Object data;
        private TreeNode parent;
        private TreeNode left;
        private TreeNode right;

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public TreeNode getParent() {
            return parent;
        }

        public void setParent(TreeNode parent) {
            this.parent = parent;
        }

        public TreeNode getLeft() {
            return left;
        }

        public void setLeft(TreeNode left) {
            this.left = left;
        }

        public TreeNode getRight() {
            return right;
        }

        public void setRight(TreeNode right) {
            this.right = right;
        }

        public TreeNode(Object data, TreeNode parent, TreeNode left, TreeNode right) {
            this.data = data;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return "TreeNode{" +
                    "data=" + data+'}';
        }
    }

    public void add(T ele){
        if(root == null){
            root = new TreeNode(ele, null, null, null);
        }
        else{
            TreeNode current = root;
            TreeNode parent = null;
            int cmp;

            //搜索合适的叶子节点，以该叶子节点为父节点添加新节点
            do{
                parent = current;
                cmp = ele.compareTo(current.data);

                //如果新节点的值大于当前节点的值
                if(cmp > 0){
                    //以当前节点的右子节点作为当前节点
                    current = current.right;
                }else{
                    current = current.left;
                }
            }while(current != null);

            //创建新节点
            TreeNode newNode = new TreeNode(ele, parent, null, null);

            //如果新节点的值大于父节点的值
            if(cmp > 0){
                parent.right = newNode;
            }else{
                parent.left = newNode;
            }
        }
    }

    /**
     * 删除节点,将删除节点的位置重新补上
     *
     * @param data
     */
    public void deleteNode(T data) {
        TreeNode tagNode = searchNode(data);

        if (tagNode == null) {
            return;
        }
        if (tagNode == root) {
            root = null;
        }
        if (tagNode.left == null && tagNode.right == null) {
            if (tagNode.parent.left == tagNode) {
                tagNode.parent.left = null;
            } else if (tagNode.parent.right == tagNode) {
                tagNode.parent.right = null;
            }
            return;
        }
        if (tagNode.left == null && tagNode.right != null) {
            if (tagNode.parent.left == tagNode) {
                tagNode.parent.left = tagNode.right;
            } else if (tagNode.parent.right == tagNode) {
                tagNode.parent.right = tagNode.right;
            }
            return;
        }

        if (tagNode.left != null && tagNode.right == null) {
            if (tagNode.parent.left == tagNode) {
                tagNode.parent.left = tagNode.left;
            } else if (tagNode.parent.right == tagNode) {
                tagNode.parent.right = tagNode.left;
            }
            return;
        }

        TreeNode leftMaxNode = tagNode.left;

        while (leftMaxNode.right != null) {
            leftMaxNode = leftMaxNode.right;
        }

        leftMaxNode.parent.right = null;
        leftMaxNode.parent = tagNode.parent;

        if (tagNode == tagNode.parent.left) {
            leftMaxNode.parent.left = leftMaxNode;
        } else {
            leftMaxNode.parent.right = leftMaxNode;
        }

        leftMaxNode.left = tagNode.left;
        leftMaxNode.right = tagNode.right;
        tagNode.left = null;
        tagNode.right = null;
        tagNode.parent = null;
    }


    public void visted(TreeNode subTree) {
        System.out.println("--name:" + subTree.data);
    }



    //中序遍历的非递归实现:LNR,从最左边的left按顺序排列
    public void nonRecInOrder(TreeNode p) {
        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode node = p;
        while (node != null || stack.size() > 0) {
            //存在左子树，把所有左子树添加到栈
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            //栈非空
            if (stack.size() > 0) {
                node = stack.pop();
                visted(node);
                node = node.right;
            }
        }
    }

    //前序遍历递归实现：NLR
    public List<TreeNode> preOrder(TreeNode subTree) {
        List<TreeNode> list = new ArrayList<TreeNode>();
        if (subTree != null) {

            list.add(subTree);
            if (subTree.left != null)
                list.addAll(preOrder(subTree.left));
            if (subTree.right != null)
                list.addAll(preOrder(subTree.right));
        }
        return list;
    }


    //中序遍历的递归实现:LNR,从最左边的left按顺序排列,就是从小到大排序
    public List<TreeNode> midNodeInOrder(TreeNode node){
        List<TreeNode> list = new ArrayList<TreeNode>();
        //保存所有左子树
        if (node !=null && node.left !=null)
            list.addAll(midNodeInOrder(node.left));
        //保存根节点
        list.add(node);
        //保存所有右子树
        if(node.right != null)
            list.addAll(midNodeInOrder(node.right));

        return list;
    }

    //中序遍历实现：RNL,从最右边的right按从大到小排序
    public List<TreeNode> mid2NodeInOrder(TreeNode node){

        List<TreeNode> list = new ArrayList<TreeNode>();
        Stack<TreeNode> stack = new Stack<TreeNode>();
            //保存所有右子树到栈
            if (node.right !=null){
                stack.push(node.right);
            }

            if (node != null){
                stack.push(node);
                if (node.right != null)
                list.addAll(mid2NodeInOrder(node.right));
            }

            //将栈顶的元素pop出来，也就是最后添加进来的数，应该是离root最远的右边的右子树，第二次调用就依次类推
            list.add(stack.pop());
            if (node.left != null){
                stack.push(node.left);
                list.addAll(mid2NodeInOrder(node.left));
            }

        return list;
    }


    //根据给定的值搜索节点
    public TreeNode searchNode(T ele)
    {
        //从根节点开始搜索
        TreeNode p = root;
        while (p != null)
        {
            int cmp = ele.compareTo(p.data);
            //如果搜索的值小于当前p节点的值
            if (cmp < 0)
            {
                //向左子树搜索
                p = p.left;
            }
            //如果搜索的值大于当前p节点的值
            else if (cmp > 0)
            {
                //向右子树搜索
                p = p.right;
            }
            else
            {
                return p;
            }
        }
        return null;
    }


    //广度优先遍历
    public  List<TreeNode> deepFirst(TreeNode root) {

        List<TreeNode> list = new ArrayList<TreeNode>();
        Queue<TreeNode> queue = new ArrayDeque<TreeNode>();
        queue.add(root);

        while (!queue.isEmpty()) {

            list.add(queue.peek());

            TreeNode twoLinkNode = queue.poll();

            if (twoLinkNode.left != null) {
                queue.add(twoLinkNode.left);
            }

            if (twoLinkNode.right != null) {
                queue.add(twoLinkNode.right);
            }
        }

        return list;
    }

}
