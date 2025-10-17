package net.atobaazul.sulidae_utils.registries;

import com.george_vi.electroenergetics.CreateElecrtoEnergetics;
import com.george_vi.electroenergetics.content.connector.ConnectorDevice;
import com.george_vi.electroenergetics.simulation.SimulatedDevice;
import net.atobaazul.sulidae_utils.common.device.ElectrodeDevice;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

import static net.atobaazul.sulidae_utils.SulidaeUtils2.MOD_ID;

public class SulidaeSimulatedDevices {
    static Map<ResourceLocation, SimulatedDevice> BY_ID = new HashMap();

    public static final SimulatedDevice ELECTRODE = register(new ElectrodeDevice(ResourceLocation.fromNamespaceAndPath(MOD_ID, "electrode")));

    public SulidaeSimulatedDevices() {
    }

    public static SimulatedDevice register(SimulatedDevice device) {
        BY_ID.put(device.getID(), device);
        return device;
    }

    public static SimulatedDevice get(ResourceLocation id) {
        return (SimulatedDevice)BY_ID.get(id);
    }
}
