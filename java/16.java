import java.util.Arrays;
import java.util.Random;

class Individual {
    private int[] genes;
    private int fitness;

    public Individual(int geneLength) {
        genes = new int[geneLength];
        Random random = new Random();
        for (int i = 0; i < genes.length; i++) {
            genes[i] = random.nextInt(2);
        }
        calculateFitness();
    }

    public void calculateFitness() {
        fitness = 0;
        for (int gene : genes) {
            fitness += gene;
        }
    }

    public int getFitness() {
        return fitness;
    }

    public int[] getGenes() {
        return genes;
    }
}

class Population {
    private Individual[] individuals;

    public Population(int populationSize, int geneLength) {
        individuals = new Individual[populationSize];
        for (int i = 0; i < populationSize; i++) {
            individuals[i] = new Individual(geneLength);
        }
    }

    public Individual getFittest() {
        Individual fittest = individuals[0];
        for (int i = 1; i < individuals.length; i++) {
            if (individuals[i].getFitness() > fittest.getFitness()) {
                fittest = individuals[i];
            }
        }
        return fittest;
    }

    public Individual[] getIndividuals() {
        return individuals;
    }
}

class GeneticAlgorithm {
    private static final int GENE_LENGTH = 10;
    private static final int POPULATION_SIZE = 6;
    private static final int MAX_GENERATIONS = 50;

    private static final double MUTATION_RATE = 0.01;
    private static final double CROSSOVER_RATE = 0.95;

    public static void main(String[] args) {
        Population population = new Population(POPULATION_SIZE, GENE_LENGTH);
        int generationCount = 0;

        while (generationCount < MAX_GENERATIONS) {
            generationCount++;
            System.out.println("Generation: " + generationCount + " Fittest: " + population.getFittest().getFitness());
            population = evolvePopulation(population);
        }

        System.out.println("Solution found in generation " + generationCount);
        System.out.println("Genes: " + Arrays.toString(population.getFittest().getGenes()));
    }

    private static Population evolvePopulation(Population pop) {
        Population newPopulation = new Population(pop.getIndividuals().length, GENE_LENGTH);
        for (int i = 0; i < pop.getIndividuals().length; i++) {
            Individual parent1 = selectFittest(pop);
            Individual parent2 = selectFittest(pop);
            Individual offspring = crossover(parent1, parent2);
            newPopulation.getIndividuals()[i] = offspring;
        }
        for (int i = 0; i < newPopulation.getIndividuals().length; i++) {
            mutate(newPopulation.getIndividuals()[i]);
        }
        return newPopulation;
    }

    private static Individual selectFittest(Population pop) {
        return pop.getFittest();
    }

    private static Individual crossover(Individual parent1, Individual parent2) {
        Individual offspring = new Individual(GENE_LENGTH);
        for (int i = 0; i < GENE_LENGTH; i++) {
            if (Math.random() <= CROSSOVER_RATE) {
                offspring.getGenes()[i] = parent1.getGenes()[i];
            } else {
                offspring.getGenes()[i] = parent2.getGenes()[i];
            }
        }
        return offspring;
    }

    private static void mutate(Individual indiv) {
        for (int i = 0; i < GENE_LENGTH; i++) {
            if (Math.random() <= MUTATION_RATE) {
                int gene = indiv.getGenes()[i];
                indiv.getGenes()[i] = 1 - gene;
            }
        }
    }
}