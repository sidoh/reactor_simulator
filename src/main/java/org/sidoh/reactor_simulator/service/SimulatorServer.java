package org.sidoh.reactor_simulator.service;

import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.sidoh.reactor_simulator.thrift.SimulatorService;
import org.sidoh.reactor_simulator.thrift.SimulatorService.Processor;

public class SimulatorServer {
  public static final int DEFAULT_PORT = 3141;

  private final TNonblockingServerSocket transport;
  private final THsHaServer server;

  public SimulatorServer(int port) throws TTransportException {
    transport = new TNonblockingServerSocket(port);
    final Processor<SimulatorService.Iface> processor = new Processor<SimulatorService.Iface>(new SimulatorServiceHandler());
    server = new THsHaServer(new THsHaServer.Args(transport).processor(processor));
  }

  public void start() {
    server.serve();
  }
}
