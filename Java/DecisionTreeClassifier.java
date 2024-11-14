
import java.util.ArrayList;
import java.util.List;

// Class to represent a Decision Tree Node
class TreeNode {
    String feature;
    double threshold;
    TreeNode left, right;
    Boolean label;

    public TreeNode(String feature, double threshold) {
        this.feature = feature;
        this.threshold = threshold;
    }

    public TreeNode(Boolean label) {
        this.label = label;
    }

    public boolean isLeaf() {
        return label != null;
    }
}

// Simple Decision Tree classifier
public class DecisionTreeClassifier {
    private TreeNode root;

    public void train(List<double[]> data, List<Boolean> labels) {
        this.root = buildTree(data, labels);
    }

    private TreeNode buildTree(List<double[]> data, List<Boolean> labels) {
        if (labels.stream().allMatch(l -> l == labels.get(0))) {
            return new TreeNode(labels.get(0));
        }
        if (data.get(0).length == 0) {
            return new TreeNode(majorityLabel(labels));
        }

        int bestFeature = 0;
        double bestThreshold = 0;
        double bestImpurity = Double.MAX_VALUE;

        for (int i = 0; i < data.get(0).length; i++) {
            double[] featureColumn = data.stream().mapToDouble(row -> row[i]).toArray();
            double threshold = findBestThreshold(featureColumn, labels);
            double impurity = giniImpurity(split(data, labels, i, threshold));

            if (impurity < bestImpurity) {
                bestImpurity = impurity;
                bestFeature = i;
                bestThreshold = threshold;
            }
        }

        List<double[]> leftData = new ArrayList<>();
        List<Boolean> leftLabels = new ArrayList<>();
        List<double[]> rightData = new ArrayList<>();
        List<Boolean> rightLabels = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i)[bestFeature] <= bestThreshold) {
                leftData.add(data.get(i));
                leftLabels.add(labels.get(i));
            } else {
                rightData.add(data.get(i));
                rightLabels.add(labels.get(i));
            }
        }

        TreeNode node = new TreeNode("Feature " + bestFeature, bestThreshold);
        node.left = buildTree(leftData, leftLabels);
        node.right = buildTree(rightData, rightLabels);
        return node;
    }

    private double findBestThreshold(double[] featureColumn, List<Boolean> labels) {
        double bestThreshold = 0;
        double bestImpurity = Double.MAX_VALUE;

        for (double threshold : featureColumn) {
            double impurity = giniImpurity(split(featureColumn, labels, threshold));
            if (impurity < bestImpurity) {
                bestImpurity = impurity;
                bestThreshold = threshold;
            }
        }
        return bestThreshold;
    }

    private List<List<Boolean>> split(List<double[]> data, List<Boolean> labels, int featureIndex, double threshold) {
        List<Boolean> left = new ArrayList<>();
        List<Boolean> right = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i)[featureIndex] <= threshold) {
                left.add(labels.get(i));
            } else {
                right.add(labels.get(i));
            }
        }
        List<List<Boolean>> splitResult = new ArrayList<>();
        splitResult.add(left);
        splitResult.add(right);
        return splitResult;
    }

    private List<List<Boolean>> split(double[] featureColumn, List<Boolean> labels, double threshold) {
        List<Boolean> left = new ArrayList<>();
        List<Boolean> right = new ArrayList<>();

        for (int i = 0; i < featureColumn.length; i++) {
            if (featureColumn[i] <= threshold) {
                left.add(labels.get(i));
            } else {
                right.add(labels.get(i));
            }
        }
        List<List<Boolean>> splitResult = new ArrayList<>();
        splitResult.add(left);
        splitResult.add(right);
        return splitResult;
    }

    private double giniImpurity(List<List<Boolean>> split) {
        double totalSize = split.get(0).size() + split.get(1).size();
        double impurity = 0;

        for (List<Boolean> group : split) {
            if (group.isEmpty()) continue;

            double score = 0;
            long positiveCount = group.stream().filter(label -> label).count();
            double p = (double) positiveCount / group.size();
            score = p * (1 - p);
            impurity += score * (group.size() / totalSize);
        }
        return impurity;
    }

    private Boolean majorityLabel(List<Boolean> labels) {
        long positiveCount = labels.stream().filter(label -> label).count();
        return positiveCount > labels.size() / 2;
    }

    public Boolean predict(double[] features) {
        TreeNode node = root;
        while (!node.isLeaf()) {
            if (features[Integer.parseInt(node.feature.split(" ")[1])] <= node.threshold) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return node.label;
    }

    public static void main(String[] args) {
        DecisionTreeClassifier tree = new DecisionTreeClassifier();

        List<double[]> data = new ArrayList<>();
        data.add(new double[]{2.3, 1.5});
        data.add(new double[]{1.2, 3.8});
        data.add(new double[]{3.3, 2.1});
        data.add(new double[]{2.9, 3.0});

        List<Boolean> labels = new ArrayList<>();
        labels.add(true);
        labels.add(false);
        labels.add(true);
        labels.add(false);

        tree.train(data, labels);
        System.out.println("Prediction for [2.5, 2.0]: " + tree.predict(new double[]{2.5, 2.0}));
    }
}
