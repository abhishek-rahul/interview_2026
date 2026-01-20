// 1
// https://leetcode.com/problems/binary-tree-level-order-traversal/

class Level_Order_Traversal {
    public List<List<Integer>> levelOrder(TreeNode root) {
        Queue<TreeNode> q = new LinkedList<>();
        if (root != null) {
            q.offer(root);
        }

        int qlen;
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        while (!q.isEmpty()) {
            qlen = q.size();
            List<Integer> lst = new ArrayList<Integer>();
            for (int i = 0; i < qlen; i++) {
                TreeNode tn = q.poll();
                if (tn != null) {
                    lst.add(tn.val);
                    if (tn.left != null) {
                        q.offer(tn.left);
                    }
                    if (tn.right != null) {
                        q.offer(tn.right);
                    }
                }
            }
            ans.add(lst);
        }
        return ans;
    }
}

// 2
// https://www.geeksforgeeks.org/problems/left-view-of-binary-tree/1

class Left_View {

    private static int maxLevel;

    private void leftViewSolve(Node root,ArrayList<Integer> ans,int level) {
        if(root==null) {
          return ;
        }
        if (maxLevel<level) {
            ans.add(root.data);
            maxLevel++;
        }
        leftViewSolve(root.left,ans,level+1);
        leftViewSolve(root.right,ans,level+1);
    }

    public ArrayList<Integer> leftView(Node root) {
        // code here
        ArrayList<Integer> ans  = new ArrayList<Integer>();
        maxLevel = 0;
        if(root!=null) {
            leftViewSolve(root,ans,1);
        }
        return ans;
    }
}

// 3
// https://leetcode.com/problems/binary-tree-right-side-view

class Rigth_View {
    private static int maxLevel;
    private void rightSideViewSolve(TreeNode root,List<Integer> ans,int level) {
        if(root==null) {
          return ;
        }
        if (maxLevel<level) {
            ans.add(root.val);
            maxLevel++;
        }
        rightSideViewSolve(root.right,ans,level+1);
        rightSideViewSolve(root.left,ans,level+1);
    }

    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> ans  = new ArrayList<Integer>();
        maxLevel = 0;
        if(root!=null) {
            rightSideViewSolve(root,ans,1);
        }
        return ans;
    }
}

// 4
// https://leetcode.com/problems/binary-tree-paths/

class Binary_Tree_Paths {
    private List<String> ans;

    public void solve(TreeNode root, String str) {
        if (root == null) {
            return;
        }
        if (root != null) {
            if (root.left == null && root.right == null) {
                if (str.length() != 0) {
                    ans.add(str + "->" + root.val);
                } else {
                    ans.add(str + root.val);
                }
            } else {
                if (root.left != null) {
                    if (str.length() != 0) {
                        solve(root.left, str + "->" + root.val);
                    } else {
                        solve(root.left, str + root.val);
                    }
                }
                if (root.right != null) {
                    if (str.length() != 0) {
                        solve(root.right, str + "->" + root.val);
                    } else {
                        solve(root.right, str + root.val);
                    }
                }
            }
        }
    }

    public List<String> binaryTreePaths(TreeNode root) {
        ans = new ArrayList<String>();
        if (root == null) {
            return ans;
        }
        String str = "";
        solve(root, str);
        return ans;
    }
}


// 5
// https://leetcode.com/problems/binary-tree-right-side-view/

class Solution {
    private static int maxLevel;
    private void rightSideViewSolve(TreeNode root,List<Integer> ans,int level) {
        if(root==null) {
          return ;
        }
        if (maxLevel<level) {
            ans.add(root.val);
            maxLevel++;
        }
        rightSideViewSolve(root.right,ans,level+1);
        rightSideViewSolve(root.left,ans,level+1);
    }

    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> ans  = new ArrayList<Integer>();
        maxLevel = 0;
        if(root!=null) {
            rightSideViewSolve(root,ans,1);
        }
        return ans;
    }
}


// 6
// https://www.geeksforgeeks.org/problems/diagonal-traversal-of-binary-tree


class DiagonalTraversal {

    private void solveDiagonalTraversal(Node root,SortedMap<Integer, List<Integer>> sm,int diagonalLevel ) {
        if(root==null) {
            return ;
        }
        if(!sm.containsKey(diagonalLevel)){
            sm.put(diagonalLevel,new ArrayList<Integer>());
        }
        List<Integer> mappedArray = sm.get(diagonalLevel);
        mappedArray.add(root.data);

        solveDiagonalTraversal(root.left,sm,diagonalLevel+1);
        solveDiagonalTraversal(root.right,sm,diagonalLevel);
    }

    public ArrayList<Integer> diagonal(Node root) {
        // add your code here.
        SortedMap<Integer, List<Integer>> sm = new TreeMap<Integer, List<Integer>>();
        ArrayList<Integer> ans  = new ArrayList<Integer>();
        solveDiagonalTraversal(root,sm,0);
        Integer key;
        List<Integer> value;
        for (Map.Entry<Integer, List<Integer>> entry : sm.entrySet()) {
            value = entry.getValue();
            for(Integer val:value) {
                ans.add(val);
            }
        }
        return ans;
    }
}

// 7 TopView
// https://www.geeksforgeeks.org/problems/diagonal-traversal-of-binary-tree

class QueuePair {
    Integer verticalLevel;
    Node root;
    QueuePair(int verticalLevel,Node root) {
        this.verticalLevel = verticalLevel;
        this.root = root;
    }
}

class Solution {
    private void solveTopView(Node root,SortedMap<Integer, Integer> sm,int verticalLevel ) {
        if (root==null) {
            return ;
        }
        Queue<QueuePair> qp = new LinkedList<>();
        qp.offer(new QueuePair(verticalLevel,root));
        QueuePair temp ;
        int len ;
        while(!qp.isEmpty()){
            len = qp.size();
            for(int i=0;i<len;i++) {
                temp = qp.poll();
                if(temp!=null){
                    // check map
                    if(!sm.containsKey(temp.verticalLevel)){
                        sm.put(temp.verticalLevel,temp.root.data);
                    }
                    if (temp.root.left!=null) {
                        qp.offer(new QueuePair((temp.verticalLevel-1),temp.root.left));
                    }
                    if(temp.root.right!=null) {
                        qp.offer(new QueuePair((temp.verticalLevel+1),temp.root.right));
                    }
                }
            }
        }
    }

    public ArrayList<Integer> topView(Node root) {
        // add your code here.
        SortedMap<Integer, Integer> sm = new TreeMap<Integer, Integer>();
        ArrayList<Integer> ans  = new ArrayList<Integer>();
        solveTopView(root,sm,0);
        Integer value;
        for (Map.Entry<Integer, Integer> entry : sm.entrySet()) {
            value = entry.getValue();
            ans.add(value);
        }
        return ans;
    }
}

// 8 BottomView
// https://www.geeksforgeeks.org/problems/bottom-view-of-binary-tree

class QueuePair {
    Integer verticalLevel;
    Node root;
    QueuePair(int verticalLevel,Node root) {
        this.verticalLevel = verticalLevel;
        this.root = root;
    }
}

class Solution {
    private void solveTopView(Node root,SortedMap<Integer, Integer> sm,int verticalLevel ) {
        if (root==null) {
            return ;
        }
        Queue<QueuePair> qp = new LinkedList<>();
        qp.offer(new QueuePair(verticalLevel,root));
        QueuePair temp ;
        int len ;
        while(!qp.isEmpty()){
            len = qp.size();
            for(int i=0;i<len;i++) {
                temp = qp.poll();
                if(temp!=null){
                    // check map
                    if(!sm.containsKey(temp.verticalLevel)){
                        sm.put(temp.verticalLevel,temp.root.data);
                    }
                    if (temp.root.left!=null) {
                        qp.offer(new QueuePair((temp.verticalLevel-1),temp.root.left));
                    }
                    if(temp.root.right!=null) {
                        qp.offer(new QueuePair((temp.verticalLevel+1),temp.root.right));
                    }
                }
            }
        }
    }

    public ArrayList<Integer> topView(Node root) {
        // add your code here.
        SortedMap<Integer, Integer> sm = new TreeMap<Integer, Integer>();
        ArrayList<Integer> ans  = new ArrayList<Integer>();
        solveTopView(root,sm,0);
        Integer value;
        for (Map.Entry<Integer, Integer> entry : sm.entrySet()) {
            value = entry.getValue();
            ans.add(value);
        }
        return ans;
    }
}


// 9 Boundary Tree traversal
// https://www.geeksforgeeks.org/problems/boundary-traversal-of-binary-tree

class Solution
{
    public void traverseLeft(ArrayList<Integer> arr,Node node)
    {
        if(node==null)
            return ;
        if(node.left==null && node.right == null)
        {
            return ;
        }
        if(node.left!=null)
        {
            arr.add(node.data);
            traverseLeft(arr,node.left);
        }
        else
        {
            arr.add(node.data);
            traverseLeft(arr,node.right);
        }
    }

    public void traverseRight(ArrayList<Integer> arr,Node node)
    {
        if(node==null)
            return ;
        if(node.left==null && node.right == null)
        {
            return ;
        }
        if(node.right!=null)
        {
            traverseRight(arr,node.right);
            arr.add(node.data);
        }
        else
        {
            traverseRight(arr,node.left);
            arr.add(node.data);
        }
    }

    public void traverseLeave(ArrayList<Integer> arr,Node node)
    {
        if(node==null)
            return ;
        if(node.left==null && node.right == null)
        {
            arr.add(node.data);
            return ;
        }
        traverseLeave(arr,node.left);
        traverseLeave(arr,node.right);
    }


	ArrayList<Integer> printBoundary(Node node)
	{

	    ArrayList<Integer> arr = new ArrayList<Integer>();
	    arr.clear();
	    if(node==null)
	        return arr;
	    arr.add(node.data);
	    traverseLeft(arr,node.left);
	    traverseLeave(arr,node.left);
	    traverseLeave(arr,node.right);
	    traverseRight(arr,node.right);

	    return arr;
	}
}

// 10 Check If Two Tree are identical
// https://leetcode.com/problems/same-tree/

class Solution {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p==null && q==null) {
            return true;
        }
        if(p==null || q==null) {
            return false;
        }
        return ((boolean)(p.val==q.val) && isSameTree(p.left,q.left) && isSameTree(p.right,q.right));
    }
}

// 11
// https://leetcode.com/problems/symmetric-tree
//
class Solution {

    private boolean solveIsSymmetric(TreeNode left,TreeNode right) {
        if (left==null && right==null) {
            return true;
        }else if(left==null || right==null) {
            return false;
        }
        return (left.val==right.val) && solveIsSymmetric(left.left,right.right) && solveIsSymmetric(left.right,right.left);
    }

    public boolean isSymmetric(TreeNode root) {
        if(root==null) {
            return true;
        }
        return solveIsSymmetric(root.left,root.right);
    }
}

// 12
// https://leetcode.com/problems/maximum-depth-of-binary-tree/
// 00:02:04

class Solution {
    public int maxDepth(TreeNode root) {
        if(root==null) {
            return 0;
        }
        return 1+ Math.max(maxDepth(root.left),maxDepth(root.right));
    }
}


// 13
// https://leetcode.com/problems/balanced-binary-tree/description/
// 00:15:01

class Solution {

    private int heightTree(TreeNode root) {
        if(root==null){
            return 0;
        }
        int lheight = heightTree(root.left);
        int rheight = heightTree(root.right);

        if (lheight <0 || rheight<0) {
            return -1;
        }
        if ( Math.abs(lheight-rheight)<=1) {
            return 1+Math.max(lheight,rheight);
        }
        return -1;
    }

    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }

        int height;
        height = heightTree(root);
        if (height >= 0) {
            return true;
        }
        return false;
    }
}


// 14
// https://leetcode.com/problems/diameter-of-binary-tree/description/
// 00:10:00

class Solution {

    private static int maxi ;

    private int solveDiameterOfBinaryTree(TreeNode root) {
        if(root==null) {
            return 0;
        }
        int lh = solveDiameterOfBinaryTree(root.left);
        int rh = solveDiameterOfBinaryTree(root.right);

        maxi = Math.max(maxi,1+lh+rh);
        return 1+Math.max(lh,rh);
    }

    public int diameterOfBinaryTree(TreeNode root) {
        if ( root == null) {
            return 0;
        }
        maxi = 1;
        int s = solveDiameterOfBinaryTree(root);
        return maxi-1;
    }
}

// 15
// https://leetcode.com/problems/diameter-of-binary-tree/description/
// 00:10:00

class Solution {

    private static int maxi ;

    private int solveDiameterOfBinaryTree(TreeNode root) {
        if(root==null) {
            return 0;
        }
        int lh = solveDiameterOfBinaryTree(root.left);
        int rh = solveDiameterOfBinaryTree(root.right);

        maxi = Math.max(maxi,1+lh+rh);
        return 1+Math.max(lh,rh);
    }

    public int diameterOfBinaryTree(TreeNode root) {
        if ( root == null) {
            return 0;
        }
        maxi = 1;
        int s = solveDiameterOfBinaryTree(root);
        return maxi-1;
    }
}


// 16
// https://leetcode.com/problems/binary-tree-maximum-path-sum/
// 00:22:04

class Solution {
    private int maxi;

    private int solveMaxPathSum(TreeNode root) {
        if (root==null) {
            return 0;
        }

        int left = solveMaxPathSum(root.left);
        int right = solveMaxPathSum(root.right);

        int tmaxi = Math.max(Math.max(left+root.val,right+root.val),Math.max(root.val,root.val+left+right));
        maxi = Math.max(maxi,tmaxi);
        int tempMax = Math.max(Math.max(left+root.val,right+root.val),root.val);
        return tempMax;    }

    public int maxPathSum(TreeNode root) {
        if(root==null) {
            return 0;
        }
        maxi = Integer.MIN_VALUE;
        int s= solveMaxPathSum(root);
        return maxi;
    }
}

Another Way :
int ans;
    int maxPathSumSolve(Node root){

        if(root==null) return 0;

        int lsum = Math.max(0 , maxPathSumSolve(root.left));
        int rsum = Math.max(0 , maxPathSumSolve(root.right));

        ans = Math.max(ans , lsum + rsum + root.data);

        return root.data + Math.max(lsum, rsum);
    }
    public int maxPathSum(Node root) {
        ans = Integer.MIN_VALUE;
        maxPathSumSolve(root);
        return ans;
    }

// 17
// https://leetcode.com/problems/binary-tree-paths/description/
//

class Solution {
    private List<String> ans;

    public void solve(TreeNode root, String str) {
        if (root == null) {
            return;
        }
        if (root != null) {
            if (root.left == null && root.right == null) {
                if (str.length() != 0) {
                    ans.add(str + "->" + root.val);
                } else {
                    ans.add(str + root.val);
                }
            } else {
                if (root.left != null) {
                    if (str.length() != 0) {
                        solve(root.left, str + "->" + root.val);
                    } else {
                        solve(root.left, str + root.val);
                    }
                }
                if (root.right != null) {
                    if (str.length() != 0) {
                        solve(root.right, str + "->" + root.val);
                    } else {
                        solve(root.right, str + root.val);
                    }
                }
            }
        }
    }

    public List<String> binaryTreePaths(TreeNode root) {
        ans = new ArrayList<String>();
        if (root == null) {
            return ans;
        }
        String str = "";
        solve(root, str);
        return ans;
    }
}

// 18
// https://leetcode.com/problems/maximum-width-of-binary-tree/
//  00:58:00

class QueuePair {
    TreeNode root;
    Integer depth;

    QueuePair(TreeNode root, int depth) {
        this.root = root;
        this.depth = depth;
    }
}

class Solution {

    private int solveWidthOfBinaryTree(TreeNode root) {
        int maxi = 1;
        Queue<QueuePair> qp = new LinkedList<>();
        qp.offer(new QueuePair(root, 0));
        QueuePair temp;
        int size;
        int minDepth, maxDepth;
        while (!qp.isEmpty()) {
            size = qp.size();
            int base = qp.peek().depth;
            minDepth = Integer.MAX_VALUE;
            maxDepth = Integer.MIN_VALUE;
            for (int i = 0; i < size; i++) {
                temp = qp.poll();
                int normDepth = temp.depth - base;
                if (temp.root != null) {
                    minDepth = Math.min(minDepth, normDepth);
                    maxDepth = Math.max(maxDepth, normDepth);
                    if (temp.root.left != null) {
                        qp.offer(new QueuePair(temp.root.left, 2 * normDepth));
                    }
                    if (temp.root.right != null) {
                        qp.offer(new QueuePair(temp.root.right, 2 * normDepth + 1));
                    }
                }
            }
            maxi = Math.max(maxi, maxDepth - minDepth + 1);
        }
        return maxi;
    }

    public int widthOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int width = solveWidthOfBinaryTree(root);
        return width;
    }
}


// 19
// https://leetcode.com/problems/maximum-width-of-binary-tree/
//
