// Voter.java
public class Voter {
    private String voterId;
    private String name;
    private boolean hasVoted;

    public Voter(String voterId, String name) {
        this.voterId = voterId;
        this.name = name;
        this.hasVoted = false;
    }

    public String getVoterId() {
        return voterId;
    }

    public String getName() {
        return name;
    }

    public boolean hasVoted() {
        return hasVoted;
    }

    public void castVote() {
        this.hasVoted = true;
    }

    @Override
    public String toString() {
        return "Voter[ID: " + voterId + ", Name: " + name + ", Has Voted: " + hasVoted + "]";
    }
}

// Vote.java
public class Vote {
    private String voterId;
    private String candidate;

    public Vote(String voterId, String candidate) {
        this.voterId = voterId;
        this.candidate = candidate;
    }

    public String getVoterId() {
        return voterId;
    }

    public String getCandidate() {
        return candidate;
    }

    @Override
    public String toString() {
        return "Vote[VoterID: " + voterId + ", Candidate: " + candidate + "]";
    }
}

// Block.java
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Block {
    private int index;
    private long timestamp;
    private List<Vote> votes;
    private String previousHash;
    private String hash;
    private int nonce;

    public Block(int index, List<Vote> votes, String previousHash) {
        this.index = index;
        this.timestamp = new Date().getTime();
        this.votes = votes;
        this.previousHash = previousHash;
        this.hash = calculateHash();
    }

    public String calculateHash() {
        return Integer.toString(Objects.hash(index, timestamp, votes.toString(), previousHash, nonce));
    }

    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0');
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block Mined: " + hash);
    }

    @Override
    public String toString() {
        return "Block[Index: " + index + ", Votes: " + votes + ", Hash: " + hash + "]";
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getHash() {
        return hash;
    }
}

// Blockchain.java
import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    private List<Block> chain;
    private int difficulty;

    public Blockchain(int difficulty) {
        this.chain = new ArrayList<>();
        this.difficulty = difficulty;
        // Genesis block
        chain.add(new Block(0, new ArrayList<>(), "0"));
    }

    public void addBlock(List<Vote> votes) {
        Block newBlock = new Block(chain.size(), votes, chain.get(chain.size() - 1).getHash());
        newBlock.mineBlock(difficulty);
        chain.add(newBlock);
    }

    public boolean isChainValid() {
        for (int i = 1; i < chain.size(); i++) {
            Block currentBlock = chain.get(i);
            Block previousBlock = chain.get(i - 1);

            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                return false;
            }
            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }
        }
        return true;
    }

    public List<Block> getChain() {
        return chain;
    }
}

// VotingPlatform.java
import java.util.ArrayList;
import java.util.List;

public class VotingPlatform {
    private List<Voter> voters;
    private Blockchain blockchain;

    public VotingPlatform(int difficulty) {
        this.voters = new ArrayList<>();
        this.blockchain = new Blockchain(difficulty);
    }

    public void registerVoter(String voterId, String name) {
        voters.add(new Voter(voterId, name));
    }

    public void castVote(String voterId, String candidate) {
        for (Voter voter : voters) {
            if (voter.getVoterId().equals(voterId) && !voter.hasVoted()) {
                voter.castVote();
                Vote vote = new Vote(voterId, candidate);
                List<Vote> votes = new ArrayList<>();
                votes.add(vote);
                blockchain.addBlock(votes);
                System.out.println("Vote cast for " + candidate + " by voter ID " + voterId);
                return;
            }
        }
        System.out.println("Voter not found or has already voted.");
    }

    public void displayBlockchain() {
        for (Block block : blockchain.getChain()) {
            System.out.println(block);
        }
    }

    public boolean verifyElectionIntegrity() {
        return blockchain.isChainValid();
    }
}

// VotingApp.java
public class VotingApp {
    public static void main(String[] args) {
        VotingPlatform platform = new VotingPlatform(4);

        platform.registerVoter("VOTER1", "John Doe");
        platform.registerVoter("VOTER2", "Jane Doe");

        platform.castVote("VOTER1", "Candidate A");
        platform.castVote("VOTER2", "Candidate B");

        System.out.println("\nBlockchain:");
        platform.displayBlockchain();

        System.out.println("\nIs blockchain valid? " + platform.verifyElectionIntegrity());
    }
}