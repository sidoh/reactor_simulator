package org.sidoh.reactor_simulator.simulator;

public class ReallyBigReactor {
  public static IFakeReactorWorld make() {

    FakeReactorWorld world = new FakeReactorWorld(32,48,32);

    for (int x = 1; x < 31; x++) {
      for (int z = 1; z < 31; z++) {
        if(x >= 5 && z >=5 && x<= 25 && z <= 25) { //keep 4 deep layer of cryo
          int every = 2;
          int modulus = 1;
          if(x% every == modulus && z % every == modulus){
            world.makeControlRod(x,z);
          }else if(x% every == modulus || z% every == modulus){
           // world.makeCoolantColumn(x,z,"block:blockGraphite");
            world.makeCoolantColumn(x,z,"fluid:cryotheum");

          }else{
            world.makeCoolantColumn(x,z,"fluid:cryotheum");
          }
        }else{
          world.makeCoolantColumn(x,z,"fluid:cryotheum");
        }
      }
    }
    System.out.println(world.display());
    return world;
  }


}
