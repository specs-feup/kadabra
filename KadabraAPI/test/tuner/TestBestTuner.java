package tuner;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import autotuner.algorithm.Algorithm;
import autotuner.algorithm.SimpleAlgorithm;
import autotuner.algorithm.provider.AlgorithmListProvider;
import autotuner.algorithm.provider.AlgorithmProvider;
import autotuner.configs.factory.ConfigFactory;
import autotuner.manager.ConfigProvider;
import autotuner.manager.ExplorationSupervisor;
import autotuner.measurer.AvgLongMeasurer;

public class TestBestTuner extends ExplorationSupervisor<Integer, Void, Long> {
    private AlgorithmListProvider<Void> algListProvider;

    public TestBestTuner(int warmup, int samples) {
        super(warmup, samples);
        initAlgProvider();
    }

    private void initAlgProvider() {
        algListProvider = new AlgorithmListProvider<>();
    }

    @Override
    protected Algorithm<Void> defaultAlgorithm() {
        return new SimpleAlgorithm<>(null, "original");
    }

    @Override
    protected Supplier<AvgLongMeasurer> measurerProvider() {
        return AvgLongMeasurer.asSupplier();
    }

    @Override
    protected ConfigProvider<Void> configurationProvider() {
        return ConfigFactory::normal;
    }

    @Override
    protected BiFunction<Integer, Integer, Double> distanceProvider() {
        return null;
    }

    @Override
    protected List<AlgorithmProvider<Void>> getAlgorithms() {
        return algListProvider.build();
    }
}
