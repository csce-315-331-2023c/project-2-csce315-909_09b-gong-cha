package com.example.gongchapos;
public class Main {
    
    /**
     * This represents the main startup of our application
     * 
     * @author Reid Jenkins
     * @param args - The args given for main
     */
    public static void main(String[] args)
    {
        //Pass in NetID and Password as command line arguments
        if(args.length != 2) 
        {
            System.out.println("Error: Must supply NetID and Password as command line arguments");
            System.exit(0);
        }
        String netID = args[0];
        String password = args[1];
        System.out.println("NetID: " + netID + "\nPassword: " + password);

        Application app = new Application();
        app.run(netID, password);

    }
}
