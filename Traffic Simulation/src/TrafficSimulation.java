/**
 * Simulates traffic with roads, traffic lights, and different types of vehicles
 *
 * @author Casey Hild
 * @version 1
 */
import java.util.*;
public class TrafficSimulation
{   
    /**
     * This is the main function
     * 
     * @param args input arguments
     */
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("Vehicle arrival probability (1:n) (recommend 10): ");
        int vehicleProbability = sc.nextInt();
        System.out.print("Green light duration NS (recommend 500): ");
        int lightDurationNS = sc.nextInt();
        System.out.print("Green light duration EW (recommend 500): ");
        int lightDurationEW = sc.nextInt();
        System.out.print("Simulation duration (recommend 10000): ");
        int simulationDuration = sc.nextInt();
        System.out.println();

        Road northRoad = new Road(20, 39, 40, "N");
        VehicleQueue northQueue = new VehicleQueue(northRoad.getPlace(0), "N", vehicleProbability);

        Road southRoad = new Road(19, 0, 40, "S");
        VehicleQueue southQueue = new VehicleQueue(southRoad.getPlace(0), "S", vehicleProbability);

        Road eastRoad = new Road(0, 20, 40, "E");
        VehicleQueue eastQueue = new VehicleQueue(eastRoad.getPlace(0), "E", vehicleProbability);

        Road westRoad = new Road(39, 19, 40, "W");
        VehicleQueue westQueue = new VehicleQueue(westRoad.getPlace(0), "W", vehicleProbability);

        TrafficLight light = new TrafficLight(northRoad.getPlace(19), eastRoad.getPlace(19), southRoad.getPlace(19), westRoad.getPlace(19), lightDurationNS, lightDurationEW);

        GUI gui = new GUI();

        long timer = 0;
        gui.updateTraffic(northRoad, southRoad, eastRoad, westRoad, northQueue, southQueue, eastQueue, westQueue, light);
        int framesPerUpdate = 10000000; // only update the traffic light and vehicles every 10,000,000 frames, so we can visually see what is going on
        long simulationLength = (long) simulationDuration * framesPerUpdate;
        while(timer < simulationLength)
        {
            if(timer % framesPerUpdate == 0)
            {
                northQueue.update();
                southQueue.update();
                eastQueue.update();
                westQueue.update();
                light.update();
                gui.updateTraffic(northRoad, southRoad, eastRoad, westRoad, northQueue, southQueue, eastQueue, westQueue, light);
                if(timer != 0 && timer % (simulationLength / 25) == 0)
                {
                    int totalVehicles = northQueue.numberOfVehicles() + southQueue.numberOfVehicles() + eastQueue.numberOfVehicles() + westQueue.numberOfVehicles();
                    double totalTimeOnRoad = northQueue.totalTimeOnRoad() + southQueue.totalTimeOnRoad() + eastQueue.totalTimeOnRoad() + westQueue.totalTimeOnRoad();
                    double totalWaitingTime = northQueue.totalWaitingTime() + southQueue.totalWaitingTime() + eastQueue.totalWaitingTime() + westQueue.totalWaitingTime();
                    double totalDistanceTraveled = northQueue.totalDistanceTraveled() + southQueue.totalDistanceTraveled() + eastQueue.totalDistanceTraveled() + westQueue.totalDistanceTraveled();
                    double combinedSpeed = northQueue.combinedSpeed() + southQueue.combinedSpeed() + eastQueue.combinedSpeed() + westQueue.combinedSpeed();
                    double totalWeight = northQueue.totalWeight() + southQueue.totalWeight() + eastQueue.totalWeight() + westQueue.totalWeight();
                    double averageTimeOnRoad = totalTimeOnRoad / totalVehicles;
                    double averageWaitingTime = totalWaitingTime / totalVehicles;
                    double averageDistanceTraveled = totalDistanceTraveled / totalVehicles;
                    double averageSpeed = combinedSpeed / totalVehicles;
                    double averageWeight = totalWeight / totalVehicles;
                    System.out.printf("Average time on road for all vehicles currently on the road: %.2f%n", averageTimeOnRoad);
                    System.out.printf("Average waiting time for all vehicles currently on the road: %.2f%n", averageWaitingTime);
                    System.out.printf("Average distance traveled for all vehicles currently on the road: %.2f%n", averageDistanceTraveled);
                    System.out.printf("Average speed for all vehicles currently on the road: %.2f%n", averageSpeed);
                    System.out.printf("Average weight for all vehicles currently on the road: %.2f%n%n", averageWeight);
                }
            }
            timer++;
        }
    }
}