import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Node {
    private int id;
    private boolean isLeader;
    private String agreedValue;

    public Node(int id) {
        this.id = id;
        this.isLeader = false;
    }

    public int getId() {
        return id;
    }

    public boolean isLeader() {
        return isLeader;
    }

    public void setLeader(boolean leader) {
        isLeader = leader;
    }

    public void proposeValue(String value) {
        if (isLeader) {
            System.out.println("Node " + id + " proposes the value: " + value);
            agreedValue = value;
        }
    }

    public String getAgreedValue() {
        return agreedValue;
    }
}

class ConsensusNetwork {
    private List<Node> nodes;
    private Node leader;

    public ConsensusNetwork(int numNodes) {
        nodes = new ArrayList<>();
        for (int i = 0; i < numNodes; i++) {
            nodes.add(new Node(i));
        }
        electLeader();
    }

    private void electLeader() {
        Random random = new Random();
        leader = nodes.get(random.nextInt(nodes.size()));
        leader.setLeader(true);
        System.out.println("Leader elected: Node " + leader.getId());
    }

    public void reachConsensus(String value) {
        leader.proposeValue(value);
        for (Node node : nodes) {
            System.out.println("Node " + node.getId() + " has agreed on value: " + leader.getAgreedValue());
        }
    }
}

public class DistributedConsensusSimulation {
    public static void main(String[] args) {
        ConsensusNetwork network = new ConsensusNetwork(5);
        network.reachConsensus("ConsensusValue");
    }
}