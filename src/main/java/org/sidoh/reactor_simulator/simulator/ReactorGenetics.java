package org.sidoh.reactor_simulator.simulator;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.StringGene;

public class ReactorGenetics {


  public class ReactorFitnessFunction extends FitnessFunction {

    private final BigReactorSimulator simulator;

    public ReactorFitnessFunction(boolean activeCooling) {
      this.simulator = new BigReactorSimulator(activeCooling, 500);
    }

    @Override
    protected double evaluate(IChromosome chromosome) {
      ReactorResult result = getResult(chromosome);
      return result.efficiency;
    }

    public ReactorResult getResult(IChromosome chromosome) {
      String s = makeReactorString(chromosome);
      FakeReactorWorld reactor = reactorFactory.create(s);
      return simulator.simulate(reactor);
    }

    public String makeReactorString(IChromosome chromosome) {
      String reactor = "";
      for (int i = 0; i < chromosome.size(); i++) {
        reactor += (String)chromosome.getGene(i).getAllele();
      }
      return reactor;
    }
  }

  public Gene[] getGenes(Configuration conf) throws InvalidConfigurationException {
    int chromosomeLength = (xSize - 2) * (zSize - 2);
    Gene[] genes = new Gene[chromosomeLength];
    for (int i = 0; i < chromosomeLength; i++) {
      genes[i] = new StringGene(conf, 1, 1, "CXE");
    }
    return genes;
  }

  public void jgapGenetics() throws InvalidConfigurationException {
    ReactorFitnessFunction function = new ReactorFitnessFunction(true);
    Configuration conf = new DefaultConfiguration();
    Gene[] genes = getGenes(conf);
    Chromosome sample = new Chromosome(conf, genes);
    conf.setSampleChromosome(sample);
    conf.setPopulationSize(500);
    conf.setFitnessFunction(function);
    Genotype population = Genotype.randomInitialGenotype(conf);
    for (int i = 0; i < 30; i++) {
      population.evolve();
      System.out.println("Gen " + i);
      System.out.println(function.getResult(population.getFittestChromosome()));
    }
    IChromosome chromosome = population.getFittestChromosome();
    display(function.makeReactorString(chromosome));
    System.out.println(function.getResult(chromosome));
  }


  private final int xSize;
  private final int zSize;
  private final int height;
  private double selectionFactor;
  private final FakeReactorWorld.Factory reactorFactory;

  private ExecutorService service = new ScheduledThreadPoolExecutor(10);

  public ReactorGenetics(int xSize, int zSize, int height) {
    this.xSize = xSize;
    this.zSize = zSize;
    this.height = height;
    this.reactorFactory = new FakeReactorWorld.Factory(xSize, zSize, height);
  }

  public static final List<Character> weightedGenes = Lists.newArrayList(
      'X',
      'C',
      'C',
      'C',
      'E'
  );

  public String makeCandidate() {
    Random r = new Random();
    String reactor = "";
    for (int i = 0; i < (xSize - 2) * (zSize - 2); i++) {
      reactor += weightedGenes.get(r.nextInt(weightedGenes.size()));
    }
    return reactor;
  }

  public static class SimulationRun implements Runnable {

    private FakeReactorWorld reactor;
    private ReactorGenome genome;
    private BigReactorSimulator function;
    private final Evaluator evaluator;

    public SimulationRun(FakeReactorWorld reactor, ReactorGenome genome, BigReactorSimulator function, Evaluator evaluator) {
      this.reactor = reactor;
      this.genome = genome;
      this.function = function;
      this.evaluator = evaluator;
    }

    public void run() {
      ReactorResult result = function.simulate(reactor);
      genome.fitness = evaluator.eval(result);
      genome.result = result;
    }

    public static double calcFitness(ReactorResult result, ReactorGenome genome) {
      return (result.efficiency + (result.output * (1 / 8.0)));
    }

  }

  public List<ReactorGenome> select(List<ReactorGenome> genomes, BigReactorSimulator simulator, Evaluator evaluator) {
    fitness(genomes, simulator, evaluator);
    Collections.sort(genomes);
    ReactorGenome best = genomes.get(genomes.size() - 1);
    display(best.reactor);
    System.out.println("Best: " + best.result);
    System.out.println("Fitness: " + evaluator.eval(best.result));

    selectionFactor = 0.20;
    int toTake = (int)(genomes.size() * selectionFactor);
    List<ReactorGenome> selected = Lists.newArrayList();
    for (int i = genomes.size() - 1; i > genomes.size() - toTake; i--) {
      selected.add(genomes.get(i));
    }
    return selected;
  }

  public void fitness(List<ReactorGenome> genomes, BigReactorSimulator simulator, Evaluator evaluator) {
    long start = System.currentTimeMillis();
    service = new ScheduledThreadPoolExecutor(50);
    for (ReactorGenome genome : genomes) {
      SimulationRun run = new SimulationRun(reactorFactory.create(genome.reactor), genome, simulator, evaluator);
      service.submit(run);
    }
    try {
      service.shutdown();
      service.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    //System.out.println("Fitness time: " + (System.currentTimeMillis() - start));
  }

  public List<ReactorGenome> generation(List<ReactorGenome> selected, int popSize) {
    List<ReactorGenome> nextGeneration = Lists.newArrayList();

    for (int i = 0; i < Math.ceil(1 / selectionFactor) + 1; i++) {
      Collections.shuffle(selected);
      addGeneration(selected, nextGeneration);
    }
    nextGeneration = nextGeneration.subList(0, popSize);
    List<ReactorGenome> mutatedNextGeneration = Lists.newArrayList();
    for (ReactorGenome reactorGenome : nextGeneration) {
      mutatedNextGeneration.add(reactorGenome.mutate(popSize));
    }
    return mutatedNextGeneration;
  }

  public void addGeneration(List<ReactorGenome> selected, List<ReactorGenome> nextGeneration) {
    for (int i = 0; i < selected.size(); i++) {
      ReactorGenome primary = selected.get(i % selected.size());
      ReactorGenome secondary = selected.get((i + 1) % selected.size());
      ReactorGenome tertiary = selected.get((i + 2) % selected.size());
      nextGeneration.add(primary.breed(secondary, tertiary));
    }
  }

  public ReactorGenome breedGoodReactor(int startingPopulation, int generations, boolean activelyCooled, int ticks, Evaluator evaluator) {
    BigReactorSimulator simulator = new BigReactorSimulator(activelyCooled, ticks);

    List<ReactorGenome> population = Lists.newArrayList();
    for (int i = 0; i < startingPopulation; i++) {
      population.add(new ReactorGenome(makeCandidate()));
    }

    for (int i = 0; i < generations; i++) {
      List<ReactorGenome> selected = select(population, simulator, evaluator);
      population = generation(selected, startingPopulation);
      System.out.println("Gen: " + i);
    }

    fitness(population, simulator, evaluator);
    Collections.sort(population);
    return population.get(population.size() - 1);
  }




  public static void main(String[] args) throws Exception {
    BigReactorSimulator.init();
    Map<String, Evaluator> evaluatorMap = Maps.newHashMap();
    evaluatorMap.put("eff", new Evaluator() {
      @Override
      public double eval(ReactorResult result) {
        return result.efficiency;
      }
    });

    evaluatorMap.put("out", new Evaluator() {
      @Override
      public double eval(ReactorResult result) {
        return result.output + (Math.pow(result.efficiency, 1/4.0));
      }
    });

    evaluatorMap.put("outPerRod", new Evaluator() {
      @Override
      public double eval(ReactorResult result) {
        return result.output/result.numRods;
      }
    });

    evaluatorMap.put("both", new Evaluator() {
      @Override
      public double eval(ReactorResult result) {
        return result.output*result.efficiency;
      }
    });

    evaluatorMap.put("nim", new Evaluator() {
      @Override
      public double eval(ReactorResult result) {
        return result.output > 20000 ? result.efficiency : result.output/100;
      }
    });

    ExecutorService service = new ScheduledThreadPoolExecutor(10);
    System.out.println("Awaiting input...");
    Scanner input = new Scanner(System.in);
    while (input.hasNext()) {
      try {
        String config = input.nextLine();
        System.out.println(config);
        String[] parts = config.split(" ");
        int i = 0;
        String id = parts[i++];
        int x = Integer.parseInt(parts[i++]);
        int z = Integer.parseInt(parts[i++]);
        int y = Integer.parseInt(parts[i++]);
        boolean cooled = Boolean.parseBoolean(parts[i++]);
        String function = parts[i++];
        int gens = Integer.parseInt(parts[i++]);
        int pop = Integer.parseInt(parts[i++]);
        int ticks = Integer.parseInt(parts[i++]);
        if (evaluatorMap.containsKey(function)) {
          MyGeneticsRun run = new MyGeneticsRun(id, evaluatorMap.get(function), pop, gens, ticks, cooled, x, z, y);
          service.submit(run);
        } else {
          System.out.println("Bad function " + function);
        }
      }catch (Exception e){
        e.printStackTrace();
      }
    }



  }

  public static class MyGeneticsRun implements Runnable {

    private final String id;
    private final Evaluator function;
    int population;
    int generations;
    int ticks;
    boolean activelyCooled;
    int x;
    int z;
    int y;

    public MyGeneticsRun(String id, Evaluator function, int population, int generations, int ticks, boolean activelyCooled, int x, int z, int y) {
      this.id = id;
      this.function = function;
      this.population = population;
      this.generations = generations;
      this.ticks = ticks;
      this.activelyCooled = activelyCooled;
      this.x = x;
      this.z = z;
      this.y = y;
    }

    public void run() {
      ReactorGenetics genetics = new ReactorGenetics(z,z,y);
      ReactorGenome reactorGenome = genetics.breedGoodReactor(population, generations, activelyCooled, ticks, function);
      System.out.println(id);
      genetics.display(reactorGenome.reactor);
      System.out.println(reactorGenome.result);
    }
  }

  private static interface Evaluator{
    public double eval(ReactorResult result);
  }

  public void display(String s) {
    s = s.replaceAll(" ","");
    s = s.replaceAll("\n","");
    for (int i = 0; i < s.length(); i++) {
      if (i % (xSize - 2) == 0) {
        System.out.println();
      }
      System.out.print(s.charAt(i) + " ");
    }
    System.out.println();
  }

  private static class ReactorGenome implements Comparable<ReactorGenome> {

    private String reactor;
    private double fitness;
    private ReactorResult result;
    private static Random random = new Random();

    private ReactorGenome(String reactor) {
      this.reactor = reactor;
    }

    public ReactorGenome breed(ReactorGenome otherParent1, ReactorGenome otherParent2) {
      String result = "";
      for (int i = 0; i < reactor.length(); i++) {
        if (otherParent1.reactor.charAt(i) == otherParent2.reactor.charAt(i)) {
          result += otherParent1.reactor.charAt(i);
        } else {
          result += this.reactor.charAt(i);
        }
      }
      return new ReactorGenome(result);
    }

    public ReactorGenome mutate(int popSize) {
      String result = "";
      double chanceOfTotalShuffle = 1.0 / popSize;
      if (random.nextDouble() < chanceOfTotalShuffle) {
        result = shuffleAll(result);
      } else {
        result = mutateSlightly(result);
      }
      return new ReactorGenome(result);

    }

    public String mutateSlightly(String result) {
      double chance = 1.0 / reactor.length();
      for (int i = 0; i < reactor.length(); i++) {
        if (random.nextDouble() < chance) {
          result += weightedGenes.get(random.nextInt(weightedGenes.size()));
        } else {
          result += reactor.charAt(i);
        }
      }
      return result;
    }

    public String shuffleAll(String result) {
      for (int i = 0; i < reactor.length(); i++) {
        result += weightedGenes.get((weightedGenes.indexOf(reactor.charAt(i)) + 1) % weightedGenes.size());
      }
      return result;
    }

    @Override
    public int compareTo(ReactorGenome reactorGenome) {
      return Double.compare(this.fitness, reactorGenome.fitness);
    }

    @Override
    public String toString() {
      return "ReactorGenome{" +
          "reactor='" + reactor + '\'' +
          ", fitness=" + fitness +
          '}';
    }
  }
}
