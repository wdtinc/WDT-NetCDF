package com.wdtinc.wdtnetcdfexample;

import java.util.List;
import ucar.ma2.ArrayFloat;
import ucar.nc2.Dimension;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

public class Main {

    public static void main(String args[]) throws Exception {

        String filename = "turb.nc";
        NetcdfFile dataFile = NetcdfFile.open(filename, null);

        // print out the file structure
        System.out.println(dataFile.toString());

        // read the lat/lon and temperature variables
        Variable lats = dataFile.findVariable("latitude");
        Variable lons = dataFile.findVariable("longitude");
        Variable turbs = dataFile.findVariable("turb");
        Variable edrs = dataFile.findVariable("edr");

        // Read the units for the temperature variable
        String turbUnits = turbs.findAttributeIgnoreCase("units").getStringValue();
        System.out.println("units: " + turbUnits);

        String edrUnits = edrs.findAttributeIgnoreCase("units").getStringValue();
        System.out.println("units: " + edrUnits);

        // Read the valid time for this data
        Variable time = dataFile.findVariable("time");
        System.out.println("data valid time: " + time.findAttributeIgnoreCase("string").getStringValue());

        Variable forecastTime = dataFile.findVariable("forecast_reference_time");
        System.out.println("data valid time: " + forecastTime.findAttributeIgnoreCase("string").getStringValue());

        // dimensions of the lat/lon array
        List<Dimension> dimensions = dataFile.getDimensions();
        int lonLength = dimensions.get(0).getLength();
        int latLength = dimensions.get(1).getLength();

        System.out.println("lon length: " + lonLength);
        System.out.println("lat length: " + latLength);

        // Read the latitude and longitude coordinate variables into arrays
        ArrayFloat.D2 latArray = (ArrayFloat.D2) lats.read();
        ArrayFloat.D2 lonArray = (ArrayFloat.D2) lons.read();
        
        // Read the turbulence & edr data
        ArrayFloat.D2 turbArray = (ArrayFloat.D2) turbs.read();
        ArrayFloat.D2 edrArray = (ArrayFloat.D2) edrs.read();

        for (int lat = 0; lat < latLength; lat++) {
            for (int lon = 0; lon < lonLength; lon++) {
                System.out.print(latArray.get(lat, lon) + ",");
                System.out.print(lonArray.get(lat, lon) + ",");
                System.out.print(turbArray.get(lat, lon) + ",");
                System.out.println(edrArray.get(lat, lon));
            }
        }
    }
}
