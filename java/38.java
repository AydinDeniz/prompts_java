// Resume.java
public class Resume {
    private String candidateName;
    private String content;

    public Resume(String candidateName, String content) {
        this.candidateName = candidateName;
        this.content = content;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Resume[" + candidateName + "]";
    }
}

// JobDescription.java
public class JobDescription {
    private String title;
    private String requirements;

    public JobDescription(String title, String requirements) {
        this.title = title;
        this.requirements = requirements;
    }

    public String getTitle() {
        return title;
    }

    public String getRequirements() {
        return requirements;
    }

    @Override
    public String toString() {
        return "JobDescription[" + title + "]";
    }
}

// NLPProcessor.java
import java.util.*;

public class NLPProcessor {
    private static final Set<String> POSITIVE_WORDS = Set.of("experienced", "skilled", "proficient", "expert");
    private static final Set<String> NEGATIVE_WORDS = Set.of("novice", "beginner", "inexperienced");

    public double matchScore(String resumeContent, String jobRequirements) {
        String[] resumeWords = resumeContent.toLowerCase().split("\\W+");
        String[] jobWords = jobRequirements.toLowerCase().split("\\W+");

        Set<String> resumeSet = new HashSet<>(Arrays.asList(resumeWords));
        Set<String> jobSet = new HashSet<>(Arrays.asList(jobWords));

        int matches = 0;
        for (String word : jobSet) {
            if (resumeSet.contains(word)) {
                matches++;
            }
        }

        return (double) matches / jobSet.size();
    }

    public String performSentimentAnalysis(String text) {
        int positiveScore = 0;
        int negativeScore = 0;

        String[] words = text.toLowerCase().split("\\W+");
        for (String word : words) {
            if (POSITIVE_WORDS.contains(word)) {
                positiveScore++;
            } else if (NEGATIVE_WORDS.contains(word)) {
                negativeScore++;
            }
        }

        if (positiveScore > negativeScore) {
            return "Positive";
        } else if (negativeScore > positiveScore) {
            return "Negative";
        }
        return "Neutral";
    }
}

// RecruitmentTool.java
import java.util.*;

public class RecruitmentTool {
    private List<Resume> resumes;
    private NLPProcessor nlpProcessor;

    public RecruitmentTool() {
        this.resumes = new ArrayList<>();
        this.nlpProcessor = new NLPProcessor();
    }

    public void addResume(Resume resume) {
        resumes.add(resume);
    }

    public void matchResumesWithJob(JobDescription job) {
        System.out.println("Matching resumes for " + job);
        for (Resume resume : resumes) {
            double score = nlpProcessor.matchScore(resume.getContent(), job.getRequirements());
            String sentiment = nlpProcessor.performSentimentAnalysis(resume.getContent());
            System.out.printf("Resume: %s, Match Score: %.2f, Sentiment: %s\n", resume.getCandidateName(), score, sentiment);
        }
    }
}

// RecruitmentToolApp.java
public class RecruitmentToolApp {
    public static void main(String[] args) {
        Resume resume1 = new Resume("Alice Johnson", "Alice is an experienced software engineer skilled in Java and database management.");
        Resume resume2 = new Resume("Bob Smith", "Bob is a novice Java developer with some experience using Python and SQL.");

        JobDescription job = new JobDescription("Java Developer", "experienced in Java and database management");

        RecruitmentTool tool = new RecruitmentTool();
        tool.addResume(resume1);
        tool.addResume(resume2);

        tool.matchResumesWithJob(job);
    }
}