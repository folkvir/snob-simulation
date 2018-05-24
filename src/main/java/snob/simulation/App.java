package snob.simulation;

import peersim.Simulator;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        String[] arguments = {"./configs/config-example1.txt"};
        System.out.println( "Snob simulation loading..." );
        Simulator.main(arguments);
    }
}
