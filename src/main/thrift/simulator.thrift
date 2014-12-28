namespace java org.sidoh.reactor_simulator.thrift

struct ReactorDefinition {
  1: i32 xSize;
  2: i32 zSize;
  3: i32 height;
  4: string layout;
  5: bool isActivelyCooled;
}

struct ReactorResult {
  1: double efficiency;
  2: double output;
  3: double fuelFertility;
  4: double coolantTemperature;
  5: double fuelHeat;
  6: double reactorHeat;
  7: ReactorDefinition reactorDefinition;
}

service SimulatorService {
  ReactorResult simulateReactor(1: ReactorDefinition definition)
}
