package com.nj.zddemo.ui.adapter.tree;

import com.nj.zddemo.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-08-09.
 */

public class TreeHelper {

    /**
     * 传入我们的普通bean，转化为我们排序后的Node
     *
     * @param datas
     * @param defaultExpandLevel
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static <T> List<Node> getSortedNodes(List<T> datas, int defaultExpandLevel) throws IllegalArgumentException,
            IllegalAccessException {
        List<Node> result = new ArrayList<Node>();
        //将用户数据转化为List<Node>以及设置Node间关系
        List<Node> nodes = convetData2Node(datas);
        //拿到根节点
        List<Node> rootNodes = getRootNodes(nodes);
        //排序，从根节点开始依次把节点添加到result中
        for (Node node : rootNodes) {
            addNode(result, node, defaultExpandLevel, 1);
        }
        return result;
    }

    /**
     * 过滤出所有可见的Node
     *
     * @param nodes
     * @return
     */
    public static List<Node> filterVisibleNode(List<Node> nodes) {
        List<Node> result = new ArrayList<Node>();
        for (Node node : nodes) {
            // 如果为跟节点，或者上层目录为展开状态
            if (node.isRoot() || node.isParentExpand()) {
                setNodeIcon(node);
                result.add(node);
            }
        }
        return result;
    }

    /**
     * 将我们的数据转化为树的节点
     *
     * @param datas
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    private static <T> List<Node> convetData2Node(List<T> datas) throws IllegalArgumentException, IllegalAccessException {
        List<Node> nodes = new ArrayList<Node>();
        Node node = null;
        //根据注解解析并转化数据
        for (T t : datas) {
            String id = null;
            String pId = null;
            String label = null;
            Class<? extends Object> clazz = t.getClass();
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field f : declaredFields) {
                if (f.getAnnotation(TreeNodeId.class) != null) {
                    f.setAccessible(true);
                    id = (String) f.get(t);
                }
                if (f.getAnnotation(TreeNodePid.class) != null) {
                    f.setAccessible(true);
                    pId = (String) f.get(t);
                }
                if (f.getAnnotation(TreeNodeLabel.class) != null) {
                    f.setAccessible(true);
                    label = (String) f.get(t);
                }
                if (id != null && pId != null && label != null) {
                    break;
                }
            }
            node = new Node(id, pId, label);
            nodes.add(node);
        }
        // 设置Node间，父子关系;让每两个节点都比较一次，即可设置其中的关系
        for (int i = 0; i < nodes.size(); i++) {
            Node n = nodes.get(i);
            for (int j = i + 1; j < nodes.size(); j++) {
                Node m = nodes.get(j);
                if (m.getpId().equals(n.getId())) {
                    n.getChildren().add(m);
                    m.setParent(n);
                } else if (m.getId().equals(n.getpId())) {
                    m.getChildren().add(n);
                    n.setParent(m);
                }
            }
        }
        // 设置图片
        for (Node n : nodes) {
            setNodeIcon(n);
        }
        return nodes;
    }

    private static List<Node> getRootNodes(List<Node> nodes) {
        List<Node> root = new ArrayList<Node>();
        for (Node node : nodes) {
            if (node.isRoot())
                root.add(node);
        }
        return root;
    }

    /**
     * 把一个节点上的所有的内容(包含子类别)都添加到集合中
     */
    private static void addNode(List<Node> nodes, Node node, int defaultExpandLeval, int currentLevel) {
        // 先把当前的节点添加上
        nodes.add(node);
        // 如果默认展示的节点级别等于或者大于当前节点所属的级别，就将当前的节点设置为展开
        if (defaultExpandLeval >= currentLevel) {
            node.setExpand(true);
        }
        // 如果这个节点是叶子节点，直接返回
        if (node.isLeaf())
            return;
        // 不是叶子节点，就递归添加
        for (int i = 0; i < node.getChildren().size(); i++) {
            addNode(nodes, node.getChildren().get(i), defaultExpandLeval, currentLevel + 1);
        }
    }

    /**
     * 设置节点的图标
     *
     * @param node
     */
    private static void setNodeIcon(Node node) {
        // 这里就不添加图片了
//        if (node.getChildren().size() > 0 && node.isExpand()) {
//            node.setIcon(R.drawable.tree_ex);
//        } else if (node.getChildren().size() > 0 && !node.isExpand()) {
//            node.setIcon(R.drawable.tree_ec);
//        } else {
//            // 不能是-1，否则会报android.content.res.Resources$NotFoundException: Resource ID #0xffffffff
//            // 是0就可以了
//            node.setIcon(0);
//        }
    }

}
