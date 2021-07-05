package org.fog.test.perfeval;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.power.PowerHost;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;
import org.cloudbus.cloudsim.sdn.overbooking.BwProvisionerOverbooking;
import org.cloudbus.cloudsim.sdn.overbooking.PeProvisionerOverbooking;
import org.fog.application.AppEdge;
import org.fog.application.AppLoop;
import org.fog.application.Application;
import org.fog.application.selectivity.FractionalSelectivity;
import org.fog.entities.*;
import org.fog.placement.Controller;
import org.fog.placement.ModuleMapping;
import org.fog.placement.ModulePlacementEdgewards;
import org.fog.policy.AppModuleAllocationPolicy;
import org.fog.scheduler.StreamOperatorScheduler;
import org.fog.utils.FogLinearPowerModel;
import org.fog.utils.FogUtils;
import org.fog.utils.TimeKeeper;
import org.fog.utils.distribution.DeterministicDistribution;

import java.util.*;

public class Sample {
    static int numOfFogDevices = 5;
    static int numOfClientsPerFogDevice = 2;
    static List<FogDevice> fogDevices = new ArrayList<>();
    static Map<String, Integer> getIdByName = new HashMap<>();
    static List<Sensor> sensors = new ArrayList<>();
    static List<Actuator> actuators = new ArrayList<>();

    private static FogDevice createAFogDevice(String nodeName, long mips,
                                              int ram, long upBw, long downBw, int level, double ratePerMips, double busyPower, double idlePower) {
        List<Pe> peList = new ArrayList<>();
        peList.add(new Pe(0, new PeProvisionerOverbooking(mips)));
        int hostId = FogUtils.generateEntityId();
        long storage = 1000000;
        int bw = 10000;
        PowerHost host = new PowerHost(hostId, new RamProvisionerSimple(ram), new BwProvisionerOverbooking(bw), storage, peList, new StreamOperatorScheduler(peList), new FogLinearPowerModel(busyPower,
                idlePower));
        List<Host> hostList = new ArrayList<>();
        hostList.add(host);
        String arch = "x86";
        String os = "Linux";
        String vmm = "Xen";
        double time_zone = 10.0;
        double cost = 3.0;
        double costPerMem = 0.05;
        double costPerStorage = 0.001;
        double costPerBw = 0.0;
        LinkedList<Storage> storageList = new LinkedList<>();
        FogDeviceCharacteristics characteristics = new FogDeviceCharacteristics(arch, os, vmm, host, time_zone, cost,
                costPerMem, costPerStorage, costPerBw);
        FogDevice fogdevice = null;
        try {
            fogdevice = new FogDevice(nodeName, characteristics,
                    new AppModuleAllocationPolicy(hostList), storageList, 10, upBw, downBw, 0, ratePerMips);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert fogdevice != null;
        fogdevice.setLevel(level);
        return fogdevice;
    }
    //10-scheduling interval
    //0-latency (delay)

    @SuppressWarnings("serial")
	private static Application createApplication(String appId, int brokerId) {
        Application application = Application.createApplication(appId, brokerId);
        application.addAppModule("ClientModule", 16);
        application.addAppModule("MainModule", 120);
        application.addAppModule("StorageModule", 100);

        application.addAppEdge("Sensor", "ClientModule", 4500, 8800,
                "Sensor", Tuple.UP, AppEdge.SENSOR);
        application.addAppEdge("ClientModule", "MainModule", 3000,
                1000, "PreProcessedData", Tuple.UP, AppEdge.MODULE);
        application.addAppEdge("ClientModule", "StorageModule", 2500,
                8800, "StoreData", Tuple.UP, AppEdge.MODULE);
        application.addAppEdge("MainModule", "ClientModule", 2000,
                1000, "ProcessedData", Tuple.DOWN, AppEdge.MODULE);
        application.addAppEdge("ClientModule", "Actuators", 6000, 1000,
                "OutputData", Tuple.DOWN, AppEdge.ACTUATOR);
                
        //(source, destination, tupleCPUlength, tupleNWlength, tupleType, direction, edgeType)

        application.addTupleMapping("ClientModule", "Sensor", "PreProcessedData", new FractionalSelectivity(1.0));
        application.addTupleMapping("ClientModule", "Sensor", "StoreData", new FractionalSelectivity(1.0));
        application.addTupleMapping("MainModule", "PreProcessedData",
                "ProcessedData", new FractionalSelectivity(1.0));
        application.addTupleMapping("ClientModule", "ProcessedData",
                "OutputData", new FractionalSelectivity(1.0));

        //(moduleName, inputTupleType, outputTupleType, selectivity model)

        final AppLoop loop1 = new AppLoop(new ArrayList<>() {{
            add("ClientModule");
            add("StorageModule");
        }});
        final AppLoop loop2 = new AppLoop(new ArrayList<>() {{
            add("ClientModule");
            add("MainModule");
            add("ClientModule");
        }});
        final AppLoop loop3 = new AppLoop(new ArrayList<>() {{
            add("ClientModule");
            add("MainModule");
        }});
        final AppLoop loop4 = new AppLoop(new ArrayList<>() {{
            add("MainModule");
            add("ClientModule");
        }});
        List<AppLoop> loops = new ArrayList<>() {{
            add(loop1);
            add(loop2);
            add(loop3);
            add(loop4);
        }};
        application.setLoops(loops);
        return application;
    }

    private static FogDevice addLowLevelFogDevice(String id, int brokerId, String appId, int parentId) {
        FogDevice lowLevelFogDevice = createAFogDevice("LowLevelFogDevice-" + id, 1000, 1000, 10000, 270, 2, 0, 87.53, 82.44);
        lowLevelFogDevice.setParentId(parentId);
        getIdByName.put(lowLevelFogDevice.getName(), lowLevelFogDevice.getId());
        Sensor sensor = new Sensor("s-" + id, "Sensor", brokerId, appId, new DeterministicDistribution(getValue(5.00)));
        sensors.add(sensor);
        Actuator actuator = new Actuator("a-" + id, brokerId, appId,
                "Actuators");
        actuators.add(actuator);
        sensor.setGatewayDeviceId(lowLevelFogDevice.getId());
        sensor.setLatency(10.0);
        actuator.setGatewayDeviceId(lowLevelFogDevice.getId());
        actuator.setLatency(1.0);
        return lowLevelFogDevice;
    }

    private static double getValue(double min) {
        Random rn = new Random();
        return rn.nextDouble() * 10 + min;
    }

    private static void createFogDevices(int brokerId, String appId) {
        FogDevice cloud = createAFogDevice("cloud", 1000000, 64000, 10000,
                10000, 0, 0.01, 16 * 103, 16 * 83.25);
        cloud.setParentId(-1);
        fogDevices.add(cloud);
        getIdByName.put(cloud.getName(), cloud.getId());
        for (int i = 0; i < numOfFogDevices; i++) {
            FogDevice device = createAFogDevice("FogDevice-" + i, 1000, 2000,
                    1000, 1000, 1, 0.01,
                    100, 70);
            device.setParentId(cloud.getId());
            device.setUplinkLatency(20);
            for (int j = 0; j < numOfClientsPerFogDevice; j++) {
                String clientId = i + "-" + j;
                FogDevice client = addLowLevelFogDevice(clientId, brokerId, appId, device.getId());
                client.setUplinkLatency(3);
                fogDevices.add(client);
            }
            fogDevices.add(device);
            getIdByName.put(device.getName(), device.getId());
        }
    }

    public static void main(String[] args) {

        Log.printLine("Starting Sample...");

        try {
            //Log.disable();
            int num_user = 4; // number of cloud users
            Calendar calendar = Calendar.getInstance();

            CloudSim.init(num_user, calendar, false);

            String appId = "sample"; // identifier of the application
            FogBroker broker = new FogBroker("broker");

            Application application = createApplication(appId, broker.getId());
            application.setUserId(broker.getId());

            createFogDevices(broker.getId(), appId);

            ModuleMapping moduleMapping = ModuleMapping.createModuleMapping(); // initializing a module mapping
            moduleMapping.addModuleToDevice("MainModule", "cloud");
            moduleMapping.addModuleToDevice("StorageModule", "cloud");
            for (FogDevice device : fogDevices) {
                if (device.getName().startsWith("FogDevice")) {
                    moduleMapping.addModuleToDevice("ClientModule", device.getName());
                }
            }

            Controller controller = new Controller("master-controller", fogDevices, sensors,
                    actuators);

            controller.submitApplication(application, 0,
                    new ModulePlacementEdgewards(fogDevices, sensors, actuators, application, moduleMapping));

            TimeKeeper.getInstance().setSimulationStartTime(Calendar.getInstance().getTimeInMillis());

            CloudSim.startSimulation();

            CloudSim.stopSimulation();

            Log.printLine("Sample finished!");
        } catch (Exception e) {
            e.printStackTrace();
            Log.printLine("Unwanted errors happen");
        }
    }

}